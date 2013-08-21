package com.integer.quiz.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "quiz$Answer")
@Table(name = "QUIZ_ANSWER")

@NamePattern("%s|content")
public class Answer extends StandardEntity{
    @Column(name = "CONTENT")
    protected String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
