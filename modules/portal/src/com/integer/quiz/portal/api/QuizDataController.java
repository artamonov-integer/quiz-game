package com.integer.quiz.portal.api;

import com.integer.quiz.service.QuizDataService;
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
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/xml");
            String resultStringData = quizDataService.getAnswersXml();
            response.getWriter().print(resultStringData);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/api/getQuestions", method = RequestMethod.GET)
    public void getQuestion(
            @RequestParam(value = "s") String stageStr,
            HttpServletRequest request,
            HttpServletResponse
                    response) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, TransformerException {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/xml");
            Integer stage;
            try {
                stage = Integer.parseInt(stageStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.getWriter().print("wrong stage!");
                return;
            }
            String resultStringData = quizDataService.getQuestionsXml(stage);
            response.getWriter().print(resultStringData);

        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/api/getScore", method = RequestMethod.GET)
    public void getScore(
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
                response.getWriter().print("wrong number parameter!");
                return;
            }
            String resultStringData = quizDataService.getScoreXml(count, type);
//            String resultStringData = "test";
            response.getWriter().print(resultStringData);

        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}