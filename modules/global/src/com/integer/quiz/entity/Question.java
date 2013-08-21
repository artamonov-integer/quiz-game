package com.integer.quiz.entity;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;

@Entity(name = "quiz$Question")
@Table(name = "QUIZ_QUESTION")
public class Question extends StandardEntity {
    @Column(name="CONTENT")
    protected String content;

    @Column(name="STAGE")
    protected Integer stage;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "ANSWER")
    protected Answer answer;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "IMAGE")
    protected FileDescriptor image;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public FileDescriptor getImage() {
        return image;
    }

    public void setImage(FileDescriptor image) {
        this.image = image;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }
}
