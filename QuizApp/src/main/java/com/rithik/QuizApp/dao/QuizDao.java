package com.rithik.QuizApp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rithik.QuizApp.model.Quiz;

public interface QuizDao extends JpaRepository<Quiz,Integer> {

}
