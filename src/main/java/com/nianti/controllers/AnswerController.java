package com.nianti.controllers;

import com.nianti.models.Answer;
import com.nianti.models.Question;
import com.nianti.services.AnswerDao;
import com.nianti.services.QuestionDao;
import com.nianti.services.QuizDao;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.naming.Binding;

@Controller
public class AnswerController
{
    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AnswerDao answerDao;

    @GetMapping("/questions/{questionId}/answers/add")
    public String addAnswer(Model model, @PathVariable int questionId)
    {
        var questions = questionDao.getAllQuestionsByQuizId(questionId);
        var questionText = questionDao.getQuestionText(questionId);

        model.addAttribute("answer", new Answer());
        model.addAttribute("questions", questions);
        model.addAttribute("selectedQuestionId", questionId);
        model.addAttribute("questionText", questionText);
        model.addAttribute("action", "add");

        return "answer/add-edit";
    }

    @PostMapping("/questions/{questionId}/answers/add")
    public String addAnswer(Model model, @PathVariable int questionId, @Valid @ModelAttribute("answer") Answer answer, BindingResult result)
    {
        if(result.hasErrors())
        {
            var question = questionDao.getQuestionById(questionId);
            var questionText = questionDao.getQuestionText(questionId);

            model.addAttribute("question", question);
            model.addAttribute("questionText", questionText);
            model.addAttribute("selectedQuestionId", questionId);
            model.addAttribute("isInvalid", true);
            model.addAttribute("action", "add");
            return "answer/add-edit";
        }
        answerDao.addAnswer(answer);
        return "redirect:/quizzes";
    }

    @GetMapping("/answers/{answerId}/edit")
    public String editAnswer(Model model, @PathVariable int answerId)
    {
        var answer = answerDao.getAnswerById(answerId);
        var questionId = answer.getQuestionId();
        var questionText = questionDao.getQuestionText(questionId);

        if(answer == null)
        {
            return "404";
        }

        model.addAttribute("questionId", questionId);
        model.addAttribute("questionText", questionText);
        model.addAttribute("answer", answer);
        model.addAttribute("action", "edit");
        return "answer/add-edit";
    }

    @PostMapping("/answers/{answerId}/edit")
    public String editAnswer(Model model, @Valid @ModelAttribute("answer") Answer answer, BindingResult result, @PathVariable int answerId)
    {
        if(result.hasErrors())
        {
            var selectedAnswer = answerDao.getAnswerById(answerId);
            var questionId = selectedAnswer.getQuestionId();
            var questionText = questionDao.getQuestionText(questionId);

            model.addAttribute("selectedAnswer", selectedAnswer);
            model.addAttribute("questionId", questionId);
            model.addAttribute("questionText", questionText);
            model.addAttribute("isInvalid", true);
            model.addAttribute("action", "edit");
            return "answer/add-edit";
        }
        answerDao.updateAnswer(answer);
        return "redirect:/quizzes";
    }

    @GetMapping("/answers/{answerId}/delete")
    public String deleteAnswer(Model model, @PathVariable int answerId)
    {
        var answer = answerDao.getAnswerById(answerId);

        if(answer == null)
        {
            return "404";
        }

        var answerText = answer.getAnswerText();
        var questionId = answer.getQuestionId();
        var question = questionDao.getQuestionById(questionId);
        var quizId = question.getQuizId();

        model.addAttribute("answerText", answerText);
        model.addAttribute("question", question);
        model.addAttribute("questionId", questionId);
        model.addAttribute("quizId", quizId);
        model.addAttribute("answer", answer);
        return "answer/delete";
    }

    @PostMapping("/answers/{answerId}/delete")
    public String deleteAnswer(@PathVariable int answerId)
    {
        answerDao.deleteAnswer(answerId);
        return "redirect:/quizzes";
    }
}
