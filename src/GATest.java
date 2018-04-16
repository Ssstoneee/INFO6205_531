import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class GATest {

    @Test
    public void crossover() {
    }

    @Test
    public void isPrime() {
        GA ga = new GA();
        int i =  2;
        Assert.assertFalse(!ga.isPrime(i));
        int j = 7;
        Assert.assertTrue(ga.isPrime(j));

    }

    @Test
    public void mutate() {
        GA g = new GA();
        Tsp t = new Tsp();
//        assert (g.mutate() != );
    }

    @Test
    public void executeCrossover() {
        GA g = new GA();
        g.executeCrossover(1,2);

    }
}