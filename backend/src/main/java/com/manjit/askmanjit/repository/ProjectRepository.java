package com.manjit.askmanjit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manjit.askmanjit.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}