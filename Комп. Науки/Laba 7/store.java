import java.util.*;

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

    @Override
    public String toString() {
        return String.format("%s (Вес: %.2f кг, Цена: %.2f)", name, weight, price);
    }
}

class Store {
    protected int cashRegisters;
    protected List<Product> products;
    protected int sellers;
    private static final String[] PRODUCT_NAMES = {"Молоко", "Хлеб", "Сыр", "Яблоки", "Мясо", "Рыба", "Шоколад"};

    public Store(int cashRegisters, int sellers) {
        this.cashRegisters = cashRegisters;
        this.sellers = sellers;
        this.products = new ArrayList<>();
    }

    public void addProduct(String name, double weight, double price) {
        for (Product p : products) {
            if (p.getName().equals(name)) {
                return; // Не добавляем дубликаты
            }
        }
        products.add(new Product(name, weight, price));
    }

    public void generateProducts(int count) {
        addProduct("Шоколад", 1.0, 50); // Любимый продукт всегда добавляется
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            String name = PRODUCT_NAMES[random.nextInt(PRODUCT_NAMES.length)];
            double weight = 0.5 + random.nextDouble() * 10;
            double price = 5 + random.nextDouble() * 15000;
            addProduct(name, weight, price);
        }
    }

    public void removeHeavyAndExpensive() {
        products.removeIf(p -> p.getWeight() > 5 || p.getPrice() > 10000);
    }

    public void prioritizeFavoriteProduct(String favorite) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().equals(favorite)) {
                Product favoriteProduct = products.remove(i);
                products.add(0, favoriteProduct);
                break;
            }
        }
    }

    public void printFilteredProducts() {
        for (Product p : products) {
            if (p.getPrice() < 10 && p.getWeight() > 2) {
                System.out.println(p);
            }
        }
    }

    public double calculateEfficiency() {
        double avgWeight = products.stream().mapToDouble(Product::getWeight).average().orElse(0);
        double cashEfficiency = cashRegisters == 0 ? 0 : (double) sellers / cashRegisters;
        return Math.min(avgWeight * cashEfficiency, 0.99);
    }
}

class Supermarket extends Store {
    public double getArea() {
        return area;
    }
    private double area;
    private List<String> categories;

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
        double cashEfficiency = cashRegisters == 0 ? 0 : (double) sellers / cashRegisters;
        if (categories.isEmpty()) return 0;
        double areaEfficiency = (area / categories.size()) * cashEfficiency;
        return Math.min(areaEfficiency, 0.99);
    }
}
