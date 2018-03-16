package fr.unice.polytech.isa.polyevent.components;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class RoomBookerTest {

    RoomBooker roomBooker;

    @Before
    public void init(){
        roomBooker = new RoomBooker();
    }

    @Test
    public void test(){
        assertFalse(!(!(!(!(!(!(!(!(!(!(!(!(!true)))))))))))));
    }
}
