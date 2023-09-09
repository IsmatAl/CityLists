package com.example.citylist.domain.service;

import com.example.citylist.domain.model.city.City;
import com.example.citylist.domain.model.city.filter.CityRequestFilter;
import com.example.citylist.domain.validation.CityValidator;
import com.example.citylist.persistence.city.CityAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.example.citylist.domain.model.city.CityBuilder.testCity;
import static com.example.citylist.domain.model.city.filter.CityRequestFilterBuilder.cityRequestFilterBuilderWithCreatedFromNow;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CityServiceUnitTest {

	@InjectMocks
	private CityService cityService;

	@Mock
	private CityAdapter cityAdapter;

	@Mock
	private CityValidator cityValidator;

	@Test
	void shouldFindCityById() {
		// given
		final City city = testCity().id(1L).build();
		final Long id = city.getId();
		given(cityAdapter.findById(city.getId())).willReturn(city);

		// when
		final City actual = cityService.findById(id);

		// then
		verify(cityAdapter).findById(id);
		assertThat(actual).usingRecursiveComparison().isEqualTo(city);
	}

	@Test
	void shouldReturnNullIfCityWithGivenIdDoesNotExist() {
		// given
		final Long nonExistingId = 2L;
		given(cityAdapter.findById(nonExistingId)).willReturn(null);

		// when
		final City actual = cityService.findById(nonExistingId);

		// then
		verify(cityAdapter).findById(nonExistingId);
		assertThat(actual).isNull();
	}

	@Test
	void shouldGetCities() {
		// given
		final City city = testCity().build();
		final PageRequest pageRequest = PageRequest.of(1, 3);
		final CityRequestFilter cityRequestFilter = cityRequestFilterBuilderWithCreatedFromNow().build();
		given(cityAdapter.getAll(pageRequest, cityRequestFilter)).willReturn(new PageImpl<>(List.of(city)));

		// when
		final Page<City> actual = cityService.getAll(pageRequest, cityRequestFilter);

		// then
		verify(cityAdapter).getAll(pageRequest, cityRequestFilter);
		assertThat(actual.getContent()).containsExactly(city);
	}

	@Test
	void shouldAllowAdminToUpdateCity() {
		// given
		final Long cityId = 1L;
		final City city = testCity().id(cityId).build();
		doNothing().when(cityAdapter).update(city);

		// when
		cityService.update(city);

		// then
		verify(cityValidator).validateCityExists(cityId);
		verify(cityAdapter).update(city);
	}
}
