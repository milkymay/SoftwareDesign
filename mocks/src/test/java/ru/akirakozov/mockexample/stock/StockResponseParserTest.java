package ru.akirakozov.mockexample.stock;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author akirakozov
 */
public class StockResponseParserTest {
    private final static String testResponse =
            "// [\n" +
                    "{\n" +
                    "\"id\": \"304466804484872\"\n" +
                    ",\"t\" : \"GOOG\"\n" +
                    ",\"e\" : \"NASDAQ\"\n" +
                    ",\"l\" : \"770.47\"\n" +
                    ",\"l_fix\" : \"770.47\"\n" +
                    ",\"l_cur\" : \"770.47\"\n" +
                    ",\"s\": \"0\"\n" +
                    ",\"ltt\":\"12:11PM EDT\"\n" +
                    ",\"lt\" : \"Sep 21, 12:11PM EDT\"\n" +
                    ",\"lt_dts\" : \"2016-09-21T12:11:32Z\"\n" +
                    ",\"c\" : \"-0.94\"\n" +
                    ",\"c_fix\" : \"-0.94\"\n" +
                    ",\"cp\" : \"-0.12\"\n" +
                    ",\"cp_fix\" : \"-0.12\"\n" +
                    ",\"ccol\" : \"chr\"\n" +
                    ",\"pcls_fix\" : \"771.41\"\n" +
                    "}\n" +
                    ",{\n" +
                    "\"id\": \"10670257\"\n" +
                    ",\"t\" : \"YNDX\"\n" +
                    ",\"e\" : \"NASDAQ\"\n" +
                    ",\"l\" : \"20.99\"\n" +
                    ",\"l_fix\" : \"20.99\"\n" +
                    ",\"l_cur\" : \"20.99\"\n" +
                    ",\"s\": \"0\"\n" +
                    ",\"ltt\":\"12:10PM EDT\"\n" +
                    ",\"lt\" : \"Sep 21, 12:10PM EDT\"\n" +
                    ",\"lt_dts\" : \"2016-09-21T12:10:45Z\"\n" +
                    ",\"c\" : \"+0.29\"\n" +
                    ",\"c_fix\" : \"0.29\"\n" +
                    ",\"cp\" : \"1.40\"\n" +
                    ",\"cp_fix\" : \"1.40\"\n" +
                    ",\"ccol\" : \"chg\"\n" +
                    ",\"pcls_fix\" : \"20.7\"\n" +
                    "}\n" +
                    "]";

    @Test
    public void parseResponse() throws Exception {
        StockResponseParser parser = new StockResponseParser();
        List<StockInfo> info = parser.parseResponse(testResponse);

        Assert.assertTrue(info.size() == 2);

        Assert.assertEquals(info.get(0), new StockInfo("GOOG", 770.47, -0.94));
        Assert.assertEquals(info.get(1), new StockInfo("YNDX", 20.99, 0.29));
    }
}
