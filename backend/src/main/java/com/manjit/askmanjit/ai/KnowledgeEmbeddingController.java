package com.manjit.askmanjit.ai;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manjit.askmanjit.service.KnowledgeEmbeddingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class KnowledgeEmbeddingController {

    private final KnowledgeEmbeddingService knowledgeEmbeddingService;

    @GetMapping("/api/ai/generate-embeddings")
    public String generateEmbeddings() {
        knowledgeEmbeddingService.generateEmbeddingsForAll();
        return "Embeddings generated successfully.";
    }
}