package com.manjit.askmanjit.ai;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manjit.askmanjit.service.EmbeddingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EmbeddingTestController {

	private final EmbeddingService embeddingService;

	@GetMapping("/api/ai/test-embedding")
	public String testEmbedding(@RequestParam(defaultValue = "Manjit is a Java backend developer") String text) {
		float[] embedding = embeddingService.generateQueryEmbedding(text);
		return "Embedding generated successfully. Dimension = " + embedding.length;
	}
}