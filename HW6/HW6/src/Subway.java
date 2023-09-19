import java.io.*;
import java.util.*;

public class Subway {
    public static void main(String args[]) {
        try {
            fileRead(args[0]);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                String input = br.readLine();
                if (input.compareTo("QUIT") == 0)
                    break;

                shortestpath(input);

            }
        } catch (Exception e) {
            System.out.println("wrong Input error " + e.toString());
        }
    }

    public static HashMap<String, costvertexname> graphinfo = new HashMap<>(1000000);
    public static HashMap<String, ArrayList<String>> nameIndex = new HashMap<>(200000);

    public static void fileRead(String fileName) throws IOException {
        FileInputStream reader = new FileInputStream(fileName);
        BufferedReader bf = new BufferedReader(new InputStreamReader(reader, "utf-8"));
        String line = bf.readLine();
        while (!line.equals("")) {
            command1(line);
            line = bf.readLine();
        }
        line = bf.readLine();
        while (!line.equals("")) {
            command2(line);
            line = bf.readLine();
        }
        line = bf.readLine();
        while (line != null) {
            command3(line);
            line = bf.readLine();
        }
    }

    public static void command1(String line) throws IOException {
        String args[] = line.split(" ", 3);
        if (!nameIndex.containsKey(args[1])) {
            ArrayList<String> Index = new ArrayList<>(100);
            Index.add(args[0]);
            nameIndex.put(args[1], Index);
            costvertexname tmp = new costvertexname(new HashMap<String, Integer>(500), args[1]);
            graphinfo.put(args[0], tmp);
        } else {
            ArrayList<String> Index = nameIndex.get(args[1]);
            Index.add(args[0]);
            nameIndex.put(args[1], Index);
            costvertexname tmp = new costvertexname(new HashMap<String, Integer>(500), args[1]);
            graphinfo.put(args[0], tmp);
            ArrayList<String> tmp1 = nameIndex.get(args[1]);
            for(int i =0;i<tmp1.size();i++){
                graphinfo.get(args[0]).costvertexIndex.put(tmp1.get(i),5);
                graphinfo.get(tmp1.get(i)).costvertexIndex.put(args[0],5);
            }
        }
    }

    public static void command2(String line) throws IOException {
        String args[] = line.split(" ", 3);
        graphinfo.get(args[0]).costvertexIndex.put(args[1],Integer.parseInt(args[2]));
    }

    public static void command3(String line) throws IOException{
        String args[] = line.split(" ", 2);
        ArrayList<String> tmp1 = nameIndex.get(args[0]);
        int a = Integer.parseInt(args[1]);
        for(int i =1;i<tmp1.size();i++){
            for(int j=0; j<i;j++){
                graphinfo.get(tmp1.get(i)).costvertexIndex.put(tmp1.get(j),a);
                graphinfo.get(tmp1.get(j)).costvertexIndex.put(tmp1.get(i),a);
            }
        }

    }
    public static void shortestpath(String line) throws Exception{
        String args[] = line.split(" ", 2);
        int INF = Integer.MAX_VALUE;
        Node Dest = new Node(null, INF,null,null,-1);
        for(int i=0;i<nameIndex.get(args[0]).size();i++){
            Node tmp2 = Dijkstra(nameIndex.get(args[0]).get(i),args[1]);
            if(tmp2.cost< Dest.cost){
                Dest = tmp2;
            }
        }
        Node tmp = Dest;
        Stack<Node> Route = new Stack<>();
        Route.add(Dest);
        while(tmp.name.compareTo(args[0])!=0){
            tmp = tmp.prev;
            Route.add(tmp);
        }
        int cost = Dest.cost;
        String result = "";
        Node tmp1;
        while(!Route.isEmpty()){
            tmp1 = Route.pop();
            if(!Route.isEmpty()){
                if(tmp1.name.compareTo(Route.peek().name)==0){
                    result+="["+tmp1.name+"]"+" ";
                    tmp1 = Route.pop();
                }else{
                    result += tmp1.name+" ";
                }
            }else{
                result += tmp1.name+" ";
            }

        }
        System.out.print(result.trim()+"\r\n");
        System.out.print(""+cost+"\r\n");

    }


    public static Node Dijkstra(String s,String d)throws Exception{
        MyHeap costpd = new MyHeap(500000);
        HashMap<String, Node>NodeIndex = new HashMap<>();
        int INF = Integer.MAX_VALUE;
        Iterator<String> iter = graphinfo.keySet().iterator();
        while(iter.hasNext()){ // iterator에 다음 값이 있다면
            String key = iter.next();
            Node tmp5 = new Node(key, INF, null, graphinfo.get(key).name,-1);
            NodeIndex.put(key,tmp5); // iter에서 값 꺼내기
        }//NodeIndex 초기화
        NodeIndex.put(s, new Node(s,0,null,graphinfo.get(s).name,-1));//정점에 대하여 초기화
        costpd.insert(NodeIndex.get(s));
        for(int j=0;j<5000000;j++){
            Node u =costpd.deleteMin();
            u.setIn = true;
            if(graphinfo.get(u.id).name.compareTo(d) == 0){
                return u;
            }else{
                Iterator<Map.Entry<String, Integer>> itr = graphinfo.get(u.id).costvertexIndex.entrySet().iterator();
                while(itr.hasNext()){
                    Map.Entry<String, Integer> entry = itr.next();
                    Node v = NodeIndex.get(entry.getKey());
                    if(!v.setIn& entry.getValue()+ u.cost<v.cost){
                        v.cost = entry.getValue()+ u.cost;
                        v.prev =u;
                        NodeIndex.put(v.id,v);
                        costpd.replace(v.id, v);
                    }
                }
            }
        }throw new Exception("Error NodeIndex");

    }

}


