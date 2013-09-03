package com.integer.quiz.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import java.util.List;

@Entity(name = "quiz$Answer")
@Table(name = "QUIZ_ANSWER")

@NamePattern("%s|content")
public class Answer extends StandardEntity{

    @Column(name = "CONTENT", unique = true)
    protected String content;

    @OneToMany(mappedBy = "answer", fetch = FetchType.LAZY)
    @OnDelete(DeletePolicy.CASCADE)
    protected List<Question> questionList;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
}
