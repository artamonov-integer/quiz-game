package com.integer.quiz.app;

import com.haulmont.cuba.core.*;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.MetadataProvider;
import com.haulmont.cuba.security.entity.Group;
import com.haulmont.cuba.security.entity.Role;
import com.haulmont.cuba.security.entity.User;
import com.integer.quiz.entity.*;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import java.util.*;

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
    public List<Question> getQuestions() {
        Transaction tx = persistence.createTransaction();
        EntityManager em = persistence.getEntityManager();
        List<Question> resultlist = null;
        em.setView(MetadataProvider.getViewRepository().getView(Question.class, "question.edit"));
        try {
            Query q = em.createQuery("SELECT question FROM quiz$Question question");
//            q.setParameter(1, stage);
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
            Long position = getScorePosition(score.getPoints(),type, user);
            if (position != null) {
                map.put("position", position.toString());
                map.put("points", score.getPoints().toString());
            }
        }
        return map;
    }

    @Override
    public Group getGroupByName(String name){
        Transaction tx = persistence.createTransaction();
        EntityManager em = persistence.getEntityManager();
        try {
            TypedQuery q = em.createQuery("select gr from sec$Group gr where gr.name = ?1", Group.class);
            q.setParameter(1, name);
            List resultList = q.getResultList();
            if ((resultList != null) && !resultList.isEmpty())
                return (Group) resultList.get(0);
            else return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            tx.end();
        }
    }

    @Override
    public Group addGroup(String name){
        Group group = new Group();
        group.setName(name);
        createOrUpdateEntity(group);
        return group;
    }

    @Override
    public Role getRoleByName(String name){
        Transaction tx = persistence.createTransaction();
        EntityManager em = persistence.getEntityManager();
        try {
            TypedQuery q = em.createQuery("select role from sec$Role role where role.name = ?1", Role.class);
            q.setParameter(1, name);
            List resultList = q.getResultList();
            if ((resultList != null) && !resultList.isEmpty())
                return (Role) resultList.get(0);
            else return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            tx.end();
        }
    }

    @Override
    public Role addRole(String name){
        Role role = new Role();
        role.setName(name);
        createOrUpdateEntity(role);
        return role;
    }

    @Override
    public User getUserById(UUID id){
        Transaction tx = persistence.createTransaction();
        EntityManager em = persistence.getEntityManager();
        try {
            TypedQuery q = em.createQuery("select user from sec$User user where user.id = ?1", User.class);
            q.setView(MetadataProvider.getViewRepository().getView(User.class, "user.edit"));
            q.setParameter(1, id);
            List resultList = q.getResultList();
            if ((resultList != null) && !resultList.isEmpty())
                return (User) resultList.get(0);
            else return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            tx.end();
        }
    }

    @Override
    public User getUserByMailLogin(String login, String email){
        Transaction tx = persistence.createTransaction();
        EntityManager em = persistence.getEntityManager();
        try {
            TypedQuery q = em.createQuery("select user from sec$User user where (user.login = ?1 or user.email = ?2)" +
                    " and user.deleteTs is null", User.class);
            q.setParameter(1, login);
            q.setParameter(2, email);
            List resultList = q.getResultList();
            if ((resultList != null) && !resultList.isEmpty())
                return (User) resultList.get(0);
            else return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            tx.end();
        }
    }

    @Override
    public List<Score> getNeighborScores(Integer count, Integer type, User user){
        Transaction tx = persistence.createTransaction();
        EntityManager em = persistence.getEntityManager();
        List<Score> resultList = new ArrayList<>();
        List<Score> scoreList = null;
        Score score = getScore(type, user);
        em.setView(MetadataProvider.getViewRepository().getView(Score.class, "score.edit"));
        TypedQuery<Score> q;
        try {
            q = em.createQuery("SELECT score FROM quiz$Score score where (score.points > ?2" +
                    " or (score.points = ?2 and score.user.login < ?3)) and score.quizType=?1" +
                    " order by score.points, score.user.login desc", Score.class);
            q.setParameter(1, type);
            q.setParameter(2, score.getPoints());
            q.setParameter(3, user.getLogin());
            q.setMaxResults(count);
            scoreList = q.getResultList();
            if(!scoreList.isEmpty()){
                Integer size = scoreList.size()-1;
                while (size>=0) {
                    resultList.add(scoreList.get(size));
                    size--;
                }
            }
            q = em.createQuery("SELECT score FROM quiz$Score score where (score.points < ?2" +
                    " or (score.points = ?2 and score.user.login >= ?3)) and score.quizType=?1" +
                    " order by score.points desc, score.user.login", Score.class);
            q.setParameter(1, type);
            q.setParameter(2, score.getPoints());
            q.setParameter(3, user.getLogin());
            q.setMaxResults(count+1);
            scoreList = q.getResultList();
            if(!scoreList.isEmpty()){
                for (Score s:scoreList)
                    resultList.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tx.end();
        }
        return resultList;
    }

    public Long getScorePosition(Integer points, Integer type, User user) {
        Long position = null;
        Transaction tx = persistence.createTransaction();
        try {
            EntityManager em = persistence.getEntityManager();
            Query q = em.createQuery("select count(score.id) from quiz$Score score where (score.points > ?1" +
                    " or (score.points = ?1 and score.user.login < ?3)) and score.quizType = ?2");
            q.setParameter(1, points);
            q.setParameter(2, type);
            q.setParameter(3, user.getLogin());
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

    @Override
    public void addScoreHistory(Integer points, QuizType quizType, User user){
        ScoreHistory scoreHistory = new ScoreHistory();
        scoreHistory.setPoints(points);
        scoreHistory.setQuizType(quizType);
        scoreHistory.setUser(user);
        createOrUpdateEntity(scoreHistory);
    }
}
