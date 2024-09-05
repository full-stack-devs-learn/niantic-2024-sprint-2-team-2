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
}
