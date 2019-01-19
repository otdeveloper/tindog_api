package com.key.tindog.service;

import com.key.tindog.exception.FileStorageException;
import com.key.tindog.exception.ProfileNotFoundException;
import com.key.tindog.model.Image;
import com.key.tindog.model.Location;
import com.key.tindog.model.Profile;
import com.key.tindog.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {

	private final ProfileRepository profileRepository;

	public ProfileService(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}

	public void saveProfile(Profile profile) {
		try {
			profileRepository.save(profile);

		} catch (Exception e) {
			throw new FileStorageException("Error saving profile to DB: Id = " + profile.getId());
		}
	}

	public Profile findById(Long Id) {
		return profileRepository.findById(Id).orElseThrow(() -> new ProfileNotFoundException("Profile not found with id " + Id));
	}

	public Image findImageById(Long fileId) {
		return findById(fileId).getImage();
	}

	public List<Profile> getProfilesInRange(Location location, int range) {
		ArrayList<Profile> allByLocationIsNear = profileRepository.findAll();

		return allByLocationIsNear.stream()
				.filter(profile -> LocationService.getDistance(location, profile.getLocation()) < range)
				.sorted(Comparator.comparing(profile -> LocationService.getDistance(location, profile.getLocation())))
				.collect(Collectors.toList());
	}
	public List<Profile> getAllProfiles(){
		return profileRepository.findAll();
	}
}
