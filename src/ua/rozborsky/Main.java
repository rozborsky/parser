package ua.rozborsky;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by roman on 28.12.2016.
 */
public class Main {
    private static BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));

    enum Type {number, operand, other}

    public static void main(String[] args) {
        while(true) {
            try{
                System.out.println("Insert string");
                String string = bufferReader.readLine();

                List parts = divideStringOnParts(string);
                List processedList = deleteStrings(parts);
                
                printNumbers(processedList);
                System.out.println("Max: " + Collections.max(processedList).toString());
                System.out.println("Min: " + Collections.min(processedList).toString());
                System.out.println("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static List divideStringOnParts(String string) {
        List partsOfString = new ArrayList<>();
        Type typeOfCurrentSymbol;
        Type typeOfPreviousSymbol = null;
        String part = "";

        for (int i = 0; i < string.length(); i++) {

            char symbol = string.charAt(i);
            if (Character.isDigit(symbol)){
                typeOfCurrentSymbol = Type.number;
            } else if ((symbol == '*') || (symbol == '/') || (symbol == '+') || (symbol == '-')){
                typeOfCurrentSymbol = Type.operand;
            } else {
                typeOfCurrentSymbol = Type.other;
            }

            if (!typeOfCurrentSymbol.equals(typeOfPreviousSymbol) && (typeOfPreviousSymbol != null)){
                partsOfString.add(part);
                part = "";
            }
            part += (symbol);
            typeOfPreviousSymbol = typeOfCurrentSymbol;
        }
        partsOfString.add(part);

        return partsOfString;
    }

    private static void printNumbers(List partsOfString) {
        System.out.println("Numbers:");
        for (int i = 0; i < partsOfString.size(); i++) {
            System.out.println(partsOfString.get(i));
        }
    }

    private static List deleteStrings(List partsOfString) {
        List numbers = new ArrayList<>();
        int numberOfMember = 0;

        for (int i = 0; i < partsOfString.size(); i++) {
            try{
                numbers.add(numberOfMember, Integer.valueOf(partsOfString.get(i).toString()));

                if ((partsOfString.get(i + 1).equals("*")) || (partsOfString.get(i + 1).equals("/"))
                        || (partsOfString.get(i + 1).equals("+")) || (partsOfString.get(i + 1).equals("-"))) {
                    int secondValue = Integer.valueOf(partsOfString.get(i + 2).toString());
                    int result = calculateValue((int) numbers.get(numberOfMember), secondValue, partsOfString.get(i + 1).toString());
                    i += 2;

                    numbers.set(numberOfMember, result);
                }
                numberOfMember++;
            } catch (NumberFormatException | IndexOutOfBoundsException e){
                //do nothing, continue
            }
        }

        return numbers;
    }

    private static Integer calculateValue(int firstTerm, int secondTerm, String operand) {
        if (operand.equals("*")) {
            return firstTerm * secondTerm;
        } else if (operand.equals("/")) {
            return firstTerm / secondTerm;
        } else if (operand.equals("+")) {
            return firstTerm + secondTerm;
        } else {
            return firstTerm - secondTerm;
        }
    }
}