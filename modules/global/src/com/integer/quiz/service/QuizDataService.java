package com.integer.quiz.service;

import java.lang.String;

public interface QuizDataService {
    String NAME = "quiz_QuizDataService";

    public String getAnswersXml();

    public String getQuestionsXml(Integer stage);

    public void setConnectionString(String connectionString);

    public String getConnectionString();

    public void refreshAnswerList();
}