import java.io.*;
import java.util.LinkedList;
import java.util.Stack;

public class CalculatorTest {
    public static void main(String args[]) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                String input = br.readLine();
                if (input.compareTo("q") == 0)
                    break;
                command(input);
            } catch (Exception e) {
                System.out.println("ERROR");
            }
        }
    }

    private static void command(String input) throws Exception{
        Stack<String> post1 = Convertpostfix(input);
        Stack<String> post2 = Convertpostfix(input);
        long result = postfixcalculator(post2);
        while (!post1.isEmpty()) {
            if(post1.size() != 1){
                System.out.print(post1.pop() + " ");
            }else System.out.println(post1.pop());
        }
        System.out.println(result);
    }

    private static Stack<String> Convertpostfix(String input) throws Exception {
        Stack<Character> s = new Stack<>();//operator stack
        Stack<String> I = new Stack<>();//postfix stack(reverse version)
        Stack<String> P = new Stack<>();//postfix stack
        boolean digitpreviously = false;//expression from textbook for combine digits
        boolean unary = true;//aim to distinguish binary and unary for '-'
        boolean comma = false;//aim to distinguish previous operator is comma if true throw error
        boolean numprev = false;//aim to solve the case like 2(+3)
        for (int i = 0; i < input.length(); i++) {
            int k =0;//average number ex) k avg
            char ch = input.charAt(i);
            if (Character.isDigit(ch)) {//digit combining process
                if (digitpreviously == true) {
                    long tmp = Long.parseLong(I.pop());
                    tmp = (long) 10 * tmp + (ch - '0');
                    I.push(String.valueOf(tmp));
                } else I.push(String.valueOf(ch));
                digitpreviously = true;
                unary = false;
                comma = false;
                numprev = true;
            } else if (ch == '(') {//always push ( to stack
                if(numprev == false){
                    s.push(ch);
                    digitpreviously = false;
                    unary = true;//Since (-4) then - is unary operator
                    comma = false;
                    numprev = false;
                }else throw new Exception();
            } else if (ch == ')') {
                while (s.peek() != '(') {
                    if(s.peek() == ','){//average case needed count k instead of ,
                        k++;
                        s.pop();
                    }else I.push(String.valueOf(s.pop()));//pop and push all operator before (
                }
                if(k!=0){
                    I.push(String.valueOf(k+1));
                    I.push("avg");
                }
                s.pop();//discard ( at the end
                digitpreviously = false;
                unary = false;
                comma = false;
                numprev = true;//because if ( ) is end then it is number.
            } else if (ch == '^') {// push '^' always because of right associativity
                s.push(ch);
                digitpreviously = false;
                unary = true;
                comma = false;
                numprev = false;
            } else if (ch == '-') {
                if (s.isEmpty()) {
                    if (unary == true) s.push('~');
                    else s.push(ch);
                } else {
                    if (unary == true) {
                        s.push('~');//'~' push always because of right associativity and 2^-4 case
                    } else {
                        if (s.peek()=='('||s.peek() == ',') {//'-' case
                            s.push(ch);//push the operator to operator stack.
                        } else {
                            while(!s.isEmpty()) {
                                if (s.peek() == '('||s.peek() == ',') {
                                    break;
                                } else {//pop operator stack and push it to postfix stack until meet lower priority
                                    I.push(String.valueOf(s.pop()));
                                }
                            }
                            s.push(ch);
                        }
                    }
                }
                digitpreviously = false;
                unary = true;
                comma = false;
                numprev = false;
            } else if (ch == '*' || ch == '/' || ch == '%') {
                if (s.isEmpty()) {
                    s.push(ch);
                } else {
                    if (s.peek() == '+' || s.peek() == '-' || s.peek()=='('||s.peek() == ',') {
                        s.push(ch);
                    } else {
                        while(!s.isEmpty()){
                            if(s.peek() == '+'||s.peek() =='-'||s.peek() == '('||s.peek() == ','){
                                break;
                            }else {
                                I.push(String.valueOf(s.pop()));
                            }
                        }
                        s.push(ch);
                    }
                }
                digitpreviously = false;
                unary = true;
                comma = false;
                numprev = false;
            } else if (ch == '+') {
                if (s.isEmpty()) {
                    s.push(ch);
                } else {
                    if (s.peek()=='('||s.peek() == ',') {
                        s.push(ch);
                    } else {
                        while(!(s.isEmpty())){
                            if(s.peek() == '('||s.peek() == ','){
                                break;
                            }else I.push(String.valueOf(s.pop()));
                        }
                        s.push(ch);
                    }
                }
                digitpreviously = false;
                unary = true;
                comma = false;
                numprev = false;
            } else if (ch == ' '|ch == 9){
                digitpreviously = false;
            } else if (ch == ',') {
                if(s.contains('(')&!comma) {//if contains ( or s.peek is ',' then ',' is operator.
                    while(!(s.peek() == ','|s.peek() == '(')){//',' priority is lowest.
                        I.push(String.valueOf(s.pop()));
                    }
                    s.push(ch);
                    digitpreviously = false;
                    unary = true;
                    comma = true;
                    numprev = false;
                } else throw new Exception();
            } else throw new Exception();
        }
        if(s.contains('(')){
            throw new Exception();
        }
        while (!s.isEmpty()) {
            I.push(String.valueOf(s.pop()));
        }

        while (!I.isEmpty()) {
            P.push(I.pop());
        }

        return P;
    }
    private static long postfixcalculator(Stack<String> s) throws Exception{
        long A,B;//similar with textbook page 203.
        Stack<Long> result = new Stack<>();
        while(!s.isEmpty()){
            String tmp = s.pop();
            if(tmp.equals("+")||tmp.equals("-")||tmp.equals("*")||tmp.equals("/")||tmp.equals("%")||tmp.equals("^")){
                A = result.pop();
                B = result.pop();
                result.push(operation(A,B,tmp));
            } else if (tmp.equals("~")) {
                A = result.pop();
                result.push(unary(A));
            } else if (tmp.equals("avg")) {
                A = result.pop();
                long avg = 0;
                long k;
                for (long i=0;i<A;i++){
                    k = result.pop();
                    avg =(long) k+avg;
                }
                result.push((long) avg/A);
            } else {
                result.push(Long.parseLong(tmp));
            }
        }
        if(result.size()==1) {
            return result.pop();
        }else throw new Exception();
    }
    private static long operation(long a, long b, String s) throws Exception{
        long val = 0;
        if(s.equals("+")){
            val = (long) a+b;
        } else if (s.equals("-")) {
            val = (long) b-a;
        } else if(s.equals("*")){
            val = (long) b*a;
        } else if(s.equals("%")){
                val = (long) b%a;
        } else if(s.equals("/")){
                val = (long) b/a;
        } else if(s.equals("^")){
            if(b == 0 & a<0) {
                throw new Exception();
            }else val = (long) Math.pow(b, a);
        }
        return val;
    }
    private static long unary(long a){
        return Math.multiplyExact(-1,a);
    }

}