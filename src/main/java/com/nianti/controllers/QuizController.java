package com.nianti.controllers;

import com.nianti.models.Quiz;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.nianti.services.QuizDao;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class QuizController
{
    @Autowired
    private QuizDao quizDao;

    @GetMapping("/quiz/{quizId}")
    public String quiz(Model model, @PathVariable int quizId)
    {
        var quiz = quizDao.getQuizById(quizId);

        model.addAttribute("quiz", quiz);

        return "quiz/index";
    }

    @GetMapping("/quiz")
    public String quizManagement(Model model)
    {
        var quizzes = quizDao.getAllQuizzes();

        model.addAttribute("quizzes", quizzes);

        return "quiz/quiz-management";
    }

    @GetMapping("/quiz/add")
    public String addQuiz(Model model)
    {
        model.addAttribute("quiz", new Quiz());
        model.addAttribute("action", "add");

        return "quiz/add-edit";
    }

    @PostMapping("/quiz/add")
    public String addQuiz(Model model, @Valid @ModelAttribute("quiz") Quiz quiz, BindingResult result)
    {
        if(result.hasErrors())
        {
            model.addAttribute("isInvalid", true);
            return "quiz/add-edit";
        }

        quizDao.addQuiz(quiz);
        return "redirect:/quiz";
    }

    @GetMapping("/quiz/{quizId}/edit")
    public String editQuiz(Model model, @PathVariable int quizId)
    {
        Quiz quiz = quizDao.getQuizById(quizId);
        model.addAttribute("quiz", quiz);
        model.addAttribute("action", "edit");
        return "quiz/add-edit";
    }

    @PostMapping("/quiz/{quizId}/edit")
    public String editQuiz(Model model, @Valid @ModelAttribute("quiz") Quiz quiz, @PathVariable int quizId, BindingResult result)
    {
        if(result.hasErrors())
        {
            model.addAttribute("isInvalid", true);
            return "redirect:/quiz/{quizId}/edit";
        }

        quiz.setQuizId(quizId);
        quizDao.updateQuiz(quiz);
        return "redirect:/quiz";
    }
}
