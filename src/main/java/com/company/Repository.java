package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Repository {
    private static final char AVAILABLE_PRODUCT_SYMBOL = 'a';
    private static final char SOLD_PRODUCT_SYMBOL = 's';
    private static final String FILE = "products.txt";


    public Repository() {
    }

    public ProductContainer provideAvailableProducts() {
        return getProductContainerByDiscriminator(AVAILABLE_PRODUCT_SYMBOL);

    }

    public ProductContainer provideSoldProducts() {
        return getProductContainerByDiscriminator(SOLD_PRODUCT_SYMBOL);
    }

    public void persistProducts(ProductContainer availableProducts, ProductContainer soldProducts) {
        try (PrintWriter writer = new PrintWriter(FILE)) {
            saveProducts(writer, availableProducts, AVAILABLE_PRODUCT_SYMBOL);
            saveProducts(writer, soldProducts, SOLD_PRODUCT_SYMBOL);
        } catch (IOException e) {
        }
    }

    private void saveProducts(PrintWriter writer, ProductContainer products, char discriminator) {
        for (ProductQuantity productQuantity : products.getProducts()) {
            String productString = mapToString(productQuantity, discriminator);
            writer.println(productString);
        }
    }

    private ProductContainer getProductContainerByDiscriminator(char discriminator) {
        List<ProductQuantity> productQuantities = new ArrayList<>();
        try (var reader = new BufferedReader(new FileReader(new File(FILE)))) {
            productQuantities = reader.lines()
                    .filter(line -> line.charAt(0) == discriminator)
                    .map(this::mapToProductQuantity)
                    .collect(Collectors.toList());
        } catch (IOException e) {
        }
        return new ProductContainer(productQuantities);
    }

    private ProductQuantity mapToProductQuantity(String line) {
        String[] elements = line.split(" ");
        String name = elements[1];
        double price = Double.parseDouble(elements[2]);
        Category category = Category.valueOf(elements[3].toUpperCase());
        long quantity = Long.parseLong(elements[4]);
        Product product = new Product(name, price, category);
        return new ProductQuantity(product, quantity);
    }

    private String mapToString(ProductQuantity productQuantity, char discriminator) {
        Product product = productQuantity.getProduct();
        return discriminator + " " + product.getName() + " " +
                product.getPrice() + " " + product.getCategory() + " " + productQuantity.getQuantity();
    }

}
