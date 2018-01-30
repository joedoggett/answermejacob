package com.joe.answermejacob.AnswerMeJacob.util;

import org.springframework.stereotype.Component;

import org.jasypt.digest.StandardStringDigester;



@Component
public class AuthenticationSecurityUtility implements java.io.Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = -4635814829043495302L;
    private static final String ALGORITHM  = "SHA-1";
    private static final int    ITERATIONS = 50000;

    /**
     * if want to use to create own digester. digester instantiated with default algorithm and iterations.
     * @return
     */
    public StandardStringDigester createDigester()
    {
        StandardStringDigester digester = new StandardStringDigester();
        digester.setAlgorithm(ALGORITHM);   // optionally set the algorithm
        digester.setIterations(ITERATIONS);  // increase security by performing 50000 hashing iterations
        return digester;
    }

    /**
     * create digest of passed in value
     * @param inString: value to digest
     * @return
     */
    public String createDigest(String inString)
    {
        return this.createDigester().digest(inString);
    }

    /**
     * determine if digested value matches provided value
     * @param inString: value provided
     * @param inDigested: value already digested.
     * @return
     */
    public boolean digestMatch(String inString, String inDigested)
    {
        return this.createDigester().matches(inString, inDigested);
    }



}
