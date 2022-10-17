
package cfg;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class MakeGraph {
    
    ArrayList<String>Lines;
    
    SyntaxIdentifier checker = new SyntaxIdentifier();
    int cur = 0;
    //Constructor to pass the lines of code
    public MakeGraph (ArrayList<String> lines){
        this.Lines = lines;
        //for(int i=0; i<Lines.size(); i++) System.out.println(i+ " " + Lines.get(i));
    }
    
    public void start() throws IOException{
        cur=0;
        
        while(Lines.get(cur).contains("intmain(){") || Lines.get(cur).charAt(0)=='#' || (Lines.get(cur).charAt(0)=='/' && Lines.get(cur).charAt(0)=='/')){
            cur++;
        }
        
        Node root = new Node(cur,Lines.get(cur));
        cur++;
        
        System.out.println("\n\n\nroot node no: " + root.nodeNumber +"\n"+ "root node statement: "+root.Statement);
        
        makeRelations(root, false);

        Adjacency adj = new Adjacency(Lines);
        
        adj.dfs(root, -1);
        adj.bfs(root);
        adj.printGraph();
        adj.saveGraph();
    }
    
    
    public Node makeRelations(Node branchRoot, boolean inLoop){
        Node par = branchRoot;
        //System.out.println(cur);
        ArrayList<Node> branchingsOfThisBranch = new ArrayList<>();
        //System.out.println(branchRoot.nodeNumber+" "+inLoop);
        
        while(cur<Lines.size()) {
            //System.out.println("finished " + cur);
            Node curNode = new Node(cur,Lines.get(cur));
            
            if(checker.isElse(curNode.Statement)){
                //System.out.println("Else - "+ curNode.Statement);
                
                par.childs.add(curNode);
                cur++;
                branchingsOfThisBranch.add(makeRelations(curNode, false));
            }
            
            else if(checker.isElseIf(curNode.Statement)){
                //System.out.println("Else If - "+ curNode.Statement);
                
                par.childs.add(curNode);
                cur++;
                branchingsOfThisBranch.add(makeRelations(curNode, false));
            }
    
            else if(checker.isIf(curNode.Statement)){
                //System.out.println("If - "+ curNode.Statement);
                
                if(branchingsOfThisBranch.size()>0){
                    for(int i=0; i<branchingsOfThisBranch.size(); i++){
                        branchingsOfThisBranch.get(i).childs.add(curNode);
                        branchingsOfThisBranch.clear();
                    }
                }
                else{
                    par.childs.add(curNode);
                }
                cur++;
                branchingsOfThisBranch.add(makeRelations(curNode, false));
            }
    
            else if(checker.isLoop(curNode.Statement)){
                //System.out.println("loop - "+ curNode.Statement);
                
                if(branchingsOfThisBranch.size()>0){
                    for(int i=0; i<branchingsOfThisBranch.size(); i++){
                        branchingsOfThisBranch.get(i).childs.add(curNode);
                        branchingsOfThisBranch.clear();
                    }
                }
                else{
                    par.childs.add(curNode);
                }
                branchingsOfThisBranch.add(curNode);
                cur++;
                makeRelations(curNode, true);
            }
        
            else{
                //System.out.println("Statement - "+ curNode.Statement+branchingsOfThisBranch.size());
                if(branchingsOfThisBranch.size()>0){
                    for(int i=0; i<branchingsOfThisBranch.size(); i++){
                        branchingsOfThisBranch.get(i).childs.add(curNode);
                    }
                    branchingsOfThisBranch.clear();
                }
                else{
                    par.childs.add(curNode);
                }
                //branchingsOfThisBranch.add(curNode);
                cur++;
                if(checker.foundEnd(curNode.Statement)){
                    if(inLoop==true) {
                        curNode.childs.add(branchRoot);
                    }
                    return curNode;
                }
                par = curNode;
            }
     
        }
        return null;
    }
    
    

    
}
