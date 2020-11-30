package com.company;
import java.io.*;
import java.util.*;

public class Assemble {
    public static String Pad(int n, int w) {
        //Method that takes n, the the number to be padded, and w, the amount of zero's to pad with
        String paddedString = "";
        int nLengthValue = String.valueOf(n).length();
        int zerosNeeded = w - nLengthValue;
        if (zerosNeeded <= 0) {
            return String.valueOf(n);
        } else {
            for(int i = 0; i < zerosNeeded; i++) {
                paddedString += "0";
            }
            paddedString += String.valueOf(n);
            return paddedString;
        }
    }

    static void readSrc(String fname) throws IOException
    {
        FileReader fr     = new FileReader(fname);
        BufferedReader br = new BufferedReader(fr);
        String buffer = null;

        String[] operatorCodes = {"HALT", "ADD", "SUB", "MLT", "DIV", "ILOAD", "LOAD", "STOR", "READ", "WRITE", "BR", "BZ", "BN", "DUMP"};
        while ((buffer = br.readLine()) != null)
        {
            // skip any lines of length zero or starting with '#'
            if ((buffer.length() == 0)) {
                continue;
            }
            if (buffer.charAt(0) == '#') {
                continue;
            }

            // tokenize string.  1st token is operator, 2nd is operand
            StringTokenizer tokenString = new StringTokenizer(buffer, " ");
            String operator = tokenString.nextToken();
            String operand = tokenString.nextToken();


            // If operand is invalid, display error and abort.
            if ((Integer.valueOf(operand) < 0)  | (Integer.valueOf(operand) >= 100)) {
                System.out.println("Operand: " + operand +  " out of bounds.");
                System.exit(0);
            }

            // If operator is invalid, display error and abort.
            // convert string operator to numeric form.
            int opCode = -1;
            for (int i = 0; i < operatorCodes.length; i++) {
                if (operator.equals(operatorCodes[i])) {
                    opCode = i;
                    break;
                }
            }
            if(opCode == -1) {
                System.out.println("Invalid operator: " + operator);
                System.exit(0);
            }

            // output opcode and operand, each padded to two characters.
            System.out.println(Pad(opCode,2) + Pad(Integer.valueOf(operand), 2));

        }
    }

    public static void main(String argv[]) throws IOException
    {
        if (argv.length != 1)
        {
            System.out.println("usage:  java Assemble INPUTFILE");
            System.exit(0);
        }

        readSrc(argv[0]);
    }
}

