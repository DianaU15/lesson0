package ru.stq.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SquareTests {

    @Test
    public void testArea() {
        Square s = new Square(5.0);
        Assert.assertEquals(s.area(), 25.0);
    }

    @Test
    public void testDistance() {
        Point p1 = new Point(2.0, -5.0);
        Point p2 = new Point(-4.0, 3.0);
        Assert.assertEquals(p1.distance(p2), 10.0);
    }

    @Test
    public void testDistance1() {
        Point p1 = new Point(2.0, -5.0);
        Point p2 = new Point(-4.0, 3.0);
        Assert.assertEquals(p2.distance(p1), 10.0);
    }

    @Test
    public void testDistance2() {
        Point p1 = new Point(2.0, -5.0);
        Point p2 = new Point(-4.0, 3.0);
        Assert.assertNotEquals(p2.distance(p1), 10);
    }
}
