package com.joe.answermejacob.AnswerMeJacob.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NonceService implements java.io.Serializable
{

    @Value("${insert.nonce.pass.phrase}")
    private String INSERT_NONCE_PASS_PHRASE;

    @Value("${validate.nonce.pass.phrase}")
    private String VALIDATE_NONCE_PASS_PHRASE;



    @Value("${nonce.service.insert}")
    private String insertNonceURL;

    @Value("${nonce.service.validate}")
    private String validateNonceURL;


    @Async
    public void insertNonce(String nonce, String userName, int userId)
    {

        NoncePass noncePass = new NoncePass();
        noncePass.setNonce(nonce);
        noncePass.setUserName(userName);
        noncePass.setUserId(userId);
        noncePass.setPassPhrase(INSERT_NONCE_PASS_PHRASE);

        //nothing returned.
        NoncePass responseEntity = new RestTemplate().postForObject(insertNonceURL, this.buildHttpEntityNoncePass(noncePass), NoncePass.class);


    }

    public NoncePass validateNonce(String nonce, String userName)
    {
        NoncePass noncePass = new NoncePass();
        noncePass.setNonce(nonce);
        noncePass.setUserName(userName);
        noncePass.setPassPhrase(VALIDATE_NONCE_PASS_PHRASE);

        return new RestTemplate().postForObject(validateNonceURL, this.buildHttpEntityNoncePass(noncePass), NoncePass.class);

    }

    private HttpEntity<NoncePass> buildHttpEntityNoncePass(NoncePass noncePass)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<NoncePass> request = new HttpEntity<>(noncePass, headers);
        return request;
    }



}
