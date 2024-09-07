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

    @GetMapping("/quizzes/{quizId}")
    public String quiz(Model model, @PathVariable int quizId)
    {
        var quiz = quizDao.getQuizById(quizId);

        model.addAttribute("quiz", quiz);

        return "quiz/index";
    }

    @GetMapping("/quizzes")
    public String quizManagement(Model model)
    {
        var quizzes = quizDao.getAllQuizzes();

        model.addAttribute("quizzes", quizzes);

        return "quiz/quiz-management";
    }

    @GetMapping("/quizzes/add")
    public String addQuiz(Model model)
    {
        model.addAttribute("quiz", new Quiz());
        model.addAttribute("action", "add");

        return "quiz/add-edit";
    }

    @PostMapping("/quizzes/add")
    public String addQuiz(Model model, @Valid @ModelAttribute("quiz") Quiz quiz, BindingResult result)
    {
        if(result.hasErrors())
        {
            model.addAttribute("isInvalid", true);
            model.addAttribute("action", "add");
            return "quiz/add-edit";
        }

        quizDao.addQuiz(quiz);
        return "redirect:/quizzes";
    }

    @GetMapping("/quizzes/{quizId}/edit")
    public String editQuiz(Model model, @PathVariable int quizId)
    {
        Quiz quiz = quizDao.getQuizById(quizId);
        model.addAttribute("quiz", quiz);
        model.addAttribute("action", "edit");
        return "quiz/add-edit";
    }

    @PostMapping("/quizzes/{quizId}/edit")
    public String editQuiz(Model model, @Valid @ModelAttribute("quiz") Quiz quiz, BindingResult result, @PathVariable int quizId)
    {
        if(result.hasErrors())
        {
            model.addAttribute("isInvalid", true);
            model.addAttribute("action", "edit");
            return "quiz/add-edit";
        }

        quiz.setQuizId(quizId);
        quizDao.updateQuiz(quiz);
        return "redirect:/quizzes";
    }

    @GetMapping("/quizzes/{quizId}/delete")
    public String deleteQuiz(Model model, @PathVariable int quizId)
    {
        var quiz = quizDao.getQuizById(quizId);

        if (quiz == null)
        {
            return "404";
        }

        model.addAttribute("quiz", quiz);
        return "quiz/delete";
    }

    @PostMapping("/quizzes/{quizId}/delete")
    public String deleteQuizConfirm(@PathVariable int quizId)
    {
        var quiz = quizDao.getQuizById(quizId);

        if (quiz == null)
        {
            return "404";
        }
        quizDao.deleteQuiz(quizId);

        return "redirect:/quizzes";
    }
}
