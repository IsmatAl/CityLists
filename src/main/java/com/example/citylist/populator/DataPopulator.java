package com.example.citylist.populator;

import com.example.citylist.domain.model.city.City;
import com.example.citylist.persistence.city.CityAdapter;
import com.example.citylist.populator.helper.CustomKey;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static com.example.citylist.populator.helper.Helper.distinctByKeyClass;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataPopulator {
	private final CityAdapter cityAdapter;

	@EventListener(ApplicationReadyEvent.class)
	public void populateAllData() throws IOException, CsvException {
		insertCity();
		log.info("Cities are inserted for populate database");
	}

	private List<City> loadDataFromCSV(String fileName) throws IOException, CsvException {
		final ClassPathResource lastNamesFile = new ClassPathResource(fileName);

		try (final CSVReaderHeaderAware csvFileReader = new CSVReaderHeaderAware(
				new BufferedReader(new InputStreamReader(lastNamesFile.getInputStream())))) {
			final List<String[]> allRows = csvFileReader.readAll();
			return allRows.stream().map(v -> City.builder().cityName(v[1]).imageURL(v[2]).build())
					.filter(distinctByKeyClass(CustomKey::new)).toList();
		}
	}

	private void insertCity() throws IOException, CsvException {
		cityAdapter.saveAll(loadDataFromCSV("data/cities.csv"));
	}
}
