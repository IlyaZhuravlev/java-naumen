public class Employee {
    private String fullName;
    private Integer age;
    private String department;
    private Double salary;

    public Employee(String fullName, Integer age, String department, Double salary) {
        this.fullName = fullName;
        this.age = age;
        this.department = department;
        this.salary = salary;
    }

    // Геттеры
    public String getFullName() {
        return fullName;
    }

    public Integer getAge() {
        return age;
    }

    public String getDepartment() {
        return department;
    }

    public Double getSalary() {
        return salary;
    }

    // Сеттеры (необязательно для задания, но добавлены для полноты)
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "fullName='" + fullName + '\'' +
                ", age=" + age +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                '}';
    }
}