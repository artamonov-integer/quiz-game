package com.integer.quiz.service;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.UserSessionProvider;
import com.haulmont.cuba.core.sys.encryption.Sha1EncryptionModule;
import com.haulmont.cuba.security.entity.Group;
import com.haulmont.cuba.security.entity.Role;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.entity.UserRole;
import com.integer.quiz.app.Storage;
import com.integer.quiz.app.XMLHelper;
import com.integer.quiz.entity.Answer;
import com.integer.quiz.entity.Question;
import com.integer.quiz.entity.QuizType;
import com.integer.quiz.entity.Score;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(QuizDataService.NAME)
public class QuizDataServiceBean implements QuizDataService {

    @Inject
    private Storage storage;

    @Inject
    private XMLHelper helper;

    //    String connectionString = "http://localhost:8383/";
    String port = "8080";
    String imagePort = "8383";
    String host = "localhost";

    Group participantGroup = null;
    Role participantRole = null;

    List<Answer> answerList = null;
    Integer answerListSize = null;

    String d_email = "quiz.game2013@yandex.ru",
            d_password = "quizinteger",
            d_host = "smtp.yandex.com",
            d_port = "465",
            m_subject = "Welcome to Game Quiz!",
            m_text = "<a href=\"@link\">Confirm Link</a>";


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
        return s;
    }

    @Override
    public String getQuestionsXml(String quality) {
        String s = "";
        List<Question> questionList = storage.getQuestions();
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
            //for (int i = 0; i < 100; i++)
            for (Question question : questionList) {
                Element rateElement = document.createElement("question");
                rateElement.setAttribute("content", question.getContent());

                FileDescriptor image = null;
                switch (quality) {
                    case "10":
                        image = question.getImage();
                        break;
                    case "20":
                        image = question.getImageMid();
                        break;
                    case "30":
                        image = question.getImageHigh();
                        break;
                    default:
                        image = null;
                        break;
                }

                if (image != null && question.getAnswer() != null) {
                    DateFormat df = new SimpleDateFormat("yyyy/MM/dd/");
                    String imageStr = "http://" + host + ":" + imagePort + "/" + df.format(image.getCreateDate()) + image.getFileName();

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
        return s;
    }

    @Override
    public String getScoreXml(String sessionId) {
        String s = "";
        List<Score> scoreList = null;
        Integer countTop = 10;
        Integer countNeighbor = 2;
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
        QuizType[] quizTypeList = QuizType.values();
        for (QuizType quizType : quizTypeList) {
            Integer position = 1;
            Element quizElement = document.createElement("quiz");
            quizElement.setAttribute("type", quizType.getId().toString());
            rootElement.appendChild(quizElement);
            //add personal high score
            if (sessionId != null) {
                User user = UserSessionProvider.getUserSession().getUser();
                if (user != null && quizType != null) {
                    HashMap<String, String> map = storage.getScorePosition(quizType.getId(), user);
                    quizElement.setAttribute("points", map.get("points"));
                    quizElement.setAttribute("user", user.getLogin());
                    quizElement.setAttribute("position", map.get("position"));

                    Integer userPosition = null;
                    if (!map.get("position").isEmpty()) {
                        try {
                            userPosition = Integer.parseInt(map.get("position"));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    //neighbor
                    if (userPosition != null && userPosition > countTop) {
                        scoreList = storage.getScores(countTop-countNeighbor*2-1, quizType.getId());
                        if (scoreList != null && scoreList.size() > 0) {
                            for (Score score : scoreList) {
                                Element rateElement = document.createElement("score");
                                if (score.getPoints() != null && score.getUser() != null) {
                                    rateElement.setAttribute("points", score.getPoints().toString());
                                    rateElement.setAttribute("user", score.getUser().getLogin());
                                    rateElement.setAttribute("position", position.toString());
                                    quizElement.appendChild(rateElement);
                                    position++;
                                }
                            }
                        }
                        position = userPosition-countNeighbor;
                        scoreList = storage.getNeighborScores(countNeighbor, quizType.getId(), user);
                        if (scoreList != null && scoreList.size() > 0) {
                            for (Score score : scoreList) {
                                Element rateElement = document.createElement("score");
                                if (score.getPoints() != null && score.getUser() != null) {
                                    rateElement.setAttribute("points", score.getPoints().toString());
                                    rateElement.setAttribute("user", score.getUser().getLogin());
                                    rateElement.setAttribute("position", position.toString());
                                    quizElement.appendChild(rateElement);
                                    position++;
                                }
                            }
                        }
                    } else {
                        scoreList = storage.getScores(countTop, quizType.getId());
                        if (scoreList != null && scoreList.size() > 0) {
                            for (Score score : scoreList) {
                                Element rateElement = document.createElement("score");
                                if (score.getPoints() != null && score.getUser() != null) {
                                    rateElement.setAttribute("points", score.getPoints().toString());
                                    rateElement.setAttribute("user", score.getUser().getLogin());
                                    rateElement.setAttribute("position", position.toString());
                                    quizElement.appendChild(rateElement);
                                    position++;
                                }
                            }
                        }
                    }
                }
            }
        }

        try {
            s = helper.convertXMLToString(document);
        } catch (TransformerException e) {
            e.printStackTrace();
            return "error " + e.getMessage();
        }
        return s;
    }

    /*@Override
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
        return s;
    } */

    @Override
    public String addScore(Integer points, Integer type) {
        String response = "";
        QuizType quizType = QuizType.fromId(type);
        User user = UserSessionProvider.getUserSession().getUser();
        if (quizType != null && user != null) {
            storage.addScoreHistory(points, quizType, user);
            Score score = storage.getScore(type, user);
            if (score == null) {
                score = new Score();
                score.setPoints(points);
                score.setUser(user);
                score.setQuizType(quizType);
                response = "add record";
            } else {
                if (score.getPoints() < points) {
                    score.setPoints(points);
                    response = "new record";
                } else
                    response = "no record";
            }
            storage.createOrUpdateEntity(score);
        } else return user != null ? "wrong quiz type" : "no user in database";
        return response;
    }

    @Override
    public void setConnectionSettings(String host, String port, String imagePort) {
        this.host = host;
        this.port = port;
        this.imagePort = imagePort;
    }

    @Override
    public HashMap<String, String> getConnectionSettings() {
        HashMap<String, String> map = new HashMap<>();
        map.put("host", this.host);
        map.put("port", this.port);
        map.put("imagePort", this.imagePort);
        return map;
    }

    @Override
    public void setMailSettings(String mail, String password, String host, String port, String subject, String text){
        this.d_email = mail;
        this.d_password = password;
        this.d_host = host;
        this.d_port = port;
        this.m_subject = subject;
        this.m_text = text;
    }

    @Override
    public HashMap<String, String> getMailSettings() {
        HashMap<String, String> map = new HashMap<>();
        map.put("email", this.d_email);
        map.put("password", this.d_password);
        map.put("host", this.d_host);
        map.put("port", this.d_port);
        map.put("subject", this.m_subject);
        map.put("text", this.m_text);
        return map;
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

    @Override
    public String signUp(String login, String email) {
        //check login and email

        //create user
        if (!login.isEmpty() && !email.isEmpty()) {
            User checkUser = storage.getUserByMailLogin(login, email);
            if (checkUser == null) {
                User user = new User();
                user.setEmail(email);
                user.setLogin(login);
                user.setActive(false);
            /*Sha1EncryptionModule sha = new Sha1EncryptionModule();
            String password = sha.getPasswordHash(user.getId(), email);
            user.setPassword(password);
            if (this.participantGroup == null)
                refreshParticipantGroup();
            user.setGroup(this.participantGroup);
            if (this.participantRole == null)
                refreshRole();
            */

            /*UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(this.participantRole);
            storage.createOrUpdateEntity(userRole);*/
                //send link on email
                Boolean isSend = sendLink(getMailText(user.getId().toString()), user.getEmail());
                if (isSend) {
                    storage.createOrUpdateEntity(user);
                    return "confirm link was send";
                } else
                    return "confirm link not send";
            } else
                return "user already exist";
        }
        return "empty data";
    }

    @Override
    public String confirmRegistration(String id) {
        //sendLink();
        if (!id.isEmpty()) {
            UUID uuid = UUID.fromString(id);
            User user = storage.getUserById(uuid);
            if (user != null && user.getEmail() != null && user.getGroup() == null) {
                //password
                Sha1EncryptionModule sha = new Sha1EncryptionModule();
                String password = sha.getPasswordHash(user.getId(), user.getEmail());
                user.setPassword(password);
                //group
                if (this.participantGroup == null)
                    refreshParticipantGroup();
                user.setGroup(this.participantGroup);
                //role
                if (this.participantRole == null)
                    refreshRole();
                UserRole userRole = new UserRole();
                userRole.setUser(user);
                userRole.setRole(this.participantRole);
                storage.createOrUpdateEntity(userRole);
                user.setActive(true);
                storage.createOrUpdateEntity(user);
                return "registration confirm";
            } else
                return "registration already confirm";
        }
        return "registration not confirm";
    }

    private void refreshParticipantGroup() {
        this.participantGroup = storage.getGroupByName("Quiz Participant");
        if (this.participantGroup == null)
            this.participantGroup = storage.addGroup("Quiz Participant");
    }

    private void refreshRole() {
        this.participantRole = storage.getRoleByName("Participants");
        if (this.participantRole == null)
            this.participantRole = storage.addRole("Participants");
    }

    private Boolean sendLink(String text, String mail_to) {
        Properties props = new Properties();
        props.put("mail.smtp.user", d_email);
        props.put("mail.smtp.host", d_host);
        props.put("mail.smtp.port", d_port);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.socketFactory.port", d_port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        //SecurityManager security = System.getSecurityManager();

        try {
            Authenticator auth = new SMTPAuthenticator(d_email, d_password);
            Session session = Session.getInstance(props, auth);
            session.setDebug(true);
            MimeMessage msg = new MimeMessage(session);
            msg.setContent(text, "text/html");
            msg.setSubject(m_subject);
            msg.setFrom(new InternetAddress(d_email));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mail_to));
            Transport.send(msg);
        } catch (Exception mex) {
            mex.printStackTrace();
            return false;
        }
        return true;
    }

    private String getMailText(String id) {
        String link = "http://" + host + ":" + port + "/" + "app-portal/api/confirmRegistration?id=" + id;
        String text = this.m_text.replace("@link", link);
        return text;
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {

        String d_email;
        String d_password;

        public SMTPAuthenticator(String email, String password) {
            this.d_email = email;
            this.d_password = password;
        }

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(d_email, d_password);
        }
    }
}