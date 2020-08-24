package Model;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class LogicSimulator
{
    private Vector<Device> circuits = new Vector<>();
    private Vector<Device> iPins = new Vector<>();
    private Vector<Device> oPins = new Vector<>();

    private void buildCircuits(String data, int counter)
    {
        String[] datas = data.replace("\n", "").split(" ");
    }

    public boolean load(String filePath)
    {
        try {
            File myFile = new File(filePath);
            Scanner myReader = new Scanner(myFile);
            int counter = 0;
            while (myReader.hasNextLine()) {
                counter++;
                String data = myReader.nextLine();
                buildCircuits(data, counter);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
        return true;
    }

    public String getSimulationResult(Vector<Boolean> inputValues)
    {

    }
}
