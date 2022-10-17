package cfg;
import java.io.IOException;
import java.util.ArrayList;

public class TestCaseGen {


    ArrayList<String>Lines;
    boolean vis[];
    int[][] adj = new int[50][50];
    SyntaxIdentifier checker = new SyntaxIdentifier();
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
        
        while(cur<Lines.size()) {

            //System.out.println("finished " + cur);
            Node curNode = new Node(cur,Lines.get(cur));
            // System.out.println(curNode.Statement + " NAHID");
            String currentString = curNode.Statement;

            if(checker.isIf(currentString) || checker.isElseIf(currentString)){
                System.out.println("HEY");
                int cursor = 3;
                if(checker.isElseIf(currentString)){
                    System.out.println("ELSEIF");
                    cursor = 7;
                }
                Character var1;
                currentString = currentString.replaceAll("\\s","");

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
                
                System.out.println(currentString.charAt(cursor));
                // int var2 =Integer.parseInt(String.valueOf(currentString.charAt(cursor)));  
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
    
    
    
 
   
    public void printGraph (){
        System.out.println("\nAdjacency List:");
        for(int i=0; i<Lines.size(); i++){
            System.out.print("\t"+i+"  ->   ");
            for(int j=0; j<Lines.size(); j++){
                if(adj[i][j]==1){
                    System.out.print(j+" ");
                }
            }
            System.out.println();
        }
        System.out.println("\nAdjacency Matrix:");
        for(int i=0; i<Lines.size(); i++){
            System.out.print("\t"+i+"\t");
            for(int j=0; j<Lines.size(); j++){
                System.out.print(adj[i][j]+" ");
            }
            System.out.println();
        }
    }
 
    
}
