package pl.mate00.sleeperfriendlyapp.timeline;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by mamy on 19.01.15.
 */
public class TimeTest {

    @Test(expected = IllegalArgumentException.class)
    public void testWrongArguments() {
        Time.of(-1, -1);
    }

    @Test
    public void comparison_SmallerMinute() {
        Time t1 = Time.of(0, 0);
        Time t2 = Time.of(0, 1);

        Assert.assertTrue(t1.compareTo(t2) == -1);
    }

    @Test
    public void comparison_SmallerHour() {
        Time t1 = Time.of(0, 1);
        Time t2 = Time.of(1, 1);

        Assert.assertTrue(t1.compareTo(t2) == -1);
    }

    @Test
    public void comparison_SmallerHour_GreaterMinute() {
        Time t1 = Time.of(0, 10);
        Time t2 = Time.of(1, 1);

        Assert.assertTrue(t1.compareTo(t2) == -1);
    }

    @Test
    public void comparison_GreaterHour() {
        Time t1 = Time.of(7, 0);
        Time t2 = Time.of(6, 0);

        Assert.assertTrue(t1.compareTo(t2) == 1);
    }

    @Test
    public void comparison_GreaterMinute() {
        Time t1 = Time.of(7, 20);
        Time t2 = Time.of(7, 0);

        Assert.assertTrue(t1.compareTo(t2) == 1);
    }
}
