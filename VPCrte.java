package com.company;

import java.io.*;




public class VPCrte
{
    static final int MEMSIZ = 100;

    static final int HALT  = 0;
    static final int ADD   = 1;
    static final int SUB   = 2;
    static final int MLT   = 3;
    static final int DIV   = 4;
    static final int ILOAD = 5;
    static final int LOAD  = 6;
    static final int STOR  = 7;
    static final int READ  = 8;
    static final int WRITE = 9;
    static final int BR    = 10;
    static final int BZ    = 11;
    static final int BN    = 12;
    static final int DUMP  = 13;

    static int MEMORY[] = new int[MEMSIZ];


    static int PCREG;
    static int IRREG;
    static int GPREG;

    static boolean debug = false;

    static void readToMemory(String fname) throws IOException
    {
        FileReader fr     = new FileReader(fname);
        BufferedReader br = new BufferedReader(fr);
        String buffer = null;
        int x = 0;

        // for each line of "machine code": (while loop)
        //need looping variable that starts at 0
        //runtime enviroment will exit when HAULT command is processed

        while ((buffer = br.readLine()) != null)
        {
            String address = pad(x, 2);
            if (debug)
            {
                System.out.println("readToMemory: " + "[" + address + "] " + "= " + "(" + buffer + ")");
            }

            // copy machine code instruction into memory
            //memory is an array of ints, but read from strings, so will need to cast them
            MEMORY[x] = Integer.valueOf(buffer);
            x += 1;

        }
    }

    public static String pad(int n, int w)
    {
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

    public static void dumpMemory()
    {
        System.out.println("===================================================================");
        int c=0;
        System.out.println("PCREG = " + pad(PCREG, 4));
        System.out.println("IRREG = " + pad(IRREG,4));
        System.out.println("GPREG = " + pad(GPREG, 4) + "\n");

        System.out.println("MEMORY:     0     1     2     3     4     5     6     7     8     9");
        System.out.println("    ---------------------------------------------------------------");

        for (int i=0; i<MEMSIZ; i++)
        {
            if ((i%10) == 0)
            {
                System.out.print("     " + c + "|");
                c++;
            }

            System.out.print("  " + pad(MEMORY[i], 4));

            if (((i+1)%10) == 0)
                System.out.println();
        }
        System.out.println();
        System.out.println("===================================================================");
    }

    public static void runProg() throws IOException
    {
        String[] operatorCodes = {"HALT", "ADD", "SUB", "MLT", "DIV", "ILOAD", "LOAD", "STOR", "READ", "WRITE", "BR", "BZ", "BN", "DUMP"};
        // initialize PCREG to zero - which is our looping variable
        //address of the currently executing instruction
        PCREG = 0;
        GPREG = 0;
        boolean haltRead = false;

        while (!haltRead)
        {
            // fetch current instruction from memory and copy to IRREG
            IRREG = MEMORY[PCREG];

            // extract opcode and operand from IRREG
            int operand = IRREG % 100;
            int opcode = (IRREG - operand) / 100;



            if (debug)
            {
                System.out.println("runProg:  " + "MEMORY[" + pad(PCREG, 2) + "] = " + pad(IRREG, 4) + ", " + "opcode = " + pad(opcode, 2) + ", operand = " + pad(operand, 2) + ", GPREG = " + pad(GPREG, 4) + "(" + operatorCodes[opcode] + ")");
            }

            // handle all of the opcodes with a large if/else if/else if/...
            // or switch statement.
            switch (opcode) {
                case HALT:
                    haltRead = true;
                    System.exit(0);
                    break;
                case ADD:
                    GPREG += MEMORY[operand];
                    break;
                case SUB:
                    GPREG -= MEMORY[operand];
                    break;
                case MLT:
                    GPREG *= MEMORY[operand];
                    break;
                case DIV:
                    if (MEMORY[operand] == 0) {
                        GPREG = 0;
                    } else {
                        GPREG /= MEMORY[operand];
                    }
                    break;
                case ILOAD:
                    GPREG = operand;
                    break;
                case LOAD:
                    GPREG = MEMORY[operand];
                    break;
                case STOR:
                    MEMORY[operand] = GPREG;
                    break;
                case READ:
                    BufferedReader br =
                            new BufferedReader(new InputStreamReader(System.in));
                    //Get input
                    System.out.print("[" + String.valueOf(operand) + "]? ");
                    MEMORY[operand] = Integer.parseInt(br.readLine());
                    break;
                case WRITE:
                    System.out.println("[" + pad(operand, 2) + "] -> " + GPREG);
                    break;
                case BR:
                    PCREG = operand - 1;
                    break;
                case BZ:
                    if (GPREG == 0) {
                        PCREG = operand - 1;
                    }
                    break;
                case BN:
                    if (GPREG != 0) {
                        PCREG = operand - 1;
                    }
                    break;
                case DUMP:
                    dumpMemory();
                    break;
                default:
                    break;
            }

            // increment PCREG to prepare for next instruction
            PCREG += 1;
        }
    }

    public static void main(String argv[]) throws IOException
    {
        if (argv.length == 0)
        {
            System.out.println("usage: java VPCrte FILENAME.exe [ debug ]");
            System.exit(0);
        }

        if ((argv.length == 2) && (argv[1].equals("debug"))) debug = true;

        readToMemory(argv[0]);

        if (debug) dumpMemory();

        runProg();
    }
}
