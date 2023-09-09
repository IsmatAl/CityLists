package com.example.citylist.persistence.city;

import com.example.citylist.domain.model.city.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface CityEntityRepository extends JpaRepository<CityEntity, Long> {

	@Query("SELECT new com.example.citylist.domain.model.city.City(c.id, c.cityName, c.imageURL) "
			+ "FROM CityEntity c WHERE (cast(:createdFrom as date) IS NULL OR c.createdAt >= :createdFrom ) "
			+ " AND (cast(:cityName as string) IS NULL OR lower(c.cityName) like lower(concat('%', :cityName,'%'))) "
			+ "ORDER BY c.createdAt DESC")
	Page<City> findCities(@Param("createdFrom") final Instant createdFrom, @Param("cityName") final String cityName,
			final Pageable pageable);
}
