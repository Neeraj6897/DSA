import java.util.*;

public class Main
{
    public static class Edge {
        int v1;
        int v2;
        int wt;
        
        public Edge(int v1, int v2, int wt){
            this.v1 = v1;
            this.v2 = v2;
            this.wt = wt;
        }
    }
    
/************************************************************************************************
=======================
ADD EDGES TO THE graph:
=======================                                                                         */

    public static void addEdge(ArrayList<Edge>[] graph, int v1, int v2, int wt){
        graph[v1].add(new Edge(v1, v2, wt));
        graph[v2].add(new Edge(v2, v1, wt));
    }
   
/************************************************************************************************
==============
DISPLAY GRAPH:
==============                                                                                  */

    public static void display(ArrayList<Edge>[] graph){
        //First traverse to the index of Graph
        for(int i=0; i<graph.length; i++){
            System.out.print("[" + i + "] -> ");    //Here we are printing vertex no.
            
            //Now we traverse in ArrayList of edges for that index i.e. i'th index
            for(int n=0; n<graph[i].size(); n++){
                Edge e = graph[i].get(n);
                System.out.print("[" + e.v1 + "-" + e.v2 + " @ " + e.wt + "] ");
            }
            System.out.println();
        }
    }
 
/************************************************************************************************
============================================
FIND IF THE PATH EXIST BETWEEN SRC AND DEST:
============================================                                                    */

// THIS IS A DEPTH FIRST SEARCH(DFS) TRAVERSAL:
//-----------------------------------------------
    public static boolean hasPath(ArrayList<Edge>[] graph, int src, int dest, boolean[] vis){
        if(src == dest){    //If condition matched means path found so return true  
            return true;
        }
        
        vis[src] = true;    //Make this true as soon as we reach to the vertex
        
        for(Edge e : graph[src]){   //Now for each edge of that vertex, get the neighbour i.e. v2
            int nbr = e.v2;
            
            if(vis[nbr] == false){  //Now traverse only if the vertex is not visited, this is necessary to avoid going into ∞ loop
                boolean res = hasPath(graph, nbr, dest, vis);
                if(res == true){
                    return true;
                }
            }
        }
        return false;
    }

/************************************************************************************************
================================
PRINT ALL PATH FROM SRC TO DEST:
================================                                                                */

    public static void printAllPath(ArrayList<Edge>[] graph, int src, int dest, boolean[] vis, String pathSoFar){
        if(src == dest){
            System.out.println(pathSoFar+dest); //Add destination also to pathSoFar while printing.
            return;
        }
        
        vis[src] = true;
        for(Edge e : graph[src]){
            int nbr = e.v2;
            
            if(vis[nbr]==false){
                printAllPath(graph, nbr, dest, vis, pathSoFar + src); //Add src to pathSoFar
            }
        }
        vis[src] = false;   //Important change from hasPath to traverse all paths 
    }
    
/************************************************************************************************
=================================================
PRINT PATH WITH MINIMUN WEIGHT FROM SRC TO DEST :
=================================================                                          */

    static String smallestPath = "";
    static int MinWeight = Integer.MAX_VALUE;
    
    public static void smallestPathFromSrcToDest(ArrayList<Edge>[] graph, int src, int dest, boolean[] vis, String pathSoFar, int w){
        if(src == dest){
            pathSoFar += src;
            if(MinWeight > w){
                smallestPath = pathSoFar;
                MinWeight = w;
            }
            return;
        }
        
        vis[src] = true;
        for(Edge e : graph[src]){
            int nbr = e.v2;
            int weight = e.wt;
            
            if(vis[nbr]==false){
                smallestPathFromSrcToDest(graph, nbr, dest, vis, pathSoFar + src, w + weight); //Add src to pathSoFar
            }
        }
        vis[src] = false;   //Important change from hasPath to traverse all paths 
    }

/************************************************************************************************
=================================================
PRINT PATH WITH MAXIMUM WEIGHT FROM SRC TO DEST :   
=================================================                                           */

    static String LargestPath = "";
    static int MaxWeight = Integer.MIN_VALUE;
    
