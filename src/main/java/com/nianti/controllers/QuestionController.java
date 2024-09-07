package com.nianti.controllers;

import com.nianti.models.Question;
import com.nianti.services.QuestionDao;
import com.nianti.services.AnswerDao;
import com.nianti.services.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController
{
    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private QuizDao quizDao;

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

    @GetMapping("/questions/add")
    public String addQuestion(Model model)
    {
        var quizzes = quizDao.getAllQuizzes();
        model. addAttribute("question", new Question());
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("action", "add");

        return "question/add-edit";
    }

    @PostMapping("/questions/add")
    public String addQuestion(Model model, @ModelAttribute("question") Question question)
    {
        questionDao.addQuestion(question);
        return "redirect:/questions";
    }

    @GetMapping("/questions/{questionId}/edit")
    public String editQuestion(Model model, @PathVariable int questionId)
    {
        var question = questionDao.getQuestionById(questionId);

        if(question == null)
        {
            return "404";
        }
    }
}
