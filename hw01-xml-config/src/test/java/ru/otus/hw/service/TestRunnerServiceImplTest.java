package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class TestRunnerServiceImplTest {

    private static TestRunnerServiceImpl testRunnerService;

    private static TestServiceImpl testService;

    @BeforeAll
    static void setUp() {
        testService = mock(TestServiceImpl.class);
        testRunnerService = new TestRunnerServiceImpl(testService);
    }

    @Test
    public void serviceRunTest() {
        TestRunnerServiceImpl service = spy(testRunnerService);
        service.run();
        verify(testService).executeTest();
    }
}