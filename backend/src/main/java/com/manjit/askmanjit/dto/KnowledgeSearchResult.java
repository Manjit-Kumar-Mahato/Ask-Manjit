package com.manjit.askmanjit.dto;

import com.manjit.askmanjit.entity.Knowledge;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KnowledgeSearchResult {

    private Knowledge knowledge;
    private double distance;
}