import java.util.ArrayList;
import java.util.List;


class Product {
    private double weight;
    private double price;
    private String name;

    public Product(String name, double weight, double price) {
        this.name = name;
        this.weight = weight;
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}

class Store {
    protected int cashRegisters; // Количество касс
    protected List<Product> products; // Список товаров
    protected int sellers; // Количество продавцов

    public Store() {
        this.cashRegisters = 1;
        this.products = new ArrayList<>();
        this.sellers = 1;
    }

    public Store(int cashRegisters, int sellers) {
        this.cashRegisters = cashRegisters;
        this.sellers = sellers;
        this.products = new ArrayList<>();
    }

    public void addProduct(String name, double weight, double price) {
        products.add(new Product(name, weight, price));
    }

    public double calculateAverageProductWeight() {
        if (products.isEmpty()) return 0;
        double totalWeight = 0;
        for (Product product : products) {
            totalWeight += product.getWeight();
        }
        return totalWeight / products.size();
    }

    public double calculateCashRegisterEfficiency() {
        if (cashRegisters == 0) return 0;
        return (double) sellers / cashRegisters;
    }

    public double calculateEfficiency() {
        double avgWeight = calculateAverageProductWeight();
        double cashEfficiency = calculateCashRegisterEfficiency();
        return (avgWeight * cashEfficiency) < 1 ? (avgWeight * cashEfficiency) : 0.99;
    }

    public String toString() {
        return "Магазин с " + cashRegisters + " кассами, " + sellers + " продавцами. Эффективность: " + calculateEfficiency();
    }
}


class Supermarket extends Store {
    private double area; // Площадь магазина
    private List<String> categories;

    public Supermarket() {
        super();
        this.area = 100.0;
        this.categories = new ArrayList<>();
    }

    public Supermarket(int cashRegisters, int sellers, double area) {
        super(cashRegisters, sellers);
        this.area = area;
        this.categories = new ArrayList<>();
    }

    public void addCategory(String category) {
        categories.add(category);
    }

    @Override
    public double calculateEfficiency() {
        double cashEfficiency = calculateCashRegisterEfficiency();
        if (categories.isEmpty()) return 0;
        double areaEfficiency = (area / categories.size()) * cashEfficiency;
        return areaEfficiency < 1 ? areaEfficiency : 0.99;
    }

    @Override
    public String toString() {
        return "Супермаркет с " + cashRegisters + " кассами, " + sellers + " продавцами, площадью " + area + " кв.м. Эффективность: " + calculateEfficiency();
    }
}
