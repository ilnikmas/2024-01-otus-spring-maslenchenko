package ru.otus.hw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.hw.service.TestRunnerService;

@Component
public class ApplicationRunner implements CommandLineRunner {

    @Autowired
    private TestRunnerService testRunnerService;

    @Override
    public void run(String... args) throws Exception {
        testRunnerService.run();
    }
}
