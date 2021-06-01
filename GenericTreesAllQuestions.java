import java.util.*;

public class Main
{
    public static class Node {
        int data;
        ArrayList<Node> children;
        
        public Node(){
            this.data = 0;
            this.children = new ArrayList<>();
        }
        
        public Node(int data){
            this.data = data;
            this.children = new ArrayList<>();
        }
    }
    
//===============================================================================
    
//CONSTRUCTING GENERIC TREE

    public static Node construct(Integer[] arr){
        Node root = null;
        Stack<Node> st = new Stack<>();
        
        for(int i=0; i<arr.length; i++){
            Integer data = arr[i];
            if(data != null){
                Node nn = new Node(data);
                if(st.size()==0){
                    root = nn;
                    st.push(nn);
                }
                else{
                    st.peek().children.add(nn);
                    st.push(nn);
                }
            }
            else{
                st.pop();
            }
        }
        return root;
    }
    
//===============================================================================

//DISPLAYING GENERIC TREE

    public static void display(Node root){
        String str = "["+root.data+"] -> ";
        for(Node child : root.children){
            str += child.data + ", ";
        }
        System.out.println(str + " . ");
        
        for(int i=0; i<root.children.size(); i++){
            Node child = root.children.get(i);
            display(child);
        }
    }
    
//===============================================================================
    
//FINDING SIZE OF GENERIC TREE

    public static int size(Node root){
        int count = 0;
        for(int i=0; i<root.children.size(); i++){
            Node child = root.children.get(i);
            count += size(child);
        }
        return count + 1;
    }

//===============================================================================

//FINDING MIN OF GENERIC TREE

    public static int minimum(Node root){
        int mn = Integer.MAX_VALUE;
        for(Node child : root.children){
            mn = Math.min(mn, minimum(child));
        }
        return Math.min(mn, root.data);
    }

//===============================================================================

//FINDING HEIGHT OF GENERIC TREE

    public static int height(Node root){
        int ht = -1;
        for(Node child : root.children){
            ht = Math.max(ht, height(child));
        }
        return ht + 1;
    }
    
//================================================================================

//PRE AND POST ORDER TRAVERSALS

    public static void traversals(Node node){
        System.out.println("Node Pre " + node.data);
        for(Node child : node.children){
            System.out.println("Edge Pre " + node.data + "--" + child.data);
            traversals(child);
            System.out.println("Edge Post " + node.data + "--" + child.data);
        }
        System.out.println("Node Post " + node.data);
    }
    
 //================================================================================
 
 //LEVEL ORDER TRAVERSAL
 
    public static void levelOrder(Node node){
      Queue<Node> qu = new ArrayDeque<>();
      qu.add(node);
      
      while(qu.size()>0){
          //1. get + remove
          Node rem = qu.remove();
          
          //2. Print 
          System.out.print(rem.data + " ");
          
          //3. Add children of node to Queue
          for(Node child : rem.children){
              qu.add(child);
          }
      }
    
    }
    
//================================================================================
 
 //LEVEL ORDER TRAVERSAL LINEWISE APPROACH: 1
 
 //Approach 1:  Using two Queues: 
 
    public static void levelOrderLinewise1(Node node){
      Queue<Node> mainq = new ArrayDeque<>();
      Queue<Node> childq = new ArrayDeque<>();
      
      //Adding root in mainq
      mainq.add(node);
      
      while(mainq.size()>0){
          //1. get + remove
          Node rem = mainq.remove();
          
          //2. Print 
          System.out.print(rem.data + " ");
          
          //3. Add children of node to Childq
          for(Node child : rem.children){
              childq.add(child);
          }
          
          //Check if size of mainq got 0 then print "enter" and swap the mainq and childq
          if(mainq.size()==0){
              System.out.println();
              Queue<Node> temp = new ArrayDeque<>();
              temp = mainq;
              mainq = childq;
              childq = temp;
          }
      }
    }
    

//================================================================================
 
 //LEVEL ORDER TRAVERSAL LINEWISE APPROACH: 2
 
 //Approach 1:  Using Single Queue with Delimiter:
 
