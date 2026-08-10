package com.manjit.askmanjit.service;

import org.springframework.stereotype.Service;

import com.manjit.askmanjit.entity.Profile;
import com.manjit.askmanjit.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

	private final ProfileRepository profileRepository;

	public Profile getProfile() {
		return profileRepository.findAll().stream().findFirst().orElse(null);
	}

	public Profile createProfile(Profile profile) {
		return profileRepository.save(profile);
	}
}