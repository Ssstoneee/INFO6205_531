import org.junit.Test;

import static org.junit.Assert.*;

public class TspTest {
    private String cityName[]={"A","B","C"};

    public int cityNum=cityName.length;     //城市个数 The number of the cities
    public int population = 100;               //种群数量 The number of the population
    public int generation = 1000;            //迭代次数 The number of the generation
    public double pxover = 0.5;            //交叉概率 The rate of the crossover
    public double mutate_Rate = 0.0015;       //变异概率 The rate of the mutate
    public long[][] distance = new long[cityNum][cityNum];//距离矩阵 The matrix of the distance
    public int range = 200;

     Tsp t=new Tsp();
//qwe

    Tsp.genotype[] cities = new Tsp.genotype[population];


    @Test
    public void Load() {
        int best = 0;
        int bad = 0;

        while(true){
            while(t.cities[best].isSelected <= 1 && best< t.population -1)
                best ++;
            while(t.cities[bad].isSelected != 0 && bad< t.population -1)
                bad ++;
            for(int i = 0; i< cityNum; i++)
                t.cities[bad].city[i] = t.cities[best].city[i];
            t.cities[best].isSelected --;
            t.cities[bad].isSelected ++;
            bad ++;
            if(best == population ||bad == population)
                break;
        }
        assert (best == population || bad == population);
    }


    @Test
    public void initDistance() {
        int cityNum = 10;
        Tsp t = new Tsp();
        for (int i = 0; i<cityNum; i++) {
            for (int j = 0; j < cityNum; j++) {
//                distance[i][j] = distance[j][i];
                assert (t.distance[i][j] == t.distance[j][i]);
//                assert (t.distance[1][3] == t.distance[3][1]);
            }

        }



    }

    @Test
    public void calFitness() {
        for (int i = 0; i < population; i++) {
            for (int j = 0; j < cityNum - 1; j++)
                t.cities[i].fitness += t.distance[cities[i].city[j]][cities[i].city[j + 1]];
            t.cities[i].fitness += t.distance[cities[i].city[0]][cities[i].city[cityNum - 1]];
        }

//        assert (t.cities[population-1].fitness == t.distance[cities[]])
    }

    @Test
    public void calIsSelected() {
        long sum = 0;
//        Tsp t = new Tsp();

        for(int i = 0; i < population; i++)
            sum += t.cities[i].fitness;
        for(int i = 0; i < population; i++) {
            t.cities[i].selectP = (double)t.cities[i].fitness/sum;
        }
        assert (t.cities[1].selectP == (double)t.cities[1].fitness/sum);

    }

    @Test
    public void calExceptP() {


    }

    @Test
    public void calSelectP() {

    }
}

