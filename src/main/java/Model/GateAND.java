package Model;

public class GateAND extends Device
{
    @Override
    public boolean getOutput() {
        boolean outputValue = true;

        for (Device iPin: iPins)
        {
            outputValue = outputValue & iPin.getOutput();
        }

        return outputValue;
    }
}
