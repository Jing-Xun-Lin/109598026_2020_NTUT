package Model;

import java.awt.*;
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

    public Vector<Device> getCircuits()
    {
        return circuits;
    }

    public Vector<Device> getIPins()
    {
        return iPins;
    }

    public Vector<Device> getOPins()
    {
        return oPins;
    }

    private void addGate(String str)
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

    private void buildGates(String data, int counter)
    {
        String[] dataset = data.replace("\n", "").split(" ");
        if (counter == 1)
        {
            iPins.setSize(Integer.parseInt(dataset[0]));
        }
        else
        {
            for (String str: dataset)
            {
                int circuitIndex = counter - 3;
                if (str.matches("[1-3]{1}"))
                {
                    addGate(str);
                }
                else if (str.matches("[1-9]\\d*\\.1"))
                {
                    String inputDeviceIndex = String.valueOf(circuitIndex);
                    String outputDeviceIndex = String.valueOf(Integer.parseInt(str.replace(".1", "")) - 1);
                    pinConnections.add(outputDeviceIndex + "." + inputDeviceIndex);
                }
                else if (str.matches("-[1-9]\\d*"))
                {
                    int iPinIndex = Math.abs(Integer.parseInt(str)) - 1;
                    if (iPins.get(iPinIndex) == null)
                    {
                        iPins.set(iPinIndex, new IPin());
                    }
                    circuits.get(circuitIndex).addInputPin(iPins.get(iPinIndex));
                }
                else if (str.equals("0"))
                {
                    return;
                }
                else
                {
                    throw new RuntimeException("input data error");
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
        try
        {
            File myFile = new File(filePath);
            Scanner myReader = new Scanner(myFile);
            int counter = 0;
            while (myReader.hasNextLine())
            {
                counter++;
                String data = myReader.nextLine();
                buildGates(data, counter);
            }
            myReader.close();
            connectDevices();
            setOutputPins();
        }
        catch (FileNotFoundException e)
        {
            return false;
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

    private void setInput(Vector<Boolean> inputValues)
    {
        for (int i = 0; i < inputValues.size(); i++)
        {
            iPins.get(i).setInput(inputValues.get(i));
        }
    }

    private String getTruthTableHeader()
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

    private StringBuilder getInputValueRow(Vector<Boolean> inputValues)
    {
        StringBuilder iValueString = new StringBuilder();

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

        return iValueString;
    }

    private StringBuilder getOutputValueRow()
    {
        StringBuilder oValueString = new StringBuilder();

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

        return oValueString;
    }

    public String getSimulationResult(Vector<Boolean> inputValues)
    {
        setInput(inputValues);

        StringBuilder iValueString = getInputValueRow(inputValues);
        StringBuilder oValueString = getOutputValueRow();
        String truthTableHeader = getTruthTableHeader();
        String result = "";

        result = "Simulation Result:\n" + truthTableHeader + iValueString + "|" + oValueString + "\n";

        return result;
    }

    private boolean intToBoolean(int n)
    {
        return n == 1;
    }

    public String getTruthTable()
    {
        int rows = (int) Math.pow(2, iPins.size());
        Vector<Boolean> inputValues = new Vector<>();
        String truthTableHeader = getTruthTableHeader();
        StringBuilder truthTable = new StringBuilder();
        String result = "";

        for (int i = 0; i < rows; i++)
        {
            inputValues = new Vector<>();
            for (int j = iPins.size() - 1; j >= 0; j--)
            {
                inputValues.add(intToBoolean((i / (int) Math.pow(2, j)) % 2));
            }
            setInput(inputValues);
            StringBuilder iValueString = getInputValueRow(inputValues);
            StringBuilder oValueString = getOutputValueRow();
            truthTable.append(iValueString).append("|").append(oValueString).append("\n");
        }

        result = "Truth table:\n" + truthTableHeader + truthTable;
        return result;
    }
}
