package com.company;

import com.company.exception.NegativeProductQuantityException;

public class Warehouse {

    private ProductContainer availableProducts;
    private ProductContainer soldProducts;
    private Repository repository;


    public Warehouse() {
        this.repository = new Repository();
        this.availableProducts = repository.provideAvailableProducts();
        this.soldProducts = repository.provideSoldProducts();
    }

    public ProductContainer getAvailableProducts() {
        return this.availableProducts;
    }

    public ProductContainer getSoldProducts() {
        return this.soldProducts;
    }

    public void createNewProduct(String name, double price, Category category, long quantity) {
        Product product = new Product(name, price, category);
        availableProducts.addProduct(product, quantity);
    }

    public void decreaseQuantityBy(int productIndex, long quantity) {
        ProductQuantity productQuantity = availableProducts.getProducts().get(productIndex);
        try {
            productQuantity.changeQuantityBy(-quantity);
            soldProducts.addProduct(productQuantity.getProduct(), quantity);
        } catch (NegativeProductQuantityException e) {
            System.out.println("Ilosc produktu nie moze byc ujemna!");
        }
    }

    public void newDelivery(int productIndex, long quantity) {
        ProductQuantity productQuantity = availableProducts.getProducts().get(productIndex);
        try {
            productQuantity.changeQuantityBy(quantity);
        }
        catch(NegativeProductQuantityException e) {
            System.out.println("Ilosc produktu nie moze byc ujemna!");
        }
    }

    public double calculateIncomeOnProductAtIndex(int index) {
        ProductQuantity productQuantity = soldProducts.getProducts().get(index);
        return productQuantity.getQuantity() * productQuantity.getProduct().getPrice();
    }

    public void saveChanges() {
        repository.persistProducts(availableProducts, soldProducts);
    }


}
