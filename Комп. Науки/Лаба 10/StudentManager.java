import java.util.*;

class Student {
    private String firstName;
    private String lastName;
    private String middleName;
    private int age;
    private double averageGrade;
    private double scholarship;
    private int attendedClasses;
    private int missedClasses;
    private String gender;

    public Student(String firstName, String lastName, String middleName, int age, double averageGrade, double scholarship, int attendedClasses, int missedClasses, String gender) throws IllegalArgumentException {
        if (firstName.equals("Алексей") && lastName.equals("Белоусов") && middleName.equals("Юрьевич")) {
            throw new IllegalArgumentException("Такой студент не может существовать!");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.age = age;
        this.averageGrade = averageGrade;
        this.scholarship = scholarship;
        this.attendedClasses = attendedClasses;
        this.missedClasses = missedClasses;
        this.gender = gender;
    }

    public double getAttendancePercentage() {
        int totalClasses = attendedClasses + missedClasses;
        return totalClasses == 0 ? 0 : (double) attendedClasses / totalClasses * 100;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public void setScholarship(double scholarship) {
        this.scholarship = scholarship;
    }

    public void addAttendance(boolean attended) {
        if (attended) {
            attendedClasses++;
        } else {
            missedClasses++;
        }
    }

    public String toString() {
        return String.format("%s %s %s, Age: %d, Grade: %.2f, Scholarship: %.2f, Attendance: %.2f%%", firstName, lastName, middleName, age, averageGrade, scholarship, getAttendancePercentage());
    }

    public double getAverageGrade() { return averageGrade; }
    public double getScholarship() { return scholarship; }
    public int getAge() { return age; }
}

class CompareByGrade implements Comparator<Student> {
    public int compare(Student s1, Student s2) {
        return Double.compare(s2.getAverageGrade(), s1.getAverageGrade());
    }
}

class CompareByScholarship implements Comparator<Student> {
    public int compare(Student s1, Student s2) {
        return Double.compare(s2.getScholarship(), s1.getScholarship());
    }
}

class CompareByAge implements Comparator<Student> {
    public int compare(Student s1, Student s2) {
        return Integer.compare(s1.getAge(), s2.getAge());
    }
}

public class StudentManager {
    private List<Student> students = new ArrayList<>();
    private TreeSet<Student> sortedByGrade = new TreeSet<>(new CompareByGrade());
    private TreeSet<Student> sortedByScholarship = new TreeSet<>(new CompareByScholarship());
    private TreeSet<Student> sortedByAge = new TreeSet<>(new CompareByAge());

    public void addStudent(Student student) {
        students.add(student);
        sortedByGrade.add(student);
        sortedByScholarship.add(student);
        sortedByAge.add(student);
    }

    public void removeStudent(String lastName) {
        students.removeIf(s -> s.toString().contains(lastName));
        sortedByGrade.removeIf(s -> s.toString().contains(lastName));
        sortedByScholarship.removeIf(s -> s.toString().contains(lastName));
        sortedByAge.removeIf(s -> s.toString().contains(lastName));
    }

    public void displayStudents(Collection<Student> collection) {
        for (Student s : collection) {
            System.out.println(s);
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Добавить студента\n2. Удалить студента\n3. Изменить оценку\n4. Изменить стипендию\n5. Добавить посещение\n6. Показать студентов\n7. Выход");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        System.out.println("Введите имя, фамилию, отчество, возраст, средний балл, стипендию, пол");
                        try {
                            addStudent(new Student(scanner.next(), scanner.next(), scanner.next(), scanner.nextInt(), scanner.nextDouble(), scanner.nextDouble(), 0, 0, scanner.next()));
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 2:
                        System.out.println("Введите фамилию");
                        removeStudent(scanner.next());
                        break;
                    case 3:
                        System.out.println("Введите фамилию и новую оценку");
                        students.forEach(s -> { if (s.toString().contains(scanner.next())) s.setAverageGrade(scanner.nextDouble()); });
                        break;
                    case 4:
                        System.out.println("Введите фамилию и новую стипендию");
                        students.forEach(s -> { if (s.toString().contains(scanner.next())) s.setScholarship(scanner.nextDouble()); });
                        break;
                    case 5:
                        System.out.println("Введите фамилию и 1 - посещено, 0 - не посещено");
                        students.forEach(s -> { if (s.toString().contains(scanner.next())) s.addAttendance(scanner.nextInt() == 1); });
                        break;
                    case 6:
                        System.out.println("Сортировать по: 1-средняя оценка, 2-стипендия, 3-возраст");
                        int sortChoice = scanner.nextInt();
                        switch (sortChoice) {
                            case 1 -> displayStudents(sortedByGrade);
                            case 2 -> displayStudents(sortedByScholarship);
                            case 3 -> displayStudents(sortedByAge);
                            default -> displayStudents(students);
                        }
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("Неверный ввод");
                }
            } catch (Exception e) {
                System.out.println("Ошибка ввода");
                scanner.nextLine();
            }
        }
    }

    public static void main(String[] args) {
        new StudentManager().run();
    }
}