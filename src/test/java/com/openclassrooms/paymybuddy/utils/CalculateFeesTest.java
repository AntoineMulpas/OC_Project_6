package com.openclassrooms.paymybuddy.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculateFeesTest {

    @Test
    void calculateFee() {
        double result = CalculateFees.calculateFee(10.0);
        assertEquals(0.05, result);
    }
}