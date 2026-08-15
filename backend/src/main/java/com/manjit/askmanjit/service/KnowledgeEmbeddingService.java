package com.manjit.askmanjit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.manjit.askmanjit.entity.Knowledge;
import com.manjit.askmanjit.repository.KnowledgeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KnowledgeEmbeddingService {
	private final KnowledgeRepository knowledgeRepository;
	private final EmbeddingService embeddingService;

	public void generateEmbeddingsForAll() {
		List<Knowledge> knowledgeList = knowledgeRepository.findByActiveTrue();
		for (Knowledge knowledge : knowledgeList) {
			float[] embedding = embeddingService.generateDocumentEmbedding(knowledge.getContent());
			knowledge.setEmbedding(embedding);
			knowledgeRepository.save(knowledge);
		}
	}
}