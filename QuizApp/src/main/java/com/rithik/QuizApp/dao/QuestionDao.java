package com.rithik.QuizApp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rithik.QuizApp.model.Question;

@Repository
public interface QuestionDao extends JpaRepository<Question,Integer>{

	List<Question> findByCategory(String category);

	@Query(value="SELECT * FROM questions WHERE category = ? ORDER BY RAND() LIMIT ?",nativeQuery=true)
	List<Question> findRandomQuestionsByCategory(String category, int numQ);
}
