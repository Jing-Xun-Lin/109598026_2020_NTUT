import Model.*;
import org.junit.*;

import java.util.Vector;

import static org.junit.Assert.*;

public class DeviceTest
{
    @Before
    public void setUp()
    {

    }

    @After
    public void tearDown()
    {

    }

    @Test
    public void testDevicePolymorphism()
    {
        Device device = new IPin();
        assertEquals(IPin.class.getName(), device.getClass().getName());

        device = new OPin();
        assertEquals(OPin.class.getName(), device.getClass().getName());

        device = new GateAND();
        assertEquals(GateAND.class.getName(), device.getClass().getName());

        device = new GateNOT();
        assertEquals(GateNOT.class.getName(), device.getClass().getName());

        device = new GateOR();
        assertEquals(GateOR.class.getName(), device.getClass().getName());
    }

    @Test
    public void testIPinAndOPin()
    {
        IPin iPin = new IPin();
        OPin oPin = new OPin();
        iPin.setInput(false);
        oPin.addInputPin(iPin);

        assertEquals(false, iPin.getOutput());
        assertEquals(false, oPin.getOutput());
    }

    @Test
    public void testGateNOT()
    {
        GateNOT gateNOT = new GateNOT();
        IPin iPin = new IPin();
        iPin.setInput(true);
        gateNOT.addInputPin(iPin);

        assertEquals(false, gateNOT.getOutput());
    }

    @Test
    public void testGateAND()
    {
        // 0 and 0 = 0
        IPin iPin0 = new IPin();
        IPin iPin1 = new IPin();
        iPin0.setInput(false);
        iPin1.setInput(false);

        GateAND gateAND = new GateAND();
        gateAND.addInputPin(iPin0);
        gateAND.addInputPin(iPin1);

        assertEquals(false, gateAND.getOutput());

        // 0 and 1 = 0
        iPin0 = new IPin();
        iPin1 = new IPin();
        iPin0.setInput(false);
        iPin1.setInput(true);

        gateAND = new GateAND();
        gateAND.addInputPin(iPin0);
        gateAND.addInputPin(iPin1);

        assertEquals(false, gateAND.getOutput());

        // 1 and 0 = 0
        iPin0 = new IPin();
        iPin1 = new IPin();
        iPin0.setInput(true);
        iPin1.setInput(false);

        gateAND = new GateAND();
        gateAND.addInputPin(iPin0);
        gateAND.addInputPin(iPin1);

        assertEquals(false, gateAND.getOutput());

        // 1 and 1 = 1
        iPin0 = new IPin();
        iPin1 = new IPin();
        iPin0.setInput(true);
        iPin1.setInput(true);

        gateAND = new GateAND();
        gateAND.addInputPin(iPin0);
        gateAND.addInputPin(iPin1);

        assertEquals(true, gateAND.getOutput());
    }

    @Test
    public void testGateOR()
    {
        // 0 or 0 = 0
        IPin iPin0 = new IPin();
        IPin iPin1 = new IPin();
        iPin0.setInput(false);
        iPin1.setInput(false);

        GateOR gateOR = new GateOR();
        gateOR.addInputPin(iPin0);
        gateOR.addInputPin(iPin1);

        assertEquals(false, gateOR.getOutput());

        // 0 or 1 = 1
        iPin0 = new IPin();
        iPin1 = new IPin();
        iPin0.setInput(false);
        iPin1.setInput(true);

        gateOR = new GateOR();
        gateOR.addInputPin(iPin0);
        gateOR.addInputPin(iPin1);

        assertEquals(true, gateOR.getOutput());

        // 1 or 0 = 1
        iPin0 = new IPin();
        iPin1 = new IPin();
        iPin0.setInput(true);
        iPin1.setInput(false);

        gateOR = new GateOR();
        gateOR.addInputPin(iPin0);
        gateOR.addInputPin(iPin1);

        assertEquals(true, gateOR.getOutput());

        // 1 or 1 = 1
        iPin0 = new IPin();
        iPin1 = new IPin();
        iPin0.setInput(true);
        iPin1.setInput(true);

        gateOR = new GateOR();
        gateOR.addInputPin(iPin0);
        gateOR.addInputPin(iPin1);

        assertEquals(true, gateOR.getOutput());
    }
}
