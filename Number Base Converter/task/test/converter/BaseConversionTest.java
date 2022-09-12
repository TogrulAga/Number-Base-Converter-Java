package converter;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class BaseConversionTest {
    private final int sourceRadix;
    private final int targetRadix;
    private final String sourceNumber;
    private final String targetNumber;

    public BaseConversionTest(int sourceRadix, int targetRadix, String sourceNumber, String targetNumber) {
        super();
        this.sourceRadix = sourceRadix;
        this.targetRadix = targetRadix;
        this.sourceNumber = sourceNumber;
        this.targetNumber = targetNumber;
    }

    @Before
    public void initialize() {

    }

    @Parameterized.Parameters
    public static Collection input() {
        return Arrays.asList(new Object[][] {{10, 7, "0.234", "0.14315"}, {10, 7, "10.234", "13.14315"}, {10, 7, "0", "0"},
                {35, 17, "af.xy", "148.g88a8"}, {35, 17, "aaaa.0", "54e36.00000"},
                {21, 10, "4242", "38012"}, {21, 10, "4242.13a", "38012.05550"},
                {22, 20, "3i2hi45ffcf.1248hd48hd48ig704511ek2a6h", "9i77aa5ecbf.10000"},
                {22, 17, "ih0634b81if.d48hd48hd48h419ka3d8cf25bf", "E95AA93F190A.A36DA"},
                {22, 14, "cg9h33kelia.9jhd48hd48hd8l60gk46fe9j53", "5D86D8B8201AA.642B2"},
                {2, 5, "1000111011100100111000111010011000011101001011110.00110011001100110011001100", "312141300302134114233.04444"}});
    }

    @Test
    public void testBaseConversion() {
        Number number1 = NumberFactory.getNumber(sourceRadix, sourceNumber);
        Number number2 = NumberFactory.getNumber(targetRadix, targetNumber);

        Number convertedNumber = number1.convertToRadix(targetRadix);
        System.out.printf("Expected: %s    Received: %s%n", number2.getValue(), convertedNumber.getValue());
        junit.framework.TestCase.assertEquals(convertedNumber.getValue(), number2.getValue());
    }
}