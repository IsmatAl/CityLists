package com.example.citylist.domain.model.city;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class City {
	Long id;
	String cityName;
	String imageURL;
}
