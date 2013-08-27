package com.integer.quiz.app;

import com.haulmont.cuba.core.*;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.MetadataProvider;
import com.haulmont.cuba.security.entity.User;
import com.integer.quiz.entity.Answer;
import com.integer.quiz.entity.Question;
import com.integer.quiz.entity.Score;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@ManagedBean(Storage.NAME)
public class StorageBean implements Storage {

    @Inject
    private Persistence persistence;

    @Override
    public <E extends StandardEntity> void createOrUpdateEntity(E entity) {
        Transaction tx = persistence.createTransaction();
        EntityManager entitymanager = persistence.getEntityManager();
        try {
            E newentity = entitymanager.merge(entity);
            tx.commit();
//            return newentity;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tx.end();
        }
    }

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
    public List<Score> getScores(Integer count, Integer type) {
        Transaction tx = persistence.createTransaction();
        EntityManager em = persistence.getEntityManager();
        List<Score> resultList = null;
        em.setView(MetadataProvider.getViewRepository().getView(Score.class, "score.edit"));
        try {
            TypedQuery q = em.createQuery("SELECT score FROM quiz$Score score where score.points is not null" +
                    " and score.user is not null and score.quizType=?1 order by score.points desc, score.user.login", Score.class);
            q.setParameter(1, type);
            q.setMaxResults(count);
            resultList = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tx.end();
        }
        return resultList;
    }

    @Override
    public Score getScore(Integer type, User user) {
        Transaction tx = persistence.createTransaction();
        EntityManager em = persistence.getEntityManager();
        try {
            TypedQuery q = em.createQuery("select score from quiz$Score score where score.quizType = ?1 and score.user.id = ?2", Score.class);
            q.setParameter(1, type);
            q.setParameter(2, user.getId());
            List resultList = q.getResultList();
            if ((resultList != null) && !resultList.isEmpty())
                return (Score) resultList.get(0);
            else return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            tx.end();
        }
    }

    @Override
    public HashMap<String, String> getScorePosition(Integer type, User user) {
        HashMap<String, String> map = new HashMap<>();
        Score score = getScore(type, user);
        map.put("position", "");
        map.put("points", "");
        if (score != null && score.getPoints() != null) {
            Long position = getScorePosition(score.getPoints(),type);
            if (position != null) {
                map.put("position", position.toString());
                map.put("points", score.getPoints().toString());
            }
        }
        return map;
    }

    public Long getScorePosition(Integer points, Integer type) {
        Long position = null;
        Transaction tx = persistence.createTransaction();
        try {
            EntityManager em = persistence.getEntityManager();
            Query q = em.createQuery("select count(score.id) from quiz$Score score where score.points > ?1" +
                    " and score.quizType = ?2");
            q.setParameter(1, points);
            q.setParameter(2,type);
            position = (Long) q.getSingleResult() + 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tx.end();
        }
        return position;
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
