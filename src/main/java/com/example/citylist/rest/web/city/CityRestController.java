package com.example.citylist.rest.web.city;

import com.example.citylist.domain.model.city.City;
import com.example.citylist.domain.model.city.filter.CityRequestFilter;
import com.example.citylist.domain.service.CityService;
import com.example.citylist.domain.service.DateService;
import com.example.citylist.rest.web.city.converter.CityResponseResourceConverter;
import com.example.citylist.rest.web.city.resource.CityResponseResource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/public/cities")
@RequiredArgsConstructor
public class CityRestController {

	private final DateService dateService;
	private final CityService cityService;
	private final CityResponseResourceConverter cityResourceConverter;

	@GetMapping
	public Page<CityResponseResource> getAll(@RequestParam(name = "page", defaultValue = "0") final Integer pageNumber,
			@RequestParam(name = "size", defaultValue = "10") final Integer pageSize,
			@RequestParam(name = "cityName", required = false) final String cityName,
			@RequestParam(name = "createdFrom", required = false) final String createdFrom) {
		final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
		final Instant createdFromInstant = dateService.convertStringToInstant(createdFrom);
		final CityRequestFilter cityRequestFilter = CityRequestFilter.builder().createdFrom(createdFromInstant)
				.cityName(cityName).build();
		final Page<City> projectProjectionsPage = cityService.getAll(pageRequest, cityRequestFilter);
		return projectProjectionsPage.map(cityResourceConverter::convertToResource);
	}
}
