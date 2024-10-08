package com.nianti.controllers.apis;

import com.nianti.models.Answer;
import com.nianti.services.AnswerDao;
import com.nianti.services.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionsApiController
{
    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AnswerDao answerDao;

    @GetMapping("/api/quizzes/{quizId}")
    public int getQuestionsCount(@PathVariable int quizId)
    {
        int count = questionDao.getQuestionsCountByQuizId(quizId);

        return count;
    }

    @GetMapping("/api/question/{questionId}")
    public int hasCorrectAnswer(@PathVariable int questionId)
    {
        return answerDao.getCorrectAnswerCount(questionId);
    }
}
