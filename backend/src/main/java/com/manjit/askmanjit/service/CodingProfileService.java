package com.manjit.askmanjit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.manjit.askmanjit.entity.CodingProfile;
import com.manjit.askmanjit.repository.CodingProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CodingProfileService {

    private final CodingProfileRepository codingProfileRepository;

    public List<CodingProfile> getAllCodingProfiles() {
        return codingProfileRepository.findAll();
    }
}