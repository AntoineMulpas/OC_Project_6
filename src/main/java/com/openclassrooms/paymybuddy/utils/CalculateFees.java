package com.openclassrooms.paymybuddy.utils;

public class CalculateFees {

    private static final double fees = 0.5;

    public static double calculateFee(double amount) {
        return (amount * fees) / 100;
    }
}
