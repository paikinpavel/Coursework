import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Главный класс кода
 *
 * @author Paikin Pavel
 * @version 1.4
 */
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    /**
     * Главный метод кода, который просит у пользователя пути к файлом, для дальнейшей работы с ними
     *
     * @param args Аргументы командной строки
     */
    public static void main(String[] args) {
        log.info("Запуск приложения");

        log.trace("trace");
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");

        Scanner scanner = new Scanner(System.in);
        boolean done = false;

        while (!done) {
            String path1 = getPath(scanner, "Введите полный путь к файлу, тематику которого хотите определить:");
            String path2 = getPath(scanner, "Введите полный путь к файлу, в котором будет записан частотный анализ текста:");
            String topic = WorkWithFiles.workWithFiles(path1, path2);
            System.out.println("Тематика текста - " + topic);
            done = askContinue(scanner);
        }

        scanner.close();
        log.info("Завершение приложения");
    }

    /**
     * Метод, который обрабатывает пути к файлам, введенные пользователем
     *
     * @param scanner Введенный пользователем путь к файлу
     * @param message Вывод на экран того, что именно требуют ввести пользователя
     * @return Путь к файлу
     */
    private static String getPath(Scanner scanner, String message) {
        String path;
        while (true) {
            try {
                log.info("Запрос пути к файлу");
                System.out.println(message);
                path = scanner.nextLine();
                String extension = path.substring(path.lastIndexOf(".") + 1);
                if (extension.equals("txt") || extension.equals("docx") || extension.equals("doc")) {
                    log.info("Путь к файлу: " + path);
                    return path;
                } else {
                    log.error("Неверный формат файла");
                    System.out.println("Ошибка ввода, попробуйте снова");
                }
            } catch (StringIndexOutOfBoundsException e) {
                log.error("Ошибка ввода пути к файлу", e);
                System.out.println("Ошибка ввода, попробуйте снова");
            }
        }
    }

    /**
     * Метод, который спрашивает у пользователя, хочет ли он продолжить работу приложения
     *
     * @param scanner Ответ пользователя
     * @return Ответ пользователя
     */
    private static boolean askContinue(Scanner scanner) {
        while (true) {
            log.info("Запрос на продолжение обработки файлов");
            System.out.println("Хотите ли вы обработать еще один файл? (ответьте 'yes' или 'no')");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                return false;
            } else if (response.equalsIgnoreCase("no")) {
                return true;
            } else {
                log.warn("Неверный ввод при запросе на продолжение");
                System.out.println("Ошибка ввода, попробуйте снова");
            }
        }
    }
}
