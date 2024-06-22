import java.util.ArrayList;
import java.util.List;
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

        //создается список потоков
        List<Thread> threads = new ArrayList<>();


        for (String str : texts) {
            Thread thread1 = new Thread(() -> countString(str));
            Thread thread2 = new Thread(() -> literEquals(str));
            Thread thread3 = new Thread(() -> literABC(str));
            threads.add(thread1);
            threads.add(thread2);
            threads.add(thread3);
            thread1.start();
            thread2.start();
            thread3.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Красивых слов длиной 3 буквы : " + counter3
                + "\nКрасивых слов длиной 4 буквы : " + counter4
                + "\nКрасивых слов длиной 5 букв : " + counter5);
    }


    //метод для проверки слов на полиндромность
    public static void countString(String text) {
        //передаем строку в StringBuilder
        // с помощью reverse переворачиваем строку
        if (text.equals(new StringBuilder(text).reverse().toString())) {
            //если условие выполнено ,то соответственно увеличиваем
            // счетчик соответствующий длине слова на 1
            switch (text.length()) {
                case 3:
                    counter3.incrementAndGet();
                    break;
                case 4:
                    counter4.incrementAndGet();
                    break;
                case 5:
                    counter5.incrementAndGet();
                    break;
                default:
            }
        }
    }


    // метод для проверки слов на то что в слове все буквы одинаковые
    public static void literEquals(String text) {
        if (text.chars().allMatch(c -> c == text.charAt(0))) {
            //если условие выполнено ,то соответственно увеличиваем
            // счетчик соответствующий длине слова на 1
            switch (text.length()) {
                case 3:
                    counter3.incrementAndGet();
                    break;
                case 4:
                    counter4.incrementAndGet();
                    break;
                case 5:
                    counter5.incrementAndGet();
                    break;
                default:
            }
        }
    }


    //метод  определяющий находятся ли буквы в слове по возрастанию
    public static void literABC(String text) {
        //передаем в метод matches регулярное выражение на основании на основании
        // которого проверяется удовлетворяет слово условию или нет
        // также проверяем что длина слова равна 5
        if (text.matches("a*b*c*")) {
            //если условие выполнено ,то соответственно увеличиваем
            // счетчик соответствующий длине слова на 1
            switch (text.length()) {
                case 3:
                    counter3.incrementAndGet();
                    break;
                case 4:
                    counter4.incrementAndGet();
                    break;
                case 5:
                    counter5.incrementAndGet();
                    break;
                default:
            }
        }
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

