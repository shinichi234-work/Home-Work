// Интерфейс Worker
interface Worker {
    default void work() {}
}

// Базовый класс Person
class Person {
    protected String name;
    protected String surname;
    protected String gender;
    protected boolean active;

    public Person(String name, String surname, String gender, boolean active) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.active = active;
    }

    public void sleep() {
        System.out.println(name + " спит.");
    }
    
    // Геттеры и сеттеры
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}

// Класс Employee
class Employee extends Person implements Worker {
    protected String position;
    protected double payment;
    protected int experience;
    protected String department;

    public Employee(String name, String surname, String gender, boolean active, String position, double payment, int experience, String department) {
        super(name, surname, gender, active);
        this.position = position;
        this.payment = payment;
        this.experience = experience;
        this.department = department;
    }

    @Override
    public void work() {
        System.out.println(name + " выполняет свою работу как " + position);
    }

    // Геттеры и сеттеры
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public double getPayment() { return payment; }
    public void setPayment(double payment) { this.payment = payment; }
    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}

// Класс KitchenWorker
class KitchenWorker extends Employee {
    private String kitchenType;

    public KitchenWorker(String name, String surname, String gender, boolean active, double payment, int experience, String department, String kitchenType) {
        super(name, surname, gender, active, "Повар", payment, experience, department);
        this.kitchenType = kitchenType;
    }

    @Override
    public void work() {
        System.out.println(name + " готовит еду на кухне типа " + kitchenType);
    }

    public void cook() {
        System.out.println(name + " готовит фирменное блюдо!");
    }
}

// Класс OfficeWorker
class OfficeWorker extends Employee {
    private String softwareUsed;

    public OfficeWorker(String name, String surname, String gender, boolean active, double payment, int experience, String department, String softwareUsed) {
        super(name, surname, gender, active, "Офисный сотрудник", payment, experience, department);
        this.softwareUsed = softwareUsed;
    }

    @Override
    public void work() {
        System.out.println(name + " работает в офисе, используя " + softwareUsed);
    }

    public void makeReport() {
        System.out.println(name + " составляет отчет.");
    }
}

// Класс GuardWorker
class GuardWorker extends Employee {
    private boolean armed;

    public GuardWorker(String name, String surname, String gender, boolean active, double payment, int experience, String department, boolean armed) {
        super(name, surname, gender, active, "Охранник", payment, experience, department);
        this.armed = armed;
    }

    @Override
    public void work() {
        System.out.println(name + " охраняет объект. Вооружен: " + (armed ? "Да" : "Нет"));
    }

    public void patrol() {
        System.out.println(name + " патрулирует территорию.");
    }
}

// Тестирование классов
public class Main {
    public static void main(String[] args) {
        KitchenWorker cook = new KitchenWorker("Иван", "Петров", "Мужской", true, 50000, 5, "Ресторан", "Итальянская кухня");
        OfficeWorker clerk = new OfficeWorker("Мария", "Иванова", "Женский", true, 70000, 8, "Офис", "MS Office");
        GuardWorker guard = new GuardWorker("Сергей", "Сидоров", "Мужской", true, 60000, 10, "Охрана", true);

        cook.work();
        cook.cook();
        
        clerk.work();
        clerk.makeReport();
        
        guard.work();
        guard.patrol();
    }
}