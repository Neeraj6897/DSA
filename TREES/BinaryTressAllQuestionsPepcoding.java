import java.util.*;

public class Main
{
    /* Node for Binary tree has following structure */
    public static class Node {
        int data;
        Node left; 
        Node right;
        
        Node(int data){
            this.data = data;
            this.left = this.right = null;
        }
        
        Node(int data, Node left, Node right){
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }
    
/*******************************************************************************************************/

/* CONSTRUCTOR FOR BINARY TREE USING INTEGER ARRAY */

//We will use Pair of state and Node to construct binary tree from Integer array.
    public static class Pair{
        Node node;
        int state;
        
        Pair(Node node, int state){
            this.node = node;
            this.state = state;
        }
    }
    
/* Here the approach is based on value of state: 
1. if state = 0, start process for adding left child 
2. if state = 1, start process for adding right child 
3. other values of state, pop the value from stack */

    public static Node constructor(Integer[] arr){
        
        Node root = new Node(arr[0]);       //A root node is create using first value of arr.
        Stack<Pair> st = new Stack<>();
        st.push(new Pair(root, 0));         //Value is pushed to stack with state = 0
        
        int indx = 0;       // To iterate over array
        
        while(st.size() > 0){
            Pair p = st.peek();
            
            if(p.state == 0){
                /*1. Increase indx and if arr[indx] is not null then we can create a node and add it 
                to the left of p.node and then push this node to stack with state 0 for next left child to process. */
                indx++;
                if(arr[indx] != null){
                    Node nn = new Node(arr[indx]);
                    p.node.left = nn;
                    st.push(new Pair(nn, 0));
                }
                
                //2. Increase state of p so that next time we can process for right child of p
                p.state++;
            }
            
            else if(p.state == 1){
                //Steps are similar to left child processing
                indx++;
                if(arr[indx] != null){
                    Node nn = new Node(arr[indx]);
                    p.node.right = nn;
                    st.push(new Pair(nn, 0));
                }
                p.state++;
            }
            
            else{
                //Else pop the node pair from stack
                st.pop();
            }
        }
        
        //In the end return root of the tree
        return root;
        
    }
    

/*******************************************************************************************************/

//DISPLAY BINARY TREE IN INORDER FORM

    public static void display(Node node){
        if(node == null){
            return;
        }
        
        String str = "";
        str += node.left == null ? ". " : node.left.data + "";
        str += " <- [" + node.data + "] ->";
        str += node.right == null ? " ." : node.right.data + "";
        
        System.out.println(str);
        
        display(node.left);
        display(node.right);
    }
    

/*******************************************************************************************************/

//SIZE OF A BINARY TREE

    public static int size(Node node){
        //This base condition is important and should be used in every recursive code for binary tree
        if(node == null){
            return 0;
        }
        
        int ls = size(node.left);
        int rs = size(node.right);
        
        return ls + rs + 1;
    }


/*******************************************************************************************************/

//SUM OF BINARY TREE

    public static int sum(Node node){
        if(node ==  null){
            return 0;
        }
        
        int lsum = sum(node.left);
        int rsum = sum(node.right);
        
        return lsum + rsum + node.data;
    }


/*******************************************************************************************************/

//MAX IN BINARY TREE

    public static int max(Node node){
        if(node == null){
            return Integer.MIN_VALUE;
        }
        
        int lmax = max(node.left);
        int rmax = max(node.right);
        
        return Math.max(node.data, Math.max(lmax, rmax));
    }



/*******************************************************************************************************/

//MIN IN BINARY TREE

    public static int min(Node node){
        if(node == null) return Integer.MAX_VALUE;
        
        int lmin = min(node.left);
        int rmin = min(node.right);
        
        return Math.min(node.data, Math.min(lmin, rmin));
    }

/*******************************************************************************************************/

//HEIGHT OF BINARY TREE
    
    public static int height(Node node){
        if(node == null){
            return -1;
        }
        
        int lht = height(node.left);
        int rht = height(node.right);
        
        return Math.max(lht, rht) + 1;
    }


/*******************************************************************************************************/

//LEVEL ORDER TRAVERSAL(LINEWISE) IN A BINARY TREE: Approach is similar to levelOrder traversal of Generic tree.

    //We are using Approach 3 for this
    public static void levelOrder(Node node) {
        Queue<Node> que = new ArrayDeque<>();

        que.add(node);

        while(que.size() > 0) {
            int sz = que.size();

            while(sz-- > 0) {
                // 1. get + remove
                Node rem = que.remove();
                // 2. print
                System.out.print(rem.data + " ");
                // 3. add children
                if(rem.left != null)
                    que.add(rem.left);

                if(rem.right != null) 
                    que.add(rem.right);
            }
            System.out.println();
        }
    }
    
/*******************************************************************************************************/

//ITERATIVE PREORDER POSTORDER INORDER TRAVERSAL USING SINGLE CODE

