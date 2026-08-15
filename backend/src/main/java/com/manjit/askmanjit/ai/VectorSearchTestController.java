package com.manjit.askmanjit.ai;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manjit.askmanjit.entity.Knowledge;
import com.manjit.askmanjit.service.KnowledgeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class VectorSearchTestController {

	private final KnowledgeService knowledgeService;

	@GetMapping("/api/ai/vector-search")
	public List<KnowledgeResult> vectorSearch(@RequestParam String question,
			@RequestParam(defaultValue = "5") int limit) {
		return knowledgeService.searchSimilarKnowledge(question, limit).stream()
				.map(k -> new KnowledgeResult(k.getId(), k.getCategory(), k.getTopic(), k.getContent())).toList();
	}

	public record KnowledgeResult(Long id, String category, String topic, String content) {
	}
}