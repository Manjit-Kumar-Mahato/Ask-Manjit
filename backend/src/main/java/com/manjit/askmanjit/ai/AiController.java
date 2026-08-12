package com.manjit.askmanjit.ai;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/ask")
    public String ask(@RequestBody AiRequest request) {
        return aiService.ask(request.getQuestion());
    }
}