import java.util.ArrayList;
import java.util.Scanner;

class NotCorrectAgeException extends Exception {
    public NotCorrectAgeException(String message) {
        super(message);
    }
}

class Student {
    private int age;
    private String gender;
    private String firstName;
    private String lastName;
    private String middleName;
    private int course;
    private double averageGrade;

    public Student(int age, String gender, String firstName, String lastName, String middleName, int course, double averageGrade) throws NotCorrectAgeException {
        setAge(age);
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.course = course;
        this.averageGrade = averageGrade;
    }

    public void setAge(int age) throws NotCorrectAgeException {
        if (age < 16 || age > 60) {
            throw new NotCorrectAgeException("Возраст должен быть в диапазоне от 16 до 60 лет.");
        }
        this.age = age;
    }

    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getMiddleName() { return middleName; }
    public int getCourse() { return course; }
    public double getAverageGrade() { return averageGrade; }
    
    @Override
    public String toString() {
        return "Студент: " + firstName + " " + lastName + " " + middleName + ", Возраст: " + age + ", Пол: " + gender + ", Курс: " + course + ", Средний бал: " + averageGrade;
    }
}

class Group {
    private static final int MAX_STUDENTS = 15;
    private static final int MIN_STUDENTS = 3;
    private ArrayList<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        if (students.size() >= MAX_STUDENTS) {
            throw new IllegalStateException("Группа переполнена. Максимальное количество студентов: " + MAX_STUDENTS);
        }
        students.add(student);
    }

    public void addStudent(Scanner scanner) {
        if (students.size() >= MAX_STUDENTS) {
            throw new IllegalStateException("Группа переполнена. Максимальное количество студентов: " + MAX_STUDENTS);
        }
        try {
            System.out.print("Введите возраст: ");
            int age = getIntInput(scanner);

            scanner.nextLine(); // Очищаем буфер после числа
            System.out.print("Введите пол: ");
            String gender = scanner.nextLine();
            System.out.print("Введите имя: ");
            String firstName = scanner.nextLine();
            System.out.print("Введите фамилию: ");
            String lastName = scanner.nextLine();
            System.out.print("Введите отчество: ");
            String middleName = scanner.nextLine();
            System.out.print("Введите курс: ");
            int course = getIntInput(scanner);
            System.out.print("Введите средний бал: ");
            double averageGrade = getDoubleInput(scanner);

            Student student = new Student(age, gender, firstName, lastName, middleName, course, averageGrade);
            students.add(student);
            System.out.println("Студент добавлен успешно.");
            System.out.println(student); // Вывод информации о студенте
            listStudents(); // Вывод всех студентов в группе
        } catch (NotCorrectAgeException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private int getIntInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Ошибка! Введите целое число.");
            scanner.next(); // Пропускаем неверный ввод
        }
        return scanner.nextInt();
    }

    private double getDoubleInput(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.println("Ошибка! Введите число с плавающей точкой.");
            scanner.next(); // Пропускаем неверный ввод
        }
        return scanner.nextDouble();
    }

    public void listStudents() {
        System.out.println("Список студентов группы:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println(i + ". " + students.get(i));
        }
    }

    public Student getStudent(int index) {
        if (index < 0 || index >= students.size()) {
            throw new IndexOutOfBoundsException("Неверный номер студента.");
        }
        return students.get(index);
    }
}

public class Main {
    public static void main(String[] args) {
        System.setProperty("console.encoding", "UTF-8"); // Устанавливаем кодировку консоли
        Group group = new Group();
        Scanner scanner = new Scanner(System.in, java.nio.charset.StandardCharsets.UTF_8);
        
        try {
            group.addStudent(new Student(20, "Мужской", "Иван", "Иванов", "Иванович", 2, 4.5));
            group.addStudent(scanner);
            
            System.out.println("Выберите номер студента для просмотра данных: ");
            group.listStudents();
            System.out.print("Введите номер студента: ");
            int index = scanner.nextInt();
            scanner.nextLine(); // Очищаем буфер
            
            Student student = group.getStudent(index);
            System.out.println("Данные студента: " + student);
        } catch (NotCorrectAgeException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