    public static void LargestPathFromSrcToDest(ArrayList<Edge>[] graph, int src, int dest, boolean[] vis, String pathSoFar, int w){
        if(src == dest){
            pathSoFar += src;
            if(MaxWeight < w){
                LargestPath = pathSoFar;
                MaxWeight = w;
            }
            return;
        }
        
        vis[src] = true;
        for(Edge e : graph[src]){
            int nbr = e.v2;
            int weight = e.wt;
            
            if(vis[nbr]==false){
                LargestPathFromSrcToDest(graph, nbr, dest, vis, pathSoFar + src, w + weight); //Add src to pathSoFar
            }
        }
        vis[src] = false;   //Important change from hasPath to traverse all paths 
    }

/*********************************************************************************************************
==================================================
FIND CEIL AND FLOOR ABOUT A GIVEN DATA IN A GRAPH: Data is w.r.t. weight of paths
==================================================                                                      */

    static int ceil = Integer.MAX_VALUE; //CEIL means just larger data from given data
    static int floor = Integer.MIN_VALUE; // FLOOR means just smaller data from given data
    static String ceilPath = "";
    static String floorPath = "";
    
    public static void ceilAndFloor(ArrayList<Edge>[] graph, int src, int dest, boolean[] vis, String pathSoFar, int w, int data){
        if(src == dest){
            if(w > data && ceil > w){ //Calculating value of ceil
                ceil = w;
                ceilPath = pathSoFar + src;
            }
            
            else if(w < data && floor < w){
                floor = w;
                floorPath = pathSoFar + src;
            }
            
            return;
        }
        
        vis[src] = true;
        for(Edge e : graph[src]){
            int nbr = e.v2;
            int weight = e.wt;
            if(vis[nbr] == false){
                ceilAndFloor(graph, nbr, dest, vis, pathSoFar+src, w + weight, data);
            }
        }
        vis[src] = false;
    }

/*********************************************************************************************************
================================================
FIND PATH WITH K'TH LARGEST VALUE W.R.T. WEIGHT:
================================================                                                            */
    
    //Approach 1: Similar to the one used in Generic Tree
    static int val = Integer.MAX_VALUE;
    public static void KthLargestPath(ArrayList<Edge>[] graph, int src, int dest, boolean[] vis, String pathSoFar, int w, int k){
        for(int i=0; i<k; i++){
            ceilAndFloor(graph, src, dest, vis, pathSoFar, w, val);
            val = floor;
            floor = Integer.MIN_VALUE;
        }
    }
    
    //Approach 2: Using Priority Queue: Here we will use a Pair class. Add elements to the PriorityQueue till its size
    //becomes equal to "k". If size=="k" then check if "wt" of peek is less than "wt" of current element. If yes then
    //remove peek and add new Pair with greater "wt".
    
    static class Pair implements Comparable<Pair>{
        int wsf;
        String psf;
        
        public Pair(int wsf, String psf){
            this.wsf = wsf;
            this.psf = psf;
        }
        
        public int compareTo(Pair o){ //compareTo function comparing on basis of wsf(weight so far)
            return this.wsf - o.wsf;
        }
    }
    
    static PriorityQueue<Pair> pq = new PriorityQueue<>();
    public static void KthLargestPath2(ArrayList<Edge>[] graph, int src, int dest, boolean[] vis, String pathSoFar, int w, int k){
        if(src == dest){
            pathSoFar += src;
            if(pq.size() < k){  //If size<k then keep adding
                pq.add(new Pair(w, pathSoFar));
            }
            else{
                if(pq.peek().wsf < w){  //If equal then if peek.wt is less than "w" so to get kth largest, we need to remove peek and add this
                    pq.remove();
                    pq.add(new Pair(w, pathSoFar));
                }
            }
            //return;
        }
        
        vis[src] = true;
        for(Edge e : graph[src]){
            int nbr = e.v2;
            int weight = e.wt;
            if(vis[nbr]==false){
                KthLargestPath2(graph, nbr, dest, vis, pathSoFar+src, w+weight, k);
            }
        }
        vis[src] = false;
    }
/*********************************************************************************************************
===============================================
FIND CONNECTED COMPONENTS(vertices) IN A GRAPH:
===============================================                                                                   */

