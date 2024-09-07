package com.nianti.controllers.apis;

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

    @GetMapping("/api/quizzes/{quizId}")
    public int getQuestionsCount(@PathVariable int quizId)
    {
        int count = questionDao.getQuestionsCountByQuizId(quizId);

        return count;
    }
}
