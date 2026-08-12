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
		// Profile
		if (lowerQuestion.contains("who") || lowerQuestion.contains("about") || lowerQuestion.contains("education")
				|| lowerQuestion.contains("contact")) {
			Profile profile = profileService.getProfile();
			if (profile != null) {
				context.append("PROFILE:\n").append(profile).append("\n\n");
			}
		}

		// Projects
		if (lowerQuestion.contains("project") || lowerQuestion.contains("e-notes") || lowerQuestion.contains("built")
				|| lowerQuestion.contains("application")) {
			List<Project> projects = projectService.getAllProjects();
			context.append("PROJECTS:\n");
			for (Project project : projects) {
				context.append("Project Name: ").append(project.getName()).append("\n");
				context.append("Description: ").append(project.getShortDescription()).append("\n");
				context.append("Technologies: ").append(project.getTechnologies()).append("\n");
				context.append("GitHub: ").append(project.getGithubUrl()).append("\n");
				context.append("Live URL: ").append(project.getLiveUrl()).append("\n\n");
			}
			context.append("\n");
		}

		// Coding profiles
		if (lowerQuestion.contains("leetcode") || lowerQuestion.contains("codeforces")
				|| lowerQuestion.contains("codechef") || lowerQuestion.contains("coding")
				|| lowerQuestion.contains("rating") || lowerQuestion.contains("coding profiles")) {
			List<CodingProfile> profiles = codingProfileService.getAllCodingProfiles();
			context.append("CODING PROFILES:\n");
			for (CodingProfile profile : profiles) {
				context.append(profile).append("\n");
			}
			context.append("\n");
		}

		// Skills
		if (lowerQuestion.contains("skill") || lowerQuestion.contains("technology")
				|| lowerQuestion.contains("tech stack") || lowerQuestion.contains("programming")
				|| lowerQuestion.contains("language") || lowerQuestion.contains("languages")) {
			List<Skill> skills = skillService.getAllSkills();
			context.append("SKILLS:\n");
			for (Skill skill : skills) {
				context.append("Skill: ").append(skill.getName()).append("\n");
				context.append("Category: ").append(skill.getCategory()).append("\n");
			}
			context.append("\n");
		}

		// Knowledge
		List<Knowledge> knowledge = knowledgeService.getAllActiveKnowledge();
		context.append("KNOWLEDGE:\n");
		for (Knowledge item : knowledge) {
			context.append(item.getTopic()).append(": ").append(item.getContent()).append("\n");
		}
		return context.toString();
	}
}