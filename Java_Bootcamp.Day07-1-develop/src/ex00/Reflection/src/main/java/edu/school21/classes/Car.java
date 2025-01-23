package edu.school21.classes;

public class Car {
    private String model = "Audi";
    private int vin = 0;
    private boolean isWorking = true;
    private double price = 10000;

    public Car(String model, int vin, boolean isWorking, double price) {
        this.model = model;
        this.vin = vin;
        this.isWorking = isWorking;
        this.price = price;
    }

    public Car() {
    }
    public double breakFunc(){
        isWorking = false;
        price = price/2;
        return price;
    }
    public double repairFunc(){
        isWorking = true;
        price = price*2;
        return price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", vin=" + vin +
                ", isWorking=" + isWorking +
                ", price=" + price +
                '}';
    }
}
