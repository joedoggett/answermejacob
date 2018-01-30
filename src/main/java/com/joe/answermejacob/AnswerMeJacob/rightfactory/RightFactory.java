package com.joe.answermejacob.AnswerMeJacob.rightfactory;

import com.joe.ithoughtiwasrightgeneratedclasses.*;
import org.springframework.stereotype.Component;

@Component
public class RightFactory implements java.io.Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 8717214559049585496L;

    public Right createRight()
    {
        Right right = new Right();
        right.setRightResponse(this.createRightResponse());
        right.setSecurity(this.createSecurity());
        right.setSingleQuestionAnswer(this.createSingleQuestionAnswer());
        return right;
    }

    public Security createSecurity()
    {
        return new Security();
    }

    public RightResponse createRightResponse()
    {
        return new RightResponse();
    }

    public SingleQuestionAnswer createSingleQuestionAnswer()
    {
        SingleQuestionAnswer sqa = new SingleQuestionAnswer();
        sqa.setAnswer(this.createAnswer());
        sqa.setQuestion(this.createQuestion());
        return sqa;
    }

    public Question createQuestion()
    {
        return new Question();
    }

    public Answer createAnswer()
    {
        return new Answer();
    }




}
