package com.integer.quiz.service;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.UserSessionProvider;
import com.haulmont.cuba.security.entity.User;
import com.integer.quiz.app.Storage;
import com.integer.quiz.app.XMLHelper;
import com.integer.quiz.entity.Answer;
import com.integer.quiz.entity.Question;
import com.integer.quiz.entity.QuizType;
import com.integer.quiz.entity.Score;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service(QuizDataService.NAME)
public class QuizDataServiceBean implements QuizDataService {

    @Inject
    private Storage storage;

    @Inject
    private XMLHelper helper;

    String connectionString = "http://localhost:8383/";

    List<Answer> answerList = null;
    Integer answerListSize = null;

    @Override
    public String getAnswersXml() {
        String s = "";
        List<Answer> answerList = storage.getAnswers();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        org.w3c.dom.Document document = documentBuilder.newDocument();
        Element rootElement = document.createElement("data");
        document.appendChild(rootElement);
        if (answerList != null && answerList.size() > 0) {
            for (Answer answer : answerList) {
                Element rateElement = document.createElement("answer");
                rateElement.setAttribute("id", answer.getId().toString());
                rateElement.setAttribute("content", answer.getContent());
                rootElement.appendChild(rateElement);
            }
        }
        try {
            s = helper.convertXMLToString(document);
        } catch (TransformerException e) {
            e.printStackTrace();
            return "error " + e.getMessage();
        }
        try {
            s = helper.convertXMLToString(document);
        } catch (TransformerException e) {
            e.printStackTrace();
            return "error " + e.getMessage();
        }
        return s;
    }

    @Override
    public String getQuestionsXml(Integer stage) {
        String s = "";
        List<Question> questionList = storage.getQuestions(stage);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        org.w3c.dom.Document document = documentBuilder.newDocument();
        Element rootElement = document.createElement("data");
        document.appendChild(rootElement);
        if (questionList != null && questionList.size() > 0) {
            if (answerListSize == null)
                refreshAnswerList();
            List<Element> elementList = new ArrayList<>();
            for (Question question : questionList) {
                Element rateElement = document.createElement("question");
                rateElement.setAttribute("content", question.getContent());
                FileDescriptor image = question.getImage();
                if (image != null && question.getAnswer() != null) {
                    DateFormat df = new SimpleDateFormat("yyyy/MM/dd/");
                    String imageStr = connectionString + df.format(image.getCreateDate()) + image.getFileName();

                    rateElement.setAttribute("image", imageStr);

                    Element answersElement = document.createElement("answers");

                    List<Answer> answerList = getRandomAnswers(question.getAnswer());
                    if (answerList != null && answerList.size() == 4) {
                        for (Answer answer : answerList) {
                            Element answerElement = document.createElement("answer");
                            answerElement.setAttribute("content", answer.getContent());
                            if (answer.equals(question.getAnswer()))
                                answerElement.setAttribute("right", "1");
                            else
                                answerElement.setAttribute("right", "0");
                            answersElement.appendChild(answerElement);
                        }
                    }
                    rateElement.appendChild(answersElement);
                    elementList.add(rateElement);
                }
            }
            while (!elementList.isEmpty()) {
                Integer elementNumber = randInt(0, elementList.size() - 1);
                Element questionElement = elementList.get(elementNumber);
                rootElement.appendChild(questionElement);
                elementList.remove(questionElement);
            }
        }
        try {
            s = helper.convertXMLToString(document);
        } catch (TransformerException e) {
            e.printStackTrace();
            return "error " + e.getMessage();
        }
        try {
            s = helper.convertXMLToString(document);
        } catch (TransformerException e) {
            e.printStackTrace();
            return "error " + e.getMessage();
        }
        return s;
    }

