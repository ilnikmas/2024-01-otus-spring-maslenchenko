package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CsvQuestionDaoTest {

    private static AppProperties appProperties;

    @BeforeAll
    static void setUp() {
        appProperties = mock(AppProperties.class);
    }

    @Test
    void findAllSuccessfull() {
        String correctFileName = "/questions.csv";
        when(appProperties.getTestFileName()).thenReturn(correctFileName);
        CsvQuestionDao dao = new CsvQuestionDao(appProperties);
        List<Question> questions = dao.findAll();
        assertEquals(5, questions.size());
    }

    @Test
    void questionReadException() {
        String wrongFileName = "/wrongFileName.csv";
        when(appProperties.getTestFileName()).thenReturn(wrongFileName);
        CsvQuestionDao dao = new CsvQuestionDao(appProperties);
        assertThrows(QuestionReadException.class, () -> {
            List<Question> questions = dao.findAll();
        });
    }
}