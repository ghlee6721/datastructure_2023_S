import java.io.*;
import java.util.*;
public class SortingTest
{
    public static void main(String args[])
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            int[] value;
            String nums = br.readLine();
            if (nums.charAt(0) == 'r') {
                String[] nums_arg = nums.split(" ");
                int numsize = Integer.parseInt(nums_arg[1]);
                int rminimum = Integer.parseInt(nums_arg[2]);
                int rmaximum = Integer.parseInt(nums_arg[3]);
                int experimentnum = Integer.parseInt(nums_arg[4]);
                Random rand = new Random();
                List<Long> result = new ArrayList<>();
                for (int j = 1; j <= experimentnum; j++) {
                    value = new int[numsize];
                    long a = (long) 0;
                    for (int i = 0; i < value.length; i++) {
                        value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
                    }
                    int[] newvalue = (int[]) value.clone();
                    char algo = ' ';
                    long t = System.currentTimeMillis();
                    if (nums_arg[5].equals("B")) {
                        newvalue = DoBubbleSort(newvalue);
                    } else if (nums_arg[5].equals("I")) {
                        newvalue = DoInsertionSort(newvalue);
                    } else if (nums_arg[5].equals("H")) {
                        newvalue = DoHeapSort(newvalue);

                    } else if (nums_arg[5].equals("M")) {
                        newvalue = DoMergeSort(newvalue);

                    } else if (nums_arg[5].equals("Q")) {
                        newvalue = DoQuickSort(newvalue);
                    } else if (nums_arg[5].equals("R")) {
                        newvalue = DoRadixSort(newvalue);
                    } else if (nums_arg[5].equals("S")) {
                        algo = DoSearch(newvalue);
                    }else if (nums_arg[5].equals("T")) {
                        newvalue = DomedianQuickSort(newvalue);
                    }
                    a = System.currentTimeMillis() - t;
                    result.add(a);
                }
                result.sort(Comparator.naturalOrder());
                int sum = 0;

                for (int i = 0; i < experimentnum; i++)
                    sum += result.get(i);


                double avg = (double) sum / experimentnum;

                sum = 0;
                for (int i = 0; i < experimentnum; i++)
                    sum += Math.pow(result.get(i) - avg, 2);
                double var = (double) sum / experimentnum;
                double sd = Math.sqrt(var);
                System.out.println("min " + result.get(0));
                System.out.println("max " + result.get(experimentnum - 1));
                System.out.println("avg: " + avg);
                System.out.println("sd: " + sd);

            } else if (nums.charAt(0) == 'S') {
                String[] nums_arg = nums.split(" ");
                int numsize = Integer.parseInt(nums_arg[1]);
                int experimentnum = Integer.parseInt(nums_arg[2]);
                Random rand = new Random();
                List<Long> result = new ArrayList<>();
                List<Double> sortratio = new ArrayList<>();
                for (int j = 1; j <= experimentnum; j++) {
                    value = new int[numsize];
                    long a = (long) 0;
                    value[0] = 0;
                    for (int i = 1; i < value.length; i++) {
                        if (Math.random() <= 0.5) {
                            value[i] = rand.nextInt(i)+value[i-1];
                        } else {
                            value[i] = -rand.nextInt(i) + value[i - 1];
                        }


                    }
                    int[] newvalue = (int[]) value.clone();
                    int sorted = 0;
                    for (int i = 0; i < newvalue.length - 1; i++) {
                        if (newvalue[i] <= newvalue[i + 1]) {
                            sorted++;
                        }
                    }
                    double ratio_sorted = (double) sorted / (double) (value.length - 1);
                    sortratio.add(ratio_sorted);
                    char algo = ' ';
                    long t = System.currentTimeMillis();
                    if (nums_arg[3].equals("B")) {
                        newvalue = DoBubbleSort(newvalue);
                    } else if (nums_arg[3].equals("I")) {
                        newvalue = DoInsertionSort(newvalue);
                    } else if (nums_arg[3].equals("H")) {
                        newvalue = DoHeapSort(newvalue);

                    } else if (nums_arg[3].equals("M")) {
                        newvalue = DoMergeSort(newvalue);

                    } else if (nums_arg[3].equals("Q")) {
                        newvalue = DoQuickSort(newvalue);
                    } else if (nums_arg[3].equals("R")) {
                        newvalue = DoRadixSort(newvalue);
                    }else if (nums_arg[3].equals("T")) {
                        newvalue = DomedianQuickSort(newvalue);
                    }
                    a = System.currentTimeMillis() - t;
                    result.add(a);
                }
                result.sort(Comparator.naturalOrder());
                int sum = 0;
                double sum_ratio = 0.0;
                for (int i = 0; i < experimentnum; i++)
                    sum += result.get(i);
                for (int i = 0; i < experimentnum; i++)
                    sum_ratio += sortratio.get(i);


                double avg = (double) sum / experimentnum;
                double avg_sortratio = (double) sum_ratio / experimentnum;
                sum = 0;
                for (int i = 0; i < experimentnum; i++)
                    sum += Math.pow(result.get(i) - avg, 2);
                double var = (double) sum / experimentnum;
                double sd = Math.sqrt(var);
                System.out.println("min " + result.get(0));
                System.out.println("max " + result.get(experimentnum - 1));
                System.out.println("avg: " + avg);
                System.out.println("sd: " + sd);
                System.out.println("sortratioavg" + avg_sortratio);
            } else if (nums.charAt(0) == 'E') {
                String[] nums_arg = nums.split(" ");
                int numsize = Integer.parseInt(nums_arg[1]);
                int rminimum = Integer.parseInt(nums_arg[2]);
                int rmaximum = Integer.parseInt(nums_arg[3]);
                int experimentnum = Integer.parseInt(nums_arg[4]);
                Random rand = new Random();
                List<Long> result = new ArrayList<>();
                List<Long> result1 = new ArrayList<>();
                for (int j = 1; j <= experimentnum; j++) {
                    value = new int[numsize];
                    long a = (long) 0;
                    long b = (long) 0;
                    for (int i = 0; i < value.length; i++) {
                        value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
                    }
                    int[] newvalue1 = (int[]) value.clone();
                    int[] newvalue2 = (int[]) value.clone();
                    int[] newvalue3 = (int[]) value.clone();
                    int[] newvalue4 = (int[]) value.clone();
                    int[] newvalue5 = (int[]) value.clone();
                    int[] newvalue6 = (int[]) value.clone();
                    int[] newvalue = (int[]) value.clone();
                    char algo = ' ';
                    long t = System.currentTimeMillis();
                    newvalue1 = DoBubbleSort(newvalue1);
                    newvalue2 = DoInsertionSort(newvalue2);
                    newvalue3 = DoHeapSort(newvalue3);
                    newvalue4 = DoMergeSort(newvalue4);
                    newvalue5 = DoQuickSort(newvalue5);
                    newvalue6 = DoRadixSort(newvalue6);
                    a = System.currentTimeMillis() - t;
                    long t1 = System.currentTimeMillis();
                    algo = DoSearch(newvalue);
                    b = System.currentTimeMillis() - t1;
                    result.add(a);
                    result1.add(b);
                }
                result.sort(Comparator.naturalOrder());
                int sum = 0;
                int sum1 = 0;

                for (int i = 0; i < experimentnum; i++)
                    sum += result.get(i);
                for (int i = 0; i < experimentnum; i++)
                    sum1 += result1.get(i);

                double avg = (double) sum / experimentnum;
                double avg1 = (double) sum1 / experimentnum;

                sum = 0;
                for (int i = 0; i < experimentnum; i++)
                    sum += Math.pow(result.get(i) - avg, 2);
                double var = (double) sum / experimentnum;
                double sd = Math.sqrt(var);
                System.out.println("min " + result.get(0));
                System.out.println("max " + result.get(experimentnum - 1));
                System.out.println("avg: " + avg);
                System.out.println("sd: " + sd);
                System.out.println("search_avg: " + avg1);

            }
        }catch (IOException e)
        {
            System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] DoBubbleSort(int[] value)
    {
        int tmp;
        for(int i= value.length-1;i>0;i--){
            for(int j=0;j<=i-1;j++){
                if(value[j]>value[j+1]){
                    tmp = value[j];
                    value[j] = value[j+1];
                    value[j+1] = tmp;
                }
            }
        }
        return value;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] DoInsertionSort(int[] value)
    {
        for(int i = 1; i<= value.length-1;i++){
            int loc = i-1;
            int newItem = value[i];
            while(loc>=0 && newItem<value[loc]){
                value[loc+1] = value[loc];
                loc--;
            }
            value[loc+1] = newItem;
        }
        return value;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] DoHeapSort(int[] value)
    {
        BulidHeap(value);
        int tmp;
        for(int i= value.length-1; i>=1;i--){
            tmp= value[0];
            value[0] = value[i];
            value[i] = tmp;
            percolateDown(0,i-1, value);
        }
        return (value);
    }

    private static void BulidHeap(int[] value){
        if(value.length>=2){
            for(int i = (value.length-2)/2;i>=0;i--){
                percolateDown(i, value.length-1, value);
            }
        }
    }

    private static void percolateDown(int i, int n, int[] A){
        int child = 2*i+1;//left child
        int rightchild = 2*i+2;//right child
        if (child<=n){
            if((rightchild <=n)&&(A[child]<A[rightchild])){
                child = rightchild;
            }
            if(A[i]<A[child]){
                int tmp = A[i];
                A[i] = A[child];
                A[child] = tmp;
                percolateDown(child, n, A);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] DoMergeSort(int[] value){
        int[] copy = new int[value.length];
        msort(0,value.length-1,value,copy);
        return value;
    }

    private static void msort(int p, int r, int[] A, int[] B){
        if(p<r){
            int q = (p+r)/2;
            msort(p,q,A,B);
            msort(q+1,r,A,B);
            merge(p,q,r,A,B);
        }
    }

    private static void merge(int p,int q, int r, int[] A,int[] B){
        int i = p; int j =q+1;int t=0;
        while(i<=q & j<=r) {
            if (A[i] <= A[j]) B[t++] = A[i++];
            else B[t++] = A[j++];
        }
        while(i<=q) {
            B[t++] = A[i++];
        }
        while(j <=r) {
            B[t++] = A[j++];
        }
        i=p;t=0;
        while (i<=r){
            A[i++] = B[t++];
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] DoQuickSort(int[] value)
    {
        qsort(0,value.length-1,value);
        return value;
    }

    private static void qsort(int p, int r, int[] A){
        if (p<r){
            int q = partition(p,r,A);
            qsort(p,q-1,A);
            qsort(q+1,r,A);
        }
    }

    private static int partition(int p, int r, int [] A){//when pivot is last value
        int x = A[r];
        int i = p-1;
        int tmp;
        for(int j =p;j<=r-1;j++){
            if(A[j]<=x){
                i++;
                tmp = A[i]; A[i] = A[j];A[j] = tmp;
            }
        }
        tmp = A[i+1];A[i+1] = A[r];A[r] = tmp;
        return i+1;
    }
    private static int[] DomedianQuickSort(int[] value)
    {
        medianqsort(0,value.length-1,value);
        return value;
    }

    private static void medianqsort(int p, int r, int[] A){
        if (p<r){
            int q = partitionmedian(p,r,A);
            medianqsort(p,q-1,A);
            medianqsort(q+1,r,A);
        }
    }
    private static int partitionmedian(int p, int r, int [] A){//when pivot is last value
        int swap;
        int median = (p+r)/2;
        swap = A[median]; A[median] = A[r];A[r] = swap;
        int x = A[r];
        int i = p-1;
        int tmp;
        for(int j =p;j<=r-1;j++){
            if(A[j]<x|(A[j]==x & j%2 ==0)){
                i++;
                tmp = A[i]; A[i] = A[j];A[j] = tmp;
            }
        }
        tmp = A[i+1];A[i+1] = A[r];A[r] = tmp;
        return i+1;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] DoRadixSort(int[] value)
    {
        RadixSort(value);
        return value;
    }

    private static void RadixSort(int[] A) {
        int[] tmpplus = new int [10];
        int[] tmpminus = new int [10];
        int[] plus = new int[10];
        int[] minus = new int[10];
        int[] B = new int[A.length];
        int max = Math.abs(A[0]);
        for (int i = 0; i < A.length; i++) {
            if (Math.abs(A[i]) > max) {
                max = Math.abs(A[i]);
            }
        }
        int numdigits = (int) Math.log10(max)+1;
        for(int digits=1; digits<=numdigits; digits++){
            for(int d=0;d<=9;d++){
                tmpplus[d] =0;
                tmpminus[d] = 0;
            }
            for(int i =0;i<A.length;i++){
                if(A[i]>=0){
                    tmpplus[(int)(A[i]/Math.pow(10,digits-1))%10]++;
                }else{
                    tmpminus[(int)(-A[i]/Math.pow(10,digits-1))%10]++;
                }
            }
            minus[9] =0;
            for(int d=9;d>=1;d--){
                minus[d-1] = minus[d]+tmpminus[d];
            }
            plus[0] = minus[0]+tmpminus[0];
            for(int d=1;d<=9;d++){
                plus[d] = plus[d-1]+tmpplus[d-1];
            }
            for(int i=0;i<A.length;i++){
                if(A[i]<0){
                    B[minus[(int)(-A[i]/Math.pow(10,digits-1))%10]++] = A[i];
                }else{
                    B[plus[(int)(A[i]/Math.pow(10,digits-1))%10]++] = A[i];
                }
            }
            for(int i=0;i<A.length;i++){
                A[i] = B[i];
            }
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static char DoSearch(int[] value)
    {
        int max = value[0];
        int min = value[0];

        for (int i = 0; i < value.length; i++) {
            if (value[i] > max) {
                max = value[i];
            }
            if(value[i] < min){
                min = value[i];
            }
        }
        int scope = max-min;
        Integer table[] = new Integer [scope+1];
        for(int i =0; i<scope+1;i++){
            table[i] = null;
        }
        int slot;
        int collison=0;
        for(int i =0; i<value.length;i++){
            if(table[value[i]-min] == null){
                table[value[i]-min] = value[i];
            }else collison++;
        }
        double ratio_duplication = (double) collison/ (double) table.length;

        int sorted = 0;
        for (int i = 0; i < value.length-1; i++) {
            if (value[i] < value[i+1]) {
                sorted++;
            }
        }
        double ratio_sorted = (double) sorted/ (double) (value.length-1);
        int numdigits = (int) Math.log10(max)+1;
        if(numdigits>2){
            if(ratio_duplication<60){
                if (0.49 < ratio_sorted && ratio_sorted < 0.51) {
                    DoQuickSort(value);
                    return 'Q';
                } else if ((0.54 < ratio_sorted )) {
                    DoInsertionSort(value);
                    return 'I';
                } else {
                    DoMergeSort(value);
                    return 'M';
                }
            }else {
                DoMergeSort(value);
                return 'M';
            }
        }else{
            DoRadixSort(value);
            return 'R';
        }

    }
}
