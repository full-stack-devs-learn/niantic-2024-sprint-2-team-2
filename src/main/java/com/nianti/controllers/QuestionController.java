package com.nianti.controllers;

import com.nianti.services.QuestionDao;
import com.nianti.services.AnswerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController
{
    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AnswerDao answerDao;

    @GetMapping("/quiz/{quizId}/{questionId}")
    public String questionsByQuizId(Model model, @PathVariable int quizId, @PathVariable int questionId)
    {
        var question = questionDao.getQuestionById(questionId);
        var answers = answerDao.getAnswersByQuestionId(questionId);

        model.addAttribute("question", question);
        model.addAttribute("answers", answers);

        return "quiz/fragments/quiz-questions";
    }
}
