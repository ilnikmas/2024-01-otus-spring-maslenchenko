package ru.otus.hw.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvQuestionDaoTest {

    @Test
    void findAllSuccessfull() {
        String correctFileName = "/questions.csv";
        AppProperties properties = new AppProperties(correctFileName);
        CsvQuestionDao dao = new CsvQuestionDao(properties);
        List<Question> questions = dao.findAll();
        assertEquals(5, questions.size());
    }

    @Test
    void questionReadException() {
        String wrongFileName = "";
        AppProperties properties = new AppProperties(wrongFileName);
        CsvQuestionDao dao = new CsvQuestionDao(properties);
        assertThrows(QuestionReadException.class, () -> {
            List<Question> questions = dao.findAll();
        });
    }
}