    @Override
    public String getTopScoreXml(Integer count, Integer type, String sessionId) {
        String s = "";
        QuizType quizType = QuizType.fromId(type);
        List<Score> scoreList = null;
        if (quizType != null)
            scoreList = storage.getScores(count, quizType.getId());
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        org.w3c.dom.Document document = documentBuilder.newDocument();
        Element rootElement = document.createElement("data");
        document.appendChild(rootElement);
        if (scoreList != null && scoreList.size() > 0) {
            for (Score score : scoreList) {
                Element rateElement = document.createElement("score");
                if (score.getPoints() != null && score.getUser() != null) {
                    rateElement.setAttribute("points", score.getPoints().toString());
                    rateElement.setAttribute("user", score.getUser().getCaption());
                    rootElement.appendChild(rateElement);
                }
            }
        }
        //add personal high score
        if (sessionId != null) {
            User user = UserSessionProvider.getUserSession().getUser();
            if (user != null && quizType != null) {
                HashMap<String, String> map = storage.getScorePosition(quizType.getId(), user);
                rootElement.setAttribute("position", map.get("position"));
                rootElement.setAttribute("points", map.get("points"));
            }
        }

        try {
            s = helper.convertXMLToString(document);
        } catch (TransformerException e) {
            e.printStackTrace();
            return "error " + e.getMessage();
        }
        try {
            s = helper.convertXMLToString(document);
        } catch (TransformerException e) {
            e.printStackTrace();
            return "error " + e.getMessage();
        }
        return s;
    }

    @Override
    public String getScoreXml(Integer type, String sessionId) {
        String s = "";
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        org.w3c.dom.Document document = documentBuilder.newDocument();
        Element rootElement = document.createElement("data");
        document.appendChild(rootElement);
        QuizType quizType = QuizType.fromId(type);
        User user = UserSessionProvider.getUserSession().getUser();
        if (user != null && quizType != null) {
            //add personal high score
            HashMap<String, String> map = storage.getScorePosition(quizType.getId(), user);
            rootElement.setAttribute("position", map.get("position"));
            rootElement.setAttribute("points", map.get("points"));
        } else {
            rootElement.setAttribute("position", "");
            rootElement.setAttribute("points", "");
        }
        try {
            s = helper.convertXMLToString(document);
        } catch (TransformerException e) {
            e.printStackTrace();
            return "error " + e.getMessage();
        }
        try {
            s = helper.convertXMLToString(document);
        } catch (TransformerException e) {
            e.printStackTrace();
            return "error " + e.getMessage();
        }
        return s;
    }

    @Override
    public String addScore(Integer points, Integer type) {
        String response = "";
        QuizType quizType = QuizType.fromId(type);
        User user = UserSessionProvider.getUserSession().getUser();
        if (quizType != null && user != null) {
            Score score = storage.getScore(type, user);
            if (score == null) {
                score = new Score();
                score.setPoints(points);
                score.setUser(user);
                score.setQuizType(quizType);
                response = "add high score";
            } else {
                if (score.getPoints() < points) {
                    score.setPoints(points);
                    response = "new high score!";
                } else
                    response = "no high score";
            }
            storage.createOrUpdateEntity(score);
        } else return user != null ? "wrong quiz type!" : "user don't exist!";
        return response;
    }

    @Override
    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    @Override
    public String getConnectionString() {
        return this.connectionString;
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max + 1) - min) + min;
        return randomNum;
    }

    public List<Answer> getRandomAnswers(Answer wrightAnswer) {
        List<Answer> randomAnswerList = new ArrayList<>();
        if (answerListSize > 4) {
            while (randomAnswerList.size() < 4) {
                Answer answer = answerList.get(randInt(0, answerListSize - 1));
                if (!randomAnswerList.contains(answer) && !wrightAnswer.equals(answer))
                    randomAnswerList.add(answer);
            }
            randomAnswerList.add(randInt(0, 3), wrightAnswer);
            randomAnswerList.remove(4);
        }
        return randomAnswerList;
    }

    @Override
    public void refreshAnswerList() {
        answerList = storage.getAnswers();
        if (answerList != null)
            answerListSize = answerList.size();
    }
}