package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Car implements Vehicle {
    private String carId;
    private String carName;
    private String carColor;
    private int carQuantity;

    @Override
    public String getId() {
        return carId;
    }

    @Override
    public String getName() {
        return carName;
    }

    @Override
    public int getQuantity() {
        return carQuantity;
    }
}