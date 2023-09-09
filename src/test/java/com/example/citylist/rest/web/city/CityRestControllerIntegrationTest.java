package com.example.citylist.rest.web.city;

import com.example.citylist.domain.model.city.City;
import com.example.citylist.domain.model.city.filter.CityRequestFilter;
import com.example.citylist.domain.service.CityService;
import com.example.citylist.domain.service.DateService;
import com.example.citylist.rest.web.city.converter.CityResponseResourceConverter;
import com.example.citylist.rest.web.city.resource.CityResponseResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.example.citylist.domain.model.city.CityBuilder.testAlternativeCity;
import static com.example.citylist.domain.model.city.CityBuilder.testCity;
import static com.example.citylist.domain.model.city.filter.CityRequestFilterBuilder.cityRequestFilterBuilderWithNoFilter;
import static com.example.citylist.rest.web.city.resource.CityResponseResourceBuilder.designAlternativeCityResponseResource;
import static com.example.citylist.rest.web.city.resource.CityResponseResourceBuilder.designCityResponseResource;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CityRestController.class)
@WithMockUser
public class CityRestControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CityService cityService;

	@MockBean
	private DateService dateService;

	@MockBean
	private CityResponseResourceConverter cityResponseResourceConverter;

	@Test
	void shouldGetCitiesWithDefaultPagination() throws Exception {
		// given
		final int firstPage = 0;
		final int secondPage = 1;
		final int pageSize = 1;
		final City city1 = testCity().id(1L).build();
		final City city2 = testAlternativeCity().id(2L).build();
		final CityResponseResource cityResponseResource1 = designCityResponseResource().id(1L).build();
		final CityResponseResource cityResponseResource2 = designAlternativeCityResponseResource().id(2L).build();

		final CityRequestFilter cityRequestFilter = cityRequestFilterBuilderWithNoFilter().build();
		given(dateService.convertStringToInstant(null)).willReturn(null);
		given(cityService.getAll(PageRequest.of(firstPage, pageSize), cityRequestFilter))
				.willReturn(new PageImpl<>(List.of(city1), PageRequest.of(firstPage, pageSize), 2));
		given(cityService.getAll(PageRequest.of(secondPage, pageSize), cityRequestFilter))
				.willReturn(new PageImpl<>(List.of(city2), PageRequest.of(secondPage, pageSize), 2));
		given(cityResponseResourceConverter.convertToResource(city1)).willReturn(cityResponseResource1);
		given(cityResponseResourceConverter.convertToResource(city2)).willReturn(cityResponseResource2);

		// when
		final ResultActions perform1 = mockMvc.perform(
				get("/api/public/cities?" + "page=" + firstPage + "&size=" + pageSize).accept(APPLICATION_JSON));
		final ResultActions perform2 = mockMvc.perform(
				get("/api/public/cities?" + "page=" + secondPage + "&size=" + pageSize).accept(APPLICATION_JSON));

		// then
		verify(dateService, times(2)).convertStringToInstant(null);
		verify(cityService).getAll(PageRequest.of(firstPage, pageSize), cityRequestFilter);
		verify(cityService).getAll(PageRequest.of(secondPage, pageSize), cityRequestFilter);
		verify(cityResponseResourceConverter).convertToResource(city1);
		verify(cityResponseResourceConverter).convertToResource(city2);
		perform1.andExpect(status().isOk()).andExpect(jsonPath("$.number", is(firstPage)))
				.andExpect(jsonPath("$.size", is(pageSize))).andExpect(jsonPath("$.totalElements", is(2)))
				.andExpect(jsonPath("$.totalPages", is(2))).andExpect(jsonPath("$.content[0].id", is(1)))
				.andExpect(jsonPath("$.content[0].cityName", is("Tallinn")));
		perform2.andExpect(status().isOk()).andExpect(jsonPath("$.number", is(secondPage)))
				.andExpect(jsonPath("$.size", is(pageSize))).andExpect(jsonPath("$.totalElements", is(2)))
				.andExpect(jsonPath("$.totalPages", is(2))).andExpect(jsonPath("$.content[0].id", is(2)))
				.andExpect(jsonPath("$.content[0].cityName", is("Parnu")));
	}
}
