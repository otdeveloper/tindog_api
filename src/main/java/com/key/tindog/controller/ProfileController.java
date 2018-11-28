package com.key.tindog.controller;

import com.key.tindog.model.Image;
import com.key.tindog.model.Location;
import com.key.tindog.model.Profile;
import com.key.tindog.service.ImageService;
import com.key.tindog.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/uploadProfile/")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("firstName") String firstName,
                                             @RequestParam("lastName") String lastName,
                                             @RequestParam(value = "location") double[] location) {

        Image image = ImageService.parseToImage(file);

        Profile profile = new Profile(firstName, lastName, image, new Location(location[0], location[1]));
        profileService.saveProfile(profile);

        return new ResponseEntity<>("uploaded id = " + profile.getId(), HttpStatus.ACCEPTED);
    }


    @GetMapping("/getProfiles/")
    public List<Profile> getProfiles(@RequestParam("range") int range,
                                     @RequestParam("lat") double lat,
                                     @RequestParam("lon") double lon) {
        return profileService.getProfilesInRange(new Location(lat, lon), range);

    }

    @GetMapping("/downloadProfile/{fileId}")
    public Profile downloadProfile(@PathVariable String fileId, Map response) {
        //String encodedImage = Base64.getEncoder().encodeToString(profile.getImage().getData());
        return profileService.findById(Long.parseLong(fileId));
    }


}
