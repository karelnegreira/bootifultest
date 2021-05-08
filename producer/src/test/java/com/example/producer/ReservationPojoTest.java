package com.example.producer;

import org.junit.Assert;
import org.junit.Test;

public class ReservationPojoTest {
    @Test
    public void createReservationPojo() throws Exception{
        Reservation res = new Reservation("1", "Jane");
        Assert.assertEquals(res.getId(), "1");
        Assert.assertEquals(res.getName(), "Jane");
        Assert.assertNotNull(res.getId());
    }
}
