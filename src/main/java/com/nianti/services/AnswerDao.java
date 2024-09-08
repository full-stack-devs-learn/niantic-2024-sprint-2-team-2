package com.nianti.services;

import com.nianti.models.Answer;
import com.nianti.models.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class AnswerDao
{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AnswerDao(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Answer> getAllAnswers()
    {
        ArrayList<Answer> answers = new ArrayList<>();

        String sql = """
                SELECT answer_id
                    , question_id
                    , answer_text
                    , is_correct
                FROM answer;
                """;

        var row = jdbcTemplate.queryForRowSet(sql);

        while(row.next())
        {
            int answerId = row.getInt("answer_id");
            int questionId = row.getInt("question_id");
            String answerText = row.getString("answer_text");
            Boolean isCorrect = row.getBoolean("is_correct");

            Answer answer = new Answer(answerId, questionId, answerText, isCorrect);

            answers.add(answer);
        }

        return answers;
    }

    public Answer getAnswerById(int answerId)
    {
        String sql = """
                SELECT answer_id
                    , question_id
                    , answer_text
                    , is_correct
                FROM answer
                WHERE answer_id = ?;
                """;

        var row = jdbcTemplate.queryForRowSet(sql, answerId);

        while(row.next())
        {
            int questionId = row.getInt("question_id");
            String answerText = row.getString("answer_text");
            Boolean isCorrect = row.getBoolean("is_correct");

            return new Answer(answerId, questionId, answerText, isCorrect);
        }
        return null;
    }

    public List<Answer> getAnswersByQuestionId(int questionId)
    {
        ArrayList<Answer> answers = new ArrayList<>();

        String sql = """
                SELECT answer_id
                    , question_id
                    , answer_text
                    , is_correct
                FROM answer
                WHERE question_id = ?;
                """;

        var row = jdbcTemplate.queryForRowSet(sql, questionId);

        while(row.next())
        {
            int answerId = row.getInt("answer_id");
            String answerText = row.getString("answer_text");
            Boolean isCorrect = row.getBoolean("is_correct");

            Answer answer = new Answer(answerId, questionId, answerText, isCorrect);

            answers.add(answer);
        }

        return answers;
    }

    public Answer getCorrectAnswer(int questionId)
    {
        int correct = 1; // correct answers have a value of 1 in database

        String sql = """
                SELECT answer_id
                    , question_id
                    , answer_text
                    , is_correct
                FROM answer
                WHERE question_id = ? AND is_correct = ?;
                """;

        var row = jdbcTemplate.queryForRowSet(sql, questionId, correct);

        while(row.next())
        {
            int answerId = row.getInt("answer_id");
            String answerText = row.getString("answer_text");
            Boolean isCorrect = row.getBoolean("is_correct");

            return new Answer(answerId, questionId, answerText, isCorrect);
        }

        return null;
    }
    
    public void addAnswer(Answer answer)
    {
        String sql = """
                INSERT INTO answer (question_id, answer_text, is_correct)
                VALUES (?, ?, ?)
                """;

        jdbcTemplate.update(sql,
                answer.getQuestionId(),
                answer.getAnswerText(),
                answer.isCorrect());
    }

    public void updateAnswer(Answer answer)
    {
        String sql = """
                UPDATE answer
                SET answer_text = ?
                    , is_correct = ?
                WHERE answer_id = ?;
                """;

        jdbcTemplate.update(sql,
                answer.getAnswerText(),
                answer.isCorrect(),
                answer.getAnswerId());
    }

    public void deleteAnswer(int answerId)
    {
        String sql = """
                DELETE from answer
                WHERE answer_id = ?;
                """;

        jdbcTemplate.update(sql, answerId);
    }

}
