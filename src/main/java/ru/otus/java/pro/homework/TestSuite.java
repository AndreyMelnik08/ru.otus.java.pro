package ru.otus.java.pro.homework;

public class TestSuite {
    @BeforeSuite
    public void setUp() {
        System.out.println("Начальный тест");
    }

    @Test(priority = 1)
    public void testMethod1() {
        System.out.println("Test 1");
    }

    @Test(priority = 5)
    public void testMethod2() {
        System.out.println("Test 2");
    }

    @Test(priority = 10)
    public void testMethod3() {
        System.out.println("Test 3");
    }

    @AfterSuite
    public void tearDown() {
        System.out.println("Конечный тест");
    }
}
