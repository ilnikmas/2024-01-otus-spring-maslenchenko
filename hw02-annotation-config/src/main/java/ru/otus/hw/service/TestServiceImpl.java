package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {
            var isAnswerValid = false; // Задать вопрос, получить ответ
            int max = question.answers().size();
            int answerIndex = ioService.readIntForRangeWithPrompt(1, max, questionToString(question), "Введите целое число от 1 до " + max) - 1;
            isAnswerValid = question.answers().get(answerIndex).isCorrect();
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    private String questionToString(Question question) {
        StringBuilder resultString = new StringBuilder(question.text());
        resultString.append("\n");
        int index = 1;
        for (Answer answer : question.answers()) {
            resultString.append(index++).append(". ");
            resultString.append(answer.text());
            resultString.append("\n");
        }
        return resultString.toString();
    }
}
