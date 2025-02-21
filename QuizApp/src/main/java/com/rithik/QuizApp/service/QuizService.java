package com.rithik.QuizApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rithik.QuizApp.dao.QuestionDao;
import com.rithik.QuizApp.dao.QuizDao;
import com.rithik.QuizApp.model.Question;
import com.rithik.QuizApp.model.QuestionWrapper;
import com.rithik.QuizApp.model.Quiz;
import com.rithik.QuizApp.model.Response;

@Service
public class QuizService {

	@Autowired
	QuizDao quizDao;
	
	@Autowired
	QuestionDao questionDao;

	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
		
		List<Question> questions=questionDao.findRandomQuestionsByCategory(category,numQ);
		
		Quiz quiz=new Quiz();
		quiz.setTitle(title);
		quiz.setQuestions(questions);
		quizDao.save(quiz);
		return new ResponseEntity<>("Success",HttpStatus.CREATED);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
		Optional<Quiz> quiz=quizDao.findById(id);
		List<Question> questionsFromDB= quiz.get().getQuestions();
		List<QuestionWrapper> questionsForUser=new ArrayList<>();
		for (Question q: questionsFromDB) {
			QuestionWrapper qw= new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
			questionsForUser.add(qw);
		}
		
		return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
	}

	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
	    Quiz quiz = quizDao.findById(id).orElse(null);
	    
	    if (quiz == null) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Handle missing quiz
	    }
	    
	    List<Question> questions = quiz.getQuestions();
	    int right = 0;
	    
	    for (Response response : responses) {
	        for (Question question : questions) {
	            if (question.getId().equals(response.getId())) { // Match by ID
	                if (response.getResponse() != null && response.getResponse().trim().equalsIgnoreCase(question.getRightAnswer().trim())) {
	                    right++;
	                }
	                break; // Stop searching once the question is found
	            }
	        }
	    }
	    
	    return new ResponseEntity<>(right, HttpStatus.OK);
	}


	
}
