package Model;

public class GateOR extends Device
{
    @Override
    public boolean getOutput() {
        boolean outputValue = false;

        for (Device iPin: iPins)
        {
            outputValue = outputValue | iPin.getOutput();
        }

        return outputValue;
    }
}
