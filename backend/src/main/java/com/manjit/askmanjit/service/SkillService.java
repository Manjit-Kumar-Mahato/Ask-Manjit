package com.manjit.askmanjit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.manjit.askmanjit.entity.Skill;
import com.manjit.askmanjit.repository.SkillRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SkillService {

	private final SkillRepository skillRepository;

	public List<Skill> getAllSkills() {
		return skillRepository.findAll();
	}
}