    //Approach: We will calculate all the edges connected to one vertex and using a loop we will traverse to all the vertices.
    //We will use a helper function to get all the vertices connected to one vertex and that function will return an ArrayList
    //with the connected vertices to the provided vertex.
    
    public static ArrayList<ArrayList<Integer>> connectedComponents(ArrayList<Edge>[] graph){
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        boolean[] visited = new boolean[graph.length];
        
        for(int v=0; v<graph.length; v++){  //v here is the vertex, we are traversing on all the vertices here
            if(visited[v] == false){    //to avoid repetition, if a vertex is visited already via some other vertex then we dont call our function
                ArrayList<Integer> comps = new ArrayList<>();   //Initialize this list every time, it is in heap memory
                getConnectedComponentsFromOneVertex(graph, v, visited, comps); //calling our helper function.
                ans.add(comps); //Add comps array obtained
            }
        }
    }
    
    //This is our helper function to calculate all the vertices connected to one vertex
    public static void getConnectedComponentsFromOneVertex(ArrayList<Edge>[] graph, int src, boolean[] vis, ArrayList<Integer> comps){
        comps.add(src); //Add src to comps as it is the src, everytime we do this
        vis[src] = true; //Mark this true so that next time, we dont call helper function for this vertex.
        
        for(Edge e : graph[src]){
            int nbr = e.v2;
            
            if(vis[nbr] == false){
                getConnectedComponentsFromOneVertex(graph, nbr, vis, comps);    //recursive call to check all neighbours
            }
        }
    }

/*********************************************************************************************************
==================================
FIND IF THE GRAPH *IS CONNECTED* :
==================================                                                                      */

    //Approach: Here we will use connectedComponents, if there are more than one connected components it means
    //that e can'nt travel to all vertices from one single vertex and graph is not connected. So we can use a 
    //count variable which looks for the getConnectedComponentsFromOneVertex function call. If this call happens
    //for more than 1 time then we return false.
    
    public static boolean isConnected(ArrayList<Edge>[] graph){
        boolean[] visited = new boolean[graph.length];
        int count = 0;
        
        for(int v=0; v<graph.length; v++){
            if(visited[v] == false){
                count++;
                if(count > 1){
                    return false;
                }
                getConnectedComponentsModified(graph, v, vis);
            }
        }
        return true;
    }
    
    public static void getConnectedComponentsModified(ArrayList<Edge>[] graph, int src, boolean[] vis){
        vis[src] = true; //Mark this true so that next time, we dont call helper function for this vertex.
        
        for(Edge e : graph[src]){
            int nbr = e.v2;
            
            if(vis[nbr] == false){
                getConnectedComponentsModified(graph, nbr, vis);    //recursive call to check all neighbours
            }
        }
    }

/*********************************************************************************************************
===========================
FIND THE NUMBER OF ISLANDS: Islands are defined as the connected area by 0's in the 2d array. 
===========================                                                                             */

//Question: Given a 2d array where 0's represent land and 1's represent water. Assume every cell is linked 
//to it's north, east, west and south cell. We need to find and count the region connected with 0's.

    //Approach: We traverse to each cell i.e. index and if that cell is "0" then we find connected components
    //to that cell which are 0. In this process, we increase our count of element and while visiting the element
    //we mark them "-1" so that next time the traversed element by connectedComponentsForIslands funtion is not
    //called again.
    
    public static int noOfIslands(int[][] arr){
        boolean[][] visited = new boolean[arr.length][arr[0].length];
        int count = 0;
        
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr[0].length; j++){
                if(arr[i][j] == 0){
                    connectedComponentsForIslands(arr, i, j);
                    count++;    //Increase count if a "0" is found.
                }
            }
        }
        return count;
    }
    
    static int[] rdir = {-1, 0, 1, 0};
    static int[] cdir = {0, -1, 0, 1};
    public static void connectedComponentsForIslands(int[][] arr, int row, int col){
        arr[row][col] = -1; //We mark here 0 to -1 so that next time connectedComponentsForIslands is not called for
                            //this index.
        
        //Calling connectedComponentsForIslands in all the four directions "top, left, down, right".
        for(int i=0; i<rdir.length; i++){
            int r = row + rdir[i];
            int c = col + cdir[i];
            
            if(r>=0 && r<arr.length && c>=0 && c<arr[0].length && arr[r][c] == 0){
                connectedComponentsForIslands(arr, r, c);
            }
        }
        
    }

