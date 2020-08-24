package Model;

public class IPin extends Device
{
    private boolean inputValue;

    @Override
    public void setInput(boolean input)
    {
        this.inputValue = input;
    }

    @Override
    public boolean getOutput() {
        return this.inputValue;
    }
}
