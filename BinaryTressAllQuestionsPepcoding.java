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

//FIND SIZE OF A BINARY TREE

    public static int size(Node node){
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

//ITERATIVE PREORDER POSTORDER INORDER TRAVERSAL USING SINGLE CODE

    public static void iterativePrePostInTraversal(Node node){
        Stack<Pair> st = new Stack<>();
        
        ArrayList<Integer> pre = new ArrayList<>();
        ArrayList<Integer> post = new ArrayList<>();
        ArrayList<Integer> in = new ArrayList<>();
        
        st.push(new Pair(node, 0));
        
        while(st.size() > 0){
            Pair p = st.peek();
            
            if(p.state == 0){
                pre.add(p.node.data);
                p.state++;
                
                if(p.node.left != null){
                    st.push(new Pair(p.node.left, 0));
                }
            }
            
            else if(p.state == 1){
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

    public static void printKLevelsDown(Node node, int k){
        if(node == null){
            return;
        }
        
        if(k == 0){
            System.out.println(node.data);
            return;
        }
        
        printKLevelsDown(node.left, k-1);   //if k-- is used in recursion then it gives problems
        printKLevelsDown(node.right, k-1);
    }


/*******************************************************************************************************/

// PRINT ELEMENTS WHICH ARE K DISTANCE AWAY FROM GIVEN DATA IN ANY DIRECTION (UPWARD OR DOWNWARD)

    public static void printKNodesFar(Node node, int data, int k) {
        
    }



/*******************************************************************************************************/


    
	public static void main(String[] args) {
	    Integer[] arr = { 50, 25, 12, null, null, 37, 30, null, null, null, 75, 62, null, 70, null, null, 87, null,
                null };
        Node root = constructor(arr);

        //display(root);
        //printKLevelsDown(root, 3);
        printKLevelsDown(root, 50, 2);
	}
}

