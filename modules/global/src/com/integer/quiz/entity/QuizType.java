package com.integer.quiz.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

public enum  QuizType implements EnumClass<Integer> {
    OUT_OF_TIME(10),
    TO_FIRST_MISTAKE(20),
    TO_THIRD_MISTAKE(30);
    private Integer id;

    QuizType(Integer id){
        this.id = id;
    }

    @Override
    public Integer getId(){
        return id;
    }

    public static QuizType fromId(Integer id){
        for(QuizType quizType : QuizType.values()){
            if(quizType.getId().equals(id))
                return quizType;
        }
        return null;
    }
}
