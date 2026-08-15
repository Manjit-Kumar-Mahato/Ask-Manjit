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
			// 1. ALL PROJECTS
			if (containsAny(lowerQuestion, "all projects", "every project", "all of his projects",
					"all of manjit's projects", "complete list of projects", "complete projects")) {
				List<Project> projects = projectService.getAllProjects();
				appendProjects(context, projects);
			}
			// 2. SPECIFIC PROJECT
			else if (containsAny(lowerQuestion, "e-notes", "enotes", "notes api")) {
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
			// 3. NUMBER OF PROJECTS REQUESTED
			else if (containsProjectCount(lowerQuestion)) {
				int count = getRequestedProjectCount(lowerQuestion);
				List<Project> projects = projectService.getTopProjects(count);
				appendProjects(context, projects);
			}
			// 4. BEST / TOP / GOOD WITHOUT A NUMBER
			else if (containsAny(lowerQuestion, "best", "top", "good", "featured", "notable")) {
				List<Project> projects = projectService.getTopProjects(4);
				appendProjects(context, projects);
			}
			// 5. NORMAL PROJECT QUESTION
			else {
				List<Project> projects = projectService.getFeaturedProjects();
				appendProjects(context, projects);
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
				"backend experience", "about manjit","academy","journey","academic")) {
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

	private boolean containsProjectCount(String question) {
		return question.matches(".*\\b\\d+\\s+projects?\\b.*") || question.matches(".*\\bprojects?\\s+\\d+\\b.*")
				|| containsAny(question, "one project", "two projects", "three projects", "four projects",
						"five projects", "six projects");
	}

	private int getRequestedProjectCount(String question) {
		// Numeric: 2 projects
		java.util.regex.Pattern numericPattern = java.util.regex.Pattern.compile("\\b(\\d+)\\s+projects?\\b");
		java.util.regex.Matcher numericMatcher = numericPattern.matcher(question);
		if (numericMatcher.find()) {
			return Integer.parseInt(numericMatcher.group(1));
		}
		// Numeric: projects 2
		java.util.regex.Pattern reverseNumericPattern = java.util.regex.Pattern.compile("\\bprojects?\\s+(\\d+)\\b");
		java.util.regex.Matcher reverseMatcher = reverseNumericPattern.matcher(question);
		if (reverseMatcher.find()) {
			return Integer.parseInt(reverseMatcher.group(1));
		}
		// Words
		if (question.contains("one project")) {
			return 1;
		}
		if (question.contains("two projects")) {
			return 2;
		}
		if (question.contains("three projects")) {
			return 3;
		}
		if (question.contains("four projects")) {
			return 4;
		}
		if (question.contains("five projects")) {
			return 5;
		}
		if (question.contains("six projects")) {
			return 6;
		}
		return 4;
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