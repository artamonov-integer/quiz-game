package com.integer.quiz.entity;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.security.entity.User;

import javax.persistence.*;

@Entity(name = "quiz$Score")
@Table(name = "QUIZ_SCORE")
public class Score extends StandardEntity {

    @Column(name="POINTS", nullable = false)
    protected Integer points;

    @Column(name="TYPE")
    protected Integer quizType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "USER_ID", nullable = false)
    protected User user;

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public QuizType getQuizType() {
        return quizType != null ? QuizType.fromId(quizType) : null;
    }

    public void setQuizType(QuizType quizType) {
        this.quizType = quizType != null ? quizType.getId() : null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
