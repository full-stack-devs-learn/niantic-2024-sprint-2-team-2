package com.nianti.controllers;

import com.nianti.models.Answer;
import com.nianti.services.AnswerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AnswerController
{
    @Autowired
    private AnswerDao answerDao;

    @GetMapping("/quiz/{quizId}/{questionId}")
    public String answersByQuestionId(Model model, @PathVariable int quizId, @PathVariable int questionId)
    {
        var answers = answerDao.getAnswersByQuestionId(questionId);

        model.addAttribute("answers", answers);
        return "quiz/fragments/answers-list";
    }
}
