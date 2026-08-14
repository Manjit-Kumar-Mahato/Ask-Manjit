package com.manjit.askmanjit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.manjit.askmanjit.entity.Project;
import com.manjit.askmanjit.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

	private final ProjectRepository projectRepository;

	public List<Project> getAllProjects() {
		return projectRepository.findAll();
	}

	public List<Project> getFeaturedProjects() {
		return projectRepository.findByFeaturedTrueOrderByDisplayOrderAsc();
	}

	public Project getProjectByName(String name) {
		return projectRepository.findByNameIgnoreCase(name).orElse(null);
	}

	public List<Project> getTopProjects(int limit) {
		return projectRepository.findByFeaturedTrueOrderByDisplayOrderAsc().stream().limit(limit).toList();
	}
}