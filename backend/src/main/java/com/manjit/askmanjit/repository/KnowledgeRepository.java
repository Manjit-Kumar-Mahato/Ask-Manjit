package com.manjit.askmanjit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.manjit.askmanjit.entity.Knowledge;

public interface KnowledgeRepository extends JpaRepository<Knowledge, Long> {

	List<Knowledge> findByActiveTrue();

	List<Knowledge> findByCategoryAndActiveTrue(String category);

	List<Knowledge> findByTopicContainingIgnoreCaseAndActiveTrue(String topic);

	@Query(value = """
			SELECT *
			FROM knowledge
			WHERE active = true
			  AND embedding IS NOT NULL
			  AND embedding <=> CAST(:embedding AS vector) < :threshold
			ORDER BY embedding <=> CAST(:embedding AS vector)
			LIMIT :limit
			""", nativeQuery = true)
	List<Knowledge> findSimilarKnowledge(@Param("embedding") String embedding, @Param("limit") int limit,
			@Param("threshold") double threshold);
}