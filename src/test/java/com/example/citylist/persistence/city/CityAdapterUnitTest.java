package com.example.citylist.persistence.city;

import static com.example.citylist.domain.model.city.CityBuilder.*;
import static com.example.citylist.domain.model.city.filter.CityRequestFilterBuilder.cityRequestFilterBuilderWithCreatedFromNowAndCityName;
import static com.example.citylist.persistence.city.CityEntityBuilder.testCityEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.example.citylist.domain.model.city.City;
import com.example.citylist.domain.model.city.filter.CityRequestFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CityAdapterUnitTest {

	@InjectMocks
	private CityAdapter cityAdapter;

	@Mock
	private CityEntityRepository cityEntityRepository;

	@Mock
	private CityEntityConverter cityEntityConverter;

	@Captor
	private ArgumentCaptor<CityEntity> cityEntityArgumentCaptor;

	@Captor
	private ArgumentCaptor<List<CityEntity>> cityEntitiesArgumentCaptor;

	@Test
	void shouldFindById() {

		// given
		final Long id = 1L;
		final City city = testCity().build();
		final CityEntity cityEntity = testCityEntity().id(1L).build();
		given(cityEntityRepository.findById(id)).willReturn(Optional.ofNullable(cityEntity));
		given(cityEntityConverter.convertToDomain(cityEntity)).willReturn(city);

		// when
		final City actual = cityAdapter.findById(id);

		// then
		verify(cityEntityRepository).findById(id);
		verify(cityEntityConverter).convertToDomain(cityEntity);
		assertThat(actual).usingRecursiveComparison().isEqualTo(city);
	}

	@Test
	void shouldUpdateCity() {
		// given
		final Long id = 1L;
		final City cityWithNewName = testCityWithNewName().build();
		final CityEntity cityEntity = testCityEntity().id(id).build();
		given(cityEntityRepository.getReferenceById(cityWithNewName.getId())).willReturn(cityEntity);
		given(cityEntityRepository.save(any())).willReturn(cityEntity);

		// when
		cityAdapter.update(cityWithNewName);

		// then
		verify(cityEntityRepository).save(cityEntityArgumentCaptor.capture());
		assertThat(cityEntityArgumentCaptor.getValue()).usingRecursiveComparison()
				.ignoringFields("id", "imageURL", "createdAt", "createdBy", "modifiedAt", "modifiedBy")
				.isEqualTo(cityWithNewName);
	}

	@Test
	void shouldReturnCities() {
		// given
		final City city = testCity().build();
		final PageRequest pageRequest = PageRequest.of(1, 3);
		final CityRequestFilter cityRequestFilter = cityRequestFilterBuilderWithCreatedFromNowAndCityName().build();
		given(cityEntityRepository.findCities(cityRequestFilter.getCreatedFrom(), cityRequestFilter.getCityName(),
				pageRequest)).willReturn(new PageImpl<>(List.of(city), pageRequest, 1));

		// when
		final Page<City> actual = cityAdapter.getAll(pageRequest, cityRequestFilter);

		// then
		verify(cityEntityRepository).findCities(cityRequestFilter.getCreatedFrom(), cityRequestFilter.getCityName(),
				PageRequest.of(1, 3));
		assertThat(actual.getContent()).containsExactly(city);
	}

	@Test
	void shouldSaveCities() {
		// given
		final Long id = 1L;
		final City city = testCity().id(id).build();
		final CityEntity cityEntity = testCityEntity().id(id).build();
		final List<CityEntity> cityEntities = List.of(cityEntity);
		final List<City> cities = List.of(city);

		given(cityEntityConverter.convertToEntity(city)).willReturn(cityEntity);
		given(cityEntityRepository.saveAll(cityEntities)).willReturn(cityEntities);

		// when
		cityAdapter.saveAll(cities);

		// then
		verify(cityEntityConverter).convertToEntity(city);
		verify(cityEntityRepository).saveAll(cityEntitiesArgumentCaptor.capture());
		assertThat(cityEntitiesArgumentCaptor.getValue()).containsExactly(cityEntities.get(0));
	}
}
