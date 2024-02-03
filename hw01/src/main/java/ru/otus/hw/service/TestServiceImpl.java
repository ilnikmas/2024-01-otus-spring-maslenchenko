package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final CsvQuestionDao csvQuestionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        // Получить вопросы из дао и вывести их с вариантами ответов
        outputQuestions(csvQuestionDao.findAll());
    }

    private void outputQuestions(List<Question> questions) {
        for (Question question : questions) {
            ioService.printFormattedLine("Question: ");
            ioService.printFormattedLine(question.text());
            ioService.printFormattedLine("Answers:");
            ioService.printFormattedLine(question.answers().stream().map(Answer::text).toList().toString());
        }
    }
}
