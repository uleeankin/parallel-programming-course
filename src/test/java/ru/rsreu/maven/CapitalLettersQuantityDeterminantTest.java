package ru.rsreu.maven;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class CapitalLettersQuantityDeterminantTest
{

    @Test
    public void testTwoCapitalLetters() {
        Assert.assertEquals(2, CapitalLettersQuantityDeterminant
                                        .determineCapitalLettersNumber("Hello World!"));
    }

    @Test
    public void testZeroCapitalLetters() {
        Assert.assertEquals(0, CapitalLettersQuantityDeterminant
                .determineCapitalLettersNumber("hello world!"));
    }

    @Test
    public void testNoLetters() {
        Assert.assertEquals(0, CapitalLettersQuantityDeterminant
                .determineCapitalLettersNumber("6253 !_=+)98793485"));
    }

    @Test
    public void testAllCapitalLetters() {
        Assert.assertEquals(20, CapitalLettersQuantityDeterminant
                .determineCapitalLettersNumber("FJUVGHIWURJFHYWHEDFD"));
    }

    @Test
    public void testCapitalLettersInEmptyLine() {
        Assert.assertEquals(0, CapitalLettersQuantityDeterminant
                .determineCapitalLettersNumber(""));
    }

    @Test
    public void testOneCapitalLetter() {
        Assert.assertEquals(1, CapitalLettersQuantityDeterminant
                .determineCapitalLettersNumber("Test this line."));
    }

    @Test
    public void testFourCapitalLetters() {
        Assert.assertEquals(4, CapitalLettersQuantityDeterminant
                .determineCapitalLettersNumber("First Parallel Programming Lab"));
    }
}
