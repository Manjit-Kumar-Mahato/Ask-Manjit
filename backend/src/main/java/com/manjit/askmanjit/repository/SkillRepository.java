package com.manjit.askmanjit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manjit.askmanjit.entity.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {

}