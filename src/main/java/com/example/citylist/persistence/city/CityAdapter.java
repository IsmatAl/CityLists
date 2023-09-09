package com.example.citylist.persistence.city;

import com.example.citylist.domain.model.city.City;
import com.example.citylist.domain.model.city.filter.CityRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityAdapter {

	private final CityEntityRepository cityEntityRepository;
	private final CityEntityConverter cityEntityConverter;

	@Transactional(readOnly = true)
	public City findById(final Long id) {
		final CityEntity cityEntity = cityEntityRepository.findById(id).orElse(null);
		return cityEntityConverter.convertToDomain(cityEntity);
	}

	@Transactional
	public void update(final City city) {
		final CityEntity build = cityEntityRepository.getReferenceById(city.getId()).toBuilder()
				.cityName(city.getCityName()).imageURL(city.getImageURL()).build();
		cityEntityRepository.save(build);
	}

	@Transactional
	public void saveAll(final List<City> cities) {
		List<CityEntity> cityEntities = cities.stream().map(cityEntityConverter::convertToEntity).toList();
		cityEntityRepository.saveAll(cityEntities);
	}

	@Transactional(readOnly = true)
	public Page<City> getAll(final PageRequest pageRequest, final CityRequestFilter cityRequestFilter) {
		final Instant createdFrom = cityRequestFilter.getCreatedFrom();
		final String cityName = cityRequestFilter.getCityName();
		return cityEntityRepository.findCities(createdFrom, cityName, pageRequest);
	}
}
