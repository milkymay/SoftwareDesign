package ru.akirakozov.ermishina.mocks.stock;

import org.junit.Assert;
import org.junit.Test;
import ru.akirakozov.ermishina.mocks.Main;

import java.util.List;

public class MainTest {
    @Test
    public void getInfo() {
        List<Long> ans = Main.getTwitterStats("any", 2);
        Assert.assertEquals(ans.size(), 2);
    }
}
