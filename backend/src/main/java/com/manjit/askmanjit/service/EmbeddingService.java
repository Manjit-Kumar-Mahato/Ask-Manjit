package com.manjit.askmanjit.service;

import org.springframework.stereotype.Service;

import com.google.genai.Client;
import com.google.genai.types.ContentEmbedding;
import com.google.genai.types.EmbedContentConfig;
import com.google.genai.types.EmbedContentResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmbeddingService {

	private final Client client;

	public float[] generateDocumentEmbedding(String text) {
		EmbedContentConfig config = EmbedContentConfig.builder().outputDimensionality(768)
				.taskType("RETRIEVAL_DOCUMENT").build();
		return generate(text, config);
	}

	public float[] generateQueryEmbedding(String text) {
		EmbedContentConfig config = EmbedContentConfig.builder().outputDimensionality(768).taskType("RETRIEVAL_QUERY")
				.build();
		return generate(text, config);
	}

	private float[] generate(String text, EmbedContentConfig config) {
		EmbedContentResponse response = client.models.embedContent("gemini-embedding-001", text, config);
		ContentEmbedding embedding = response.embeddings().get().get(0);
		var values = embedding.values().get();
		float[] result = new float[values.size()];
		for (int i = 0; i < values.size(); i++) {
			result[i] = values.get(i);
		}
		return result;
	}

	public String toVectorString(float[] embedding) {
		StringBuilder builder = new StringBuilder("[");
		for (int i = 0; i < embedding.length; i++) {
			if (i > 0) {
				builder.append(",");
			}
			builder.append(embedding[i]);
		}
		builder.append("]");
		return builder.toString();
	}
}