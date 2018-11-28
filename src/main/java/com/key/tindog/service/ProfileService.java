package com.key.tindog.service;

import com.key.tindog.exception.FileStorageException;
import com.key.tindog.exception.ProfileNotFoundException;
import com.key.tindog.model.Location;
import com.key.tindog.model.Profile;
import com.key.tindog.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

    public ArrayList<Profile> getProfilesInRange(Location location, int range) {
        ArrayList<Profile> allByLocationIsNear = profileRepository.findAll();
        for (Profile profile : allByLocationIsNear) {
            if (LocationService.getDistance(location, profile.getLocation()) > range) {
                allByLocationIsNear.remove(profile);
            }
        }
        return allByLocationIsNear;
    }
}
