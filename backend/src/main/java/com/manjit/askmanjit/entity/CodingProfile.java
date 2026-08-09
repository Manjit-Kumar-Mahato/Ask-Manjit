package com.manjit.askmanjit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "coding_profiles")
@Getter
@Setter
@NoArgsConstructor
public class CodingProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String platform;

    private String username;

    private Integer rating;

    private Integer problemsSolved;

    private String profileUrl;
}