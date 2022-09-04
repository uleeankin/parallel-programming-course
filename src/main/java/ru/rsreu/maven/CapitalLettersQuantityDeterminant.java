package ru.rsreu.maven;

public class CapitalLettersQuantityDeterminant
{

    /*public static void main( String[] args )
    {
        System.out.println( determineCapitalLettersNumber("hello world!") );
    }*/

    public static int determineCapitalLettersNumber(String sourceString) {
        int result = 0;
        for (char character : sourceString.toCharArray()) {
            if (Character.isUpperCase(character)) {
                result++;
            }
        }
        return result;
    }
}
