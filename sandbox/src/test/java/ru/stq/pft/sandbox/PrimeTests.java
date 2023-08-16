package ru.stq.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PrimeTests {

    @Test
    public static void testPrimes(){
        Assert.assertTrue(Primes.isPrimeFast(Integer.MAX_VALUE));
    }

    @Test
    public static void testNonPrimes(){
        Assert.assertFalse(Primes.isPrime(Integer.MAX_VALUE-2));
    }

    @Test(enabled = false)
    public static void testPrimesLong(){
        Assert.assertTrue(Primes.isPrime((long) Integer.MAX_VALUE));
    }
}
