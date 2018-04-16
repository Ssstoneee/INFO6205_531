import java.util.Arrays;

public class GA {
    public Tsp t=new Tsp();

//    public GA(Tsp t) {
//        this.t = t;
//    }
//
//    public Tsp getT() {
//        return t;
//    }

    /**
     *    交叉主体函数 crossover function
     */
    public void crossover() {
        int x;
        int y;
        int pop = (int)(t.population * t.pxover /2);
        while(pop>0){
            x = (int)(Math.random() * t.population);
            y = (int)(Math.random() * t.population);
            executeCrossover(x,y);//x y 两个体执行交叉 crossover x and y
            pop--;
        }
    }
    /**
     * 执行交叉函数
     * @param //个体x
     * @param //个体y
     * 对个体x和个体y执行佳点集的交叉，从而产生下一代城市序列
     * execute good lattice points crossover to x and y
     */
    public void executeCrossover(int x,int y){
        int dimension = 0;
        for( int i = 0 ;i < t.cityNum; i++)
            if(t.cities[x].city[i] != t.cities[y].city[i]){
                dimension ++;
            }
        int diffItem = 0;
        double[] diff = new double[dimension];
        for( int i = 0 ;i < t.cityNum; i++){
            if(t.cities[x].city[i] != t.cities[y].city[i]){
                diff[diffItem] = t.cities[x].city[i];
                t.cities[x].city[i] = -1;
                t.cities[y].city[i] = -1;
                diffItem ++;
            }
        }
        Arrays.sort(diff);
        double[] temp = new double[dimension];
        temp = gp(x, dimension);
        for( int k = 0; k< dimension;k++)
            for( int j = 0; j< dimension; j++)
                if(temp[j] == k){
                    double item = temp[k];
                    temp[k] = temp[j];
                    temp[j] = item;
                    item = diff[k];
                    diff[k] = diff[j];
                    diff[j] = item;
                }
        int tempDimension = dimension;
        int tempi = 0;
        while(tempDimension> 0 ){
            if(t.cities[x].city[tempi] == -1){
                t.cities[x].city[tempi] = (int)diff[dimension - tempDimension];
                tempDimension --;
            }
            tempi ++;
        }
        Arrays.sort(diff);
        temp = gp(y, dimension);
        for( int k = 0; k< dimension;k++)
            for( int j = 0; j< dimension; j++)
                if(temp[j] == k){
                    double item = temp[k];
                    temp[k] = temp[j];
                    temp[j] = item;
                    item = diff[k];
                    diff[k] = diff[j];
                    diff[j] = item;
                }
        tempDimension = dimension;
        tempi = 0;
        while(tempDimension> 0 ){
            if(t.cities[y].city[tempi] == -1){
                t.cities[y].city[tempi] = (int)diff[dimension - tempDimension];
                tempDimension --;
            }
            tempi ++;
        }
    }

    public double[] gp(int individual, int dimension){
        double[] temp = new double[dimension];
        double[] temp1 = new double[dimension];
        int p = 2 * dimension + 3;
        while(!isPrime(p))
            p++;
        for( int i = 0; i< dimension; i++){
            temp[i] = 2*Math.cos(2*Math.PI*(i+1)/p) * (individual+1);
            temp[i] = temp[i] - (int)temp[i];
            if( temp [i]< 0)
                temp[i] = 1+temp[i];
        }
        for( int i = 0; i< dimension; i++)
            temp1[i] = temp[i];
        Arrays.sort(temp1);
        //排序 sort
        for( int i = 0; i< dimension; i++)
            for( int j = 0; j< dimension; j++)
                if(temp[j]==temp1[i])
                    temp[j] = i;
        return temp;
    }

    /**
     * @param x
     * @return 判断一个数是否是素数的函数
     * Determine the number whether the prime or not
     */
    public boolean isPrime(int x){
        if(x<2) return false;
        for(int i=2;i<=x/2;i++)
            if(x%i == 0 && x!= 2) return false;
        return true;
    }

    /**
     *    变异
     *    Execute the mutate function
     */
    public void mutate(){
        double random;
        int temp;
        int temp1;
        int temp2;
        for(int i = 0; i< t.population; i++){
            random = Math.random();
            if(random<= t.mutate_Rate){
                temp1 = (int)(Math.random() * (t.cityNum));
                temp2 = (int)(Math.random() * (t.cityNum));
                temp = t.cities[i].city[temp1];
                t.cities[i].city[temp1] = t.cities[i].city[temp2];
                t.cities[i].city[temp2] = temp;
            }
        }
    }

    public void run(){
        long[] result = new long[t.range];
        //result初始化为所有的数字都不相等
        //initial the result with each number different
        for( int i  = 0; i< t.range; i++)
            result[i] = i;
        int index = 0;       //数组中的位置
        int num = 1;     //第num代
        while(t.generation >0){
            System.out.println("-----------------  The number of the Generation : " + num + "   -------------------------");
            t.CalAll();
            // print();
            t.Load();
//            GA ga=new GA();
            crossover();
            mutate();
            t.generation--;
            long temp = t.cities[0].fitness;
            for (int i = 1; i< t.population; i++)
                if(t.cities[i].fitness<temp){
                    temp = t.cities[i].fitness;
                }
            System.out.println("The best solution："+temp);
//            System.out.println();
            result[index] = temp;
            if(t.isSame(result))
                break;
            index++;
            if(index==t.range)
                index = 0;
            num++;
        }
        t.printBestRoute();
    }
}

