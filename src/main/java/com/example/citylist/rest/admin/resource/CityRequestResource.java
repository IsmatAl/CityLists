package com.example.citylist.rest.admin.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CityRequestResource {

	@NotNull(message = "City name is required")
	String cityName;

	@NotNull(message = "Image URL is required")
	@Size(max = 1024, message = "Image URL length is 1024 characters")
	String imageURL;
}
