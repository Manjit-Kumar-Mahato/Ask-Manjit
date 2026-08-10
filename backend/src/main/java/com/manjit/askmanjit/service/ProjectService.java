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

	public Project getProjectById(Long id) {
		return projectRepository.findById(id).orElse(null);
	}
	
	public Project createProject(Project project) {
	    return projectRepository.save(project);
	}
}