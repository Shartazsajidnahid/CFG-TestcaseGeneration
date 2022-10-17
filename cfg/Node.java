
package cfg;

import java.util.ArrayList;


public class Node {
    int nodeNumber;
    String Statement;
    ArrayList<Node>parents = new ArrayList<>();
    ArrayList<Node>childs = new ArrayList<>();
    boolean iff, elsif, els, loop, end;

    public Node(int nodeNo, String nodeStatement){
        this.nodeNumber = nodeNo;
        this.Statement = nodeStatement;
        this.iff = elsif = els = loop = end = false;
    }
    
    public void setParent(Node pr){
        this.parents.add(pr);
    }
    public void setChild(Node pr){
        this.childs.add(pr);
    }
    
    
    
    
    
}
