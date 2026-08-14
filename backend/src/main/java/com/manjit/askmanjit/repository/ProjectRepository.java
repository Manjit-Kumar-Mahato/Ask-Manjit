package com.manjit.askmanjit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manjit.askmanjit.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAll();

    Optional<Project> findByNameIgnoreCase(String name);
    
    List<Project> findByFeaturedTrueOrderByDisplayOrderAsc();

    List<Project> findAllByOrderByDisplayOrderAsc();
}