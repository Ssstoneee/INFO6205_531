import java.util.*;

public class Tsp {
    private String cityName[]={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    public int cityNum=cityName.length;     //城市个数 The number of the cities
    public int population = 1000;               //种群数量 The number of the population
    public int generation = 8000;            //迭代次数 The number of the generation
    public double pxover = 0.5;            //交叉概率 The rate of the crossover
    public double mutate_Rate = 0.0015;       //变异概率 The rate of the mutate
    public long[][] distance = new long[cityNum][cityNum];//距离矩阵 The matrix of the distance
    public int range = 2000;               //用于判断何时停止的数组区间 The range to determine when to stop

    public class genotype {
        int city[] = new int[cityNum];      //单个基因的城市序列 The genes
        long fitness;                      //该基因的适应度 The fitness
        double selectP;                        //选择概率 The rate of select
        double exceptp;                        //期望概率 The excepted rate
        int isSelected;                        //是否被选择 Whether selected
    }
    public genotype[] cities = new genotype[population];
    /**
     *    构造函数，初始化种群
     *    Init the population
     */
    public Tsp() {
        for (int i = 0; i < population; i++) {
            cities[i] = new genotype();
            int[] num = new int[cityNum];
            for (int j = 0; j < cityNum; j++)
                num[j] = j;
            int temp = cityNum;
            for (int j = 0; j < cityNum; j++) {
                int r = (int) (Math.random() * temp);
                cities[i].city[j] = num[r];
                num[r] = num[temp - 1];
                temp--;
            }
            cities[i].fitness = 0;
            cities[i].selectP = 0;
            cities[i].exceptp = 0;
            cities[i].isSelected = 0;
        }
        initDistance();
    }
    /**
     *  计算每个种群每个基因个体的适应度，选择概率，期望概率，和是否被选择。
     *  Calculate every individual's fitness selected_rate excepted_rate and whether selected
     */
    public void CalAll(){
        for(int i = 0; i < population; i++){
            cities[i].fitness = 0;
            cities[i].selectP = 0;
            cities[i].exceptp = 0;
            cities[i].isSelected = 0;
        }
        CalFitness();
        CalSelectP();
        CalExceptP();
        CalIsSelected();
    }
    /**
     *    填充，将多选的填充到未选的个体当中
     *    Filling the population with best replace the bad
     */
    public void Load(){
        int best = 0;
        int bad = 0;
        while(true){
            while(cities[best].isSelected <= 1 && best< population -1)
                best ++;
            while(cities[bad].isSelected != 0 && bad< population -1)
                bad ++;
            for(int i = 0; i< cityNum; i++)
                cities[bad].city[i] = cities[best].city[i];
            cities[best].isSelected --;
            cities[bad].isSelected ++;
            bad ++;
            if(best == population ||bad == population)
                break;
        }
    }

    /**
     * 初始化各城市之间的距离
     * Initial the distance between cities,
     * The distance from a to b and the distance from b to a should be the same
     */
    public void initDistance(){
        for (int i = 0; i < cityNum; i++) {
            for (int j = 0; j < cityNum; j++){
                distance[i][j] = Math.abs(i - j*10);
            }

        }
        for (int i = 0; i<cityNum; i++) {
            for (int j = 0; j < cityNum; j++) {
                distance[i][j] = distance[j][i];
            }
        }
    }
    /**
     * 计算所有城市序列的适应度
     * Calculate the fitness for the cities
     */
    public void CalFitness() {
        for (int i = 0; i < population; i++) {
            for (int j = 0; j < cityNum - 1; j++)
                cities[i].fitness += distance[cities[i].city[j]][cities[i].city[j + 1]];
            cities[i].fitness += distance[cities[i].city[0]][cities[i].city[cityNum - 1]];
        }
    }
    /**
     * 计算选择概率
     * Calculate the select rate
     */
    private void CalSelectP(){
        long sum = 0;
        for(int i = 0; i < population; i++)
            sum += cities[i].fitness;
        for(int i = 0; i < population; i++)
            cities[i].selectP = (double)cities[i].fitness/sum;
    }
    /**
     * 计算期望概率
     * Calculate the except rate
     */
    private void CalExceptP(){
        for(int i = 0; i < population; i++)
            cities[i].exceptp = (double)cities[i].selectP * population;
    }
    /**
     * 计算该城市序列是否较优，较优则被选择，进入下一代
     * Calculate whether to select
     */
    public void CalIsSelected(){
        int needSelecte = population;
        for(int i = 0; i< population; i++)
            if( cities[i].exceptp < 1){
                cities[i].isSelected++;
                needSelecte --;
            }
        double[] temp = new double[population];
        for (int i = 0; i < population; i++) {
            temp[i] = cities[i].exceptp * 10;
        }
        int j = 0;
        while ( needSelecte != 0) {
            for (int i = 0; i < population; i++) {
                if ((int) temp[i] == j) {
                    cities[i].isSelected++;
                    needSelecte--;
                    if ( needSelecte == 0)
                        break;
                }
            }
            j++;
        }
    }

    /**
     * 打印任意代最优的路径序列
     * Print the best solution sequence
     */
    public void printBestRoute(){
        CalAll();
        long temp = cities[0].fitness;
        int index = 0;
        for (int i = 1; i < population; i++) {
            if( cities[i].fitness < temp){
                temp = cities[i].fitness;
                index = i;
            }
        }
        System.out.println();
        System.out.println("The best solution sequence is：");
        for ( int j = 0; j < cityNum; j++)
        {
            String cityEnd[] = { cityName[cities[index].city[j]]};

            for( int m=0; m < cityEnd.length; m++)
            {
                System.out.print(cityEnd[m] + " ");
            }
        }
        //System.out.print(citys[index].city[j] + cityName[citys[index].city[j]] + "  ");
        //System.out.print(cityName[citys[index].city[j]]);
        System.out.println();
    }
    /**
     * @param x 数组
     * @return x数组的值是否全部相等，相等则表示x.length代的最优结果相同，则算法结束
     * If every value in the array is the same, then the x.length generation is the best and
     * the algorithm is done.
     */
    public boolean isSame (long[] x){
        for( int i = 0; i< x.length -1; i++)
            if(x[i] != x[i+1] )
                return false;
        return true;
    }
    /**
     * 算法执行
     * execute the algorithm.
     */

    public void CalTime(Calendar a,Calendar b){
        long x = b.getTimeInMillis() - a.getTimeInMillis();
        long y = x / 1000;
        x = x - 1000 * y;
        System.out.println();
        System.out.println("The cost of the time is："+ y +"."+ x +"s" );
    }


}