package com.frame.dynamicProxy;

public class ElectricCar implements Rechargable, Vehicle {

    @Override
    public void drive(String test1, String test2) {
        System.out.println("---------Electric Car is Moving silently:" + test1 + "," + test2 + "---------");
    }

    @Override
    public void recharge(String test1, String test2) {
        System.out.println("---------Electric Car is Recharging:" + test1 + "," + test2 + "---------");
    }

}
