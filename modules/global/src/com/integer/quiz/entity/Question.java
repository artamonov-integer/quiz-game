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

    @ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "ANSWER")
    protected Answer answer;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "IMAGE")
    protected FileDescriptor image;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "IMAGE_MID")
    protected FileDescriptor imageMid;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "IMAGE_HIGH")
    protected FileDescriptor imageHigh;

    public FileDescriptor getImageMid() {
        return imageMid;
    }

    public void setImageMid(FileDescriptor imageMid) {
        this.imageMid = imageMid;
    }

    public FileDescriptor getImageHigh() {
        return imageHigh;
    }

    public void setImageHigh(FileDescriptor imageHigh) {
        this.imageHigh = imageHigh;
    }

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