    //Here also we are using state approach. State=0 means preorder, State=1 means inorder, State>1 means postorder
    public static void iterativePrePostInTraversal(Node node){
        Stack<Pair> st = new Stack<>();
        
        ArrayList<Integer> pre = new ArrayList<>();
        ArrayList<Integer> post = new ArrayList<>();
        ArrayList<Integer> in = new ArrayList<>();
        
        st.push(new Pair(node, 0));
        
        while(st.size() > 0){
            Pair p = st.peek();
            
            if(p.state == 0){           //Process for preorder
                pre.add(p.node.data);   //Add data in pre array
                p.state++;
                
                if(p.node.left != null){    //If not null then push the node in stack with state 0 so next time process left child
                    st.push(new Pair(p.node.left, 0));
                }
            }
            
            else if(p.state == 1){      //Process for inorder.
                in.add(p.node.data);
                p.state++;
                
                if(p.node.right != null){
                    st.push(new Pair(p.node.right, 0));
                }
            }
            
            else{
                post.add(p.node.data);
                st.pop();
            }
        }
        
        //Print values of pre, post and in
    }
   
    
/*******************************************************************************************************/

//FIND IN A BINARY TREE

    public static boolean find(Node node, int data){
        if(node == null){
            return false;
        }
        
        if(node.data == data){
            return true;
        }
        
        boolean lres = find(node.left, data);
        if(lres == true) return true;
        
        boolean rres = find(node.right, data);
        if(rres == true) return true;
        
        return false;
    }


/*******************************************************************************************************/

//PRINT *NODE TO ROOT* PATH : Approach is similar to nodeToRootPath for Generic Tree

    public static  ArrayList<Integer> nodeToRootPath(Node node, int data){
        if(node == null){
            return new ArrayList<>();
        }
        
        if(node.data == data){
            ArrayList<Integer> bres = new ArrayList<>();
            bres.add(node.data);
            return bres;
        }
        
        ArrayList<Integer> lres = nodeToRootPath(node.left, data);
        if(lres.size() > 0){
            lres.add(node.data);
            return lres;
        }
        
        ArrayList<Integer> rres = nodeToRootPath(node.right, data);
        if(rres.size() > 0){
            rres.add(node.data);
            return rres;
        }
        
        return new ArrayList<>();
    }


/*******************************************************************************************************/

//PRINT ELEMENTS WHICH ARE K LEVEL DOWN FROM ROOT:

    //Approach: We decrease k with every recursive iteration and print the data when k becomes 0 and return.
    public static void printKLevelsDown(Node node, int k){
        if(node == null){
            return;
        }
        
        if(k == 0){
            System.out.println(node.data);
            return;
        }
        
        printKLevelsDown(node.left, k-1);   //if k-- is used in recursion then it gives problems so use k-1
        printKLevelsDown(node.right, k-1);
    }


/*******************************************************************************************************/

// PRINT NODES WHICH ARE K DISTANCE AWAY FROM GIVEN DATA IN ANY DIRECTION (UPWARD OR DOWNWARD)

    /* Approach: Here we will use nodeToRootPath function with respect to *node* instead of Integer.
    We will return list of Nodes from this function. And from this list, we will use printKLevelsDown function with
    additional element *blockage* to avoid traversing the node traversed before. 
    We will traverse in the NodetoRootPath ArrayList and print elements which are k levels down for every element
    in ArrayList and keep on decreasing k for every element. */
    
    //nodeToRootPath function with return type of Node array.
    public static ArrayList<Node> nodetoroot(Node node, int data){
        if(node == null){
            return new ArrayList<>();
        }
        
        if(node.data == data){
            ArrayList<Node> bres = new ArrayList<>();
            bres.add(node);
            return bres;
        }
        
        ArrayList<Node> lres = nodetoroot(node.left, data);
        if(lres.size() > 0){
            lres.add(node);
            return lres;
        }
        
        ArrayList<Node> rres = nodetoroot(node.right, data);
        if(rres.size() > 0){
            rres.add(node);
            return rres;
        }
        
        return new ArrayList<>();
    }

    //Modified printKLevelsDown function for additional constraint of blockage node while printing.
    public static void printKDown(Node node, Node blockage, int k){
        if(node == null || node == blockage || k < 0) return;    //Here it will not go to nodes which are printed
        
        if(k == 0){
            System.out.println(node.data);
            return;
        }
        
        printKDown(node.left, blockage, k-1);
        printKDown(node.right, blockage, k-1);
    }

