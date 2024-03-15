package ru.otus.hw.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.domain.TestResult;

import static org.mockito.Mockito.*;

@SpringBootTest
class TestRunnerServiceImplTest {

    @MockBean
    private TestService testService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private ResultService resultService;

    @MockBean
    private TestResult testResult;

    @Autowired
    private TestRunnerServiceImpl testRunnerService;

    @Test
    public void serviceRunTest() {
        when(testService.executeTestFor(any())).thenReturn(testResult);
        testRunnerService.run();
        verify(testService, times(1)).executeTestFor(any());
    }
}