package com.key.tindog.service;

import com.key.tindog.exception.FileStorageException;
import com.key.tindog.exception.ProfileNotFoundException;
import com.key.tindog.model.Profile;
import com.key.tindog.repository.ProfileRepository;
import org.springframework.stereotype.Service;

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
}
