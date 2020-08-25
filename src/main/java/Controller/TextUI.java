package Controller;

import Model.LogicSimulator;

import java.util.Scanner;
import java.util.Vector;

public class TextUI
{
    private LogicSimulator logicSimulator;
    public void displayMenu()
    {
        System.out.println();
        System.out.println("1. Load logic circuit file");
        System.out.println("2. Simulation");
        System.out.println("3. Diplay truth table");
        System.out.println("4. Exit");
        System.out.print("Command: ");
    }

    private void processCommand1(Scanner scanner)
    {
        logicSimulator = new LogicSimulator();
        System.out.print("Please key in a file path: ");
        String path = scanner.nextLine();
        if (logicSimulator.load(path))
        {
            System.out.println("Circuit: "+logicSimulator.getIPins().size()+" input pins, "
                    +logicSimulator.getOPins().size()+" output pins and "+logicSimulator.getCircuits().size()+" gates");
        }
        else
        {
            System.out.println("File not found or file format error!!");
        }
    }

    private void processCommand2(Scanner scanner)
    {
        if (!logicSimulator.getCircuits().isEmpty()) {
            int index = 0;
            Vector<Boolean> inputValues = new Vector<>();
            while (index < logicSimulator.getIPins().size()) {
                index++;
                System.out.print("Please key in the value of input pin " + index + ": ");
                String input = scanner.nextLine();

                if (!input.matches("[0-1]{1}")) {
                    System.out.println("The value of input pin must be 0/1");
                    index--;
                    continue;
                }

                if (input.equals("1"))
                {
                    inputValues.add(true);
                }
                else
                {
                    inputValues.add(false);
                }
            }

            System.out.print(logicSimulator.getSimulationResult(inputValues));
        }
        else
        {
            System.out.println("Please load an icf file, before using this operation.");
        }
    }

    public void processCommand(String command)
    {
        Scanner scanner = new Scanner(System.in);

        if (command.equals("1"))
        {
            processCommand1(scanner);
        }
        else if (command.equals("2"))
        {
            processCommand2(scanner);
        }
        else if (command.equals("3"))
        {
            if (!logicSimulator.getCircuits().isEmpty())
            {
                System.out.print(logicSimulator.getTruthTable());
            }
            else
            {
                System.out.println("Please load an icf file, before using this operation.");
            }
        }
        else if (command.equals("4"))
        {
            System.out.println("Goodbye, thanks for using LS.");
            return;
        }
        else
        {
            System.out.println("invalid input");
        }
    }
}
