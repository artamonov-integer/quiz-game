package com.integer.quiz.service;

import java.lang.String;

public interface QuizDataService {
    String NAME = "quiz_QuizDataService";

    public String getAnswersXml();

    public String getQuestionsXml(Integer stage);

    public void setConnectionString(String connectionString);

    public String getConnectionString();

    public void refreshAnswerList();

    public String getTopScoreXml(Integer count, Integer type, String sessionId);

    public String getScoreXml(Integer type, String sessionId);

    public String addScore(Integer points, Integer type);

    public String signUp(String login, String email);
}