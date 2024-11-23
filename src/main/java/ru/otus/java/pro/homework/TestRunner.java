package ru.otus.java.pro.homework;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class TestRunner {

    public static void runTests(Class<?> testClass) {
        Method beforeSuiteMethod = null;
        Method afterSuiteMethod = null;
        List<Method> testMethods = new ArrayList<>();
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                if (beforeSuiteMethod != null) {
                    throw new RuntimeException("Можно только 1 @BeforeSuite");
                }
                beforeSuiteMethod = method;
            } else if (method.isAnnotationPresent(AfterSuite.class)) {
                if (afterSuiteMethod != null) {
                    throw new RuntimeException("Можно только 1 @AfterSuite");
                }
                afterSuiteMethod = method;
            } else if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }
        }
        Collections.sort(testMethods, (m1, m2) -> {
            Test test1 = m1.getAnnotation(Test.class);
            Test test2 = m2.getAnnotation(Test.class);
            if (test1 == null || test2 == null) {
                throw new RuntimeException("Нет аннотации @Test");
            }
            return Integer.compare(test1.priority(), test2.priority());
        });
        try {
            int passed = 0;
            int failed = 0;
            if (beforeSuiteMethod != null) {
                beforeSuiteMethod.invoke(testClass.getDeclaredConstructor().newInstance());
                passed++;
            }
            for (Method testMethod : testMethods) {
                try {
                    testMethod.invoke(testClass.getDeclaredConstructor().newInstance());
                    passed++;
                } catch (Exception e) {
                    System.out.println("Тестов " + testMethod.getName() + " провалено: " + e.getCause());
                    failed++;
                }
            }
            if (afterSuiteMethod != null) {
                afterSuiteMethod.invoke(testClass.getDeclaredConstructor().newInstance());
                passed++;
            }
            System.out.println("Всего тестов: " + (passed + failed));
            System.out.println("Успешно: " + passed);
            System.out.println("Провалено: " + failed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
