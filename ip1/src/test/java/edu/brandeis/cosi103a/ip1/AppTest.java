package edu.brandeis.cosi103a.ip1;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Random;

import org.junit.Test;

public class AppTest{
    @Test
    public void testRollDie()
    {
        Random random = new Random();
        for (int i = 0; i < 100; i++) { // Test multiple rolls
            int roll = App.rollDie(random);
            assertTrue("Roll should be between 1 and 6", roll >= 1 && roll <= 6);
        }
    }

    /**
     * Test for takeTurn method
     */
    @Test
    public void testTakeTurn()
    {
        Random random = new Random();

        // Simulate user input for "no" (no re-rolls)
        String input = "no\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        int roll = App.takeTurn(new java.util.Scanner(System.in), random);
        assertTrue("Final roll should be between 1 and 6", roll >= 1 && roll <= 6);
    }
   @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}