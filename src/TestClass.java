public class TestClass {

    public TestClass() {
    }

    @BeforeSuite
    public void beforeSuiteMethod () {
        System.out.println("Выполняем метод перед тестами!");
    }

    @Test (priority = 1)
    public void test1 () {
        System.out.println("Выполняем тест с приоритетом 1");
    }
    @Test (priority = 2)
    public void test21 () {
        System.out.println("Выполняем тест с приоритетом 2_1");
    }

    @Test (priority = 2)
    public void test22 () {
        System.out.println("Выполняем тест с приоритетом 2_2");
    }

    @Test (priority = 3)
    public void test3 () {
        System.out.println("Выполняем тест с приоритетом 3");
    }
    @Test (priority = 4)
    public void test4 () {
        System.out.println("Выполняем тест с приоритетом 4");
    }

    @Test (priority = 10)
    public void test10 () {
        System.out.println("Выполняем тест с приоритетом 10");
    }

    @AfterSuite
    public void afterSuiteMethod () {
        System.out.println("Выполняем метод после всех тестов!");
    }



}
