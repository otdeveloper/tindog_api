package com.key.tindog.controller;

import com.key.tindog.exception.ProfileNotFoundException;
import com.key.tindog.model.Image;
import com.key.tindog.model.Location;
import com.key.tindog.model.Profile;
import com.key.tindog.service.ImageService;
import com.key.tindog.service.ProfileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

	private final ProfileService profileService;

	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}

	@PostMapping
	public ResponseEntity<String> uploadFile(
			@RequestParam("file") MultipartFile file,
			@RequestParam("firstName") String firstName,
	        @RequestParam("lastName") String lastName,
	        @RequestParam(value = "location") double[] location) throws IOException {

		Profile profile = new Profile(
				firstName,
				lastName,
				new Image(file.getName() , Base64.getEncoder().encodeToString(file.getBytes())),
				new Location(location[0], location[1]));
		profileService.saveProfile(profile);

		return new ResponseEntity<>("uploaded id = " + profile.getId(), HttpStatus.ACCEPTED);
	}


	@GetMapping()
	public List<Profile> getProfilesByLocation(
			@RequestParam("range") int range,
	        @RequestParam("lat") double lat,
	        @RequestParam("lon") double lon) {
		List<Profile> profilesInRange = profileService.getProfilesInRange(
				new Location(lat, lon), range);

		if (profilesInRange.isEmpty()) {
			throw new ProfileNotFoundException("No profiles in this area");
		} else {
			return profilesInRange;
		}
	}

	@GetMapping("/{fileId}")
	public Profile findProfileById(@PathVariable String fileId, Map response) {
		//String encodedImage = Base64.getEncoder().encodeToString(profile.getImage().getData());
		return profileService.findById(Long.parseLong(fileId));
	}

	@GetMapping("/admin")
	public List<Profile> getAllProfiles() {
		return profileService.getAllProfiles();
	}

}
