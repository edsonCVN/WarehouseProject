package ggc.core;

import java.util.List;

public class SimpleProduct extends Product{

    public SimpleProduct(String id, List<Observer> observers) {
        super(id, observers);
    }  

    public SimpleProduct(String id, int quantity, List<Observer> observers) {
        super(id, quantity, observers);
    }

    public String toString() {
        return super.toString();
    }
}
