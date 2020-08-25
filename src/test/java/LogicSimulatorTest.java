import Model.LogicSimulator;
import org.junit.*;

import java.util.Vector;

import static org.junit.Assert.*;

public class LogicSimulatorTest
{
    String filePath1;
    String filePath2;

    @Before
    public void setUp()
    {
        filePath1 = "src/File1.lcf";
        filePath2 = "src/File2.lcf";
    }

    @Test
    public void testLoadFile()
    {
        LogicSimulator logicSimulator = new LogicSimulator();
        assertEquals(true, logicSimulator.load(filePath1));
        assertEquals(true, logicSimulator.load(filePath2));
    }

    @Test
    public void testGetSimulationResult()
    {
        LogicSimulator logicSimulator = new LogicSimulator();

        logicSimulator.load(filePath1);

        Vector<Boolean> inputValues = new Vector<>();
        inputValues.add(false);
        inputValues.add(true);
        inputValues.add(true);

        assertEquals("Simulation Result:\n" +
                "i i i | o\n" +
                "1 2 3 | 1\n" +
                "------+--\n" +
                "0 1 1 | 0\n", logicSimulator.getSimulationResult(inputValues));

        inputValues = new Vector<>();
        inputValues.add(true);
        inputValues.add(false);
        inputValues.add(false);

        assertEquals("Simulation Result:\n" +
                "i i i | o\n" +
                "1 2 3 | 1\n" +
                "------+--\n" +
                "1 0 0 | 1\n", logicSimulator.getSimulationResult(inputValues));
//        logicSimulator.load(filePath2);
//
//        Vector<Boolean> inputValues = new Vector<>();
//        inputValues.add(true);
//        inputValues.add(false);
//        inputValues.add(false);
//
//        assertEquals("Simulation Result:\n" +
//                "i i i | o o\n" +
//                "1 2 3 | 1 2\n" +
//                "------+----\n" +
//                "1 0 0 | 1 0\n", logicSimulator.getSimulationResult(inputValues));
    }

    @Test
    public void testGetTruthTable()
    {
        LogicSimulator logicSimulator = new LogicSimulator();

        logicSimulator.load(filePath1);

        assertEquals("Truth table:\n" +
                "i i i | o\n" +
                "1 2 3 | 1\n" +
                "------+--\n" +
                "0 0 0 | 0\n" +
                "0 0 1 | 0\n" +
                "0 1 0 | 0\n" +
                "0 1 1 | 0\n" +
                "1 0 0 | 1\n" +
                "1 0 1 | 1\n" +
                "1 1 0 | 0\n" +
                "1 1 1 | 0\n", logicSimulator.getTruthTable());

        logicSimulator = new LogicSimulator();

        logicSimulator.load(filePath2);

        assertEquals("Truth table:\n" +
                "i i i | o o\n" +
                "1 2 3 | 1 2\n" +
                "------+----\n" +
                "0 0 0 | 0 1\n" +
                "0 0 1 | 0 1\n" +
                "0 1 0 | 0 1\n" +
                "0 1 1 | 0 1\n" +
                "1 0 0 | 1 0\n" +
                "1 0 1 | 1 0\n" +
                "1 1 0 | 0 1\n" +
                "1 1 1 | 0 1\n", logicSimulator.getTruthTable());
    }
}
