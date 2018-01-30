package com.joe.answermejacob.AnswerMeJacob.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import javax.validation.ConstraintViolation;
import java.util.Set;


@Service
public class JavaxValidatorUtility implements java.io.Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 4813378460756977191L;

    @Autowired
    Validator validator;


    /*
     * used code idea from
     * http://www.programcreek.com/java-api-examples/javax.validation.Validator
     */


    public <T> boolean isModelValid(T toCheck)
    {
        Set<ConstraintViolation<T>> violations = this.jsr303ModelValidation(toCheck);
        if(violations.isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public <T> Set<ConstraintViolation<T>> jsr303ModelValidation(T toCheck)
    {
        Set<ConstraintViolation<T>> violations = validator.validate(toCheck);
        return violations;
    }

}
