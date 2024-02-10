package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class TestRunnerServiceImplTest {

    private static TestRunnerServiceImpl testRunnerService;

    @BeforeAll
    static void setUp() {
        TestServiceImpl testService = mock(TestServiceImpl.class);
        testRunnerService = new TestRunnerServiceImpl(testService);
    }

    @Test
    public void serviceRunTest() {
        TestRunnerServiceImpl service = spy(testRunnerService);
        service.run();
        verify(service).run();
    }
}