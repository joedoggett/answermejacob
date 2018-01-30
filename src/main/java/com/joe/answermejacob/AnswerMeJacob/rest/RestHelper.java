package com.joe.answermejacob.AnswerMeJacob.rest;


import com.joe.answermejacob.AnswerMeJacob.dao.RightDAO;
import com.joe.answermejacob.AnswerMeJacob.rightfactory.RightFactory;
import com.joe.answermejacob.AnswerMeJacob.service.AuthenticationService;
import com.joe.answermejacob.AnswerMeJacob.service.NonceService;
import com.joe.answermejacob.AnswerMeJacob.service.RightValidationService;

import com.joe.answermejacob.AnswerMeJacob.util.AuthenticationSecurityUtility;
import com.joe.answermejacob.AnswerMeJacob.util.RandomGenerationUtility;
import com.joe.answermejacob.AnswerMeJacob.util.SendAuthenticatedEmailUtility;
import com.joe.ithoughtiwasrightgeneratedclasses.Answer;
import com.joe.ithoughtiwasrightgeneratedclasses.Question;
import com.joe.ithoughtiwasrightgeneratedclasses.Right;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RestHelper implements java.io.Serializable
{


    private static final long serialVersionUID = -8659847266898251832L;
    private static final String SUCCESSFUL_AUTHENTICATION_CODE = "1";
    private static final int NONCE_LENGTH = 128;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RightValidationService rightValidationService;

    @Autowired
    RandomGenerationUtility randomGenerationUtility;

    @Autowired
    RightDAO rightDAO;

    @Autowired
    RightFactory rightFactory;

    @Autowired
    NonceService nonceService;

    @Autowired
    AuthenticationSecurityUtility authenticationSecurityUtility;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    SendAuthenticatedEmailUtility sendAuthenticatedEmailUtility;


    public Right insertQuestion(Right right)
    {
        if(rightValidationService.validateObject(right.getSingleQuestionAnswer().getQuestion()))
        {
            String result = this.rightDAO.insertQuestion(right.getSingleQuestionAnswer().getQuestion().getQuestion(), right.getSecurity().getUserId());
            return this.returnRightResult(result, right.getSecurity().getUserName(), right.getSecurity().getUserId());
        }
        return rightFactory.createRight();
    }

    public Right voteAnswer(Right right)
    {
        /*
         * validation of user id, question id, and answer id on db.
         */
        String result = this.rightDAO.voteAnswer(right.getSecurity().getUserId(),
                right.getSingleQuestionAnswer().getQuestion().getQuestionId(),
                right.getSingleQuestionAnswer().getAnswer().getAnswerId());


        return this.returnRightResult(result, right.getSecurity().getUserName(), right.getSecurity().getUserId());
    }

    public Right insertAnswer(Right right)
    {
        /*
         * additional validation on db.
         */
        if(rightValidationService.validateObject(right.getSingleQuestionAnswer().getAnswer()))
        {
            String result = this.rightDAO.insertAnswer(right.getSingleQuestionAnswer().getAnswer().getAnswer(),
                    right.getSingleQuestionAnswer().getQuestion().getQuestionId(),
                    right.getSecurity().getUserId());

            return this.returnRightResult(result, right.getSecurity().getUserName(), right.getSecurity().getUserId());
        }
        return rightFactory.createRight();
    }

    /**
     * not currently used.
     * @param right
     * @return
     */
    public Right insertQuestionAnswer(Right right)
    {
        String result = this.rightDAO.insertQuestionAnswer(right.getSingleQuestionAnswer().getQuestion().getQuestion(),
                right.getSingleQuestionAnswer().getAnswer().getAnswer(),
                right.getSecurity().getUserId());

        return this.returnRightResult(result, right.getSecurity().getUserName(), right.getSecurity().getUserId());
    }

    public Right registerUser(Right right)
    {
        if(rightValidationService.validateObject(right.getSecurity()))
        {
            String token = randomGenerationUtility.randomCharacterGeneration(NONCE_LENGTH);
            String result = this.rightDAO.insertUser(right.getSecurity().getUserName(), authenticationSecurityUtility.createDigest(right.getSecurity().getPassword()), token);
            if(result.equals(SUCCESSFUL_AUTHENTICATION_CODE))
            {
                log.info("would have sent email based on successful authentication code");
                sendAuthenticatedEmailUtility.sendValidateRegistrationEmail(right.getSecurity().getUserName(), token);
            }
            return this.returnRightResult(result, null, -1);
        }
        return rightFactory.createRight();
    }

    public Right login(Right right)
    {
        Right rightResult = authenticationService.login(right.getSecurity().getUserName(), right.getSecurity().getPassword());
        return this.returnRightResult(rightResult.getRightResponse().getCode(), rightResult.getSecurity().getUserName(), rightResult.getSecurity().getUserId());
    }



    /**
     * logic is to perform login on user. if good, update password. if failures, done.
     * @param right
     * @return
     */
    public Right changePassword(Right right)
    {
        if(rightValidationService.validateObject(right.getChangePassword()))
        {
            Right login = authenticationService.login(right.getChangePassword().getUserName(), right.getChangePassword().getOldPassword());
            if(login.getRightResponse().getCode().equals(SUCCESSFUL_AUTHENTICATION_CODE))
            {
                String result = this.rightDAO.updateUserPassword(right.getChangePassword().getUserName(), authenticationSecurityUtility.createDigest(right.getChangePassword().getNewPassword()));
                return this.returnRightResult(result, null, -1);
            }
        }

        return this.returnRightResult(null, null, -1);
    }


    public List<Answer> getVoteCount()
    {
        return rightDAO.getVoteCount();
    }

    public List<Question> getQuestions()
    {
        return rightDAO.getQuestions();
    }

    public List<Answer> getYesterdayVoteCount()
    {
        return rightDAO.getYesterdayVoteCount();
    }

    public List<Question> getYesterdayQuestions()
    {
        return rightDAO.getYesterdayQuestions();
    }

    public String registrationValidation(String token)
    {
        return rightDAO.validateUser(token);
    }


    private Right returnRightResult(String result, String userName, Integer userId)
    {
        Right returnRight = rightFactory.createRight();
        returnRight.getRightResponse().setCode(result);
        if(userId != null && userId > 0)
        {
            String newNonce = randomGenerationUtility.randomCharacterGeneration(NONCE_LENGTH);
            returnRight.getSecurity().setNonce(newNonce);
            nonceService.insertNonce(newNonce, userName, userId);
        }
        return returnRight;
    }



}
