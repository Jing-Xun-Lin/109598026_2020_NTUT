package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class LogicSimulator
{
    private Vector<Device> circuits = new Vector<>();
    private Vector<Device> iPins = new Vector<>();
    private Vector<Device> oPins = new Vector<>();
    private Vector<String> pinConnections = new Vector<>();
    private int circuitSize;
    private int lastDevice;

    private void buildGates(String data, int counter)
    {
        String[] datas = data.replace("\n", "").split(" ");
        if (counter == 1)
        {
            iPins.setSize(Integer.parseInt(datas[0]));
        }
        else if (counter == 2)
        {
            circuitSize = Integer.parseInt(datas[0]);
        }
        else
        {
            for (String str: datas)
            {
                int circuitIndex = counter - 3;
                if (str.matches("[1-3]{1}"))
                {
                    if (str.equals("1"))
                    {
                        circuits.add(new GateAND());
                    }
                    else if (str.equals("2"))
                    {
                        circuits.add(new GateOR());
                    }
                    else
                    {
                        circuits.add(new GateNOT());
                    }
                }
                else if (str.matches("[1-9]\\d*\\.1"))
                {
                    String inputDeviceIndex = String.valueOf(circuitIndex);
                    String outputDeviceIndex = String.valueOf(Integer.parseInt(str.replace(".1", "")) - 1);
                    pinConnections.add(outputDeviceIndex + "." + inputDeviceIndex);
//                    int inputDeviceIndex = Integer.parseInt(str.replace(".1", "")) - 1;
//                    circuits.get(circuitIndex).addInputPin(circuits.get(inputDeviceIndex));
                }
                else if (str.matches("-[1-9]\\d*"))
                {
                    int iPinIndex = Math.abs(Integer.parseInt(str)) - 1;
                    iPins.set(iPinIndex, new IPin());
                    circuits.get(circuitIndex).addInputPin(iPins.get(iPinIndex));
                }
                else if (str.equals("0"))
                {
                    return;
                }
            }
        }
    }

    public void connectDevices()
    {
        for (String pinConnection: pinConnections)
        {
            int outputIndex = Integer.parseInt(pinConnection.split("\\.")[0]);
            int inputIndex = Integer.parseInt(pinConnection.split("\\.")[1]);

            circuits.get(inputIndex).addInputPin(circuits.get(outputIndex));
        }
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
                buildGates(data, counter);
            }
            myReader.close();
            connectDevices();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
        return true;
    }

    public String getSimulatorResult(Vector<Boolean> inputValues)
    {

    }

    public String getSimulationResult(Vector<Boolean> inputValues)
    {
        return "";
    }
}
