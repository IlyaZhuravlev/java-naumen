import java.util.Random;
import java.util.Scanner;

public class MaxAbsoluteValue {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите количество элементов в массиве (n >= 0): ");
        int n = scanner.nextInt();

        if (n <= 0) {
            System.out.println("Массив пуст. Завершение программы.");
            return;
        }

        int[] array = new int[n];
        Random random = new Random();

        // Заполняем массив случайными числами (включая отрицательные)
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(200) - 100; // числа от -100 до 100
        }

        // Выводим массив
        System.out.print("Массив: [");
        for (int i = 0; i < n; i++) {
            System.out.print(array[i]);
            if (i < n - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");

        // Находим максимальное значение по модулю
        int maxAbs = Math.abs(array[0]);
        for (int num : array) {
            int absNum = Math.abs(num);
            if (absNum > maxAbs) {
                maxAbs = absNum;
            }
        }
        System.out.println("Максимальное значение по модулю: " + maxAbs);
    }
}