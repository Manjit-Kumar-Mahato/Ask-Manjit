package com.manjit.askmanjit.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.manjit.askmanjit.entity.Project;
import com.manjit.askmanjit.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

	private final ProjectService projectService;

	@GetMapping
	public List<Project> getAllProjects() {
		return projectService.getAllProjects();
	}

	@GetMapping("/{id}")
	public Project getProjectById(@PathVariable Long id) {
		return projectService.getProjectById(id);
	}
	
	@PostMapping
	public Project createProject(@RequestBody Project project) {
	    return projectService.createProject(project);
	}
}