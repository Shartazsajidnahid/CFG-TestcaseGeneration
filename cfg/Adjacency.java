package cfg;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Adjacency {

    boolean vis[];
    int[][] adj = new int[50][50];
    ArrayList<String>Lines;

    
    public Adjacency (ArrayList<String> lines){
        this.Lines = lines;
        vis = new boolean[Lines.size()];
        //for(int i=0; i<Lines.size(); i++) System.out.println(i+ " " + Lines.get(i));
    }

    public void makeAdacenct(Node root, int i) throws IOException {
        dfs(root, i);
        bfs(root);
        printGraph();
        saveGraph();
    }
        
    public void dfs(Node cur, int prev){
        //System.out.println(prev + " " + cur.nodeNumber+" "+cur.Statement);
        vis[cur.nodeNumber] = true;
        
        for(int i=0; i<cur.childs.size(); i++){
            int nodeNo = cur.childs.get(i).nodeNumber;
            if(vis[nodeNo]==false){
                dfs(cur.childs.get(i), cur.nodeNumber);
            }
        }
        
        for(int i=0; i<cur.childs.size(); i++){
            adj[cur.nodeNumber][cur.childs.get(i).nodeNumber] = 1;
        }
    }
    
    public void bfs(Node root) throws IOException{
        int[] level = new int[50];
        for(int i=0; i<50; i++) level[i] = 100000000;
        
        level[root.nodeNumber] = 1;
        Queue<Integer>q = new LinkedList<>();
        q.add(root.nodeNumber);
        while(!q.isEmpty()){
            int cur = q.peek();
            q.poll();
            for(int i=0; i<50; i++){
                if(adj[cur][i]==1 && level[i]>level[cur]+1){
                    level[i] = level[cur]+1;
                    //System.out.println(i + " " + level[i]);
                    q.add(i);
                }
            }
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
    private int edgeCount = 0;
    public void saveGraph() throws IOException{
        try (FileWriter myWriter = new FileWriter("/home/nahid/Documents/CFG-TestcaseGeneration/cfg/Edges.txt")) {
            //myWriter.write((Lines.size())+"\n");
            for(int i=0; i<Lines.size(); i++){
                for(int j=0; j<Lines.size(); j++){
                    if(adj[i][j]==1){
                        myWriter.write(i+" "+j+"\n");
                        edgeCount++;
                    }
                }
            }
        }
        System.out.println("Total Nodes : " + Lines.size());
        System.out.println("Total Edges : " + edgeCount);
        int cc = edgeCount - Lines.size() + 2;
        System.out.println("\n\n\n\nCyclomatic Complexity : "   + cc );
    }


}
