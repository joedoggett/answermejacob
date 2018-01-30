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


    //CALL `ithoughtiwasright`.`insert_question_answer`(<{p_question VARCHAR(1024)}>, <{p_answer VARCHAR(2048)}>, <{p_user_id INT}>);
    //CALL `ithoughtiwasright`.`insert_question_answer`('how far away is the nearest galaxy', 'technically, 0. you are in the milky way', 1);
    private static final String CALL_INSERT_QUESTION_ANSWER = "CALL `ithoughtiwasright`.`insert_question_answer`('PARAMETER_QUESTION_PARAMETER', 'PARAMETER_ANSWER_PARAMETER', 'PARAMETER_USER_ID_PARAMETER');";

    //CALL `ithoughtiwasright`.`insert_answer`(<{p_answer VARCHAR(2048)}>, <{p_question_id int}>, <{p_user_id int}>);
    //CALL `ithoughtiwasright`.`insert_answer`('spinatos', 7, 1);
    private static final String CALL_INSERT_ANSWER = "CALL `ithoughtiwasright`.`insert_answer`('PARAMETER_ANSWER_PARAMETER', PARAMETER_QUESTION_ID_PARAMETER, PARAMETER_USER_ID_PARAMETER);";

    //CALL `ithoughtiwasright`.`vote_answer`(<{p_user_id int}>, <{p_question_id int}>, <{p_answer_id int}>);
    //CALL `ithoughtiwasright`.`vote_answer`(1, 7, 12);
    private static final String CALL_VOTE_ANSWER = "CALL `ithoughtiwasright`.`vote_answer`(PARAMETER_USER_ID_PARAMETER, PARAMETER_QUESTION_ID_PARAMETER, PARAMETER_ANSWER_ID_PARAMETER);";

    //CALL `ithoughtiwasright`.`insert_question`(<{p_question VARCHAR(1024)}>, <{p_user_id int}>);
    //CALL `ithoughtiwasright`.`insert_question`('where is a good car service station', 1);
    private static final String CALL_INSERT_QUESTION = "CALL `ithoughtiwasright`.`insert_question`('PARAMETER_QUESTION_PARAMETER', PARAMETER_USER_ID_PARAMETER);";

    private static final String PARAMETER_QUESTION_PARAMETER = "PARAMETER_QUESTION_PARAMETER";
    private static final String PARAMETER_ANSWER_PARAMETER = "PARAMETER_ANSWER_PARAMETER";
    private static final String PARAMETER_USER_ID_PARAMETER = "PARAMETER_USER_ID_PARAMETER";
    private static final String PARAMETER_QUESTION_ID_PARAMETER = "PARAMETER_QUESTION_ID_PARAMETER";
    private static final String PARAMETER_ANSWER_ID_PARAMETER = "PARAMETER_ANSWER_ID_PARAMETER";

    private static final String GET_NOW = "CALL `ithoughtiwasright`.`get_now`();";
    //CALL `ithoughtiwasright`.`insert_user`(<{p_email VARCHAR(255)}>, <{p_password VARCHAR(512)}>);
    //CALL `ithoughtiwasright`.`insert_user`('doggett.joe@gmail.com', 'asecurepassword');
    //private static final String CALL_INSERT_USER = "CALL `ithoughtiwasright`.`insert_user`('PARAMETER_EMAIL_PARAMETER', 'PARAMETER_PASSWORD_PARAMETER');";
    //CALL `ithoughtiwasright`.`insert_user`(<{p_email VARCHAR(255)}>, <{p_password VARCHAR(512)}>, <{p_token_lock VARCHAR(128)}>);
    private static final String PARAMETER_TOKEN_PARAMETER = "PARAMETER_TOKEN_PARAMETER";
    private static final String CALL_INSERT_USER = "CALL `ithoughtiwasright`.`insert_user`('PARAMETER_EMAIL_PARAMETER', 'PARAMETER_PASSWORD_PARAMETER', 'PARAMETER_TOKEN_PARAMETER');";
    private static final String PARAMETER_EMAIL_PARAMETER = "PARAMETER_EMAIL_PARAMETER";
    private static final String PARAMETER_PASSWORD_PARAMETER = "PARAMETER_PASSWORD_PARAMETER";

    //CALL `ithoughtiwasright`.`get_vote_count`();
    private static final String CALL_GET_VOTE_COUNT = "CALL `ithoughtiwasright`.`get_vote_count`();";

    //CALL `ithoughtiwasright`.`get_questions`();
    private static final String CALL_GET_QUESTIONS = "CALL `ithoughtiwasright`.`get_questions`();";

    //CALL `ithoughtiwasright`.`get_user`(<{p_email varchar(255)}>);
    //CALL `ithoughtiwasright`.`get_user`('jdoggett@azdes.gov');
    private static final String CALL_GET_USER = "CALL `ithoughtiwasright`.`get_user`('PARAMETER_EMAIL_PARAMETER');";

    //CALL `ithoughtiwasright`.`update_user_password`(<{p_email VARCHAR(255)}>, <{p_password VARCHAR(512)}>);
    private static final String CALL_UPDATE_USER_PASSWORD = "CALL `ithoughtiwasright`.`update_user_password`('PARAMETER_EMAIL_PARAMETER', 'PARAMETER_PASSWORD_PARAMETER');";

    //CALL `ithoughtiwasright`.`get_yesterday_questions`();
    private static final String CALL_GET_YESTERDAY_QUESTIONS = "CALL `ithoughtiwasright`.`get_yesterday_questions`();";

    //CALL `ithoughtiwasright`.`get_yesterday_vote_count`();
    private static final String CALL_GET_YESTERDAY_VOTE_COUNT = "CALL `ithoughtiwasright`.`get_yesterday_vote_count`();";

    //CALL `ithoughtiwasright`.`get_configuration_properties`();
    private static final String CALL_GET_CONFIGURATION_PROPERTIES = "CALL `ithoughtiwasright`.`get_configuration_properties`();";

    /*
     * 	CALL `ithoughtiwasright`.`validate_user`(<{p_token_lock varchar(128)}>);

        CALL `ithoughtiwasright`.`validate_user`('27');

     */
    private static final String CALL_VALIDATE_USER = "CALL `ithoughtiwasright`.`validate_user`('PARAMETER_TOKEN_PARAMETER');";


    @Autowired
    JdbcTemplate jdbcTemplate;


    public String insertQuestion(String question, int userId) {
        //"CALL `ithoughtiwasright`.`insert_question_answer`('PARAMETER_QUESTION_PARAMETER', 'PARAMETER_ANSWER_PARAMETER', 'PARAMETER_USER_ID_PARAMETER');";

        return jdbcTemplate.queryForObject(CALL_INSERT_QUESTION.replace(PARAMETER_QUESTION_PARAMETER, question).replace(PARAMETER_USER_ID_PARAMETER, userId + ""), String.class);
    }

    public String voteAnswer(int userId, int questionId, int answerId) {
        return jdbcTemplate.queryForObject(CALL_VOTE_ANSWER.replace(PARAMETER_USER_ID_PARAMETER, userId + "").replace(PARAMETER_QUESTION_ID_PARAMETER, questionId + "").replace(PARAMETER_ANSWER_ID_PARAMETER, answerId + ""), String.class);
    }

    public String insertAnswer(String answer, int questionId, int userId) {
        return jdbcTemplate.queryForObject(CALL_INSERT_ANSWER.replace(PARAMETER_ANSWER_PARAMETER, answer).replace(PARAMETER_QUESTION_ID_PARAMETER, questionId + "").replace(PARAMETER_USER_ID_PARAMETER, userId + ""), String.class);
    }

    public String insertQuestionAnswer(String question, String answer, int userId) {
        return jdbcTemplate.queryForObject(CALL_INSERT_QUESTION_ANSWER.replace(PARAMETER_QUESTION_PARAMETER, question).replace(PARAMETER_ANSWER_PARAMETER, answer).replace(PARAMETER_USER_ID_PARAMETER, userId + ""), String.class);
    }

    public String getNow() {
        return jdbcTemplate.queryForObject(GET_NOW, String.class);
    }

    public String insertUser(String email, String password, String token) {
        return jdbcTemplate.queryForObject(CALL_INSERT_USER.replace(PARAMETER_EMAIL_PARAMETER, email).replace(PARAMETER_PASSWORD_PARAMETER, password).replace(PARAMETER_TOKEN_PARAMETER, token), String.class);
    }

    public String updateUserPassword(String email, String password) {
        return jdbcTemplate.queryForObject(CALL_UPDATE_USER_PASSWORD.replace(PARAMETER_EMAIL_PARAMETER, email).replace(PARAMETER_PASSWORD_PARAMETER, password), String.class);
    }

    public String validateUser(String token) {
        return jdbcTemplate.queryForObject(CALL_VALIDATE_USER.replace(PARAMETER_TOKEN_PARAMETER, token), String.class);
    }

    public List<Answer> getVoteCount() {
        List<Answer> answers = jdbcTemplate.query(CALL_GET_VOTE_COUNT, new BeanPropertyRowMapper<Answer>(Answer.class));
        return answers;
    }

    public List<Question> getQuestions() {
        List<Question> questions = jdbcTemplate.query(CALL_GET_QUESTIONS, new BeanPropertyRowMapper<Question>(Question.class));
        return questions;
    }

    public List<Answer> getYesterdayVoteCount() {
        List<Answer> yesterdayAnswers = jdbcTemplate.query(CALL_GET_YESTERDAY_VOTE_COUNT, new BeanPropertyRowMapper<Answer>(Answer.class));
        return yesterdayAnswers;
    }

    public List<Question> getYesterdayQuestions() {

        List<Question> yesterdayQuestions = jdbcTemplate.query(CALL_GET_YESTERDAY_QUESTIONS, new BeanPropertyRowMapper<Question>(Question.class));
        return yesterdayQuestions;
    }


    public Security getUser(String email) {
        List<Security> users = this.getUsers(email);
        if (users.isEmpty() || users.size() > 1) {
            return new Security();
        }
        return users.get(0);
    }

    private List<Security> getUsers(String email) {
        List<Security> users = jdbcTemplate.query(CALL_GET_USER.replace(PARAMETER_EMAIL_PARAMETER, email), new BeanPropertyRowMapper<Security>(Security.class));
        return users;
    }
}