package com.manjit.askmanjit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.manjit.askmanjit.dto.KnowledgeSearchResult;
import com.manjit.askmanjit.entity.Knowledge;
import com.manjit.askmanjit.repository.KnowledgeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KnowledgeService {

	private final KnowledgeRepository knowledgeRepository;

	private final EmbeddingService embeddingService;

	public List<Knowledge> getAllActiveKnowledge() {
		return knowledgeRepository.findByActiveTrue();
	}

	public List<Knowledge> getKnowledgeByCategory(String category) {
		return knowledgeRepository.findByCategoryAndActiveTrue(category);
	}

	public List<Knowledge> searchKnowledgeByTopic(String topic) {
		return knowledgeRepository.findByTopicContainingIgnoreCaseAndActiveTrue(topic);
	}

	public List<Knowledge> searchSimilarKnowledge(String question, int limit) {
		float[] queryEmbedding = embeddingService.generateQueryEmbedding(question);
		String vector = embeddingService.toVectorString(queryEmbedding);
		return knowledgeRepository.findSimilarKnowledge(vector, limit, 0.65);
	}
}