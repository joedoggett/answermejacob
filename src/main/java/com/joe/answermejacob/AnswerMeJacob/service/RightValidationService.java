package com.joe.answermejacob.AnswerMeJacob.service;

import com.joe.answermejacob.AnswerMeJacob.util.JavaxValidatorUtility;
import com.joe.ithoughtiwasrightgeneratedclasses.Right;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RightValidationService implements java.io.Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 1587826734422919981L;


    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    JavaxValidatorUtility javaxValidatorUtility;


    /**
     * logic.
     * validate right.
     * security validated if username and password not null and length greater than 0.
     * question validated if question not null and length greater than 0.
     * answer validated if answer not null and length greater than 0.
     * @param right
     * @return
     */
    public boolean validateRight(Right right)
    {
        boolean security = true;
        boolean question = true;
        boolean answer = true;
        boolean changePassword = true;

        if(right.getSecurity() != null && !StringUtils.isBlank(right.getSecurity().getUserName()) && !StringUtils.isBlank(right.getSecurity().getPassword()))
        {
            security = javaxValidatorUtility.isModelValid(right.getSecurity());
        }

        if(right.getSingleQuestionAnswer() != null && right.getSingleQuestionAnswer().getQuestion() != null && !StringUtils.isBlank(right.getSingleQuestionAnswer().getQuestion().getQuestion()))
        {
            question = javaxValidatorUtility.isModelValid(right.getSingleQuestionAnswer().getQuestion());
        }

        if(right.getSingleQuestionAnswer() != null && right.getSingleQuestionAnswer().getAnswer() != null && !StringUtils.isBlank(right.getSingleQuestionAnswer().getAnswer().getAnswer()))
        {
            answer = javaxValidatorUtility.isModelValid(right.getSingleQuestionAnswer().getAnswer());
        }

        if(right.getChangePassword() != null && !StringUtils.isBlank(right.getChangePassword().getUserName()) && !StringUtils.isBlank(right.getChangePassword().getOldPassword()) && !StringUtils.isBlank(right.getChangePassword().getNewPassword()))
        {
            changePassword = javaxValidatorUtility.isModelValid(right.getChangePassword());
        }

        return  security && question && answer && changePassword;

    }

    public <T> boolean validateObject(T toCheck)
    {
        return javaxValidatorUtility.isModelValid(toCheck);
    }

}




















