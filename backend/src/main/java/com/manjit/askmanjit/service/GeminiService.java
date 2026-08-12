package com.manjit.askmanjit.service;

import org.springframework.stereotype.Service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GeminiService {

	private final Client client;

	public String askGemini(String question) {
		GenerateContentResponse response = client.models.generateContent("gemini-3.6-flash", question, null);
		return response.text();
	}
}