    public static void printKNodesFar(Node node, int data, int k) {
        
        ArrayList<Node> path = nodetoroot(node, data);
        
        Node blockage = null;   //To avoid re-printing of paths
        
        //Here we will call Modified printKLevelsDown function and print the elements k distance away.
        for(int i=0; i<path.size() && k>=0; i++){
            Node nn = path.get(i);
            printKDown(nn, blockage, k);
            k--;
            blockage = nn;
        }
    }

/**********************************************************************************************************/

//PRINT ALL PATHS FROM ROOT TO LEAVES WHICH ARE IN A PARTICULAR SUM RANGE:

    /* Approach: We will traverse to leaves using recursion and keep on adding path and sum and then while returning
    we print the eligible values. */
    
    public static void pathToLeafFromRoot(Node node, String path, int sum, int lo, int hi){
        if(node == null){
            return;
        }
        
        //If both child are present, call in both sides
        if(node.left != null && node.right != null){
            pathToLeafFromRoot(node.left, path + node.data + " ", sum + node.data, lo, hi);
            pathToLeafFromRoot(node.right, path + node.data + " ", sum + node.data, lo, hi);
        }
        
        //If left child is present, call in only left sides
        else if(node.left != null){
            pathToLeafFromRoot(node.left, path + node.data + " ", sum + node.data, lo, hi);
        }
        
        //If right child is present, call right sides
        else if(node.right != null){
            pathToLeafFromRoot(node.right, path + node.data + " ", sum + node.data, lo, hi);
        }
        
        //Else if no child is present i.e. both children are null then we are at end of traversal, now if sum obtained till now
        // is in range then print the path.
        else{
            sum = sum + node.data;
            path = path + node.data;
            if(sum > lo && sum < hi){
                System.out.println(path);
            }
        }
    }



/**********************************************************************************************************/

//CREATE A LEFT CLONED BINARY TREE: Create a copy of node in the left side of the same node.

    //Approach: All the work will be done in the postorder. Create a new node with left element as left tree and right as null.
    //Add new node to left.
    public static Node createLeftCloneTree(Node node){
         if(node == null) return null;
         
         //Traverse to the last nodes of the tree
         Node leftClonedTree = createLeftCloneTree(node.left);
         Node rightClonedTree = createLeftCloneTree(node.right);
         
         //Create a new node i.e. clone and add leftClonedTree to the left of the clone and null to the right of the clone.
         Node nn = new Node(node.data, leftClonedTree, null);
         node.left = nn;
         //Add obtained rightClonedTree to the right of node, without any change as we need to create a left cloned tree.
         node.right = rightClonedTree;
         
         return node;
    }

/**********************************************************************************************************/

//TRANSFORM BACK TO ORIGINAL FROM LEFT CLONED TREE: ***TRICKY***

    public static Node transBackFromLeftClonedTree(Node node){
        if(node == null) return null;
        
        Node leftTree = transBackFromLeftClonedTree(node.left.left); //Important adjustment.
        Node rightTree = transBackFromLeftClonedTree(node.right);
        
        node.left = leftTree;
        node.right = rightTree;
        return node;
    }

/**********************************************************************************************************/

//PRINT ALL THE NODES WHICH ARE SINGLE CHILD TO THEIR PARENTS

    //Check the condition of single child, if satisfies then print. And then call function for left and right to traverse.
    public static void printSingleChildNodes(Node node){
        if(node == null) return;
        
        if(node.left != null && node.right == null){
            System.out.println(node.left.data);
        }
        else if(node.right != null && node.left == null){
            System.out.println(node.right.data);
        }
        
        printSingleChildNodes(node.left);
        printSingleChildNodes(node.right);
    }

/**********************************************************************************************************/

//REMOVES LEAVES IN A BINARY TREE:

    public static Node removeLeaves(Node node){
        if(node == null) return null;
        
        //Check condition for leaf node, if met then return null to remove. It should be removed in preorder.
        if(node.left == null && node.right == null){
            return null;
        }
        
        //Call for left tree and right tree.
        Node leftTree = removeLeaves(node.left);
        Node rightTree = removeLeaves(node.right);
        
        //This assignment to node.left and node.right is necessary because we need to add the trees returned by node.
        node.left = leftTree;
        node.right = rightTree;
        
        /* It can also be assigned directly to node.left and node.right. We can understand it like receive the node returned by
        left in node.left and by right in node.right. 
        node.left = removeLeaves(node.left);
        node.right = removeLeaves(node.right);  */
        
        return node;
    }

/**********************************************************************************************************/

//TILT OF BINARY TREE:"tilt" of a node is the absolute value of difference between sum of nodes in it's left subtree 
//and right subtree. "tilt" of the whole tree is represented as the sum of "tilt"s of all it's nodes
//LEETCODE NO. = 563

