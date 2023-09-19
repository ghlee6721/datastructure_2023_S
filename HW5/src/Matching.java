import java.io.*;
import java.util.LinkedList;
import java.io.FileReader;
public class Matching
{
    public static void main(String args[])
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true)
        {
            try
            {
                String input = br.readLine();
                if (input.compareTo("QUIT") == 0)
                    break;

                command(input);
            }
            catch (IOException e)
            {
                System.out.println("wrong Input error " + e.toString());
            }
        }
    }
    public static Myhashtable hashtable;
    private static void command(String input) throws IOException
    {
            String[] args = input.split(" ",2);
            char oper = args[0].charAt(0);
            switch(oper) {
                case '<':
                    hashtable = new Myhashtable(100);
                    text = new LinkedList<String>();
                    count =0;
                    file(args[1], hashtable);
                    break;
                case '@':
                    printString(Integer.parseInt(args[1]));
                    break;
                case '?':
                    patternsearch(args[1]);
                    break;
                case '+':
                    lineadd(args[1]);
                    break;
                case '/':
                    delete(args[1]);
                    break;
            }
    }
    public static int count;
    public static LinkedList<String> text;
    public static void file(String fileName, Myhashtable hash) throws IOException{
        FileReader reader = new FileReader(fileName);
        BufferedReader bf = new BufferedReader(reader);
        String line = bf.readLine();
        while( line != null) {
            count++;
            hashing(line,count,hash);
            text.add(line);
            line = bf.readLine();
            }
            bf.close();
    }

    public static void lineadd(String x) throws IOException{
        count++;
        text.add(x);
        hashing(x,count,hashtable);
        System.out.println(count);
    }

    public static void delete(String x) throws IOException{
        LinkedList<Integer> [] tmp = new LinkedList[text.size()];
        LinkedList<String> A = hashtable.search(x);
        System.out.println(A.size());
        for(int i=0; i<text.size();i++){
            tmp[i] = new LinkedList<Integer>();
        }
        for(int k=0; k<A.size();k++){
            String tmp1 = A.get(k);
            String[] args = tmp1.substring(1,tmp1.length()-1).split(",");
            int g = Integer.parseInt(args[0]);
            int h = Integer.parseInt(args[1].substring(1,args[1].length()));
            tmp[g-1].add(h);
        }
        hashtable = new Myhashtable(100);
        for(int i=0;i<tmp.length;i++){
            if(tmp[i].size() ==0){
                hashing(text.get(i),i+1, hashtable);
            } else{
                String result = "";
                for(int j=0;j<text.get(i).length();j++){
                    boolean deleted = false;
                    for(int d=0; d<tmp[i].size();d++){
                        if(tmp[i].get(d)-1<=j & j<=tmp[i].get(d)+4) {
                            deleted =true;
                            break;
                        }
                    }
                    if(!deleted) result = result+String.valueOf(text.get(i).charAt(j));
                }
                text.remove(i);
                text.add(i,result);
                if(result.length()>=6) {
                    hashing(result, i + 1, hashtable);
                }
            }
        }
    }

    public static void hashing(String line,int i, Myhashtable hash){
        // input: ith line string / divide to substring/ hashtable insert
        String tmp;
        String tmp1;
        String tmp2;
        for(int j=0; j<line.length()-5; j++) {
            tmp1 = Integer.toString(i);
            tmp2 = Integer.toString(j+1);
            tmp = "("+tmp1+","+" "+tmp2+")";
            hash.insert(line.substring(j,j+6),tmp);
        }
    }

    public static void printString(int index) {
        MyAVLtree<String> tree = hashtable.search(index);
        if(tree.IsEmpty()) {
            System.out.println("EMPTY");
        }else{
            String print = tree.printAll();
            System.out.println(print.substring(0,print.length()-1));
        }
    }

    public static void patternsearch(String x){
        LinkedList<LinkedList<String>> tmp = new LinkedList<LinkedList<String>>();
        String result = "";
        for(int j=0; j<x.length()-5; j++){
            tmp.add(hashtable.search(x.substring(j,j+6)));
        }
        if(tmp.get(0).isEmpty()){
            System.out.println("(0, 0)");
        }else{
            for(int i=0; i<tmp.get(0).size();i++){
                boolean exist = true;
                String tmp1 = tmp.get(0).get(i);
                String[] args = tmp1.substring(1,tmp1.length()-1).split(",");
                int k = Integer.parseInt(args[0]);
                int h = Integer.parseInt(args[1].substring(1,args[1].length()));
                for(int j =1;j<tmp.size();j++){
                    String a1 = Integer.toString(k);
                    String a2 = Integer.toString(j+h);
                    String a3 = "("+a1+","+" "+a2+")";
                    if(!tmp.get(j).contains(a3)) {
                        exist = false;
                        break;
                    }else {exist = true;}
                }
                if(exist){
                    result = result+tmp.get(0).get(i)+" ";
                }
            }
            if(result.compareTo("")==0){
                System.out.println("(0, 0)");
            }else System.out.println(result.substring(0,result.length()-1));
        }

    }

}

