package com.nianti.controllers;

import com.nianti.models.Question;
import com.nianti.services.QuestionDao;
import com.nianti.services.AnswerDao;
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

    @GetMapping("quizzes/{quizId}/questions/{questionId}")
    public String questionDetails(Model model, @PathVariable int quizId, @PathVariable int questionId)
    {
        var question = questionDao.getQuestionById(questionId);

        if(question == null)
        {
            return "404";
        }

        var answers = answerDao.getAnswersByQuestionId(questionId);

        model.addAttribute("question", question);
        model.addAttribute("answers", answers);

        return "question/details";
    }

    @GetMapping("/quizzes/{quizId}/questions/add")
    public String addQuestion(Model model, @PathVariable int quizId)
    {
        var quizzes = quizDao.getAllQuizzes();
        var quizTitle = quizDao.getQuizById(quizId).getTitle();

        model.addAttribute("question", new Question());
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("selectedQuizId", quizId);
        model.addAttribute("selectedQuizTitle", quizTitle);
        model.addAttribute("action", "add");

        return "question/add-edit";
    }

    @PostMapping("/quizzes/{quizId}/questions/add")
    public String addQuestion(Model model, @Valid @ModelAttribute("question") Question question, BindingResult result, @PathVariable int quizId)
    {
        if(result.hasErrors())
        {
            var quizzes = quizDao.getAllQuizzes();
            var quizTitle = quizDao.getQuizById(quizId).getTitle();

            model.addAttribute("quizzes", quizzes);
            model.addAttribute("selectedQuizId", quizId);
            model.addAttribute("selectedQuizTitle", quizTitle);
            model.addAttribute("isInvalid", true);
            model.addAttribute("action", "add");
            return "question/add-edit";
        }

        questionDao.addQuestion(question);
        return "redirect:/quizzes";
    }

    @GetMapping("/questions/{questionId}/edit")
    public String editQuestion(Model model, @PathVariable int questionId)
    {
        var question = questionDao.getQuestionById(questionId);
        var quizzes = quizDao.getAllQuizzes();

        if(question == null)
        {
            return "404";
        }

        model.addAttribute("question", question);
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("action", "edit");
        return "question/add-edit";
    }

    @PostMapping("/questions/{questionId}/edit")
    public String editQuestion(Model model, @Valid @ModelAttribute("question") Question question, BindingResult result, @PathVariable int questionId)
    {
        if(result.hasErrors())
        {
            var quizzes = quizDao.getAllQuizzes();
            model.addAttribute("quizzes", quizzes);
            model.addAttribute("isInvalid", true);
            model.addAttribute("action", "edit");
            return "question/add-edit";
        }
        question.setQuestionId(questionId);
        questionDao.updateQuestion(question);
        return "redirect:/quizzes";
    }

    @GetMapping("/questions/{questionId}/delete")
    public String deleteQuestion(Model model, @PathVariable int questionId)
    {
        var question = questionDao.getQuestionById(questionId);

        if (question == null)
        {
            return "404";
        }

        model.addAttribute("question", question);
        return "question/delete";
    }

    @PostMapping("/questions/{questionId}/delete")
    public String deleteQuestion(@PathVariable int questionId)
    {
        questionDao.deleteQuestion(questionId);
        return "redirect:/quizzes";
    }
}