    static int tilt = 0;
    public static int tilt(Node node){
        if(node == null) return 0;
        
        //Find the left sum and right sum for the tree
        int lsum = tilt(node.left);
        int rsum = tilt(node.right);
        
        //Keep on updating the value of tilt 
        tilt += Math.abs(lsum - rsum);
        
        return lsum + rsum + node.data; //Return the sum of the subtree from that node.
    }

/**********************************************************************************************************/

//DIAMETER OF A BINARY TREE: Diameter of tree is the maximum distance between two nodes in a tree.

    static int diameter = 0;
    
    public int heightForDiameter(Node node){
        if(node == null) return -1;
        
        int leftHeight = heightForDiameter(node.left);
        int rightHeight = heightForDiameter(node.right);
        
        diameter = Math.max(diameter, leftHeight + rightHeight + 2);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public int diameterOfBinaryTree(Node node) {
        diameter = 0;
        heightForDiameter(node);
        return diameter;
    }

/**********************************************************************************************************/

//IS A TREE BINARY SEARCH TREE: For every node, node.left.data will be smaller and node.right.data will be larger from node.data.

    //APPROACH 1:

    public static boolean isBST1(Node node){
        if(node == null) return true;
        
        //self-check
        int lmax = max(node.left);
        int rmin = min(node.right);
        
        if(lmax > node.data || rmin < node.data){
            return false;
        }
        
        return isBST1(node.left) && isBST1(node.right);
    }
    
    //APPROACH 2:

    public static class BSTPair(){
        int min;
        int max;
        boolean isbst;
        
        BSTPair(){
            min = Integer.MAX_VALUE;
            max = Integer.MIN_VALUE;
            isbst = true;
        }
    }
    
    public static BSTPair isBST2(Node node){
        if(node == null) return new BSTPair();
        
        BSTPair lres = isBST2(node.left);
        BSTPair rres = isBST2(node.right);
        
        //Condition for valid bst status
        boolean status = lres.max < node.data && rres.min > node.data; //It will set true if conditon is matched else false.
        
        BSTPair mres = new BSTPair();
        mres.min = Math.min(node.data, Math.min(lres.min, rres.min));
        mres.max = Math.max(node.data, Math.max(lres.max, rres.max));
        mres.isbst = lres.isbst && rres.isbst && status;
        
        return mres;
    }

/**********************************************************************************************************/

//IS BINARY TREE A BALANCED TREE:
//---------------------------------

//A binary tree is balanced if for every node the gap between height's of it's left and right subtree is not more than 1

    static boolean isBal = true;    //Keep static variable as we need to calculate and return height from the below function
    public static int isBalanced(Node node){
        if(node == null) return -1;
        
        int l = isBalanced(node.left);
        int r = isBalanced(node.right);
        
        if(Math.abs(l-r) > 1){  //Condition for unbalanced tree.
            isBal = false;
        }
        return Math.max(l, r) + 1;
    }

/**********************************************************************************************************/

//FIND THE LARGEST SUBTREE WHICH IS BST, ALSO FIND NO. OF NODES IN THAT SUBTREE.

    //Approach: We will use BST1 and size functions. If any subtree is BST then find size of it, if it is greater than
    //previos maxSize then we update these values of maxSize and nodeData and call for left and right child for node.
    int maxSize = 0;
    int nodeData = 0;
    public static void LargestBST(Node node){
        if(node == null) return;
        
        int sz = 0;
        boolean res = isBST1(node);
        if(res == true){
            sz = size(node);
        }
        
        if(maxSize < sz){   //Upaate values if greater size BST is obtained.
            maxSize = sz;
            nodeData = node.data;
        }
        LargestBST(node.left);
        LargestBST(node.right);
    }
    
    //Output format
    System.out.println(nodeData + "@" + maxSize);


/**********************************************************************************************************/

	public static void main(String[] args) {
	    Integer[] arr = { 50, 25, 12, null, null, 37, 30, null, null, null, 75, 62, null, 70, null, null, 87, null,
                null };
        Node root = constructor(arr);

        //display(root);
        //printKLevelsDown(root, 3);
        //printKLevelsDown(root, 50, 2);
        //printKNodesFar(root, 37, 1);
        //pathToLeafFromRoot(root, "", 0, 55, 250);
        // createLeftCloneTree(root);
        // display(root);
        printSingleChildNodes(root);
	}
}




