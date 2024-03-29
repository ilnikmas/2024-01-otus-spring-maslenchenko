package ru.otus.hw.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.hw.service.TestRunnerService;

@Component
public class ApplicationRunner implements CommandLineRunner {

    private final TestRunnerService testRunnerService;

    @Autowired
    public ApplicationRunner(TestRunnerService testRunnerService) {
        this.testRunnerService = testRunnerService;
    }

    @Override
    public void run(String... args) throws Exception {
        testRunnerService.run();
    }
}
