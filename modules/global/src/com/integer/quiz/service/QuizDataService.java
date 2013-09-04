package com.integer.quiz.service;

import java.lang.String;
import java.util.HashMap;

public interface QuizDataService {
    String NAME = "quiz_QuizDataService";

    public String getAnswersXml();

    public String getQuestionsXml(String quality);

    public void setConnectionSettings(String host, String port, String imagePort);

    public HashMap<String,String> getConnectionSettings();

    public void setMailSettings(String mail, String password, String host, String port, String subject, String text);

    public HashMap<String, String> getMailSettings();

    public void refreshAnswerList();

    //public String getTopScoreXml(String sessionId);

    public String getScoreXml(String sessionId);

    public String addScore(Integer points, Integer type);

    public String signUp(String login, String email);

    public String confirmRegistration(String id);
}