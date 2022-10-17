
package cfg;
import java.io.IOException;
import java.util.ArrayList;


public class GererateTree {
    
    ArrayList<String>Lines;
    CheckSyn checker = new CheckSyn();
    int cur = 0;
    //Constructor to pass the lines of code
    public GererateTree(ArrayList<String> lines){
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
        
        TreeGen(root, false);

        Adjacency adj = new Adjacency(Lines);

        adj.makeAdacenct(root, -1);

    }
    
    
    public Node TreeGen(Node branchRoot, boolean inLoop){
        Node currentParrent = branchRoot;
        //System.out.println(cur);
        ArrayList<Node> newBranch = new ArrayList<>();
        //System.out.println(branchRoot.nodeNumber+" "+inLoop);
        
        while(cur<Lines.size()) {
            //System.out.println("finished " + cur);
            Node curNode = new Node(cur,Lines.get(cur));
            
            if(checker.isElse(curNode.Statement)){
                //System.out.println("Else - "+ curNode.Statement);
                
                currentParrent.childs.add(curNode);
                cur++;
                newBranch.add(TreeGen(curNode, false));
            }
            
            else if(checker.isElseIf(curNode.Statement)){
                //System.out.println("Else If - "+ curNode.Statement);
                
                currentParrent.childs.add(curNode);
                cur++;
                newBranch.add(TreeGen(curNode, false));
            }
    
            else if(checker.isIf(curNode.Statement)){
                //System.out.println("If - "+ curNode.Statement);
                
                if(newBranch.size()>0){
                    for(int i=0; i<newBranch.size(); i++){
                        newBranch.get(i).childs.add(curNode);
                        newBranch.clear();
                    }
                }
                else{
                    currentParrent.childs.add(curNode);
                }
                cur++;
                newBranch.add(TreeGen(curNode, false));
            }
    
            else if(checker.isLoop(curNode.Statement)){
                //System.out.println("loop - "+ curNode.Statement);
                
                if(newBranch.size()>0){
                    for(int i=0; i<newBranch.size(); i++){
                        newBranch.get(i).childs.add(curNode);
                        newBranch.clear();
                    }
                }
                else{
                    currentParrent.childs.add(curNode);
                }
                newBranch.add(curNode);
                cur++;
                TreeGen(curNode, true);
            }
        
            else{
                //System.out.println("Statement - "+ curNode.Statement+newBranch.size());
                if(newBranch.size()>0){
                    for(int i=0; i<newBranch.size(); i++){
                        newBranch.get(i).childs.add(curNode);
                    }
                    newBranch.clear();
                }
                else{
                    currentParrent.childs.add(curNode);
                }
                //newBranch.add(curNode);
                cur++;
                if(checker.foundEnd(curNode.Statement)){
                    if(inLoop==true) {
                        curNode.childs.add(branchRoot);
                    }
                    return curNode;
                }
                currentParrent = curNode;
            }
     
        }
        return null;
    }
    
    

    
}