/*********************************************************************************************************
====================
PERFECT FRIENDS:
====================

    n = no. of vertices, k = no. of edges
    
/*********************************************************************************************************
==================================================
PRINT HAMILTONIAN PATH AND CYCLE FROM A GIVEN SRC: 
==================================================                                                       */

    //Definition: A hamiltonian path is such which visits all vertices without visiting any twice. 
    //A hamiltonian path becomes a cycle if there is an edge between first and last vertex.
    
    public static void PrintHamiltonianPathAndCycle(ArrayList<Edge>[] graph, int src, int osrc, HashSet<Integer> visited, String pathSoFar){
        //Base condition
        if(visited.size() == graph.length-1){ //If size of visited is equal to graph -1 it means we traveled all edges
            pathSoFar += src;
            System.out.print(pathSoFar);
            
            //Now if we check if the path obtained is cyclic. 
            boolean isCyclic = false;
            for(Edge e : graph[osrc]){
                int nbr = e.v2;
                if(nbr == src){
                    isCyclic = true;
                    break;
                }
            }
            if(isCyclic){ System.out.println("*"); } //If cyclic then we append path with "*"
            else{ System.out.println("."); } //If not cyclic then we append path with "."
        }
        
        //Normal code
        visited.add(src); //Add src to visited
        for(Edge e : graph[src]){
            int nbr = e.v2;
            if(visited.contains(nbr) == false){ //If it is false then we call our function
                PrintHamiltonianPathAndCycle(graph, nbr, osrc, visited, pathSoFar + src);
            }
        }
        visited.remove(src); //We unmark this to travel to other paths also
    }
    
/*********************************************************************************************************
=============
KNIGHTS TOUR:
=============                                                                                   */

    public static void printKnightsTour(int[][] chess, int r, int c, int upcomingMove) {
        boolean[][] visited = 
    }
    
    
/*********************************************************************************************************
=============================
BREADTH FIRST TRAVERSAL(BFS):
=============================                                                                             */

    //BFS is basically a Level Order Traversal: Remember it in 4 steps: get+remove, mark, print/work, add unvisited neighbours
    public class BFSPair {
        int vertex;
        String pathSoFar;
        
        public BFSPair(int vertex, String pathSoFar){
            this.vertex = vertex;
            this.pathSoFar = pathSoFar;
        }
    }

    public static void BFS(ArrayList<Edge>[] graph, int src){
        Queue<BFSPair> qu = new LinkedList<>();
        qu.add(new BFSPair(src, "" + src));
        boolean[] visited = new boolean[graph.length];
        
        while(qu.size() > 0){
            //1. Get + remove
            BFSPair rem = qu.remove();
            
            //2. mark *
            if(vis[rem.vertex] == true){
                //already visited so continue
                continue;
            }
            else{
                vis[rem.vertex] = true;
            }
            
            //3. printing
            System.out.println(rem.vertex + "@" + rem.pathSoFar);
            
            //4. add unvisited neighbours to Queue
            for(Edge e : graph[src]){
                if(vis[e.v2] == false){
                    qu.add(new BFSPair(e.v2, rem.pathSoFar + e.v2));
                }
            }
        }
    }
