import org.junit.Test;

import static org.junit.Assert.*;

public class TspTest {
    private String cityName[]={"A","B","C"};

    public int cityNum=cityName.length;     //城市个数 The number of the cities
    public int population = 1000;               //种群数量 The number of the population
    public int generation = 8000;            //迭代次数 The number of the generation
    public double pxover = 0.5;            //交叉概率 The rate of the crossover
    public double mutate_Rate = 0.0015;       //变异概率 The rate of the mutate
    public long[][] distance = new long[cityNum][cityNum];//距离矩阵 The matrix of the distance
    public int range = 2000;

    @Test
    public void pad() {
    }


    @Test
    public void initDistance() {
        Tsp t = new Tsp();
        assert (t.distance[1][2] == t.distance[2][1]);
        assert (t.distance[1][3] == t.distance[3][1]);


    }

    @Test
    public void calFitness() {

    }

    @Test
    public void calIsSelected() {
        Tsp t = new Tsp();
        assert ();
    }
}

