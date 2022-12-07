import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;


public class CalculatorTest {
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void setOut() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testSubtract() throws IOException {
        String expression = "23 - 6";
        long result = Main.generateOutput(expression);
        Assert.assertEquals(result, 17);
        final String output = out.toString().trim();
        Assert.assertEquals( "NT(\"23\") NT(\"6\") OT(\"-\")", output);
    }

    @Test
    public void testDifferentPriority() {
        String expression = "23 - 6 / 2";
        long result = 0;
        try {
            result = Main.generateOutput(expression);
        } catch (IOException e) {
            Assert.fail();
        }
        Assert.assertEquals(result, 20);
        final String output = out.toString().trim();
        Assert.assertEquals( "NT(\"23\") NT(\"6\") NT(\"2\") OT(\"/\") OT(\"-\")", output);
    }

    @Test
    public void testExpression() throws IOException {
        String expression = "10930 + 60 * 339 / 3 + 23 - (2 - 4 * 7)";
        long result = Main.generateOutput(expression);
        Assert.assertEquals(result, 17759);
        final String output = out.toString().trim();
        Assert.assertEquals( "NT(\"10930\") NT(\"60\") NT(\"339\") " +
                        "OT(\"*\") NT(\"3\") OT(\"/\") OT(\"+\") NT(\"23\") OT(\"+\") " +
                        "NT(\"2\") NT(\"4\") NT(\"7\") OT(\"*\") OT(\"-\") OT(\"-\")", output);
    }

    @Test
    public void testUnevenBrackets() throws IOException {
        String expression = "23 - 6 (";
        exceptionRule.expect(IllegalStateException.class);
        exceptionRule.expectMessage("Brackets do not exist in RPN");
        Main.generateOutput(expression);
    }

    @Test
    public void testInvalidSymbol() throws IOException {
        String expression = "23 - 6 * 898 i";
        exceptionRule.expect(IllegalStateException.class);
        exceptionRule.expectMessage("Unexpected symbol: \"i\"");
        Main.generateOutput(expression);
    }
}
