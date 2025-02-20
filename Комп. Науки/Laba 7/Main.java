public class Main {
    public static void main(String[] args) {
        Store store = new Store(5, 10);
        store.generateProducts(10);
        
        System.out.println("Магазин с " + store.cashRegisters + " кассами, " + store.sellers + " продавцами.");
        System.out.println("Эффективность магазина: " + store.calculateEfficiency());
        System.out.println("Корзина после генерации продуктов:");
        store.products.forEach(System.out::println);
        
        store.removeHeavyAndExpensive();
        System.out.println("\nКорзина после удаления тяжелых и дорогих товаров:");
        store.products.forEach(System.out::println);
        
        store.prioritizeFavoriteProduct("Шоколад");
        System.out.println("\nКорзина после перемещения любимого продукта:");
        store.products.forEach(System.out::println);
        
        System.out.println("\nПродукты с ценой < 10 и весом > 2 кг:");
        store.printFilteredProducts();
        
        Supermarket supermarket = new Supermarket(10, 20, 500);
        supermarket.generateProducts(10);
        supermarket.addCategory("Молочные продукты");
        supermarket.addCategory("Фрукты");
        supermarket.addCategory("Выпечка");
        
        System.out.println("\nСупермаркет с " + supermarket.cashRegisters + " кассами, " + supermarket.sellers + " продавцами, площадью " + supermarket.getArea() + " кв.м.");
        System.out.println("Эффективность супермаркета: " + supermarket.calculateEfficiency());
    }
}
