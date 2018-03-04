package com.joe.answermejacob.AnswerMeJacob.dao;

import com.joe.ithoughtiwasrightgeneratedclasses.Answer;
import com.joe.ithoughtiwasrightgeneratedclasses.Question;
import com.joe.ithoughtiwasrightgeneratedclasses.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RightDAO implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2921665266939271019L;


    private static final String CALL_INSERT_QUESTION = "CALL `ithoughtiwasright`.`insert_question`(?, ?);";
    private static final String CALL_VOTE_ANSWER = "CALL `ithoughtiwasright`.`vote_answer`(?, ?, ?);";
    private static final String CALL_INSERT_ANSWER = "CALL `ithoughtiwasright`.`insert_answer`(?, ?, ?);";
    private static final String CALL_INSERT_QUESTION_ANSWER = "CALL `ithoughtiwasright`.`insert_question_answer`(?, ?, ?);";
    private static final String CALL_INSERT_USER = "CALL `ithoughtiwasright`.`insert_user`(?, ?, ?);";
    private static final String CALL_UPDATE_USER_PASSWORD = "CALL `ithoughtiwasright`.`update_user_password`(?, ?);";
    private static final String CALL_VALIDATE_USER = "CALL `ithoughtiwasright`.`validate_user`(?);";
    private static final String GET_NOW = "CALL `ithoughtiwasright`.`get_now`();";
    private static final String CALL_GET_VOTE_COUNT = "CALL `ithoughtiwasright`.`get_vote_count`();";
    private static final String CALL_GET_QUESTIONS = "CALL `ithoughtiwasright`.`get_questions`();";
    private static final String CALL_GET_YESTERDAY_QUESTIONS = "CALL `ithoughtiwasright`.`get_yesterday_questions`();";
    private static final String CALL_GET_YESTERDAY_VOTE_COUNT = "CALL `ithoughtiwasright`.`get_yesterday_vote_count`();";
    private static final String CALL_GET_USER = "CALL `ithoughtiwasright`.`get_user`(?);";


    @Autowired
    JdbcTemplate jdbcTemplate;



    public String insertQuestion(String question, int userId)
    {
        return jdbcTemplate.queryForObject(CALL_INSERT_QUESTION, new Object[]{question, userId}, String.class);
    }


    public String voteAnswer(int userId, int questionId, int answerId)
    {
        return jdbcTemplate.queryForObject(CALL_VOTE_ANSWER, new Object[]{userId, questionId, answerId}, String.class);
    }


    public String insertAnswer(String answer, int questionId, int userId)
    {
        return jdbcTemplate.queryForObject(CALL_INSERT_ANSWER, new Object[]{answer, questionId, userId}, String.class);
    }


    public String insertQuestionAnswer(String question, String answer, int userId)
    {
        return jdbcTemplate.queryForObject(CALL_INSERT_QUESTION_ANSWER, new Object[]{question, answer, userId}, String.class);
    }

    public String getNow() {
        return jdbcTemplate.queryForObject(GET_NOW, String.class);
    }



    public String insertUser(String email, String password, String token)
    {
        return jdbcTemplate.queryForObject(CALL_INSERT_USER, new Object[]{email, password, token}, String.class);
    }


    public String updateUserPassword(String email, String password)
    {
        return jdbcTemplate.queryForObject(CALL_UPDATE_USER_PASSWORD, new Object[]{email, password}, String.class);
    }


    public String validateUser(String token)
    {
        return jdbcTemplate.queryForObject(CALL_VALIDATE_USER, new Object[]{token}, String.class);
    }

    public List<Answer> getVoteCount()
    {
        List<Answer> answers = jdbcTemplate.query(CALL_GET_VOTE_COUNT, new BeanPropertyRowMapper<Answer>(Answer.class));
        return answers;
    }

    public List<Question> getQuestions() {
        List<Question> questions = jdbcTemplate.query(CALL_GET_QUESTIONS, new BeanPropertyRowMapper<Question>(Question.class));
        return questions;
    }

    public List<Answer> getYesterdayVoteCount()
    {
        List<Answer> yesterdayAnswers = jdbcTemplate.query(CALL_GET_YESTERDAY_VOTE_COUNT, new BeanPropertyRowMapper<Answer>(Answer.class));
        return yesterdayAnswers;
    }

    public List<Question> getYesterdayQuestions()
    {

        List<Question> yesterdayQuestions = jdbcTemplate.query(CALL_GET_YESTERDAY_QUESTIONS, new BeanPropertyRowMapper<Question>(Question.class));
        return yesterdayQuestions;
    }


    public Security getUser(String email)
    {
        List<Security> users = this.getUsers(email);
        if (users.isEmpty() || users.size() > 1)
        {
            return new Security();
        }
        return users.get(0);
    }


    private List<Security> getUsers(String email)
    {
        List<Security> users = jdbcTemplate.query(CALL_GET_USER, new Object[]{email}, new BeanPropertyRowMapper<Security>(Security.class));
        return users;
    }
}