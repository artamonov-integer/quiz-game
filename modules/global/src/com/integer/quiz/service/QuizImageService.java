package com.integer.quiz.service;

import java.io.File;

public interface QuizImageService {
    String NAME = "quiz_QuizImageService";

    public Boolean saveImage(File file, File newFile, int quality, String ext);
}
