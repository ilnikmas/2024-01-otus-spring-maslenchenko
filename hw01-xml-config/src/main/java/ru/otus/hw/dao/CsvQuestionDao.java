package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        Resource resource = new ClassPathResource(fileNameProvider.getTestFileName());
        List<QuestionDto> questions;
        try {
            questions = new CsvToBeanBuilder<QuestionDto>(new InputStreamReader(resource.getInputStream()))
                    .withSeparator(';')
                    .withType(QuestionDto.class).build().parse();
        } catch (IOException e) {
            throw new QuestionReadException("Error while reading file", e);
        }
        return questions.stream().map(QuestionDto::toDomainObject).collect(Collectors.toList());
    }
}