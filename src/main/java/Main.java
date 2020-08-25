import Controller.TextUI;

import java.util.Scanner;

public class Main
{
    public static void main(String args[])
    {
        TextUI textUI = new TextUI();
        Scanner scanner = new Scanner(System.in);
        String command = "";

        while (!command.equals("4"))
        {
            textUI.displayMenu();
            command = scanner.nextLine();
            textUI.processCommand(command);
        }
    }
}
