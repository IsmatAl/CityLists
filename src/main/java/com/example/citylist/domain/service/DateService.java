package com.example.citylist.domain.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
public class DateService {

	public Instant convertStringToInstant(final String date) {
		return date == null
				? null
				: LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay()
						.toInstant(ZoneOffset.UTC);
	}

}
