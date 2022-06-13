package com.company;

import com.company.exception.NegativeProductQuantityException;

public class ProductQuantity {
    private Product product;
    private long quantity;

    public ProductQuantity(Product product, long quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public void changeQuantityBy(long amount) throws NegativeProductQuantityException {
        if(this.quantity - Math.abs(amount) < 0) {
            throw new NegativeProductQuantityException("Product quantity cannot be negative!");
        }
        this.quantity += amount;
    }

    public boolean doesProductMatch(Product product) {
        return this.product.equals(product);
    }

    public long getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }
}
