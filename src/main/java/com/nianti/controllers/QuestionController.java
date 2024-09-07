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

    @GetMapping("/quizzes/{quizId}/{curQuestionIndex}")
    public String questionByQuizId(Model model, @PathVariable int quizId, @PathVariable int curQuestionIndex)
    {
        var question = questionDao.getQuestionByQuizId(quizId, curQuestionIndex);
        int questionId = question.getQuestionId();
        var answers = answerDao.getAnswersByQuestionId(questionId);

        model.addAttribute("question", question);
        model.addAttribute("answers", answers);

        return "quiz/fragments/quiz-questions";
    }
}
