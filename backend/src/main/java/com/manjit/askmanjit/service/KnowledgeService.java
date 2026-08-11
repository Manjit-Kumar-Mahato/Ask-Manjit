package com.manjit.askmanjit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.manjit.askmanjit.entity.Knowledge;
import com.manjit.askmanjit.repository.KnowledgeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KnowledgeService {

	private final KnowledgeRepository knowledgeRepository;

	public List<Knowledge> getAllActiveKnowledge() {
	    return knowledgeRepository.findByActiveTrue();
	}
	
	public List<Knowledge> getKnowledgeByCategory(String category) {
	    return knowledgeRepository.findByCategoryAndActiveTrue(category);
	}
}