import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeFilterExample {
    public static void main(String[] args) {
        // 2. Создаем предзаполненный список сотрудников
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Иванов Иван Иванович", 25, "IT", 85000.0));
        employees.add(new Employee("Петров Петр Петрович", 35, "HR", 75000.0));
        employees.add(new Employee("Сидорова Анна Михайловна", 28, "Finance", 90000.0));
        employees.add(new Employee("Кузнецов Алексей Викторович", 42, "IT", 110000.0));
        employees.add(new Employee("Смирнова Елена Сергеевна", 31, "Marketing", 80000.0));

        System.out.println("Все сотрудники:");
        employees.forEach(System.out::println);

        // 3. Фильтруем сотрудников старше 30 лет с помощью Stream API
        List<Employee> filteredEmployees = employees.stream()
                .filter(employee -> employee.getAge() > 30)
                .collect(Collectors.toList());

        System.out.println("\nСотрудники старше 30 лет:");
        filteredEmployees.forEach(System.out::println);
    }
}