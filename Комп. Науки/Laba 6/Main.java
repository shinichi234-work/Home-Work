public class Main {
    public static void main(String[] args) {
        Store store = new Store(5, 10);
        store.addProduct("Молоко", 1.0, 100);
        store.addProduct("Хлеб", 0.5, 50);
        System.out.println(store);

        Supermarket supermarket = new Supermarket(10, 20, 500);
        supermarket.addProduct("Сыр", 0.3, 200);
        supermarket.addProduct("Яблоки", 1.2, 150);
        supermarket.addCategory("Молочные продукты");
        supermarket.addCategory("Фрукты");
        supermarket.addCategory("Выпечка");
        System.out.println(supermarket);
    }
}
