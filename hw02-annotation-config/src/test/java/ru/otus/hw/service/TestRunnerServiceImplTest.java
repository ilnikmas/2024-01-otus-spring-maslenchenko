package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw.domain.TestResult;

import static org.mockito.Mockito.*;

class TestRunnerServiceImplTest {

    private TestRunnerServiceImpl testRunnerService;

    private TestService testService;

    @BeforeEach
    void setUp() {
        testService = mock(TestServiceImpl.class);
        StudentService studentService = mock(StudentService.class);
        ResultService resultService = mock(ResultService.class);
        testRunnerService = new TestRunnerServiceImpl(testService, studentService, resultService);
    }

    @Test
    public void serviceRunTest() {
        TestResult testResult = mock(TestResult.class);
        when(testService.executeTestFor(any())).thenReturn(testResult);
        testRunnerService.run();
        verify(testService.executeTestFor(any()), times(1));
    }
}