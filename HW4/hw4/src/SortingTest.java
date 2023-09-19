import java.io.*;
import java.util.*;

public class SortingTest {
    public static void main(String args[]) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            boolean isRandom = false;
            int[] value;
            String nums = br.readLine();
            if (nums.charAt(0) == 'r') {
                isRandom = true;

                String[] nums_arg = nums.split(" ");

                int numsize = Integer.parseInt(nums_arg[1]);
                int rminimum = Integer.parseInt(nums_arg[2]);
                int rmaximum = Integer.parseInt(nums_arg[3]);

                Random rand = new Random();

                value = new int[numsize];
                for (int i = 0; i < value.length; i++)
                    value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
            } else {
                int numsize = Integer.parseInt(nums);

                value = new int[numsize];
                for (int i = 0; i < value.length; i++)
                    value[i] = Integer.parseInt(br.readLine());
            }


            while (true) {
                int[] newvalue = (int[]) value.clone();
                char algo = ' ';

                if (args.length == 4) {
                    return;
                }

                String command = args.length > 0 ? args[0] : br.readLine();

                if (args.length > 0) {
                    args = new String[4];
                }

                long t = System.currentTimeMillis();
                switch (command.charAt(0)) {
                    case 'B':    // Bubble Sort
                        newvalue = DoBubbleSort(newvalue);
                        break;
                    case 'I':    // Insertion Sort
                        newvalue = DoInsertionSort(newvalue);
                        break;
                    case 'H':    // Heap Sort
                        newvalue = DoHeapSort(newvalue);
                        break;
                    case 'M':    // Merge Sort
                        newvalue = DoMergeSort(newvalue);
                        break;
                    case 'Q':    // Quick Sort
                        newvalue = DoQuickSort(newvalue);
                        break;
                    case 'R':    // Radix Sort
                        newvalue = DoRadixSort(newvalue);
                        break;
                    case 'S':    // Search
                        algo = DoSearch(newvalue);
                        break;
                    case 'X':
                        return;
                    default:
                        throw new IOException("Wrong sorting method.");
                }
                if (isRandom) {
                    System.out.println((System.currentTimeMillis() - t) + " ms");
                } else {
                    if (command.charAt(0) != 'S') {
                        for (int i = 0; i < newvalue.length; i++) {
                            System.out.println(newvalue[i]);
                        }
                    } else {
                        System.out.println(algo);
                    }
                }

            }
        } catch (IOException e) {
            System.out.println("wrong input : " + e.toString());
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //all sorting method refer to textbook which professor wrote.
    private static int[] DoBubbleSort(int[] value) {
        int tmp;
        for (int i = value.length - 1; i > 0; i--) {
            for (int j = 0; j <= i - 1; j++) {
                if (value[j] > value[j + 1]) {
                    tmp = value[j];
                    value[j] = value[j + 1];
                    value[j + 1] = tmp;
                }
            }
        }
        return value;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] DoInsertionSort(int[] value) {
        for (int i = 1; i <= value.length - 1; i++) {
            int loc = i - 1;
            int newItem = value[i];
            while (loc >= 0 && newItem < value[loc]) {
                value[loc + 1] = value[loc];
                loc--;
            }
            value[loc + 1] = newItem;
        }
        return value;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] DoHeapSort(int[] value) {
        BulidHeap(value);
        int tmp;
        for (int i = value.length - 1; i >= 1; i--) {
            tmp = value[0];
            value[0] = value[i];
            value[i] = tmp;
            percolateDown(0, i - 1, value);
        }
        return (value);
    }

    private static void BulidHeap(int[] value) {
        if (value.length >= 2) {
            for (int i = (value.length - 2) / 2; i >= 0; i--) {
                percolateDown(i, value.length - 1, value);
            }
        }
    }

    private static void percolateDown(int i, int n, int[] A) {
        int child = 2 * i + 1;//left child
        int rightchild = 2 * i + 2;//right child
        if (child <= n) {
            if ((rightchild <= n) && (A[child] < A[rightchild])) {
                child = rightchild;
            }
            if (A[i] < A[child]) {
                int tmp = A[i];
                A[i] = A[child];
                A[child] = tmp;
                percolateDown(child, n, A);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] DoMergeSort(int[] value) {
        int[] copy = new int[value.length];
        msort(0, value.length - 1, value, copy);
        return value;
    }

    private static void msort(int p, int r, int[] A, int[] B) {
        if (p < r) {
            int q = (p + r) / 2;
            msort(p, q, A, B);
            msort(q + 1, r, A, B);
            merge(p, q, r, A, B);
        }
    }

    private static void merge(int p, int q, int r, int[] A, int[] B) {
        int i = p;
        int j = q + 1;
        int t = 0;
        while (i <= q & j <= r) {
            if (A[i] <= A[j]) B[t++] = A[i++];
            else B[t++] = A[j++];
        }
        while (i <= q) {
            B[t++] = A[i++];
        }
        while (j <= r) {
            B[t++] = A[j++];
        }
        i = p;
        t = 0;
        while (i <= r) {
            A[i++] = B[t++];
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] DoQuickSort(int[] value) {
        qsort(0, value.length - 1, value);
        return value;
    }

    private static void qsort(int p, int r, int[] A) {
        if (p < r) {
            int q = partition(p, r, A);
            qsort(p, q - 1, A);
            qsort(q + 1, r, A);
        }
    }

    private static int partition(int p, int r, int[] A) {//when pivot is last value
        int x = A[r];
        int i = p - 1;
        int tmp;
        for (int j = p; j <= r - 1; j++) {
            if (A[j] <= x) {
                i++;
                tmp = A[i];
                A[i] = A[j];
                A[j] = tmp;
            }
        }
        tmp = A[i + 1];
        A[i + 1] = A[r];
        A[r] = tmp;
        return i + 1;
    }
    /*
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
    */

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] DoRadixSort(int[] value) {
        RadixSort(value);
        return value;
    }

    private static void RadixSort(int[] A) {
        int[] tmpplus = new int[10];
        int[] tmpminus = new int[10];
        int[] plus = new int[10];
        int[] minus = new int[10];
        int[] B = new int[A.length];
        int max = Math.abs(A[0]);
        for (int i = 0; i < A.length; i++) {
            if (Math.abs(A[i]) > max) {
                max = Math.abs(A[i]);
            }
        }
        int numdigits = (int) Math.log10(max) + 1;
        for (int digits = 1; digits <= numdigits; digits++) {
            for (int d = 0; d <= 9; d++) {
                tmpplus[d] = 0;
                tmpminus[d] = 0;
            }
            for (int i = 0; i < A.length; i++) {
                if (A[i] >= 0) {
                    tmpplus[(int) (A[i] / Math.pow(10, digits - 1)) % 10]++;
                } else {
                    tmpminus[(int) (-A[i] / Math.pow(10, digits - 1)) % 10]++;
                }
            }
            minus[9] = 0;
            for (int d = 9; d >= 1; d--) {
                minus[d - 1] = minus[d] + tmpminus[d];
            }
            plus[0] = minus[0] + tmpminus[0];
            for (int d = 1; d <= 9; d++) {
                plus[d] = plus[d - 1] + tmpplus[d - 1];
            }
            for (int i = 0; i < A.length; i++) {
                if (A[i] < 0) {
                    B[minus[(int) (-A[i] / Math.pow(10, digits - 1)) % 10]++] = A[i];
                } else {
                    B[plus[(int) (A[i] / Math.pow(10, digits - 1)) % 10]++] = A[i];
                }
            }
            for (int i = 0; i < A.length; i++) {
                A[i] = B[i];
            }
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static char DoSearch(int[] value) {
        int max = value[0];
        int min = value[0];

        for (int i = 0; i < value.length; i++) {
            if (value[i] > max) {
                max = value[i];
            }
            if (value[i] < min) {
                min = value[i];
            }
        }
        int scope = max - min;
        Integer table[] = new Integer[scope + 1];
        for (int i = 0; i < scope + 1; i++) {
            table[i] = null;
        }
        int slot;
        int collison = 0;
        for (int i = 0; i < value.length; i++) {
            if (table[value[i] - min] == null) {
                table[value[i] - min] = value[i];
            } else collison++;
        }
        double ratio_duplication = (double) collison / (double) table.length;

        int sorted = 0;
        for (int i = 0; i < value.length - 1; i++) {
            if (value[i] < value[i + 1]) {
                sorted++;
            }
        }
        double ratio_sorted = (double) sorted / (double) (value.length - 1);
        int numdigits = (int) Math.log10(max) + 1;
        if (numdigits > 2) {
            if (ratio_duplication < 60) {
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
            } else {
                DoMergeSort(value);
                return 'M';
            }
        } else {
            DoRadixSort(value);
            return 'R';
        }

    }
}
