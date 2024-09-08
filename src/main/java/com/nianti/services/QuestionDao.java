package com.nianti.services;

import com.nianti.models.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionDao
{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public QuestionDao(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Question getQuestionByQuizId(int quizId, int curQuestionIndex)
    {
        int questionPerPage = 1;
        int skip = curQuestionIndex - 1;

        String sql = """
                SELECT question_id
                    , quiz_id
                    , question_number
                    , question_text
                FROM question
                WHERE quiz_id = ?
                LIMIT ?, ?;
                """;

        var row = jdbcTemplate.queryForRowSet(sql, quizId, skip, questionPerPage);

        if(row.next())
        {
            int questionId = row.getInt("question_id");
            int questionNumber = row.getInt("question_number");
            String questionText = row.getString("question_text");

            return new Question(questionId, quizId, questionNumber, questionText);
        }
        return null;
    }

    public List<Question> getAllQuestionsByQuizId(int quizId)
    {
        ArrayList<Question> questions = new ArrayList<>();

        String sql = """
                SELECT question_id
                    , quiz_id
                    , question_number
                    , question_text
                FROM question
                WHERE quiz_id = ?;
                """;

        var row = jdbcTemplate.queryForRowSet(sql, quizId);

        while(row.next())
        {
            int questionId = row.getInt("question_id");
            int questionNumber = row.getInt("question_number");
            String questionText = row.getString("question_text");

            Question question = new Question(questionId, quizId, questionNumber, questionText);

            questions.add(question);
        }

        return questions;
    }

    public int getQuestionsCountByQuizId(int quizId)
    {
        String sql = """
                SELECT COUNT(*) AS question_count
                FROM question
                WHERE quiz_id = ?;
                """;

        var row = jdbcTemplate.queryForRowSet(sql, quizId);

        if(row.next())
        {
            return row.getInt("question_count");
        }
        return 0;
    }

    public Question getQuestionById(int questionId)
    {
        String sql = """
                SELECT question_id
                    , quiz_id
                    , question_number
                    , question_text
                FROM question
                WHERE question_id = ?;
                """;

        var row = jdbcTemplate.queryForRowSet(sql, questionId);

        if(row.next())
        {
            int quizId = row.getInt("quiz_id");
            int questionNumber = row.getInt("question_number");
            String questionText = row.getString("question_text");

            return new Question(questionId, quizId, questionNumber, questionText);
        }
        return null;
    }

    public String getQuestionText(int questionId)
    {
        String sql = """
                SELECT question_text
                FROM question
                WHERE question_id = ?;
                """;

        var row = jdbcTemplate.queryForRowSet(sql, questionId);

        if(row.next())
        {
            String questionText = row.getString("question_text");

            return questionText;
        }
        return null;
    }

    public int getQuizId(int questionId)
    {
        String sql = """
                SELECT quiz_id
                FROM question
                WHERE question_id = ?;
                """;

        var row = jdbcTemplate.queryForRowSet(sql, questionId);

        if(row.next())
        {
            int quizId = row.getInt("quiz-id");

            return quizId;
        }
        return 0;
    }

    public void addQuestion(Question question)
    {
        String sql = """
                INSERT INTO question (quiz_id, question_number, question_text)
                VALUES (?, ?, ?);
                """;

        jdbcTemplate.update(sql,
                            question.getQuizId(),
                            question.getQuestionNumber(),
                            question.getQuestionText());
    }

    public void updateQuestion(Question question)
    {
        String sql = """
                UPDATE question
                SET quiz_id = ?
                    , question_number = ?
                    , question_text = ?
                WHERE question_id = ?;
                """;

        jdbcTemplate.update(sql,
                            question.getQuizId(),
                            question.getQuestionNumber(),
                            question.getQuestionText(),
                            question.getQuestionId());
    }

    public void deleteQuestion(int questionId)
    {
        String sql = """
                DELETE FROM question
                WHERE question_id = ?;
                """;

        jdbcTemplate.update(sql, questionId);
    }
}
