package com.example.citylist.persistence.city;

import com.example.citylist.domain.model.city.City;
import com.example.citylist.persistence.DataJpaTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import javax.validation.ConstraintViolationException;
import java.time.Instant;

import static com.example.citylist.persistence.city.CityEntityBuilder.testCityEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = DataJpaTestConfiguration.class)
public class CityEntityRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private CityEntityRepository cityEntityRepository;

	@Test
	void shouldSaveCityIfFieldsAreValid() {
		// given
		final CityEntity cityEntity = entityManager.persistAndFlush(testCityEntity().build());
		final CityEntity savedCityEntity = cityEntityRepository.save(cityEntity);
		entityManager.flush();

		// when
		final CityEntity actual = cityEntityRepository.getReferenceById(savedCityEntity.getId());

		// then
		assertThat(actual).usingRecursiveComparison().ignoringFields("id", "createdAt", "modifiedAt", "modifiedBy")
				.isEqualTo(cityEntity);
		assertThat(actual.getId()).isEqualTo(savedCityEntity.getId());
		assertThat(actual.getCreatedAt()).isNotNull().isBeforeOrEqualTo(Instant.now());
		assertThat(actual.getCreatedBy()).isNotNull();
		assertThat(actual.getModifiedAt()).isNotNull().isBeforeOrEqualTo(Instant.now());
		assertThat(actual.getModifiedBy()).isNotNull();
	}

	@Test
	void shouldThrowExceptionIfCityWithNoName() {
		assertThatThrownBy(() -> {
			cityEntityRepository.save(testCityEntity().cityName(null).build());
			entityManager.flush();
		}).isInstanceOf(ConstraintViolationException.class).hasMessageContaining("City name is required");
	}

	@Test
	void shouldThrowExceptionIfCityWithNoImageURL() {
		assertThatThrownBy(() -> {
			cityEntityRepository.save(testCityEntity().imageURL(null).build());
			entityManager.flush();
		}).isInstanceOf(ConstraintViolationException.class).hasMessageContaining("Image URL is required");
	}

	@Test
	void shouldReturnCities() {
		// given
		final CityEntity cityEntity1 = cityEntityRepository.saveAndFlush(testCityEntity().build());

		// when
		final Page<City> actual = cityEntityRepository.findCities(null, null, PageRequest.of(0, 1));

		// then
		assertThat(actual.getContent()).hasSize(1);
		assertThat(actual.getContent()).extracting(City::getId).containsExactly(cityEntity1.getId());
		assertThat(actual.getContent()).extracting(City::getCityName).contains(cityEntity1.getCityName());
		assertThat(actual.getContent()).extracting(City::getImageURL).containsExactly(cityEntity1.getImageURL());
	}
}
