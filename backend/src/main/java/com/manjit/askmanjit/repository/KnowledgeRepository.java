package com.manjit.askmanjit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manjit.askmanjit.entity.Knowledge;

public interface KnowledgeRepository extends JpaRepository<Knowledge, Long> {

    List<Knowledge> findByActiveTrue();
    
    List<Knowledge> findByCategoryAndActiveTrue(String category);
}