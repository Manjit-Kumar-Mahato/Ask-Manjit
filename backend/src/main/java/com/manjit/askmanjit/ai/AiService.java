package com.manjit.askmanjit.ai;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiService {

    private final AiContextService aiContextService;

    public String ask(String question) {
        String context = aiContextService.getContext(question);
        return context;
    }
}