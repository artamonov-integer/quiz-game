package com.integer.quiz.app;

import com.integer.quiz.entity.Answer;
import com.integer.quiz.entity.Question;
import com.integer.quiz.entity.Score;

import java.lang.String;
import java.util.List;

public interface Storage {
    String NAME = "quiz_StorageService";
    public List<Answer> getAnswers();
    public List<Question> getQuestions(Integer stage);
    public List<Score> getScores(Integer count, Integer type);
    public List<Answer> getRandomAnswers(Answer wrightAnswer);
}