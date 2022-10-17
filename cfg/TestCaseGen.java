package cfg;
import java.io.IOException;
import java.security.cert.TrustAnchor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestCaseGen {


    ArrayList<String>Lines;
    boolean vis[];
    int[][] adj = new int[50][50];
    CheckSyn checker = new CheckSyn();
    int cur = 0;


    //Constructor to pass the lines of code
    public TestCaseGen (ArrayList<String> lines){
        this.Lines = lines;
        vis = new boolean[Lines.size()];
        //for(int i=0; i<Lines.size(); i++) System.out.println(i+ " " + Lines.get(i));
    }

    public void generate(){

    }



    public void start() throws IOException{
        cur=0;

        while(Lines.get(cur).contains("intmain(){") || Lines.get(cur).charAt(0)=='#' || (Lines.get(cur).charAt(0)=='/' && Lines.get(cur).charAt(0)=='/')){
            cur++;
        }

        Node root = new Node(cur,Lines.get(cur));
        cur++;
        makeRelations(root);
    }


    public void makeRelations(Node branchRoot){
        HashMap<Character, Integer> values = new HashMap<Character, Integer>();

        while(cur<Lines.size()) {

            //System.out.println("finished " + cur);
            Node curNode = new Node(cur,Lines.get(cur));
            // System.out.println(curNode.Statement + " NAHID");
            String currentString = curNode.Statement;
            currentString = currentString.replaceAll("\\s","");

            if(isInt(currentString)){
                boolean foundeq = false;
                int cursor = 3;
                while(cursor< currentString.length()){
                    if(currentString.charAt(cursor) == '='){
                        foundeq = true;
                        break;
                    }
                    System.out.println(currentString.charAt(cursor));
                    cursor++;
                }
                if(!foundeq){
                    cur++;
                    continue;
                }

                Character var = currentString.charAt(cursor-1);
                cursor++; //ahead of equal now

                String value = "";
                while(cursor< currentString.length()){
                    if(currentString.charAt(cursor) == ';'){
                        break;
                    }
                    value += currentString.charAt(cursor);
                    cursor++;
                }

                values.put(var,Integer.parseInt(value) );

                System.out.println("yessssssss : " + values);

            }
            else if(checker.isIf(currentString) || checker.isElseIf(currentString)){
                System.out.println("HEY");
                int cursor = 3;
                if(checker.isElseIf(currentString)){
                    System.out.println("ELSEIF");
                    cursor = 7;
                }
                Character var1;

                // for(;cursor<currentString.length(); cursor++ ){
                //     // if(currentString[])
                //     System.out.println(currentString.charAt(cursor) + " " + cursor);
                // }
                var1 = currentString.charAt(cursor);
                System.out.println(var1);
                cursor++;
                if(currentString.charAt(cursor) == '>' || currentString.charAt(cursor) == '<' || currentString.charAt(cursor) == '='){
                    if(currentString.charAt(cursor+1) == '='){
                        cursor+=2;
                    }
                    else{
                        cursor++;
                    }

                }

                String secondPart = "";
                int var2 = 0;
                while(currentString.charAt(cursor)!=')'){
                    secondPart += currentString.charAt(cursor++);
                }
                System.out.println(secondPart);

                try {
                    var2 = Integer.parseInt(secondPart);
                    System.out.println("integer: " + var2);

                } catch (NumberFormatException e) {
//                    System.out.println("Input String cannot be parsed to Integer.");
                    Character mined;
                    mined = secondPart.charAt(0);
                    if(values.containsKey(mined)){
                        var2 = values.get(mined);
                    }
                    if(secondPart.length()>1){
                        var2 = getCalcedval(var2, secondPart);
                    }
                    System.out.println("var2 : " + var2);

                }




                // System.out.println(currentString.charAt(cursor));
                // var2+=100;


                // System.out.println(var2);


            }

            else if(checker.isLoop(curNode.Statement)){
            }

            else{

            }
            cur++;
        }
    }

    private int getCalcedval(int var, String secondPart) {
        int val = 0;
        Character op = secondPart.charAt(1);
        String x = secondPart.substring(2, secondPart.length());
//        System.out.println("x : " + x);
        val = Integer.parseInt(x);
        if(op=='+') val = val+var;
        else if(op=='-') val = var-val;
        else if(op=='*') val = var*val;
        else if(op=='/') val = var/val;
        return  val;
    }


    public boolean isInt(String statement){
        statement = statement.replaceAll("\\s","");statement = statement.replaceAll("\\s","");
        if(statement.length()>=3){
            if(statement.charAt(0)=='i' && statement.charAt(1)=='n' && statement.charAt(2)=='t'){
                return true;
            }
        }
        return false;
    }



}