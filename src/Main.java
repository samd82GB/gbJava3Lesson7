import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Class testClass = TestClass.class;
        try {
            start(testClass);
        } catch (InstantiationException | IllegalAccessException | NullPointerException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void start(Class testClass) throws InstantiationException, IllegalAccessException, NullPointerException, InvocationTargetException {

//создаём объект класса для запуска тестов
        TestClass doTests = (TestClass) testClass.newInstance();
//создаём массив методов из класса testClass
        Method[] methods = testClass.getDeclaredMethods();
//создаём лист методов для дальнейшей сортировки
        List<Method> methodList = new ArrayList<>();

//проверка класса на наличие методов с аннотациями До и После
        int countsBefore = 0;
        int countsAfter = 0;
        for (Method method : methods) {
            if (method.getDeclaredAnnotation(BeforeSuite.class) != null) {
                countsBefore++;

            }
            if (method.getDeclaredAnnotation(AfterSuite.class) != null) {
                countsAfter++;
            }
        }
        if (countsBefore > 1) {
            throw new RuntimeException("Более одного метода ДО");
        } else if (countsBefore == 0) {
            throw new RuntimeException("Отсутсвует метод ДО");
        }

        if (countsAfter > 1) {
            throw new RuntimeException("Более одного метода ПОСЛЕ");
        } else if (countsAfter == 0) {
            throw new RuntimeException("Отсутсвует метод ПОСЛЕ");
        }
//запускаем метод с аннотацией @BeforeSuite
        for (Method method : methods) {
            if (method.getDeclaredAnnotation(BeforeSuite.class) != null) {
                System.out.println("Имя метода ДО: " +method.getName());
                method.invoke(doTests);
                System.out.println("___________________________________");
            }
        }


//заполняем лист методов только теми методами, которые имеют аннотацию Test
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getDeclaredAnnotation(Test.class) != null) {
                methodList.add(methods[i]);
            }
        }
//сортируем лист методов по признаку приоритета из аннотации Test
        for (int i = methodList.size() - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (methodList.get(j).getAnnotation(Test.class).priority() >
                        methodList.get(j + 1).getAnnotation(Test.class).priority()) {
                    Method tmpMethod = methodList.get(j);
                    methodList.set(j, methodList.get(j + 1));
                    methodList.set((j + 1), tmpMethod);
                }
            }
        }
        //запускаем методы с аннотацией Test по их приоритету
        for (Method method : methodList) {
            System.out.println("Имя метода к выполнению: " +method.getName());
            method.invoke(doTests);
            System.out.println("___________________________________");
        }

        //сначала запускаем метод ПОСЛЕ, если он есть

        for (Method method : methods) {
            if (method.getDeclaredAnnotation(AfterSuite.class) != null) {
                System.out.println("Имя метода ПОСЛЕ: " +method.getName());
                method.invoke(doTests);
                System.out.println("___________________________________");

            }
        }


    }
}

