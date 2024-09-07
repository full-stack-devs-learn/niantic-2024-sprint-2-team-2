package com.nianti.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;

public class Question
{
    private int questionId;
    private int quizId;

    @Min(value = 1, message="Question number must be greater than 0")
    private int questionNumber;

    @NotEmpty(message="Question text is required")
    private String questionText;

    private ArrayList<Answer> answers = new ArrayList<>();

    public Question()
    {
    }

    public Question(int questionId, int quizId, int questionNumber, String questionText)
    {
        this.questionId = questionId;
        this.quizId = quizId;
        this.questionNumber = questionNumber;
        this.questionText = questionText;
    }

    public int getQuestionId()
    {
        return questionId;
    }

    public void setQuestionId(int questionId)
    {
        this.questionId = questionId;
    }

    public int getQuizId()
    {
        return quizId;
    }

    public void setQuizId(int quizId)
    {
        this.quizId = quizId;
    }

    public int getQuestionNumber()
    {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber)
    {
        this.questionNumber = questionNumber;
    }

    public String getQuestionText()
    {
        return questionText;
    }

    public void setQuestionText(String questionText)
    {
        this.questionText = questionText;
    }

    public ArrayList<Answer> getAnswers()
    {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers)
    {
        this.answers = answers;
    }
}