    public static void levelOrderLinewise2(Node node){
      LinkedList<Node> mainq = new LinkedList<>();      //LinkedList is used because we can'nt add "null" in Queue, it gives NullPointerException
      
      //Adding root and null in mainq
      mainq.add(node);
      mainq.add(null);
      
      while(mainq.size()>0){
          //1. get + remove
          Node rem = mainq.remove();
          
          //If front element is null that means this level is completed hence we put "enter" and add Delimiter only when list size is >0.
          if(rem == null){
              System.out.println();
              
              //If size is more than 0 then again add "null", if we don't add below line, it will go in infinite loop
              if(mainq.size()>0){
                  mainq.add(null);
              }
          }
          
          //In else condition we can print and add children for that node to list
          
          else{
              
            //2. Print 
            System.out.print(rem.data + " ");
          
            //3. Add children of node to mainq
            for(Node child : rem.children){
                mainq.add(child);
            }
         }
      }
    }    
 
 //================================================================================
 
 //LEVEL ORDER TRAVERSAL LINEWISE APPROACH: 3
 
 //Approach 1:  Using Single Queue without using Delimiter: Here size of mainq is obtained and iteration is done till size times
 
    public static void levelOrderLinewise3(Node node){
      Queue<Node> mainq = new ArrayDeque<>();
      
      //Adding root in mainq
      mainq.add(node);
      
      while(mainq.size()>0){
          
          //Size of queue is obtained and then we iterate it for size times, this will ensure for printing for same level
          int sz = mainq.size();
          
          while(sz > 0){
            //1. get + remove
            Node rem = mainq.remove();
          
            //2. Print 
            System.out.print(rem.data + " ");
          
            //3. Add children of node to mainq
            for(Node child : rem.children){
                mainq.add(child);
            }
            sz--;
          }  
          //Above level completed so print "Enter"
          System.out.println();
      }
    }
 
    
//=================================================================================
 
//LEVEL ORDER TRAVERSAL LINEWISE IN ZIG-ZAG MANNER
 
    public static void levelOrderLinewiseZZ(Node node){
        Stack<Node> mainS = new Stack<>();
        Stack<Node> childS = new Stack<>();
        
        //. Add node to Stack
        mainS.push(node);
        //Define level
        int lvl = 1;
        
        while(mainS.size() > 0){
            //1. Get + print
            Node rem = mainS.pop();
            System.out.print(rem.data + " ");
            
            if(lvl % 2 == 1){
                //odd level = left->right additon of children is done
                for(int i=0; i<rem.children.size(); i++){
                    Node child = rem.children.get(i);
                    //Addition is done in child stack
                    childS.push(child);
                }
            }
            else{
                //even level = right to left additon of children is done
                for(int i=rem.children.size()-1; i>=0; i--){
                    Node child = rem.children.get(i);
                    //Addition is done in child stack
                    childS.push(child);
                }
            }
            
            if(mainS.size() == 0){
                //Print Enter
                System.out.println();
                
                //Increase the level
                lvl++;
                
                //Swap the stacks
                Stack<Node> temp = mainS;
                mainS = childS;
                childS = temp;
            }
        }
    }

//=================================================================================

//MIRROR A GENERIC TREE

    public static void mirror(Node node){
        //Traverse to the end of the tree first
        for(Node child : node.children){
            mirror(child);
        }
        
        //Now reverse the children of parent node
        int left = 0;
        int right = node.children.size() - 1;
        
        while(left < right){
            Node temp = node.children.get(left);
            node.children.set(left, node.children.get(right));
            node.children.set(right, temp);
            
            left++; right--;
        }
    }

//=================================================================================

//REMOVES LEAVES OF A GENERIC TREE

    public static void RemoveLeaves(Node node){
        
        //Pre-Order removal of leaves is done here
        
        //First iterate of children from last so that we can use .remove functionality of ArrayList
        for(int i=node.children.size()-1; i>=0; i--){
            Node child = node.children.get(i);
            if(child.children.size() == 0){
                node.children.remove(child);
            }
        }
        
        //Now traverse for child of node
        for(Node child : node.children){
            RemoveLeaves(child);
        }
    }
    
//=================================================================================

//FIND IN A GENERIC TREE

    public static boolean find(Node node, int data) {
        if(node.data == data){
            return true;
        }
        
        boolean res = false;
        for(Node child : node.children){
            res = find(child, data);
            if(res == true){
                return true;
            }
        }
        return res;
    }

//=================================================================================

//FIND NODE TO ROOT PATH IN A GENERIC TREE

