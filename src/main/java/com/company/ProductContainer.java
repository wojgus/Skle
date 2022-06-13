package com.company;

import com.company.exception.NegativeProductQuantityException;

import java.util.List;
import java.util.Optional;

public class ProductContainer {

    private List<ProductQuantity> products;


    public ProductContainer(List<ProductQuantity> products) {
        this.products = products;
    }

    public List<ProductQuantity> getProducts() {
        return products;
    }

    public void addProduct(Product product, long quantity) {
        if (!handleProductQuantityChange(product, quantity)) {
            this.products.add(new ProductQuantity(product, quantity));
        }
    }

    private boolean handleProductQuantityChange(Product product, long quantity) {
        Optional<ProductQuantity> optionalProductQuantity = doesProductQuantityExists(product);
        if (optionalProductQuantity.isPresent()) {
            ProductQuantity productQuantity = optionalProductQuantity.get();
            try {
                productQuantity.changeQuantityBy(quantity);
            } catch (NegativeProductQuantityException exception) {
                System.out.println("Product quantity cannot be negative!");
            }
        }
        return optionalProductQuantity.isPresent();
    }

    private Optional<ProductQuantity> doesProductQuantityExists(Product product) {
        Optional<ProductQuantity> optionalProductQuantity = this.products.stream()
                .filter(pc -> pc.doesProductMatch(product))
                .findFirst();
        return optionalProductQuantity;
    }

}
