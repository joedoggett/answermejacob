package com.joe.answermejacob.AnswerMeJacob.rest;

import com.joe.answermejacob.AnswerMeJacob.aspects.RightValidation;
import com.joe.answermejacob.AnswerMeJacob.aspects.Security;
import com.joe.ithoughtiwasrightgeneratedclasses.Right;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class AnswerMeJacobController
{

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestHelper restHelper;



    /**
     * insert a question.
     * @param right
     * @return
     */
    @RequestMapping(path = "/insertquestion", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @Security
    public ResponseEntity<?> insertQuestion(@RequestBody Right right)
    {
        //log.info("start insertQuestion");
        try
        {
            Right response = restHelper.insertQuestion(right);
            return ResponseEntity.ok().body(response);
        }
        catch(Exception e)
        {
            log.error("exception: ", e);
            return ResponseEntity.badRequest().body("");
        }

    }

    /**
     * vote an answer
     * @param right
     * @return
     */
    @RequestMapping(path = "/voteanswer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @Security
    public ResponseEntity<?> voteAnswer(@RequestBody Right right)
    {
        try
        {
            Right response = restHelper.voteAnswer(right);
            return ResponseEntity.ok().body(response);
        }
        catch(Exception e)
        {
            log.error("exception: ", e);
            return ResponseEntity.badRequest().body("");
        }
    }

    /**
     * insert an answer
     * @param right
     * @return
     */
    @RequestMapping(path = "/insertanswer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @Security
    public ResponseEntity<?> insertAnswer(@RequestBody Right right)
    {
        try
        {
            Right response = restHelper.insertAnswer(right);
            return ResponseEntity.ok().body(response);
        }
        catch(Exception e)
        {
            log.error("exception: ", e);
            return ResponseEntity.badRequest().body("");
        }
    }

    /**
     * insert both a question and an answer
     * @param right
     * @return
     */
    @RequestMapping(path = "/insertquestionanswer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @Security
    public ResponseEntity<?> insertQuestionAnswer(@RequestBody Right right)
    {
        try
        {
            Right response = restHelper.insertQuestionAnswer(right);
            return ResponseEntity.ok().body(response);
        }
        catch(Exception e)
        {
            log.error("exception: ", e);
            return ResponseEntity.badRequest().body("");
        }
    }

    /**
     * register a user
     * @param right
     * @return
     */
    @RequestMapping(path = "/registeruser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @RightValidation
    public ResponseEntity<?> registerUser(@RequestBody Right right)
    {
        try
        {
            Right response = restHelper.registerUser(right);
            return ResponseEntity.ok().body(response);
        }
        catch(Exception e)
        {
            log.error("exception: ", e);
            return ResponseEntity.badRequest().body("");
        }
    }

    /**
     * login a user
     * @param right
     * @return
     */
    @RequestMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @RightValidation
    public ResponseEntity<?> login(@RequestBody Right right)
    {
        try
        {
            Right response = restHelper.login(right);
            return ResponseEntity.ok().body(response);
        }
        catch(Exception e)
        {
            log.error("exception: ", e);
            return ResponseEntity.badRequest().body("");
        }
    }

    @RequestMapping(path = "/changepassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @RightValidation
    public ResponseEntity<?> changePassword(@RequestBody Right right)
    {
        try
        {
            Right response = restHelper.changePassword(right);
            return ResponseEntity.ok().body(response);
        }
        catch(Exception e)
        {
            log.error("exception: " , e);
            return ResponseEntity.badRequest().body("");
        }


    }

    /**
     * returns a list of answer, corresponding to questions, along with the votes
     * for each answer.
     * @return
     */
    @RequestMapping(path = "/getvotecount", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getVoteCount()
    {
        try
        {
            return ResponseEntity.ok().body(restHelper.getVoteCount());

        }
        catch(Exception e)
        {
            log.error("exception: ", e);
            return ResponseEntity.badRequest().body("");
        }
    }


    @RequestMapping(path = "/getquestions", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getQuestions()
    {
        try
        {
            return ResponseEntity.ok().body(restHelper.getQuestions());

        }
        catch(Exception e)
        {
            log.error("exception: ", e);
            return ResponseEntity.badRequest().body("");
        }
    }

    @RequestMapping(path = "/getyesterdayvotecount", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getYesterdayVoteCount()
    {
        try
        {
            return ResponseEntity.ok().body(restHelper.getYesterdayVoteCount());

        }
        catch(Exception e)
        {
            log.error("exception: ", e);
            return ResponseEntity.badRequest().body("");
        }
    }

    @RequestMapping(path = "/getyesterdayquestions", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getYesterdayQuestions()
    {
        try
        {
            return ResponseEntity.ok().body(restHelper.getYesterdayQuestions());

        }
        catch(Exception e)
        {
            log.error("exception: ", e);
            return ResponseEntity.badRequest().body("");
        }
    }

    @RequestMapping(path = "/registrationvalidation/{token}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> registrationValidation(@PathVariable("token") String token)
    {
        try
        {
            return ResponseEntity.ok().body(restHelper.registrationValidation(token));

        }
        catch(Exception e)
        {
            log.error("exception: ", e);
            return ResponseEntity.badRequest().body("");
        }
    }

}
