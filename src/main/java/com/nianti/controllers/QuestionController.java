package com.nianti.controllers;

import com.nianti.services.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController
{
    @Autowired
    private QuestionDao questionDao;

    @GetMapping("/quiz/{quizId}/{questionId}")
    public String questionsByQuizId(Model model, @PathVariable int quizId, @PathVariable int questionId)
    {
        var questions = questionDao.getQuestionsByQuizId(quizId);

        model.addAttribute("questions", questions);
        return "quiz/fragments/quiz-questions";
    }
}
