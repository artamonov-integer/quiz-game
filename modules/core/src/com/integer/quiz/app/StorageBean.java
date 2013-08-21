package com.integer.quiz.app;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.MetadataProvider;
import com.integer.quiz.entity.Answer;
import com.integer.quiz.entity.Question;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ManagedBean(Storage.NAME)
public class StorageBean implements Storage {

    @Inject
    private Persistence persistence;

    @Override
    public List<Answer> getAnswers() {
        Transaction tx = persistence.createTransaction();
        EntityManager em = persistence.getEntityManager();
        List<Answer> resultlist = null;
        try {
            Query q = em.createQuery("SELECT answer FROM quiz$Answer answer order by answer.content");
            resultlist = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tx.end();
        }
        return resultlist;
    }

    @Override
    public List<Question> getQuestions(Integer stage) {
        Transaction tx = persistence.createTransaction();
        EntityManager em = persistence.getEntityManager();
        List<Question> resultlist = null;
        em.setView(MetadataProvider.getViewRepository().getView(Question.class, "question.edit"));
        try {
            Query q = em.createQuery("SELECT question FROM quiz$Question question where question.stage=?1");
            q.setParameter(1, stage);
            resultlist = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tx.end();
        }
        return resultlist;
    }

    @Override
    public List<Answer> getRandomAnswers(Answer wrightAnswer) {
        Transaction tx = persistence.createTransaction();
        EntityManager em = persistence.getEntityManager();
        List<Answer> randomAnswerList = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT answer FROM quiz$Answer answer order by answer.content");
            List<Answer> resultlist = q.getResultList();
            if ((resultlist != null) && resultlist.size() > 4) {
                while (randomAnswerList.size() < 4) {
                    Answer answer = resultlist.get(randInt(0, resultlist.size() - 1));
                    if (!randomAnswerList.contains(answer) && !wrightAnswer.equals(answer))
                        randomAnswerList.add(answer);
                }
                randomAnswerList.add(randInt(0, 3), wrightAnswer);
                randomAnswerList.remove(4);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tx.end();
        }
        return randomAnswerList;
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max + 1) - min) + min;
        return randomNum;
    }
}