/*********************************************************************************************************/
===========================================
//IS GRAPH CYCLIC: Using both bfs and dfs:
===========================================

    //Approach: We call graph from a for loop and iterate over evey loop calling cyclic function.
    public static boolean isGraphCyclic(ArrayList<Edge>[] graph){
        boolean[] visited = new boolean[graph.length];
        
        for(int v = 0; v<graph.length; v++){
            if(visited[v] == false){
                // boolean res = bfsForCyclic(graph, v, visited);
                boolean res = dfsForCyclic(graph, v, visited, -1); //initially parent is passed as -1
                if(res == true){
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean bfsForCyclic(ArrayList<Edge>[] graph, int src, boolean[] visited){
        Queue<Integer> qu = new LinkedList<>();
        qu.add(src);
        
        while(qu.size() > 0){
            //1. get + remove
            int rem = qu.remove();
            
            //2. marking
            if(visited[rem] == true){
                //already visited it means we can reach here from some other path also, so cycle is there
                return true;
            }
            else{
                visited[rem] = true;
            }
            
            //3. Printing: It is not required in this question.
            //4. Add neighbours
            for(Edge e : graph[rem]){
                int nbr = e.v2;
                if(visited[nbr] == false){
                    qu.add(e.v2); //adding neighbour
                }
            }
        }
        return false;
    }
    
    public static boolean dfsForCyclic(ArrayList<Edge>[] graph, int src, boolean[] visited, int parent){
        //1. marking
        visited[src] = true;
        
        for(Edge e : graph[src]){
            int nbr = e.v2;
            
            //NEED TO ASK THIS THING
            if(visited[nbr] == true && nbr != parent){ //This is the condition for cycle detection via dfs.
                return true;
            }
            
            if(visited[nbr] == false){
                boolean res = dfsForCyclic(graph, nbr, visited, src); //src is passed as parent here
                if(res == true){
                    return true;
                }
            }
        }
        return false;
    }

/*********************************************************************************************************
===================
IS GRAPH BIPARTITE:
===================*/

    //Definition: A graph is called bipartite if it is possible to split it's vertices in two sets of mutually 
    //exclusive and exhaustive vertices such that all edges are across sets.

    public static boolean isBipartite(ArrayList<Edge>[] graph){
        int n = graph.length;
        int[] vis = new int[n];
        Arrays.fill(vis, -1); //We fill this initially with -1 and then with DiscoveryTime of that vertex
        
        for(int v=0; v<n; v++){
            if(vis[v] == -1){
                boolean res = isBipartiteComponent(graph, v, vis);
                if(res == false) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static class BPair{
        int vertex;
        int DiscoveryTime; //This is used to track if same path is used to find an element. If same then even cycle
                            //else odd cycle is there.
        
        public BPair(int vertex, int Dtime){
            this.vertex = vertex;
            this.DiscoveryTime = Dtime;
        }
    }
    
    //Graph is bipartite if it is acyclic or it has even sized cycle. 
    public static boolean isBipartiteComponent(ArrayList<Edge>[] graph, int src, int[] vis){
        Queue<BPair> qu = new LinkedList<>();
        qu.add(new BPair(src, 0));
        
        while(qu.size() > 0){
            //1. get + remove
            BPair rem = qu.remove();
            
            if(vis[rem.vertex] != -1){ //It means that vertex is already discovered. Now two things are checked
                
                //1. if again discovered with same DiscoveryTime --> continue
                //2. if again discovered with different DiscoveryTime --> return false because odd size cycle is detected
                
                if(vis[rem.vertex] == rem.DiscoveryTime) { //If same DiscoveryTime means graph is cyclic with even size cycle
                    continue;
                }
                else {  //If different DiscoveryTime found that means this graph is cyclic with an odd size cycle
                    return false;   
                }
            }
            
            //2. marking
            vis[rem.vertex] = rem.DiscoveryTime; //Marked the visited array with DiscoveryTime of removed vertex.
            
            for(Edge e : graph[rem.vertex]){
                int nbr = e.v2;
                if(vis[nbr] == -1){ //If unvisited then add in Queue
                    qu.add(new BPair(nbr, rem.DiscoveryTime + 1)); //We add nbr and increase DiscoveryTime by 1 from the 
                                                                    //last level DiscoveryTime.
                }
            }
        }
        return true; //If we reach at last it means either graph is acyclic or it has even sized cycle hence return true.
    }
/********************************************************************************************************
=====================
SPREAD OF INFECTION:  find how many people will get infected in time t, if the infection spreads to neighbors of 
===================== infected person in 1 unit of time.                                                      */

//Question similar to Find ROTTEN ORANGES

    //t is the given time at which no. of people are infected.
    public static int spreadOfInfection(ArrayList<Edge>[] graph, int src, int t){ 
        Queue<BPair> qu = new LinkedList<>();
        int[] visited = new int[graph.length];
        qu.add(new BPair(src, 1)); //Initially, no. of infected people is 1. DiscoveryTime now represents level
        int count = 0; //it is required to count no. of people got infected and returned at last as an answer
        
        while(qu.size() > 0){
            //1. get + remove
            BPair rem = qu.remove();
            
            //2. marking
            if(visited[rem.vertex] != 0){ //If already visited then we need to do nothing.
                continue;       //It means the person is already infected in the previous level
            }
            
            //else do the marking
            visited[rem.vertex] = rem.DiscoveryTime;
            
            if(visited[rem.vertex] > t){ //We need to check for "t" time stamp only, hence when we reach at that
                break;                  //level we break loop and return count of infected people till that level
            }
            
            count++; //Increase the count of infected persons
            
            //3.Printing: it is not required for this problem, we can print which person got infected though
            //4. Add neighbors
            for(Edge e : graph[rem.vertex]){
                int nbr = e.v2;
                if(visited[nbr] == 0){
                    qu.add(new BPair(nbr, rem.DiscoveryTime + 1));
                }
            }
        }
        return count;
    }

/*********************************************************************************************************/
===========================================================
//DIJKSTRA ALGORITHM: FIND SHORTEST PATH IN TERMS OF WEIGHT
===========================================================

    //Approach: It is a variation of BFS only, just here we use PriorityQueue instead of Queue and priority is
    //decided on the basis of weights. 

    public static class DPair implements Comparable<DPair> {
        int vtx;
        String psf; //pathSoFar
        int wsf;    //weight so far
        
        public DPair(int vtx, String psf, int wsf){
            this.vtx = vtx;
            this.psf = psf;
            this.wsf = wsf;
        }
        
        public static int compareTo(DPair o){
            return this.wsf - o.wsf;
        }
    }

    public static void dijkstraAlgo(ArrayList<Edge>[] graph, int src){
        PriorityQueue<DPair> pq = new PriorityQueue<>();
        pq.add(new DPair(src, src+"", 0));
        boolean[] visited = new boolean[graph.length];
        
        while(pq.size() > 0){
            //1. get + remove
            DPair rem = pq.remove();
            
            //2. marking
            if(visited[rem.vtx] == true){ //if already visited then continue
                continue;
            } 
            else {  //if not visited then mark this vertex as true
                visited[rem.vtx] = true;
            }
            
            //3. Printing
            System.out.println(rem.vtx + " via " + rem.psf + " @ " + rem.wsf);
            
            //4. add unvisited neighbors to PriorityQueue
            for(Edge e : graph[rem.vtx]){
                int nbr = e.v2;
                if(visited[nbr] == false){
                    pq.add(new DPair(nbr, rem.psf+nbr, rem.wsf+e.wt)); //Adding path and weight to removed DPair i.e. parent 
                }
            }
        }
    }

/*********************************************************************************************************/
//======================================================
//PRIMS ALGORITHM: CONNECT ALL PC's WITH MINIMUM WIRE:   
//======================================================

//NOTE: A graph is Tree if it is acyclic and connected. We need to find Minimum Spanning Tree for this problem

    //Approach: It is also similar to Dijkstra and BFS, here we dont keep on adding weights from previous levels
    //instead we compare on current level weight only
    
    public static class Phelper implements Comparable<Phelper> {
        int vtx;
        int parent;
        int wt;

        public Phelper(int vtx, int parent, int wt) {
            this.vtx = vtx;
            this.parent = parent;
            this.wt = wt;
        }

        public int compareTo(Phelper o) {
            return this.wt - o.wt;
        } 
    }
    
    public static void prims(ArrayList<Edge>[] graph) {
        PriorityQueue<Phelper> pq = new PriorityQueue<>();
        pq.add(new Phelper(0, -1, 0));  //Addint -1 as parent because 0 has no parent initially and initial weight is 0

        int n = graph.length;
        //We prepare a min spanning tree(mst) graph just for understanding. If it was required to return graph, we could return mst.
        ArrayList<Edge>[] mst = new ArrayList[n];
        for(int v = 0; v < n; v++) {
            mst[v] = new ArrayList<>();
        }

        //Our bfs with modifications starts from here
        boolean[] vis = new boolean[n];

        while(pq.size() > 0) {
            // 1. get + rem
            Phelper rem = pq.remove();
            // 2. mark
            if(vis[rem.vtx] == true) continue; //if already visited then continue
            
            vis[rem.vtx] = true;
            // 3. work -> print(for question) + add edge(mst graph)
            
            if(rem.parent != -1) { //We include this check because we dont want to print for initial condition where we added -1
                System.out.println("[" + rem.vtx + "-" + rem.parent + "@" + rem.wt + "]");
                addEdge(mst, rem.vtx, rem.parent, rem.wt); //This is just for reference and mst, not required for prims
            }

            // 4. add neighbour
            for(Edge e : graph[rem.vtx]) {
                if(vis[e.nbr] == false) { //Add only those which are not visited
                    pq.add(new Phelper(e.v2, rem.vtx, e.wt)); //rem.vtx is parent here
                }
            }
        }
    }

/*********************************************************************************************************
==================
TOPOLOGICAL SORT:   Directed and acyclic graph is used for this
==================

Question: Find and print the order in which tasks could be done. The task that should be done at last should be 
printed first and the task which should be done first should be printed last. This is called topological sort.
Topological sort -> A permutation of vertices for a directed acyclic graph is called topological sort if 
                    for all directed edges uv, u appears before v in the graph.*/
    
    //We need to create a directed and acyclic graph for this question. We can use addEdge function for this.

    //Approach: Similar to dfs traversal just we need to add src in postorder.
    public static void topologicalSort(ArrayList<Edge>[] graph) {
        boolean[] visited = new boolean[graph.length];
        Stack<Integer> st = new Stack<>();
        
        for(int v=0; v<graph.length; v++){
            if(visited[v] == false){
                topologicalSortDFS(graph, v, visited, st);
            }
        }
        System.out.println(st);
    }               
            
    public static void topologicalSortDFS(ArrayList<Edge>[] graph, int src, boolean[] visited, Stack<Integer> st){
        visited[src] = true;
        
        for(Edge e : graph[src]){
            int nbr = e.v2;
            if(visited[nbr] == false){
                topologicalSortDFS(graph, nbr, visited, st);
            }
        }
        st.push(src); //This addition to stack should be done in postorder only. 
    }        
    

/*********************************************************************************************************/


/*********************************************************************************************************/
    public static void fun(){
        
        int n = 7;  //n is no. of vertices in the graph
        
        //Graph: It is an array whose each element is an ArrayList of Edge type.
        ArrayList<Edge>[] graph = new ArrayList[n];
        
        boolean[] vis = new boolean[n]; //It is a blockage(visited) array useful while traversing
        
        //This initialization is necessary to initialize indices of array with ArrayList
        for(int i=0; i<n; i++){
            graph[i] = new ArrayList<>();
        }
        
        //This data represents that, for a[0], edges are from 0->1 with weight 10
        int[][] data = {
            {0, 1, 10},
            {0, 3, 40},
            {1, 2, 10},
            {2, 3, 10},
            {3, 4, 2},
            {4, 5, 3},
            {4, 6, 8},
            {5, 6, 3},
            {2, 5, 5}
        };
        
        for(int i=0; i<data.length; i++){
            addEdge(graph, data[i][0], data[i][1], data[i][2]);
        }
        //display(graph);
        //printAllPath(graph, 0, 6, vis);
        //LargestPathFromSrcToDest(graph, 0, 6, vis, "", 0);
        //System.out.println(LargestPath + "@" + MaxWeight);
        // ceilAndFloor(graph, 0, 6, vis, "", 0, 30);
        // System.out.println(ceilPath + "@" + ceil);
        // System.out.println(floorPath + "@" + floor);
        // KthLargestPath(graph, 0, 6, vis, "", 0, 4);
        // System.out.println(floorPath + "@" + val);
        KthLargestPath2(graph, 0, 6, vis, "", 0, 4);
        System.out.println(pq.peek().psf + "@" + pq.peek().wsf);
    }
    
	public static void main(String[] args) {
		fun();
	}
}