    public static ArrayList<Integer> nodeToRootPath(Node node, int data){
        //Base case
        if(node.data == data){
            ArrayList<Integer> bres = new ArrayList<>();
            bres.add(node.data);
            return bres;
        }
        
        
        for(Node child : node.children){
            // A result ArrayList is declared
            ArrayList<Integer> res = nodeToRootPath(child, data);
            //Now if size of ArrayList becomes greater then 0 it means that data element has now come into ArrayList and
            // we just now need to return by adding path into ArrayList 
            if(res.size() > 0){
                res.add(node.data);
                return res;
            }
        }
        
        //If data is not found then return empty ArrayList.
        return new ArrayList<Integer>();
    }
    

//=================================================================================

//LOWEST COMMON ANCESTOR IN A GENERIC TREE

    public static int lca(Node node, int d1, int d2){
        ArrayList<Integer> path1 = nodeToRootPath(node, d1);
        ArrayList<Integer> path2 = nodeToRootPath(node, d2);
        
        //Traverse from behind to avoid time complexity
        int i = path1.size() - 1;
        int j = path2.size() - 1;
        
        //Check for valid iterations and till the time data is common, first occurence of diff data in arraulist is the time to stop
        while(i>=0 && j>=0 && path1.get(i) == path2.get(j)){
            i--; j--;
        }
        
        //Return the last common element from behind in ArrayList
        return path1.get(i+1);
    }
    

//=================================================================================

//CALCULATE DISTANCE BETWEEN TWO NODES: LCA will be used here

    public static int distanceBetweenNodes(Node node, int d1, int d2){
        ArrayList<Integer> path1 = nodeToRootPath(node, d1);
        ArrayList<Integer> path2 = nodeToRootPath(node, d2);
        
        //Traverse from behind to avoid time complexity
        int i = path1.size() - 1;
        int j = path2.size() - 1;
        
        //Check for valid iterations and till the time data is common, first occurence of diff data in arraulist is the time to stop
        while(i>=0 && j>=0 && path1.get(i) == path2.get(j)){
            i--; j--;
        }
        
        //Here returning i+1 and j+1 means there are i+1 elements left and j+1 elements left which are not common in 
        //both ArrayList so answer will be addition of these, it will give us the distance between two nodes
        return (i+1) + (j+1);
    }


//=================================================================================

//ARE TREES SIMILAR IN *SHAPE* : We don't have other tree to check, either create one to check or just check code.

    public static boolean areSimilar(Node n1, Node n2) {
        
        //If any condition where children sizes are not same it means shape is not similar so return false
        if(n1.children.size() != n2.children.size()){
            return false;
        }
        
        boolean res = true;
        //To traverse on both trees we use loop in below form, children size are similar if we come here
        //so we can use any one for size either n1 or n2.
        for(int i=0; i<n1.children.size(); i++){
            Node child1 = n1.children.get(i);
            Node child2 = n2.children.get(i);
            
            res = areSimilar(child1, child2);
            
            //if even once a false is encountered it means no calculate further and return from here
            if(res == false){
                return false;
            }
        }
        return res;
    }
    
    
//=================================================================================

//ARE TREES MIRROR IN *SHAPE* : We don't have other tree to check, either create one to check or just check code.

    public static boolean areMirror(Node n1, Node n2) {
        //If any condition where children sizes are not same it means shape is not similar so return false
        if(n1.children.size() != n2.children.size()){
            return false;
        }
        
        boolean res = true;
        //For node2 interate from reverse
        int j = n2.children.size()-1;
        
        for(int i=0; i<n1.children.size(); i++){
            Node child1 = n1.children.get(i);
            
            Node child2 = n2.children.get(j--);   //decrease j while traversing from behind
            
            res = areMirror(child1, child2);
            
            //if even once a false is encountered it means no calculate further and return from here
            if(res == false){
                return false;
            }
        }
        return res;
    }


//=================================================================================

//IS A TREE SYMMETRIC:

    //A tree is symmetric only when it is mirror of itself so we can use areMirror function above and check mirror with itself.
    
    public static boolean IsSymmetric(Node node) {
        boolean res = areMirror(node, node);
        return res;
    }


//=================================================================================

//FIND PREDECESSOR AND SUCCESSOR IN A GENERIC TREE

    static Node predecessor;
    static Node successor;
    static int state = 0;
    public static void predecessorAndSuccessor(Node node, int data) {
        if(state == 0){
            if(node.data == data){
                state++;
            }
            else{
                predecessor = node;
            }
            
        }
        else if(state == 1){
            successor = node;
            state++;
            return;
        }
        
        for(Node child : node.children){
            if(state < 2){
                predecessorAndSuccessor(child, data);
            }
        }
    }


//=================================================================================

//CEIL AND FLOOR A GENERIC TREE:
 
