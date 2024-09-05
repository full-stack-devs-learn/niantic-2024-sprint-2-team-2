package com.nianti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.nianti.services.QuizDao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public class QuizController
{
    @Autowired
    private QuizDao quizDao;

    @GetMapping("/quizzes/{quizId}")
    public String quiz(Model model, @PathVariable int quizId)
    {
        var quiz = quizDao.g
    }
}
