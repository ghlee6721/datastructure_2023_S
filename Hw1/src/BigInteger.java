import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class BigInteger {
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "Wrong Input";

    public static final Pattern EXPRESSION_PATTERN = Pattern.compile("");

    byte[] num = new byte[100];
    byte[] N = new byte[200];
    int length=0;
    char Cal;
    public BigInteger(byte[] n, boolean positive)
    {
        int len = n.length;
        for (int i = 0; i < len; i++) {
            N[200-len+i] = n[i];
        }
        this.length =len;
        this.positive = positive;
    }
    boolean positive = true;
    public BigInteger(String s, char sgn, char Cal)
    {
        int len=s.length();
        if(sgn != '+'){positive=false;}

        for (int i = 0; i < len; i++) {
                num[100-len+i] = (byte) (s.charAt(i)-(byte)48);
            }

        this.length = len;
        this.Cal = Cal;
    }
    public BigInteger add(BigInteger big)
    {   int len = Math.max(length, big.length);
        byte[] result = new byte[len+1];
        for(int i=0;i<len;i++) {
            result[len-i] +=(byte) (num[100-i-1] + big.num[100-i-1]);
            if(result[len-i]>=10){
                result[len-i-1]++; result[len-i]-=10;
            }
        }
        if(result[0] == 0){
            byte[] y = new byte[len];
            for (int i =0;i<len;i++){
                y[i] = result[i+1];
            }
            return new BigInteger(y, this.positive);
        }else{
            return new BigInteger(result, this.positive);
        }
    }

    public BigInteger subtract(BigInteger big)
    {   int len = Math.max(length, big.length);
        byte[] result = new byte[len];
        boolean this_large = true;
        for(int i=len;i>=1;i--){
            if(num[100-i] > big.num[100-i]){
                break;
            }else if(num[100-i] < big.num[100-i]){
                this_large = false;
            } else{
                continue;
            }
        }
        BigInteger x = this;
        if (this_large) {
            for (int i = len-1; i >= 0; i--) {
                result[i] = (byte) (num[100-len+i] - big.num[100-len+i]);
                if (result[i] < 0) {
                    num[99-len+i]--;
                    result[i] += 10;
                }
            }
            return new BigInteger(result, true);
        } else {
            BigInteger y = big.subtract(x);
            y.positive = false;
            return y;
        }
    }

    public BigInteger multiply(BigInteger big)
    {int len = this.length+ big.length;
        byte[] result = new byte[len];
        for (int i = 0; i < this.length; i++) {
            for (int j = 0; j < big.length; j++) {
                result[len-(i+j)-1] += (byte) (num[100-i-1] * big.num[100-j-1]);
                result[len-(i+j)-2] += (byte)(result[len-(i+j)-1] / 10);
                result[len-(i+j)-1] = (byte) (result[len-(i+j)-1] % 10);
            }
        }
        BigInteger x = new BigInteger(result, true);
        if(big.positive^this.positive){
            x.positive = false;
        }
        return x;
    }

    public BigInteger calc(BigInteger big){
        BigInteger x = this;
        switch (this.Cal){
            case('+'):
                if(this.positive& big.positive){
                   return this.add(big);
                }else if(!this.positive& big.positive){
                    return big.subtract(x);
                }else if(this.positive&!big.positive){
                    return this.subtract(big);
                }else{ return this.add(big);
                }
            case ('-'):
                if(this.positive& big.positive){
                    return this.subtract(big);
                }else if(!this.positive& big.positive){
                    return this.add(big);
                }else if(this.positive&!big.positive){
                    return this.add(big);
                }else{ return big.subtract(x);}
            default:
                return this.multiply(big);
        }
    }

    @Override
    public String toString()
    {
        String ans = "";
        if(!positive) ans += "-";
        for(int i = 0; i<length;i++){
            if(N[200-length+i]!=0){
                length -= i;
                break;
            } else if (i == length-1) {
                length =1;
            } else{
                continue;
            }
        }

        for(int i = 0; i <length; i++) {
            ans += (char)(N[200-length+i] + (byte)48);
        }
        return ans;
    }

    static BigInteger evaluate(String input) throws IllegalArgumentException
    {
        Pattern num = Pattern.compile("([1-9][0-9]*)|0");
        Pattern sgn1 = Pattern.compile("^\\s*\\-\\s*[0-9]*\\s*[\\+\\-\\*]");
        Pattern sgn2 = Pattern.compile("[\\-\\+]\\s*[0-9]*\\s*$");
        Pattern cal1 = Pattern.compile("[\\+\\-\\*]\\s*[\\+\\-]\\s*[0-9]*\\s*$");
        Pattern cal2 = Pattern.compile("[\\*\\+\\-]\\s*[0-9]*\\s*$");
        Matcher mnumber = num.matcher(input);
        Matcher msgn1 = sgn1.matcher(input);
        Matcher msgn2 = sgn2.matcher(input);
        Matcher mcal1 = cal1.matcher(input);
        Matcher mcal2 = cal2.matcher(input);
        char Cal = 'x'; int i = 0; String[] number = new String[2]; char s1 ='+'; char s2='+';
        while(mnumber.find()){
            number[i]=mnumber.group();
            i++;
        }
        while(msgn1.find()){
            s1 = msgn1.group().charAt(0);
        }
        while(msgn2.find()){
            s2=msgn2.group().charAt(0);
        }
        while(mcal1.find()){
            Cal = mcal1.group().charAt(0);
        }
        if(Cal == 'x') {
            while (mcal2.find()) {
                Cal = mcal2.group().charAt(0);
            }
            s2='+';
        }
        BigInteger n1 = new BigInteger(number[0],s1,Cal);
        BigInteger n2 = new BigInteger(number[1],s2,Cal);
        BigInteger Result = n1.calc(n2);
        return Result;




    }

    public static void main(String[] args) throws Exception
    {
        try (InputStreamReader isr = new InputStreamReader(System.in))
        {
            try (BufferedReader reader = new BufferedReader(isr))
            {
                boolean done = false;
                while (!done)
                {
                    String input = reader.readLine();

                    try
                    {
                        done = processInput(input);
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.err.println(MSG_INVALID_INPUT);
                    }
                }
            }
        }
    }

    static boolean processInput(String input) throws IllegalArgumentException
    {
        boolean quit = isQuitCmd(input);

        if (quit)
        {
            return true;
        }
        else
        {
            BigInteger result = evaluate(input);
            System.out.println(result.toString());

            return false;
        }
    }

    static boolean isQuitCmd(String input)
    {
        return input.equalsIgnoreCase(QUIT_COMMAND);
    }
}
