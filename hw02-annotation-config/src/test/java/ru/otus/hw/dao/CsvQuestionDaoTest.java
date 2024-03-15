package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CsvQuestionDaoTest {

    private AppProperties appProperties;
    private QuestionDao dao;

    @BeforeEach
    void setUp() {
        appProperties = mock(AppProperties.class);
        dao = new CsvQuestionDao(appProperties);
    }

    @Test
    void findAllSuccessfull() {
        String correctFileName = "/questions.csv";
        when(appProperties.getTestFileName()).thenReturn(correctFileName);
        List<Question> questions = dao.findAll();
        assertEquals(5, questions.size());
        assertEquals("Is there life on Mars?", questions.get(0).text());
        assertEquals(3, questions.get(0).answers().size());
        assertTrue(questions.get(0).answers().get(0).isCorrect());
        assertEquals("What is Java?", questions.get(4).text());
        assertEquals(3, questions.get(4).answers().size());
        assertTrue(questions.get(4).answers().get(0).isCorrect());
    }

    @Test
    void questionReadException() {
        String wrongFileName = "/wrongFileName.csv";
        when(appProperties.getTestFileName()).thenReturn(wrongFileName);
        assertThrows(QuestionReadException.class, dao::findAll);
    }
}