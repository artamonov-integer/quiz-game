package com.integer.quiz.portal.api;

import com.haulmont.cuba.portal.restapi.Authentication;
import com.integer.quiz.service.QuizDataService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


@Controller
public class QuizDataController {

    private QuizDataService quizDataService;

    private Log log = LogFactory.getLog(getClass());

    @Autowired
    public QuizDataController(QuizDataService quizDataService) {
        this.quizDataService = quizDataService;
    }


    @RequestMapping(value = "/api/getAnswers", method = RequestMethod.GET)
    public void getAnswers(
            HttpServletRequest request,
            HttpServletResponse
                    response) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, TransformerException {
        try {
            log.info("request get answers come");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/xml");
            String resultStringData = quizDataService.getAnswersXml();
            response.getWriter().print(resultStringData);
            log.info("response send");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/api/getQuestions", method = RequestMethod.GET)
    public void getQuestion(
            @RequestParam(value = "q") String qualityStr,
            HttpServletRequest request,
            HttpServletResponse
                    response) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, TransformerException {
        try {
            log.info("request get questions come");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/xml");
//            Integer stage;
//            try {
//                stage = Integer.parseInt(stageStr);
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//                response.setContentType("text/html");
//                response.getWriter().print("wrong stage!");
//                return;
//            }
            String resultStringData = quizDataService.getQuestionsXml(qualityStr);
            response.getWriter().print(resultStringData);
            log.info("response send");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /*@RequestMapping(value = "/api/getTopScore", method = RequestMethod.GET)
    public void getTopScore(
            @RequestParam(value = "n") String countStr,
            @RequestParam(value = "t") String typeStr,
            HttpServletRequest request,
            HttpServletResponse
                    response) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, TransformerException {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/xml");
            Integer count;
            Integer type;
            try {
                count = Integer.parseInt(countStr);
                type = Integer.parseInt(typeStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.setContentType("text/html");
                response.getWriter().print("wrong number parameter!");
                return;
            }
            String resultStringData = quizDataService.getTopScoreXml(count, type, null);
            response.getWriter().print(resultStringData);

        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }*/

    @RequestMapping(value = "/api/getScore", method = RequestMethod.GET)
    public void getScore(
            @RequestParam(value = "s") String sessionId,
//            @RequestParam(value = "t") String typeStr,
            HttpServletRequest request,
            HttpServletResponse
                    response) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, TransformerException {
        log.info("request get score come");
        Authentication authentication = Authentication.me(sessionId);
        if (authentication == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/xml");
//            Integer count;
//            Integer type;
//            try {
//                type = Integer.parseInt(typeStr);
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//                response.setContentType("text/html");
//                response.getWriter().print("wrong number parameter!");
//                return;
//            }
            String resultStringData = quizDataService.getScoreXml(sessionId);
            response.getWriter().print(resultStringData);
            log.info("response send");

        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } finally {
            authentication.forget();
        }
    }

    @RequestMapping(value = "/api/addScore", method = RequestMethod.GET)
    public void addScore(
            @RequestParam(value = "s") String sessionId,
            @RequestParam(value = "p") String pointsStr,
            @RequestParam(value = "t") String typeStr,
            HttpServletRequest request,
            HttpServletResponse
                    response) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, TransformerException {
        log.info("request add score come");
        Authentication authentication = Authentication.me(sessionId);
        if (authentication == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            Integer points;
            Integer type;
            try {
                points = Integer.parseInt(pointsStr);
                type = Integer.parseInt(typeStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.getWriter().print("wrong number parameter!");
                return;
            }
            String resultStringData = quizDataService.addScore(points, type);
            response.getWriter().print(resultStringData);
            log.info("response send");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } finally {
            authentication.forget();
        }
    }

    @RequestMapping(value = "/api/signUp", method = RequestMethod.GET)
    public void signUp(
            @RequestParam(value = "l") String login,
            @RequestParam(value = "e") String email,
            HttpServletRequest request,
            HttpServletResponse
                    response) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, TransformerException {
        try {
            log.info("request sign up come");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            String resultStringData = quizDataService.signUp(login, email);
            response.getWriter().print(resultStringData);
            log.info("response send");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/api/confirmRegistration", method = RequestMethod.GET)
    public void confirmRegistration(
            @RequestParam(value = "id") String id,
            HttpServletRequest request,
            HttpServletResponse
                    response) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, TransformerException {
        try {
            log.info("request confirm registration come");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            String resultStringData = quizDataService.confirmRegistration(id);
            response.getWriter().print(resultStringData);
            log.info("response send");

        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}