package com.integer.quiz.app;

import com.integer.quiz.entity.Answer;
import com.integer.quiz.entity.Question;

import java.lang.String;
import java.util.List;

public interface Storage {
    String NAME = "quiz_StorageService";
    public List<Answer> getAnswers();
    public List<Question> getQuestions(Integer stage);
    public List<Answer> getRandomAnswers(Answer wrightAnswer);
}