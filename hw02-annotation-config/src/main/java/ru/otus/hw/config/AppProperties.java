package ru.otus.hw.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class AppProperties implements TestConfig, TestFileNameProvider {

    // внедрить свойство из application.properties
    @Value("${test.rightAnswersCountToPass:3}")
    private int rightAnswersCountToPass;

    // внедрить свойство из application.properties
    @Value("${test.fileName}")
    private String testFileName;
}
