import java.awt.Color;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Класс создания графика
 *
 * @author Paikin Pavel
 * @version 1.2
 */
public class Graph {
    private static final Logger log = LoggerFactory.getLogger(Graph.class);

    /**
     * Метод для создания графика частотного анализа на основе словаря
     *
     * @param data Словарь, содержащий ключ(слово) и значение(количество его повторений в тексте)
     */
    public static void graph(HashMap<String, Integer> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String key : data.keySet()) {
            dataset.addValue(data.get(key), "Words", key);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Word Count",
                "Words",
                "Count",
                dataset,
                PlotOrientation.HORIZONTAL,
                false,
                false,
                false
        );

        chart.setBackgroundPaint(Color.WHITE);

        JFrame frame = new JFrame("Chart");
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ChartPanel chartPanel = new ChartPanel(chart);
        frame.getContentPane().add(chartPanel);

        frame.setVisible(true);

        log.info("График выведен на экран");

        question();
    }

    /**
     * Метод, который ожидает ответ пользователя для продолжения работы программы (дает время рассмотреть выведенный график)
     */
    public static void question() {
        Scanner scanner = new Scanner(System.in);
        boolean ok = false;
        do {
            System.out.println("Введите 'ок', чтобы продолжить работу программы:");
            String str = scanner.next();
            if (str.equalsIgnoreCase("ок") || str.equalsIgnoreCase("ok")) {
                ok = true;
            } else {
                System.out.println("Ошибка ввода, попробуйте снова");
            }
        } while (!ok);
    }
}
