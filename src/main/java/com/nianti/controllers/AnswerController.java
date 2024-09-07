package com.nianti.controllers;

import com.nianti.models.Answer;
import com.nianti.models.Question;
import com.nianti.services.AnswerDao;
import com.nianti.services.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AnswerController
{
    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AnswerDao answerDao;
//
//    @GetMapping("/quizzes/{quizId}/{questionId}")
//    public String answersByQuestionId(Model model, @PathVariable int quizId, @PathVariable int questionId)
//    {
//        var answers = answerDao.getAnswersByQuestionId(questionId);
//
//        model.addAttribute("answers", answers);
//        return "quiz/fragments/answers-list";
//    }

    @GetMapping("/question/{questionId}/answer/add")
    public String addAnswer(Model model, @PathVariable int questionId)
    {
        var questions = questionDao.getAllQuestionsByQuizId(questionId);
        var questionText = questionDao.getQuestionText(questionId);

        model.addAttribute("answer", new Answer());
        model.addAttribute("questions", questions);
        model.addAttribute("questionText", questionText);
        model.addAttribute("action", "add");

        return "answer/add-edit";
    }
}
