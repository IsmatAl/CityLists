package com.example.citylist.rest.admin;

import com.example.citylist.domain.model.city.City;
import com.example.citylist.domain.service.CityService;
import com.example.citylist.rest.admin.converter.CityRequestResourceConverter;
import com.example.citylist.rest.admin.resource.CityRequestResource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/cities")
@RequiredArgsConstructor
public class AdminCityRestController {
	private final CityService cityService;
	private final CityRequestResourceConverter cityResourceConverter;

	@PutMapping("/{id}")
	public void updateCity(@PathVariable final Long id, @Valid @RequestBody final CityRequestResource cityResource) {
		final City city = cityResourceConverter.convertToDomain(cityResource);
		city.setId(id);
		cityService.update(city);
	}
}
