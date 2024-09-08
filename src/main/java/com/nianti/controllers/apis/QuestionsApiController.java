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

    // Check if answer already exists for the question
    @GetMapping("/api/question/{questionId}")
    public Boolean hasCorrectAnswer(@PathVariable int questionId)
    {
        // Get answers of question id
        var answers = answerDao.getAnswersByQuestionId(questionId);

        Boolean correctAnswer = false;

        for(Answer answer: answers)
        {
            if(answer.isCorrect())
            {
                correctAnswer = true;
            }
        }
        return correctAnswer;
    }
}
