package com.manjit.askmanjit.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manjit.askmanjit.entity.Knowledge;
import com.manjit.askmanjit.service.KnowledgeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/knowledge")
public class KnowledgeController {

	private final KnowledgeService knowledgeService;

	@GetMapping
	public List<Knowledge> getAllActiveKnowledge() {
		return knowledgeService.getAllActiveKnowledge();
	}

	@GetMapping("/category/{category}")
	public List<Knowledge> getKnowledgeByCategory(@PathVariable String category) {
		return knowledgeService.getKnowledgeByCategory(category);
	}
}