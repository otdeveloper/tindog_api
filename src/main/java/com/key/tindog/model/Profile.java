package com.key.tindog.model;

import javax.persistence.*;

@Entity
@Table(name = "profiles")
public class Profile {

	@Id
	@GeneratedValue
	private Long id;

	private String firstName;

	private String lastName;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	private Image image;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "location_id")
	private Location location;

	public Profile() {
	}

	public Profile(String firstName, String lastName, Image image, Location location) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.image = image;
		this.location = location;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
