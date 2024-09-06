package com.nianti.services;

import com.nianti.models.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuizDao
{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public QuizDao(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Quiz> getAllQuizzes()
    {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        String sql = """
            SELECT quiz_id
                , quiz_title
                , is_live
            FROM quiz;
        """;
        var row = jdbcTemplate.queryForRowSet(sql);
        while (row.next())
        {
            var quiz = mapRowToQuiz(row);
            quizzes.add(quiz);
        }

        return quizzes;
    }

    public List<Quiz> getAllLiveQuizzes()
    {
        ArrayList<Quiz> quizzes = new ArrayList<>();

        String sql = """
                SELECT quiz_id
                    , quiz_title
                    , is_live
                FROM quiz
                WHERE is_live = 1;
                """;

        var row = jdbcTemplate.queryForRowSet(sql);
        while (row.next())
        {
            var quiz = mapRowToQuiz(row);
            quizzes.add(quiz);
        }

        return quizzes;
    }

    private Quiz mapRowToQuiz(SqlRowSet row)
    {
        int id = row.getInt("quiz_id");
        String title = row.getString("quiz_title");
        boolean isLive = row.getBoolean("is_live");

        return new Quiz(id, title, isLive);
    }

    public Quiz getQuizById(int quizId)
    {
        String sql = """
                SELECT quiz_id
                    , quiz_title
                    , is_live
                FROM quiz
                WHERE quiz_id = ?;
                """;

        var row = jdbcTemplate.queryForRowSet(sql, quizId);

        if(row.next())
        {
            String quizTitle = row.getString("quiz_title");
            boolean isLive = row.getBoolean("is_live");

            return new Quiz(quizId, quizTitle, isLive);
        }
        return null;
    }

    public void addQuiz(Quiz quiz)
    {
        String sql = """
                INSERT INTO quiz (quiz_title, is_live)
                VALUES (?,?);
                """;

        jdbcTemplate.update(sql,
                quiz.getTitle(),
                quiz.isLive());
    }

    public void updateQuiz(Quiz quiz)
    {
        String sql = """
                UPDATE quiz
                SET quiz_title = ?
                    , is_live = ?
                WHERE quiz_id = ?;
                """;

        jdbcTemplate.update(sql,
                    quiz.getTitle(),
                    quiz.isLive(),
                    quiz.getQuizId());
    }
}
