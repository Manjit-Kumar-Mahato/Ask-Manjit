package com.manjit.askmanjit.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manjit.askmanjit.entity.CodingProfile;
import com.manjit.askmanjit.service.CodingProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coding-profiles")
public class CodingProfileController {

    private final CodingProfileService codingProfileService;

    @GetMapping
    public List<CodingProfile> getAllCodingProfiles() {
        return codingProfileService.getAllCodingProfiles();
    }
}