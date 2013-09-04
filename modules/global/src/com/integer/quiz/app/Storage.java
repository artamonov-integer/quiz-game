package com.integer.quiz.app;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.security.entity.Group;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.entity.Role;
import com.integer.quiz.entity.Answer;
import com.integer.quiz.entity.Question;
import com.integer.quiz.entity.QuizType;
import com.integer.quiz.entity.Score;

import java.lang.String;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface Storage {
    String NAME = "quiz_StorageService";

    public <E extends StandardEntity> void createOrUpdateEntity(E entity);

    public List<Answer> getAnswers();

    public List<Question> getQuestions();

    public List<Score> getScores(Integer count, Integer type);

    public List<Answer> getRandomAnswers(Answer wrightAnswer);

    public Score getScore(Integer type, User user);

    public HashMap<String, String> getScorePosition(Integer type, User user);

    public Group getGroupByName(String name);

    public Group addGroup(String name);

    public Role getRoleByName(String name);

    public Role addRole(String name);

    public User getUserById(UUID id);

    public User getUserByMailLogin(String login, String email);

    public List<Score> getNeighborScores(Integer count, Integer type, User user);

    public void addScoreHistory(Integer points, QuizType quizType, User user);
}