class MyAVLNode <T extends Comparable<T>>{
    public T item;
    public MyAVLNode<T> left,right;
    public LinkedList<T> listindex = new LinkedList<>();
    public int height;
    MyAVLNode(){
        item = null;
        left=right= null;
        height = 0;
    }
    MyAVLNode(T newitem, T index){
        item = newitem;
        left=right= MyAVLtree.NIL;
        listindex.add(index);
        height = 1;
    }
    MyAVLNode(T newitem, MyAVLNode leftchild,MyAVLNode rightchid){//newItem is substring, coord is coordinate of substring
        item = newitem;
        left = leftchild;
        right = rightchid;
        height = 1;
    }
    MyAVLNode(T newitem, MyAVLNode leftchild,MyAVLNode rightchid, int h){
        item = newitem;
        left = leftchild;
        right = rightchid;
        height = h;
    }
    MyAVLNode(T newitem, T index,MyAVLNode leftchild,MyAVLNode rightchid){//newItem is substring, index is coordinate of substring
        item = newitem;
        left = leftchild;
        right = rightchid;
        height = 1;
        listindex.add(index);
    }
}

class MyAVLtree <T extends Comparable<T>>{
    private MyAVLNode<T> root;
    static final MyAVLNode NIL = new MyAVLNode();
    public MyAVLtree(){
        root = NIL;
    }
    public MyAVLNode<T> Search(T item){
        return SearchItem(root,item);
    }
    public MyAVLNode<T> SearchItem(MyAVLNode<T> t, T x){
        if(t == NIL) return NIL;
        else if (x.compareTo(t.item)==0) return t;
        else if (x.compareTo(t.item)>0) {
            return SearchItem(t.right, x);
        }else return SearchItem(t.left, x);
    }

    public void Insert(T x, T y){
        root = InsertItem(root,x,y);
    }
    public MyAVLNode<T> InsertItem(MyAVLNode<T> t, T x, T y){
        if (t == NIL){
            t = new MyAVLNode<T>(x,y);
            return t;
        }  else if (x.compareTo(t.item)==0) {
            t.listindex.add(y);
            return t;
        } else if (x.compareTo(t.item)>0){
            t.right = InsertItem(t.right,x,y);
            t.height = 1+Math.max(t.right.height, t.left.height);
            int type = needBalance(t);
            if(type != NO){
                t = BalanceAVL(t, type);
            }
            return t;
        } else {
            t.left = InsertItem(t.left,x,y);
            t.height = 1+Math.max(t.right.height, t.left.height);
            int type = needBalance(t);
            if(type != NO){
                t = BalanceAVL(t, type);
            }
            return t;
        }
    }

    private final int LL=1,LR=2,RL=3,RR=4,NO=0;
    private int needBalance(MyAVLNode<T> t){
        if(t.left.height>=t.right.height+2){
            if(t.left.left.height>=t.left.right.height)return LL;
            else return LR;
        } else if (t.right.height>=t.left.height+2) {
            if(t.right.right.height>=t.right.left.height)return RR;
            else return RL;
        }else return NO;
    }

    private MyAVLNode<T> BalanceAVL(MyAVLNode<T> t, int type){
        switch (type){
            case LL:
                return rightRotate(t);
            case LR:
                t.left = leftRotate(t.left);
                return rightRotate(t);
            case RR:
                return leftRotate(t);
            default:
                t.right = rightRotate(t.right);
                return leftRotate(t);
        }
    }
    private MyAVLNode<T> leftRotate(MyAVLNode<T> t){
        MyAVLNode<T> Rchild = t.right;
        MyAVLNode<T> RLchild = Rchild.left;
        Rchild.left = t;
        t.right = RLchild;
        t.height = 1+ Math.max(t.left.height, t.right.height);
        Rchild.height = 1+ Math.max(Rchild.left.height, Rchild.right.height);
        return Rchild;
    }
    private MyAVLNode<T> rightRotate(MyAVLNode<T> t){
        MyAVLNode<T> Lchild = t.left;
        MyAVLNode<T> LRchild = Lchild.right;
        Lchild.right = t;
        t.left = LRchild;
        t.height = 1+ Math.max(t.left.height, t.right.height);
        Lchild.height = 1+ Math.max(Lchild.left.height, Lchild.right.height);
        return Lchild;
    }
    public String printAll(){
        return preorder(root);
    }

    public String preorder(MyAVLNode<T> root) {
        if(root!=NIL) {
            return root.item+" "+preorder(root.left)+preorder(root.right);
        } else {
            return "";
        }
    }
    public boolean IsEmpty(){
        return root == NIL;
    }
    public void clear(){
        root = NIL;
    }

}
class Myhashtable {
    private MyAVLtree<String> [] table;
    int numitems =0;
    Myhashtable(int n){
        table = (MyAVLtree<String>[]) new MyAVLtree [n];
        for( int i =0; i<n;i++){
            table[i] = new MyAVLtree<>();
        }numitems =0;
    }

    private int hash(String x) {
        int sum = 0;
        for (int i = 0; i < x.length(); i++) {
            sum += x.charAt(i);
        }
        return sum% table.length;
    }


    public void insert(String x, String y){
        int slot = hash(x);
        table[slot].Insert(x,y);
        numitems++;
    }

    public LinkedList<String> search(String x){
        int slot = hash(x);
        if(table[slot].IsEmpty()) return new LinkedList<>();
        else{
            return table[slot].Search(x).listindex;
        }
    }
    public MyAVLtree<String> search(int index){
            return table[index];
    }
    public boolean isEmpty(){
        return numitems == 0;
    }

    public void clear(){
        for( int i =0; i< table.length;i++){
            table[i] = new MyAVLtree<>();
        }numitems=0;
    }
}




