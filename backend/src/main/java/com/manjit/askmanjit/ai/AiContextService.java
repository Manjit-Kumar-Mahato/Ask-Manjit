package com.manjit.askmanjit.ai;

import java.util.List;

import org.springframework.stereotype.Service;

import com.manjit.askmanjit.entity.CodingProfile;
import com.manjit.askmanjit.entity.Knowledge;
import com.manjit.askmanjit.entity.Profile;
import com.manjit.askmanjit.entity.Project;
import com.manjit.askmanjit.entity.Skill;
import com.manjit.askmanjit.service.CodingProfileService;
import com.manjit.askmanjit.service.KnowledgeService;
import com.manjit.askmanjit.service.ProfileService;
import com.manjit.askmanjit.service.ProjectService;
import com.manjit.askmanjit.service.SkillService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiContextService {

	private final ProfileService profileService;
	private final ProjectService projectService;
	private final CodingProfileService codingProfileService;
	private final SkillService skillService;
	private final KnowledgeService knowledgeService;

	public String getContext(String question) {
		String lowerQuestion = question.toLowerCase();
		StringBuilder context = new StringBuilder();

		// ---------------- PROFILE ----------------
		if (containsAny(lowerQuestion, "who", "about manjit", "about him", "education", "college", "university",
				"degree", "contact", "email", "phone")) {
			Profile profile = profileService.getProfile();
			if (profile != null) {
				context.append("PROFILE:\n");
				context.append(profile).append("\n\n");
			}
		}

		// ---------------- PROJECTS ----------------

		if (containsAny(lowerQuestion, "project", "projects", "built", "build", "created", "developed", "development",
				"application", "applications", "work", "worked")) {
			// BEST / GOOD / FEATURED PROJECTS
			if (containsAny(lowerQuestion, "best project", "best projects", "good project", "good projects",
					"top project", "top projects", "featured project", "featured projects", "notable project",
					"notable projects")) {
				List<Project> projects = projectService.getTopProjects(4);
				appendProjects(context, projects);
			}
			// ALL PROJECTS
			else if (containsAny(lowerQuestion, "all projects", "every project", "every projects", "complete list",
					"all of his projects", "all of manjit's projects")) {
				List<Project> projects = projectService.getAllProjects();
				appendProjects(context, projects);
			}
			// NORMAL PROJECT QUESTION
			else if (containsAny(lowerQuestion, "what projects", "which projects", "list projects", "projects has",
					"projects have", "his projects", "manjit's projects", "tell me about his projects",
					"tell me about manjit's projects")) {
				List<Project> projects = projectService.getFeaturedProjects();
				appendProjects(context, projects);
			}

			// SPECIFIC PROJECT
			else {
				if (containsAny(lowerQuestion, "e-notes", "enotes", "notes api")) {
					appendProjectDetails("E-Notes REST API", "e-notes-api-service", context);
				} else if (containsAny(lowerQuestion, "e-commerce", "ecommerce", "shopping app", "shopping website")) {
					appendProjectDetails("Spring Boot E-Commerce Application", "spring-boot-ecommerce", context);
				} else if (containsAny(lowerQuestion, "expense tracker")) {
					appendProjectDetails("Online Expense Tracker", "online-expense-tracker", context);
				} else if (containsAny(lowerQuestion, "stellar", "prediction dashboard")) {
					appendProjectDetails("Stellar Prediction Dashboard", "stellar-prediction-dashboard", context);
				} else if (lowerQuestion.contains("banking")) {
					appendProjectDetails("Banking Management System", "banking-management-system", context);
				} else if (lowerQuestion.contains("hospital management")) {
					appendProjectDetails("Hospital Management System", "hospital-management-system-web", context);
				}
			}
		}

		// ---------------- CODING PROFILES ----------------
		if (containsAny(lowerQuestion, "leetcode", "codeforces", "codechef", "coding", "competitive programming",
				"rating", "problems solved", "problems", "dsa")) {
			List<CodingProfile> profiles = codingProfileService.getAllCodingProfiles();
			context.append("CODING PROFILES:\n");
			for (CodingProfile profile : profiles) {
				context.append("Platform: ").append(profile.getPlatform()).append("\n");
				context.append("Problems Solved: ").append(profile.getProblemsSolved()).append("\n");
				context.append("Rating: ").append(profile.getRating()).append("\n");
				context.append("Username: ").append(profile.getUsername()).append("\n");
				context.append("Profile URL: ").append(profile.getProfileUrl()).append("\n\n");
			}
		}

		// ---------------- SKILLS ----------------
		if (containsAny(lowerQuestion, "skill", "skills", "technology", "technologies", "tech stack", "programming",
				"programming language", "languages", "framework", "frameworks")) {
			List<Skill> skills = skillService.getAllSkills();
			context.append("SKILLS:\n");
			for (Skill skill : skills) {
				context.append("Skill: ").append(skill.getName()).append("\n");
				context.append("Category: ").append(skill.getCategory()).append("\n");
			}
			context.append("\n");
		}

		// ---------------- KNOWLEDGE ----------------

		if (containsAny(lowerQuestion, "e-notes", "enotes", "notes api", "e-notes api service", "enotes api service",
				"e-commerce", "ecommerce", "shopping app", "shopping website", "shopping web", "expense tracker",
				"hospital management", "stellar", "prediction dashboard", "banking system", "banking",
				"competitive programming", "competitive programming experience", "backend development",
				"backend experience", "about manjit")) {

			List<Knowledge> knowledge = knowledgeService.searchKnowledgeByTopic(getKnowledgeTopic(lowerQuestion));
			if (!knowledge.isEmpty()) {
				context.append("KNOWLEDGE:\n");
				for (Knowledge item : knowledge) {
					context.append("Topic: ").append(item.getTopic()).append("\n");
					context.append("Details: ").append(item.getContent()).append("\n\n");
				}
			}
		}
		return context.toString();
	}

	private void appendProjects(StringBuilder context, List<Project> projects) {
		context.append("PROJECTS:\n");
		for (Project project : projects) {
			context.append("Project Name: ").append(project.getName()).append("\n");
			context.append("Description: ").append(project.getShortDescription()).append("\n");
			context.append("Technologies: ").append(project.getTechnologies()).append("\n");
			context.append("GitHub: ").append(project.getGithubUrl()).append("\n");
			context.append("Live URL: ").append(project.getLiveUrl()).append("\n\n");
		}
	}

	private String getKnowledgeTopic(String question) {
		if (containsAny(question, "e-notes", "enotes", "notes api", "e-notes api service", "enotes api servi")) {
			return "e-notes";
		}
		if (containsAny(question, "e-commerce", "ecommerce", "shopping app", "shopping website", "shopping web")) {
			return "spring-boot-ecommerce";
		}
		if (containsAny(question, "expense tracker")) {
			return "expense";
		}
		if (containsAny(question, "hospital management")) {
			return "hospital";
		}
		if (containsAny(question, "stellar", "prediction dashboard")) {
			return "stellar";
		}
		if (containsAny(question, "banking system", "banking")) {
			return "banking";
		}
		if (containsAny(question, "competitive programming", "competitive programming experience")) {
			return "competitive";
		}
		if (containsAny(question, "backend development", "backend experience")) {
			return "backend";
		}
		return "introduction";
	}

	private boolean containsAny(String question, String... keywords) {
		for (String keyword : keywords) {
			if (question.contains(keyword)) {
				return true;
			}
		}
		return false;
	}

	private void appendProjectDetails(String projectName, String knowledgeTopic, StringBuilder context) {
		Project project = projectService.getProjectByName(projectName);
		if (project != null) {
			context.append("PROJECT:\n");
			context.append("Name: ").append(project.getName()).append("\n");
			context.append("Short Description: ").append(project.getShortDescription()).append("\n");
			context.append("Technologies: ").append(project.getTechnologies()).append("\n");
			context.append("GitHub: ").append(project.getGithubUrl()).append("\n");
			context.append("Live URL: ").append(project.getLiveUrl()).append("\n\n");
		}
		List<Knowledge> knowledge = knowledgeService.searchKnowledgeByTopic(knowledgeTopic);
		if (!knowledge.isEmpty()) {
			context.append("DETAILED PROJECT INFORMATION:\n");
			for (Knowledge item : knowledge) {
				context.append("Topic: ").append(item.getTopic()).append("\n");
				context.append("Details: ").append(item.getContent()).append("\n\n");
			}
		}
	}
}