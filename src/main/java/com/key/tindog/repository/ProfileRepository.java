package com.key.tindog.repository;

import com.key.tindog.model.Location;
import com.key.tindog.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    ArrayList<Profile> findAll();
}
