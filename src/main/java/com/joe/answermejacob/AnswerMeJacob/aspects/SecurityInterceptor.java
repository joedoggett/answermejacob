package com.joe.answermejacob.AnswerMeJacob.aspects;

import com.joe.answermejacob.AnswerMeJacob.rightfactory.RightFactory;
import com.joe.answermejacob.AnswerMeJacob.service.NoncePass;
import com.joe.answermejacob.AnswerMeJacob.service.NonceService;
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
public class SecurityInterceptor implements java.io.Serializable
{
    private static final long serialVersionUID = 7472713303283742022L;
    private static final String BAD_CODE = "-1";
    private static final String LOGIN_NEEDED = "99";

    private static final int NONCE_LENGTH = 128;


    @Autowired
    NonceService nonceService;

    @Autowired
    RightFactory rightFactory;

    @Autowired
    RightValidationService rightValidationService;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Around("@annotation(Security)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable
    {
        Object[] parameters = joinPoint.getArgs();
        Right right = (Right) parameters[0];


        NoncePass noncePass = nonceService.validateNonce(right.getSecurity().getNonce(), right.getSecurity().getUserName());

        //assure validation of incoming material.
        //if violations exist, user was able to circumvent validation on client side. assume manipulation. return bad code.
        if(!rightValidationService.validateRight(right))
        {
            log.info("need to return. violations exist.");
            return this.buildImmediateResponse(BAD_CODE);
        }

        //incoming user id should always be null. not populated from client side. if value, very bad.
        if(right.getSecurity().getUserId() != null)
        {
            log.info("incoming user id populated. uh oh.");
            //consider send off for nonce lookup and account permanent lock.
            return this.buildImmediateResponse(BAD_CODE);
        }

        //legit nonce.
        if(right.getSecurity().getNonce() != null && right.getSecurity().getNonce().trim().length() > 0 && right.getSecurity().getNonce().length() != NONCE_LENGTH)
        {
            log.info("nonce not equal to null and length not proper. uh oh.");
            return this.buildImmediateResponse(BAD_CODE);
        }


        //check nonce.
        if(right.getSecurity().getNonce() == null || right.getSecurity().getNonce().trim().length() == 0)
        {
            return this.buildImmediateResponse(LOGIN_NEEDED); //tested functionality to make sure that 99 returned would result in login needed.
        }

        //at this point, nonce is 128 characters in length and is good to check against datasource.
        //extract from datasource and see if still good.
        //check is to compare supplied userName with nonce.userName.

        //log.info("noncePass.isSuccess(): " + noncePass.isSuccess());


        if(!noncePass.isSuccess())
        {
            return this.buildImmediateResponse(LOGIN_NEEDED);
        }

        //nonce considered good.
        // need to set user id.
        right.getSecurity().setUserId(noncePass.getUserId());

        //log.info("right.getSecurity().getUserId(): " + right.getSecurity().getUserId());



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
