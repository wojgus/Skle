package com.company;

import java.util.Scanner;

public class Shop {

    private Warehouse warehouse;
    Scanner scanner = new Scanner(System.in);

    public Shop() {
        this.warehouse = new Warehouse();
    }

    public void run() {
        boolean shouldContinue = true;
        do {
            switch (printOptions()) {
                case 1:
                    printProducts(warehouse.getAvailableProducts());
                    printDetailedProductMenuForAvailableProducts();
                    break;
                case 2:
                    printProducts(warehouse.getSoldProducts());
                    printDetailedProductMenuForSoldProducts();
                    break;
                default:
                    shouldContinue = false;
                    warehouse.saveChanges();
            }
        }while(shouldContinue);
    }

    private void printProducts(ProductContainer productContainer) {
        int counter = 1;
        for(ProductQuantity productQuantity: productContainer.getProducts()) {
            Product product = productQuantity.getProduct();
            System.out.println(counter + ". name: " + product.getName() + ", price: " + product.getPrice() +
                    ", category: " + product.getCategory() + ", quantity: " + productQuantity.getQuantity());
            counter++;
        }
        System.out.println("-----------------------");
    }

    private int printOptions() {
        System.out.println("1. Wyswietl dostepne produkty");
        System.out.println("2. Wyswietl raport sprzedanych produktow");
        System.out.println("Dowolny inny - wyjscie");
        return scanner.nextInt();
    }

    private void printDetailedProductMenuForAvailableProducts() {
        System.out.println("1. Dodaj produkt");
        System.out.println("2. Zmniejsz ilosc produktu (kup)");
        System.out.println("3. Zwieksz ilosc produktu (dostawa)");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                newProductMenu();
                break;
            case 2:
                buyProductMenu();
                break;
            case 3:
                newDelivery();
                break;
        }

    }

    private void printDetailedProductMenuForSoldProducts() {
        System.out.println("Podaj numer produktu do wyswietlenia sumy dochod: ");
        System.out.println("Suma dochodow dla tego produktu to: "
                + warehouse.calculateIncomeOnProductAtIndex(scanner.nextInt() - 1));
    }

    private void buyProductMenu() {
        System.out.println("Ktory produkt chcesz kupic? nr: ");
        int productIndex = scanner.nextInt() - 1;
        System.out.println("Okej! Podaj ilosc: ");
        int quantity = scanner.nextInt();
        warehouse.decreaseQuantityBy(productIndex, quantity);
    }

    private void newDelivery() {
        System.out.println("Dostawa produktu nr: ");
        int productIndex = scanner.nextInt() - 1;
        System.out.println("Okej! Podaj ilosc: ");
        int quantity = scanner.nextInt();
        warehouse.newDelivery(productIndex, quantity);
    }

    private void newProductMenu() {
        System.out.println("Podaj nazwe produktu: ");
        String nazwa = scanner.next();
        System.out.println("Podaj cene produktu: ");
        double cena = scanner.nextDouble();
        System.out.println("Podaj kategorie produktu: ");
        Category kategoria = Category.valueOf(scanner.next().toUpperCase());
        System.out.println("Podaj ilosc danego produktu: ");
        long ilosc = scanner.nextLong();
        warehouse.createNewProduct(nazwa, cena, kategoria, ilosc);
    }

}
