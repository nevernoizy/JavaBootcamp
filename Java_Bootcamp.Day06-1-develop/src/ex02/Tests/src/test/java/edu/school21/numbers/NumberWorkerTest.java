package edu.school21.numbers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {

    @ParameterizedTest
    @ValueSource(ints = {10,12,124})
    void isPrimeForPrimes(int Number) {
        NumberWorker numberWorker = new NumberWorker();
        try {
            assertTrue(numberWorker.isPrime(Number));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @ParameterizedTest
    @ValueSource(ints = {11,13,123})
    void isPrimeForNotPrimes(int Number) {
        NumberWorker numberWorker = new NumberWorker();
        try {
            assertFalse(numberWorker.isPrime(Number));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @ParameterizedTest
    @ValueSource(ints = {-11,0,1})
    void isPrimeForIncorrectNumbers(int Number) {
        NumberWorker numberWorker = new NumberWorker();
        Exception thrown = assertThrows(Exception.class, ()->
        {
            numberWorker.isPrime(Number);
        });
        assertEquals("IllegalNumberException", thrown.getMessage());
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 0)
    void digitSum(int Number, int sum) {
        NumberWorker numberWorker = new NumberWorker();
        assertEquals(numberWorker.digitSum(Number), sum);
    }
}
