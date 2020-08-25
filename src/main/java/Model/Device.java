package Model;

import java.util.Vector;

public class Device
{
    public Vector<Device> iPins = new Vector<>();
    public boolean isOutput = true;

    public void addInputPin(Device device)
    {
        iPins.add(device);
    }

    public void setInput(boolean input)
    {
        throw new RuntimeException("Device is not allowed to call setInput()!");
    }

    public boolean getOutput()
    {
        throw new RuntimeException("Device is not allowed to call getOutput()!");
    }
}
