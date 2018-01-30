package com.joe.answermejacob.AnswerMeJacob.service;

import com.joe.answermejacob.AnswerMeJacob.dao.RightDAO;
import com.joe.answermejacob.AnswerMeJacob.rightfactory.RightFactory;
import com.joe.answermejacob.AnswerMeJacob.util.AuthenticationSecurityUtility;
import com.joe.answermejacob.AnswerMeJacob.util.RandomGenerationUtility;
import com.joe.ithoughtiwasrightgeneratedclasses.Right;
import com.joe.ithoughtiwasrightgeneratedclasses.Security;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService implements java.io.Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = -4088923700309513775L;
    private static final String FAILED_AUTHENTICATION_CODE = "0";
    private static final String SUCCESSFUL_AUTHENTICATION_CODE = "1";


    @Autowired
    RightDAO rightDAO;


    @Autowired
    RightFactory rightFactory;

    @Autowired
    AuthenticationSecurityUtility authenticationSecurityUtility;

    @Autowired
    RandomGenerationUtility randomGenerationUtility;

    /**
     * logic to login user.
     * fail fast.
     * note: SPROC called checks email and valid_date < now().
     * @param email
     * @param password
     * @return
     */
    public Right login(String email, String password)
    {

        Security user = rightDAO.getUser(email);

        if(StringUtils.isBlank(user.getUserName()) || StringUtils.isBlank(user.getPassword()))
        {
            return this.returnAuthentication(FAILED_AUTHENTICATION_CODE, user);
        }

        if(!authenticationSecurityUtility.digestMatch(password, user.getPassword()))
        {
            return this.returnAuthentication(FAILED_AUTHENTICATION_CODE, user);
        }

        return this.returnAuthentication(SUCCESSFUL_AUTHENTICATION_CODE, user);
    }



    private Right returnAuthentication(String code, Security user)
    {
        Right returnRight = rightFactory.createRight();
        returnRight.getRightResponse().setCode(code);
        returnRight.getSecurity().setUserId(user.getUserId());
        returnRight.getSecurity().setUserName(user.getUserName());
        return returnRight;
    }

}
