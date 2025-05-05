import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MergeSortExample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите количество элементов в списке (n >= 0): ");
        int n = scanner.nextInt();

        if (n <= 0) {
            System.out.println("Список пуст. Завершение программы.");
            return;
        }

        // Заполняем список случайными числами
        ArrayList<Double> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            list.add(random.nextDouble() * 100); // числа от 0.0 до 100.0
        }

        // Выводим исходный список
        System.out.println("\nИсходный список:");
        System.out.println(list);

        // Сортируем список слиянием
        mergeSort(list, 0, list.size() - 1);

        // Выводим отсортированный список
        System.out.println("\nОтсортированный список (Merge Sort):");
        System.out.println(list);
    }

    // Рекурсивная реализация сортировки слиянием
    public static void mergeSort(ArrayList<Double> list, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(list, left, mid);        // Сортировка левой половины
            mergeSort(list, mid + 1, right);   // Сортировка правой половины
            merge(list, left, mid, right);     // Слияние двух половин
        }
    }

    // Метод для слияния двух отсортированных подсписков
    public static void merge(ArrayList<Double> list, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Временные списки для левой и правой частей
        ArrayList<Double> leftList = new ArrayList<>();
        ArrayList<Double> rightList = new ArrayList<>();

        // Копируем данные во временные списки
        for (int i = 0; i < n1; i++) {
            leftList.add(list.get(left + i));
        }
        for (int j = 0; j < n2; j++) {
            rightList.add(list.get(mid + 1 + j));
        }

        // Слияние временных списков обратно в основной список
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftList.get(i) <= rightList.get(j)) {
                list.set(k, leftList.get(i));
                i++;
            } else {
                list.set(k, rightList.get(j));
                j++;
            }
            k++;
        }

        // Копируем оставшиеся элементы leftList (если они есть)
        while (i < n1) {
            list.set(k, leftList.get(i));
            i++;
            k++;
        }

        // Копируем оставшиеся элементы rightList (если они есть)
        while (j < n2) {
            list.set(k, rightList.get(j));
            j++;
            k++;
        }
    }
}