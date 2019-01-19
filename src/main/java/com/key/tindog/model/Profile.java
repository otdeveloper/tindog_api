package com.key.tindog.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "profiles")
public class Profile {

	@Id
	@GeneratedValue
	private Long id;

	@NonNull
	private String firstName;

	@NonNull
	private String lastName;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	@NonNull
	private Image image;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "location_id")
	@NonNull
	private Location location;

}
