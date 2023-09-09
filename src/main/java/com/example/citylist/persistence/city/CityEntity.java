package com.example.citylist.persistence.city;

import com.example.citylist.persistence.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "CITY")
@SequenceGenerator(name = CityEntity.SEQUENCE_GENERATOR_NAME, sequenceName = "SEQ_CITY", allocationSize = 1)
public class CityEntity extends Auditable {

	public static final String SEQUENCE_GENERATOR_NAME = "citySequenceGenerator";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_GENERATOR_NAME)
	@Column(name = "ID")
	private Long id;

	@NotBlank(message = "City name is required")
	@Size(max = 512, message = "City name max length is 512 characters")
	@Column(name = "CITY_NAME", unique = true)
	private String cityName;

	@NotBlank(message = "Image URL is required")
	@Size(max = 2048, message = "Image URL max length is 1024 characters")
	@Column(name = "IMAGE_URL")
	private String imageURL;
}