class Node  {
    String id;
    Node prev;
    int cost;

    String name;
    int heapIndex;

    boolean setIn= false;
    public Node(String a, int b, Node c, String d, int heapIndex) {
        this.id = a;
        this.cost = b;
        this.prev = c;
        this.name =d;
        this.heapIndex = heapIndex;
    }
}
class costvertexname{
    public HashMap<String, Integer> costvertexIndex;
    public String name;
    costvertexname(HashMap<String, Integer> a, String b){
        costvertexIndex = a;
        name= b;
    }
}
class MyHeap {
    private Node[] A;
    private int numItems;

    public MyHeap(int arraySize) {
        A = new Node[arraySize];
        numItems = 0;
    }

    public MyHeap(Node[] B, int numElements) {
        A = B; // 배열 레퍼런스 복사
        numItems = numElements;
    }

    // [알고리즘 8-2] 구현: 힙에 원소 삽입하기(재귀 알고리즘 버전)
    public void insert(Node newItem) throws PQException {
        // 힙 A[0...numItems-1]에 원소 newItem을 삽입한다(추가한다)
        if (numItems < A.length) {
            A[numItems] = newItem;
            percolateUp(numItems);
            numItems++;
        } else throw new PQException("HeapErr: Insert()-Overflow!");
    }

    // 스며오르기 percolateUp()
    private void percolateUp(int i) {
        // A[i]에서 시작해서 힙 성질을 만족하도록 수선한다
        // A[0...i-1]은 힙 성질을 만족하고 있음
        int parent = (i - 1) / 2;
        if (parent >= 0 && A[i].cost<A[parent].cost) {
            Node tmp = A[i];
            A[i] = A[parent];
            A[parent] = tmp;
            A[parent].heapIndex = parent;
            A[i].heapIndex = i;
            percolateUp(parent);
        }
    }

    // 힙에서 원소 삭제하기
    public Node deleteMin() throws PQException {
        // 힙 A[0...numItems-1]에서 최솟값을 삭제하면서 리턴한다
        if (!isEmpty()) {
            Node min = A[0];
            A[0] = A[numItems - 1];
            numItems--;
            percolateDown(0);
            return min;
        } else throw new PQException("HeapErr: DeleteMax()-Underflow");
    }

    // 스며내리기 percolateDown()
    private void percolateDown(int i) {
        // A[0...numItems-1]에서 A[i]를 루트로 스며내리기
        int child = 2 * i + 1;        // 왼쪽 자식
        int rightChild = 2 * i + 2;  // 오른쪽 자식
        if (child <= numItems - 1) {
            if (rightChild <= numItems - 1 && A[child].cost>A[rightChild].cost)
                child = rightChild; // 더 작은 자식의 인덱스
            if (A[i].cost> A[child].cost) {
                Node tmp = A[i];
                A[i] = A[child];
                A[child] = tmp;
                A[child].heapIndex = child;
                A[i].heapIndex = i;
                percolateDown(child);
            }
        }
    }

    // [알고리즘 8-4] 구현: 힙 만들기
    public void buildHeap() {
        if (numItems >= 2)
            for (int i = (numItems - 2) / 2; i >= 0; i--)
                percolateDown(i);
    }

    // [알고리즘 8-5] 구현: 힙의 최댓값 구하기
    public Node min() throws PQException {
        if (!isEmpty()) {
            return A[0];
        } else throw new PQException("HeapErr: Max()-Empty!");

    }

    // [알고리즘 8-6] 구현: 힙이 비었는지 확인하기
    public boolean isEmpty() { // 힙이 비어있는지 알려준다
        return numItems == 0;
    }

    // [알고리즘 8-7] 구현: 힙 비우기
    public void clear() {
        A = (Node[]) new Object[A.length];
        numItems = 0;
    }

    //////////////////////////////////////////////////
    public void heapPrint() {
        System.out.println("heapPrint()");
        for(int i=0; i<=numItems-1; i++) {
            System.out.println(A[i]);
        }
    }

    public void replace(String s,Node v)throws Exception{
        if(v.heapIndex == -1){
            insert(v);
        }else{
            A[v.heapIndex] = v;
            int child = 2 * v.heapIndex + 1;        // 왼쪽 자식
            int rightChild = 2 * v.heapIndex + 2;  // 오른쪽 자식
            int parent = (v.heapIndex - 1) / 2;//parent
            if (child <= numItems - 1) {
                if (rightChild <= numItems - 1 && A[child].cost>A[rightChild].cost)
                    child = rightChild; // 더 작은 자식의 인덱스
                if (A[v.heapIndex].cost> A[child].cost) {
                    percolateDown(v.heapIndex);
                }
            } else if (parent >= 0 && A[v.heapIndex].cost<A[parent].cost) {
                percolateUp(v.heapIndex);
            }
        }
    }
} // 코드 8-2
class PQException extends Exception {
    public PQException(String msg) {
        super(msg);
    }
} // 코드 8-3