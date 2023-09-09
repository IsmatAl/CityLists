package com.example.citylist.rest.admin;

import com.example.citylist.domain.model.city.City;
import com.example.citylist.domain.service.CityService;
import com.example.citylist.rest.admin.converter.CityRequestResourceConverter;
import com.example.citylist.rest.admin.resource.CityRequestResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.citylist.domain.model.city.CityBuilder.testCity;
import static com.example.citylist.rest.admin.resource.CityRequestResourceBuilder.designCityRequestResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AdminCityRestControllerIntegrationTest {

	@MockBean
	private CityRequestResourceConverter cityRequestResourceConverter;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CityService cityService;

	@Autowired
	private ObjectMapper objectMapper;

	@Captor
	private ArgumentCaptor<City> cityArgumentCaptor;

	@Test
	@WithMockUser(roles = "ALLOW_EDIT")
	void shouldUpdateCity() throws Exception {
		// given
		final Long cityId = 1L;
		final CityRequestResource cityRequestResource = designCityRequestResource().build();

		final City city = testCity().build();
		given(cityRequestResourceConverter.convertToDomain(cityRequestResource)).willReturn(city);
		doNothing().when(cityService).update(city);

		// when
		final ResultActions perform = mockMvc.perform(put("/api/admin/cities/" + cityId).with(csrf())
				.content(objectMapper.writeValueAsString(cityRequestResource)).contentType(APPLICATION_JSON)
				.accept(APPLICATION_JSON));

		// then
		verify(cityRequestResourceConverter).convertToDomain(cityRequestResource);
		verify(cityService).update(cityArgumentCaptor.capture());
		assertThat(cityArgumentCaptor.getValue().getId()).isEqualTo(cityId);
	}
}
