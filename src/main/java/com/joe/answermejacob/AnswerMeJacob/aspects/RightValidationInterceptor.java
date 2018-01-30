package com.joe.answermejacob.AnswerMeJacob.aspects;

import com.joe.answermejacob.AnswerMeJacob.rightfactory.RightFactory;
import com.joe.answermejacob.AnswerMeJacob.service.RightValidationService;
import com.joe.ithoughtiwasrightgeneratedclasses.Right;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RightValidationInterceptor implements java.io.Serializable
{
    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     *
     */
    private static final long serialVersionUID = -2283421222927657424L;
    private static final String BAD_CODE = "-1";



    @Autowired
    RightFactory rightFactory;

    @Autowired
    RightValidationService rightValidationService;




    @Around("@annotation(RightValidation)")
    public Object aroundInvoke(ProceedingJoinPoint joinPoint) throws Throwable
    {


        Object[] parameters = joinPoint.getArgs();
        Right right = (Right) parameters[0];

        //assure validation of incoming material.
        //if violations exist, user was able to circumvent validation on client side. assume manipulation. return bad code.
        if(!rightValidationService.validateRight(right))
        {
            log.info("need to return. violations exist.");
            return this.buildImmediateResponse(BAD_CODE);
        }


        joinPoint.getArgs()[0] = right;

        Object result = joinPoint.proceed();

        return result;
    }

    private ResponseEntity<?> buildImmediateResponse(String code)
    {
        Right badRight = rightFactory.createRight();
        badRight.getRightResponse().setCode(code);
        return ResponseEntity.ok(badRight);
    }
}
