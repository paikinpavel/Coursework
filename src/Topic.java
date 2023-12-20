import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс определения тематики текста
 *
 * @author Paikin Pavel
 * @version 1.0
 */
public class Topic {
    private static final Logger log = LoggerFactory.getLogger(Topic.class);

    /**
     * Метод, который обрабатывает Словрь с частотным анализом текста и на основе внутренних словарей определяет тематику текста
     *
     * @param wordFrequency Словарь, содержащий ключ(слово) и значение(количество его повторений в тексте)
     * @return Тематика текста
     */
    public static String definingTopic(Map<String, Integer> wordFrequency) {
        String[] medicalTerms = {"здоровый", "здорового", "здоровому", "здоровым", "здоровыми", "здоровая", "здоровую", "здоровой", "здоровою", "здорово", "доктор", "доктора", "доктору", "доктором", "докторе", "докторы", "больница", "больницы", "больнице", "больницу", "больницей", "больницам", "больницах", "лекарство", "лекарства", "лекарству", "лекарством", "лекарстве", "лекарствах", "болезнь", "болезни", "болезнью", "болезней", "болезне", "болезнями", "болезнях", "пациент", "пациента", "пациенту", "пациентом", "пациенте", "пациенты", "пациентов", "пациентам", "пациентами", "пациентах", "лечение", "лечения", "лечению", "лечением", "лечении", "лечениях", "симптом", "симптома", "симптому", "симптомом", "симптомы", "симптомов", "симптомам", "симптомами", "симптомах", "диагноз", "диагноза", "диагнозу", "диагнозом", "диагнозе", "диагнозы", "диагнозов", "диагнозам", "диагнозами", "диагнозах", "медицина", "медицины", "медицине", "медицину", "медициной", "медицинах", "реабилитация", "реабилитации", "реабилитацию", "реабилитацией", "реабилитациях", "хирургия", "хирургию", "хирургией", "хирургии", "хирургиях", "терапия", "терапию", "терапией", "терапии", "терапиях", "врач", "врача", "врачу", "врачом", "враче", "врачи", "врачей", "врачам", "врачами", "врачах", "медсестра", "медсестры", "медсестре", "медсестру", "медсестрой", "медсестрам", "медсестрах", "операция", "операцию", "операцией", "операции", "операциях"};
        String[] historyTerms = {"история", "война", "империя", "революция", "правитель", "великий", "король", "битва", "прошлое", "герой", "цивилизация", "истории", "войны", "империи", "революции", "правителя", "великого", "короля", "битвы", "прошлого", "героя", "цивилизации", "историю", "войну", "империю", "революцию", "правителю", "великому", "королю", "битву", "прошлое", "героя", "цивилизацию", "историей", "войной", "империей", "революцией", "правителем", "великим", "королем", "битвой", "прошлым", "героем", "цивилизацией", "истории", "войны", "империи", "революции", "правителя", "великого", "короля", "битвы", "прошлого", "героя", "цивилизации", "истории", "войны", "империи", "революции", "правителя", "великого", "короля", "битвы", "прошлого", "героя", "цивилизации"};
        String[] programmingTerms = {"программирование", "программированию", "программировании", "программированием", "программировании", "алгоритм", "алгоритма", "алгоритму", "алгоритмом", "алгоритме", "переменная", "переменной", "переменной", "переменную", "переменной", "цикл", "цикла", "циклу", "циклом", "цикле", "функция", "функции", "функцию", "функцией", "класс", "класса", "классу", "классом", "классе", "объект", "объекта", "объекту", "объектом", "объекте", "интерфейс", "интерфейса", "интерфейсу", "интерфейсом", "интерфейсе", "метод", "метода", "методу", "методом", "методе", "пакет", "пакета", "пакету", "пакетом", "пакете", "библиотека", "библиотеки", "библиотеке", "библиотеку", "библиотекой", "компилятор", "компилятора", "компилятору", "компилятором", "компиляторе", "отладчик", "отладчика", "отладчику", "отладчиком", "отладчике", "дефект", "дефекта", "дефекту", "дефектом", "дефекте", "оператор", "оператора", "оператору", "оператором", "операторе", "условие", "условия", "условию", "условием", "условии", "рекурсия", "рекурсии", "рекурсию", "рекурсией", "память", "памятью", "памяти", "поток", "потока", "потоку", "потоком", "потоке",};
        String[] networkTerms = {"сеть", "сети", "сетью", "сетей", "сетям", "сетями", "сетях", "интернет", "интернета", "интернету", "интернетом", "интернете", "роутер", "роутера", "роутеру", "роутером", "роутере", "сервер", "сервера", "серверу", "сервером", "сервере", "безопасность", "безопасности", "безопасностью", "браузер", "браузера", "браузеру", "браузером", "браузере", "протокол", "протокола", "протоколу", "протоколом", "протоколе", "адрес", "адреса", "адресу", "адресом", "адресе", "соединение", "соединения", "соединению", "соединением", "соединении", "пользователь", "пользователя", "пользователю", "пользователем", "пользователе", "коммутатор", "коммутатора", "коммутатору", "коммутатором", "коммутаторе", "маршрутизатор", "маршрутизатора", "маршрутизатору", "маршрутизатором", "маршрутизаторе", "сайт", "сайта", "сайту", "сайтом", "сайте", "хост", "хоста", "хосту", "хостом", "хосте", "Wi-Fi", "провайдер", "фаервол", "трафик", "веб", "клиент", "сервер", "VPN", "DNS", "IP-адрес", "канал", "прокси", "безопасный", "модем", "модема", "модему", "модемом", "модеме"};
        String[] cryptographyTerms = {"шифрование", "шифрованию", "шифрования", "шифрование", "шифрованием", "шифровании", "дешифрование", "дешифрованию", "дешифрования", "дешифрованием", "дешифровании", "алгоритм", "алгоритма", "алгоритму", "алгоритмом", "алгоритме", "ключ", "ключа", "ключу", "ключом", "шифр", "шифра", "шифру", "шифром", "шифре", "криптография", "криптографию", "криптографией", "криптографии", "криптоанализ", "криптоанализу", "криптоанализа", "криптоанализом", "криптоанализе", "шифровальный", "шифровального", "шифровальному", "шифровальным", "шифровальном", "зашифровать", "зашифровывать", "зашифровыванию", "зашифрования", "зашифровыванием", "зашифровывании", "расшифровать", "расшифровывать", "расшифровыванию", "расшифрования", "расшифровыванием", "расшифровывании", "шифровщик", "шифровщика", "шифровщику", "шифровщиком", "шифровщике", "шифротекст", "шифротекста", "шифротексту", "шифротекстом", "шифротексте", "шифрозащита", "шифрозащиты", "шифрозащите", "шифрозащиту", "шифрозащитой", "криптозащита", "криптозащиты", "криптозащите", "криптозащиту", "криптозащитой", "криптоаналитик", "криптоаналитика", "криптоаналитику", "криптоаналитиком", "криптоаналитике", "криптология", "криптологию", "криптологией", "криптологии"};
        String[] financeTerms = {"доход", "дохода", "доходу", "доходом", "доходе", "доходы", "доходов", "доходам", "доходами", "доходах", "расход", "расхода", "расходу", "расходом", "расходе", "расходы", "расходов", "расходам", "расходами", "расходах", "расходу", "расходом", "бюджет", "бюджета", "бюджету", "бюджетом", "бюджете", "бюджеты", "бюджетов", "бюджетам", "бюджетами", "бюджетах", "бюджетов", "капитал", "капитала", "капиталу", "капиталом", "капитале", "капиталы", "капиталов", "капиталам", "капиталами", "капиталах", "капиталов", "инвестиции", "инвестиций", "инвестициям", "инвестициями", "инвестициях", "инвестирований", "инвестированиям", "инвестированиями", "инвестированиях", "акции", "акций", "акциям", "акциями", "акциях", "акции", "фондовый", "фондового", "фондовому", "фондовым", "фондовом", "фондовая", "фондовой", "фондовую", "фондовые", "фондовых", "фондовыми", "фондовых", "валюта", "валюты", "валюте", "валютой", "валют", "валютам", "валютами", "валютах", "финансовый", "финансового", "финансовому", "финансовым", "финансовом", "финансовая", "финансовой", "финансовую", "финансовые", "финансовых", "финансовыми", "финансовых", "инфляция", "инфляцией", "инфляции", "экономика", "экономики", "экономике", "экономикой", "экономик", "банк", "банка", "банку", "банком", "банке", "банки", "банков", "банкам", "банками", "банках", "банк", "банки", "кредит", "кредита", "кредиту", "кредитом", "кредите", "кредиты", "кредитов"};

        List<String> medicalWords = Arrays.asList(medicalTerms);
        List<String> historyWords = Arrays.asList(historyTerms);
        List<String> programmingWords = Arrays.asList(programmingTerms);
        List<String> networkWords = Arrays.asList(networkTerms);
        List<String> cryptographyWords = Arrays.asList(cryptographyTerms);
        List<String> financeWords = Arrays.asList(financeTerms);

        HashMap<String, Integer> medicine = new HashMap<>();
        HashMap<String, Integer> history = new HashMap<>();
        HashMap<String, Integer> programming = new HashMap<>();
        HashMap<String, Integer> network = new HashMap<>();
        HashMap<String, Integer> cryptography = new HashMap<>();
        HashMap<String, Integer> finance = new HashMap<>();

        Set<String> keys = wordFrequency.keySet();
        int percentMed = 0, percentHis = 0, percentPro = 0, percentNet = 0, percentCrypto = 0, percentFin = 0;
        HashMap<String, Float> percent = new HashMap<>();
        log.info("Поиск ключевых слов");
        for (String word : keys) {
            if (medicalWords.contains(word)) {
                percentMed += wordFrequency.get(word);
                medicine.put(word, medicine.getOrDefault(word, 0) + wordFrequency.get(word));
            }
            if (historyWords.contains(word)) {
                percentHis += wordFrequency.get(word);
                history.put(word, history.getOrDefault(word, 0) + wordFrequency.get(word));
            }
            if (programmingWords.contains(word)) {
                percentPro += wordFrequency.get(word);
                programming.put(word, programming.getOrDefault(word, 0) + wordFrequency.get(word));
            }
            if (networkWords.contains(word)) {
                percentNet += wordFrequency.get(word);
                network.put(word, network.getOrDefault(word, 0) + wordFrequency.get(word));
            }
            if (cryptographyWords.contains(word)) {
                percentCrypto += wordFrequency.get(word);
                cryptography.put(word, cryptography.getOrDefault(word, 0) + wordFrequency.get(word));
            }
            if (financeWords.contains(word)) {
                percentFin += wordFrequency.get(word);
                finance.put(word, finance.getOrDefault(word, 0) + wordFrequency.get(word));
            }
        }
        float sum = 0.0f;
        for (float f : wordFrequency.values()) {
            sum += f;
        }

        percent.put("Медицина", (float) percentMed / sum);
        percent.put("История", (float) percentHis / sum);
        percent.put("Программировние", (float) percentPro / sum);
        percent.put("Сети", (float) percentNet / sum);
        percent.put("Криптография", (float) percentCrypto / sum);
        percent.put("Финансы", (float) percentFin / sum);

        log.info("Нахождение наибольшего вхождения слов тематик");
        float max = Collections.max(percent.values());
        StringBuilder subject = new StringBuilder();
        for (String word : percent.keySet()) {
            if (percent.get(word).equals(max)) {
                subject.append(word).append(" ");
            }
        }
        log.info("Вывод графика");
        String topic = subject.toString();
        System.out.println("График ключевых слов текста:");
        if (topic.equals("Медицина ")) {
            Graph.graph(medicine);
        }
        if (topic.equals("История ")) {
            Graph.graph(history);
        }
        if (topic.equals("Программировние ")) {
            Graph.graph(programming);
        }
        if (topic.equals("Сети ")) {
            Graph.graph(network);
        }
        if (topic.equals("Криптография ")) {
            Graph.graph(cryptography);
        }
        if (topic.equals("Финансы ")) {
            Graph.graph(finance);
        }

        return topic;
    }
}
