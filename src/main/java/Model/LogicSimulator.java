package Model;

import javax.xml.bind.util.ValidationEventCollector;
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

    private void buildGates(String data, int counter)
    {
        String[] dataset = data.replace("\n", "").split(" ");
        if (counter == 1)
        {
            iPins.setSize(Integer.parseInt(dataset[0]));
        }
        else if (counter == 2)
        {
            circuitSize = Integer.parseInt(dataset[0]);
        }
        else
        {
            for (String str: dataset)
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
            circuits.get(outputIndex).isOutput = false;
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
            setOutputPins();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
        return true;
    }

    private void setOutputPins()
    {
        for (Device device: circuits)
        {
            if (device.isOutput)
            {
                oPins.add(device);
            }
        }
    }

    public void setAllInput(Vector<Boolean> inputValues)
    {
        for (int i = 0; i < inputValues.size(); i++)
        {
            iPins.get(i).setInput(inputValues.get(i));
        }
    }

    public String getTruthTableHeader()
    {
        StringBuilder iString = new StringBuilder();
        StringBuilder iAmountString = new StringBuilder();
        StringBuilder oString = new StringBuilder();
        StringBuilder oAmountString = new StringBuilder();
        StringBuilder separateLine = new StringBuilder();

        for (int i = 0; i < iPins.size(); i++)
        {
            iString.append("i ");
            iAmountString.append(String.valueOf(i + 1)).append(" ");
            separateLine.append("--");
        }

        separateLine.append("+");

        for (int i = 0; i < oPins.size(); i++)
        {
            oString.append(" o");
            oAmountString.append(" ").append(String.valueOf(i + 1));
            separateLine.append("--");
        }

        String header = iString + "|" + oString + "\n" + iAmountString + "|" + oAmountString + "\n" + separateLine + "\n";
        return header;
    }

    public String getSimulationResult(Vector<Boolean> inputValues)
    {
        setAllInput(inputValues);

        StringBuilder iValueString = new StringBuilder();
        StringBuilder oValueString = new StringBuilder();
        String truthTableHeader = getTruthTableHeader();
        String result = "";

        for (boolean value: inputValues)
        {
            if (value)
            {
                iValueString.append("1 ");
            }
            else
            {
                iValueString.append("0 ");
            }
        }

        for (Device output: oPins)
        {
            if (output.getOutput())
            {
                oValueString.append(" 1");
            }
            else
            {
                oValueString.append(" 0");
            }
        }

        result = "Simulation Result:\n" + truthTableHeader + iValueString + "|" + oValueString + "\n";

        return result;
    }
}