    static int ceil = Integer.MAX_VALUE; // qualified min
    static int floor = Integer.MIN_VALUE; // qualified max
    public static void ceilAndFloor(Node node, int data) {
        if(node.data > data) {
            // ceil
            if(node.data < ceil) {
                ceil = node.data;
            }
        }

        if(node.data < data) {
            // floor
            if(node.data > floor) {
                floor = node.data;
            }
        }

        for(Node child : node.children) {
            ceilAndFloor(child, data);
        }
    }
 
 
//=================================================================================
 
//KTH LARGEST ELEMENT IN A GENERIC TREE

    public static int kthLargest(Node node, int k){
        int data = Integer.MAX_VALUE;
        for(int i=0; i<k; i++){
            floor = Integer.MIN_VALUE;
            ceilAndFloor(node, data);
            data = floor;
        }
        return data;
    }
    

//=================================================================================

//NODE WITH MAXIMUM SUBTREE SUM: (Node with Sum of all children which is maximum)
    
    static int nodeData = 0;
    static int maxSum = Integer.MIN_VALUE;
    
    public static int maxSubtreeSum(Node node){
        int sum = 0;
        for(Node child : node.children){
            sum += maxSubtreeSum(child);
        }
        //We add the data of node also to check the complete sum of subtree
        sum += node.data;
        
        //If sum of the subtree is maximun so for then we update the maxSum value and nodeData
        //nodeData and maxSum are static variable and can be directly used to to print the answer after funtion call.
        if(sum > maxSum){
            maxSum = sum;
            nodeData = node.data;
        }
        //sum is returned for the sum of subtree, if sum of particular subtree is required then use this return value to print it.
        return sum;
    }


//=================================================================================

//DIAMETER OF A GENERIC TREE:

    

//=================================================================================

//PREORDER AND POSTORDER TRAVERSAL IN ITERATIVE MANNER: We will use a class Pair for this approach

    private static class Pair {
        Node node;
        int state;
        
        Pair(Node node, int state){
            this.node = node;
            this.state = state;
        }
    }

    public static void PrePostIterative(Node node){
        /* Here the approach is based on value of state:
        1. if state = -1, it means it is during preorder traversal
        2. if state = size of children of node, it means it is in postorder traversal
        3. if state is between 0 and size of children of node then inorder traversal.
        First we create a stack of Pair type and push node in it. */
        
        Stack<Pair> st = new Stack<>();
        st.push(new Pair(node, -1));  //-1 here is for state initially
        
        //Create two ArrayList to store values
        ArrayList<Integer> pre = new ArrayList<>();
        ArrayList<Integer> post = new ArrayList<>();
        
        while(st.size() > 0){
            Pair p = st.peek();
            
            //if p.state = -1 then add value in pre and increase state
            if(p.state == -1){
                pre.add(p.node.data);
                p.state++;
            }
            
            //if p.state = node.children.size then add value in post and pop from Stack
            else if(p.state == p.node.children.size()){
                post.add(p.node.data);
                st.pop();
            }
            
            //if state is between 0 and children size then add children of node at state value to stack and increase state
            else{
                Node child = p.node.children.get(p.state);  //p.state value is the p.state'th child like 0th child, 1st child like this.
                st.push(new Pair(child, -1));
                p.state++;
            }
        }
        
        //Print values in pre and post ArrayList
        for(int val : pre){
            System.out.print(val + " ");
        }
        System.out.println();
        for(int val : post){
            System.out.print(val + " ");
        }
        System.out.println();
    }
    
//===============================================================================================


	public static void main(String[] args) {
		Integer[] data = {10, 20, 50, null, 60, null, null, 30, 70, null, 80, 110, null, 
		120, null, null, 90, null, null, 40, 100, null, null, null};
		
		Node root = construct(data);
// 		display(root);
// 		System.out.println(size(root));
// 		System.out.println(minimum(root));
// 		System.out.println(height(root));
// 		traversals(root);
// 		levelOrder(root);
// 		levelOrderLinewise3(root);
// 		levelOrderLinewiseZZ(root);
// 		mirror(root);
// 		RemoveLeaves(root);
// 		display(root);
// 		System.out.println(distanceBetweenNodes(root, 110, 70));
// 		int ans = maxSubtreeSum(root);
// 		System.out.println(nodeData);
// 		System.out.println(maxSum);
        PrePostIterative(root);
		
        
	}
}







