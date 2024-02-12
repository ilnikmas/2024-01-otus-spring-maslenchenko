package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import static org.mockito.Mockito.*;

class TestRunnerServiceImplTest {

    private static TestRunnerServiceImpl testRunnerService;

    private static Student student;

    private static TestService testService;

    @BeforeAll
    static void setUp() {
        testService = mock(TestServiceImpl.class);
        StudentService studentService = mock(StudentService.class);
        ResultService resultService = mock(ResultService.class);
        student = mock(Student.class);
        testRunnerService = new TestRunnerServiceImpl(testService, studentService, resultService);
    }

    @Test
    public void serviceRunTest() {
        TestRunnerServiceImpl service = spy(testRunnerService);
        TestResult testResult = mock(TestResult.class);
        when(testService.executeTestFor(any())).thenReturn(testResult);
        service.run();
        verify(testService.executeTestFor(student), times(1));
    }
}