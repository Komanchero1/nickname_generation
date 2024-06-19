import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    //создается и инициализируется объект для доступа
    // к атомарным методам с целочисленными значениями
    static AtomicInteger counter3 = new AtomicInteger(0);
    static AtomicInteger counter4 = new AtomicInteger(0);
    static AtomicInteger counter5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        //заполняем массив рандомно созданными строками длиной 3 - 5 букв из литеров "abc"
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        //создается поток с лямбда выражением которое
        // находит в массиве строк полиндромные слова
        Thread thread1 = new Thread(() -> {
            for (String str : texts) {
                //проверяем что бы длина строки была 3 буквы , передаем строку в StringBuilder
                // с помощью reverse переворачиваем строку
                if (str.length() == 3 && str.equals(new StringBuilder(str).reverse().toString())) {
                    //если условие выполнено  увеличиваем значения переменной counter3 на 1
                    // и возвращаем увеличенное значение с помощью метода incrementAndGet
                    counter3.incrementAndGet();
                }
            }
        });
        thread1.start();//стартуем поток
        thread1.join();//текущий поток ожидает завершения выполнения потока "thread1"
        System.out.println("Красивых слов с длинной 3  - " + counter3);


        //создается поток с лямбда выражением которое определяет
        // состоит ли слово из одинаковых букв
        Thread thread2 = new Thread(() -> {
            for (String text : texts) {
                //проверяем длину строки должна быть 4 буквы , text.charAt(0) - первый символ строки
                // метод allMatch проверяет соответствуют все символы условию заданному лямбда
                // выражением c -> c == text.charAt(0) где "с" текущий символ
                if (text.length() == 4 && text.chars().allMatch(c -> c == text.charAt(0))) {
                    //если условие выполнено  увеличиваем значения переменной counter4 на 1
                    // и возвращаем увеличенное значение с помощью метода incrementAndGet
                    counter4.incrementAndGet();
                }
            }
        });
        thread2.start();//стартуем поток
        thread2.join();//текущий поток ожидает завершения выполнения потока "thread2"
        System.out.println("Красивых слов с длинной 4  - " + counter4);


        //создается поток с лямбда выражением которое определяет
        // находятся ли буквы в слове по возрастанию
        Thread thread3 = new Thread(() -> {
            for (String txt : texts) {
                //передаем в метод matches регулярное выражение на основании на основании
                // которого проверяется удовлетворяет слово условию или нет
                // также проверяем что длина слова равна 5
                if (txt.length() == 5 && txt.matches("a*b*c*")) {
                    //если условие выполнено  увеличиваем значения переменной counter5 на 1
                    // и возвращаем увеличенное значение с помощью метода incrementAndGet
                    counter5.incrementAndGet();
                }
            }
        });
        thread3.start();//стартуем поток
        thread3.join();//текущий поток ожидает завершения выполнения потока "thread3"
        System.out.println("Красивых слов с длинной 5 -  " + counter5);
    }

    //метод для рандомного создания строки из заданных букв и заданной длины
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}

