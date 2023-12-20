import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * Класс работы с файлами. Он обрабатывает файлы формата txt, docx, doc и записывает результат своей работы в другой файл
 *
 * @author Paikin Pavel
 * @version 1.6
 */
public class WorkWithFiles {
    private static final Logger log = LoggerFactory.getLogger(WorkWithFiles.class);

    /**
     * Главный метод в классе, который с помощью других методов обрабатывает файл и возврщает его тематику
     *
     * @param path1 Путь к файлу, частотный анализ которого пользователь хочет получить
     * @param path2 Путь к файлу, в котором будет записан результат частотного анализа
     * @return Тематику текста
     */
    public static String workWithFiles(String path1, String path2) {
        log.info("Получение расширения файла");
        String extension = getFileExtension(path1);

        log.info("Мап для хранения частоты слов создан");
        Map<String, Integer> wordFrequency = new HashMap<>();

        log.info("Создана переменная с темой файла");
        String topic = "";

        log.info("Чтение файла и обработка содержимого в зависимости от расширения");
        try {
            switch (extension) {
                case "txt" -> processTxtFile(path1, wordFrequency);
                case "doc" -> processDocFile(path1, wordFrequency);
                case "docx" -> processDocxFile(path1, wordFrequency);
                default -> {
                    System.out.println("Неподдерживаемый формат файла!");
                    return null;
                }
            }

            log.info("Создание нового файла с частотным анализом");
            topic = createFrequencyFile(wordFrequency, path2);
        } catch (FileNotFoundException e) {
            log.error("Ошибка", e);
            System.out.println("Файл не найден!");
            String[] arguments = new String[]{""};
            Main.main(arguments);
        } catch (IOException e) {
            log.error("Ошибка", e);
        }
        return topic;
    }

    /**
     * Метод для получение расширения файла
     *
     * @param filePath Путь файла
     * @return Расширение файла
     */
    private static String getFileExtension(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1);
    }

    /**
     * Метод для обработки файла формата txt
     *
     * @param filePath      Путь файла, который выбран для частотного анализа
     * @param wordFrequency Словарь, содержащий ключ(слово) и значение(количество его повторений в тексте)
     * @throws IOException Обработка исключения, представляющего ошибку, возникшую при выполнении операций ввода или вывода
     */
    private static void processTxtFile(String filePath, Map<String, Integer> wordFrequency) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        Scanner scanner = new Scanner(fis);

        while (scanner.hasNext()) {
            String word = scanner.next();
            String cleanedWord = word.replaceAll("[^a-zA-Zа-яА-Я]", "").toLowerCase();

            if (!cleanedWord.isEmpty()) {
                int count = wordFrequency.getOrDefault(cleanedWord, 0);
                wordFrequency.put(cleanedWord, count + 1);
            }
        }
    }

    /**
     * Метод для обработки файла формата Doc
     *
     * @param filePath      Путь файла, который выбран для частотного анализа
     * @param wordFrequency Словарь, содержащий ключ(слово) и значение(количество его повторений в тексте)
     * @throws IOException Обработка исключения, представляющего ошибку, возникшую при выполнении операций ввода или вывода
     */
    private static void processDocFile(String filePath, Map<String, Integer> wordFrequency) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        HWPFDocument doc = new HWPFDocument(fis);

        WordExtractor extractor = new WordExtractor(doc);
        String content = extractor.getText();

        String[] words = content.split("\\s+");

        for (String word : words) {
            String cleanedWord = word.replaceAll("[^a-zA-Zа-яА-Я]", "").toLowerCase();
            wordFrequency.put(cleanedWord, wordFrequency.getOrDefault(cleanedWord, 0) + 1);
        }

        fis.close();
    }

    /**
     * Метод для обработки файла формата docx
     *
     * @param filePath      Путь файла, который выбран для частотного анализа
     * @param wordFrequency Словарь, содержащий ключ(слово) и значение(количество его повторений в тексте)
     * @throws IOException Обработка исключения, представляющего ошибку, возникшую при выполнении операций ввода или вывода
     */
    private static void processDocxFile(String filePath, Map<String, Integer> wordFrequency) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        XWPFDocument docx = new XWPFDocument(fis);

        XWPFWordExtractor extractor = new XWPFWordExtractor(docx);
        String content = extractor.getText();

        String[] words = content.split("\\s+");

        for (String word : words) {
            String cleanedWord = word.replaceAll("[^a-zA-Zа-яА-Я]", "").toLowerCase();
            wordFrequency.put(cleanedWord, wordFrequency.getOrDefault(cleanedWord, 0) + 1);
        }

        fis.close();
    }

    /**
     * Метод для создания файла, в котором будет создан частотный анализ. Также метод обращается к другим классам и их методам (Topic и Graph)
     *
     * @param wordFrequency Словарь, содержащий ключ(слово) и значение(количество его повторений в тексте)
     * @param path          Путь файла, в котором будет храниться частотный анализ
     * @return Тематику текста
     * @throws IOException Обработка исключения, представляющего ошибку, возникшую при выполнении операций ввода или вывода
     */
    private static String createFrequencyFile(Map<String, Integer> wordFrequency, String path) throws IOException {
        File outputFile = new File(path);
        FileOutputStream fos = new FileOutputStream(outputFile);

        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            String word = entry.getKey();
            int frequency = entry.getValue();

            String line = word + " - " + frequency + "\n";
            fos.write(line.getBytes());
        }
        log.info("Создался файл с частотным анализом");
        fos.close();

        getGraph(wordFrequency);
        return Topic.definingTopic(wordFrequency);
    }

    /**
     * Метод, который вызывает функцию из другого класса, для получения графика частотного анализа
     *
     * @param wordFrequency Словарь, содержащий ключ(слово) и значение(количество его повторений в тексте)
     */
    private static void getGraph(Map<String, Integer> wordFrequency) {
        System.out.println("График частотного анализа всего текста:");
        log.info("Вывод графика");
        Graph.graph((HashMap<String, Integer>) wordFrequency);
    }
}
