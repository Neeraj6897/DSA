import java.util.*;

public class Main
{
    /* Node for bst has following structure. It is similar to Binary tree. */
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

//CONSTRUCTOR FOR BINARY SEARCH TREE:

    /*Note: This works only for sorted array.
    We are using binary search approach, first we will find mid using lo and hi and then set that element as root.
    Then we will find leftChild and rightChild for that root and in the end will assign obtained leftChild and rightChild to 
    left and right of root. */
    
    public static Node constructor(Integer[] arr, int lo, int hi){
        //Base condition:
        if(lo > hi) return null;
        
        //Find mid and create root for arr[mid]
        int mid = (lo + hi)/2;
        Node root = new Node(arr[mid]);
        
        //Obtain leftChild from lo to mid-1
        Node leftChild = constructor(arr, lo, mid - 1);
        
        //Obtain rightChild from mid+1 to hi
        Node rightChild = constructor(arr, mid + 1, hi);
        
        //Assign these values to left of root and right of root to create complete tree.
        root.left = leftChild;
        root.right = rightChild;
        
        return root;
    }
    
/*******************************************************************************************************/

//DISPLAY BINARY SEARCH TREE IN INORDER FORM: SIMILAR TO BINARY TREE

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

//SIZE OF A BST: Similar to Binary tree

    public static int size(Node node) {
        if(node == null) return 0;
        
        int leftSize = size(node.left);
        int rightSize = size(node.right);
        
        return leftSize + rightSize + 1;
    }

/*******************************************************************************************************/

//SUM OF A BST: Similar to Binary Tree
    
    public static int sum(Node node) {
        if(node == null) return 0;
        
        int leftSum = sum(node.left);
        int rightSum = sum(node.right);
        
        return leftSum + rightSum + node.data;
    }

/*******************************************************************************************************/

//MAX OF A BST:

    //Rightmost element in the bst will be maximum.
    public static int max(Node node) {
        while(node.right != null){
            node = node.right;
        }
        return node.data;
    }

/*******************************************************************************************************/

//MIN OF A BST:

    //Leftmost element in the bst will be minimun.
    public static int min(Node node){
        while(node.left != null){
            node = node.left;
        }
        return node.data;
    }

/*******************************************************************************************************/

//FIND AN ELEMENT IN A BST:

    public static boolean find(Node node, int data){
        //Base condition
        if(node == null) return false;
        
        if(node.data > data){   //Elements smaller than node will be in left, so process for left child only.
            boolean lres = find(node.left, data);
            if(lres == true) return true;
        }
        
        else if(node.data < data){  //Elements greater than node are in right, so proceed for right child only.
            boolean rres = find(node.right, data);
            if(rres == true) return true;
        }
        
        //If the data is found i.e. (node.data == data)
        else{
            return true;
        }
        
        //If we reach here it means data is not found, so return false.
        return false;
    }

/*******************************************************************************************************/

//ADD AN ELEMENT TO BST:

    public static Node add(Node node, int data){
        if(node == null) {
            Node nn = new Node(data, null, null);
            return nn;
        }
        
        if(data > node.data){
            node.right = add(node.right, data);
        }
        else if(data < node.data){
            node.left = add(node.left, data);
        }
        return node;
    }

/*******************************************************************************************************/

//REPLACE A NODE WITH SUM OF ALL NODES WHICH ARE GREATER FROM IT IN BST:

    //Approach: Inorder traversal of bst gives increasing sorted order and reverse inorder traversal of bst gives decreasing
    //sorted order of elements.
    //To traverse from reverse, first we go in node.right then do our work in inorder and then go to node.left.
    
    static int sum = 0;
    public static void rwsol(Node node){
        if(node == null) return;
        
        rwsol(node.right);  //First go to right
        int val = node.data;    //Take value of node in right
        node.data = sum;        //Now assign value of sum to node.data
        sum += val;             //Update the value of sum so that next time we can update node.data with this sum
        rwsol(node.left);
        
    }

/*******************************************************************************************************/

//LOWEST COMMON ANCESTOR OF BST: This is done using property of bst.

    //If d1 and d2 are smaller then the lie in the left side. If d1 and d2 are greater than they lie in right.
    //Else they both are in left and right of root, so root.data will be the answer.
    public static int lca(Node node, int d1, int d2){
        if(node == null) return 0;
        
        if(node.data > d1 && node.data > d2){
            int left = lca(node.left, d1, d2);
            return left;
        }
        
        else if(node.data < d1 && node.data < d2){
            int right = lca(node.right, d1, d2);
            return right;
        }
        else{
            return node.data;
        }
    }

/*******************************************************************************************************/

//PRINT NODE DATA IF IT IS IN GIVEN RANGE:      Leetcode no. 938

    //Approach: If given range is less than root, then it is in left. If greater then it is in right side of root.
    //Else the given root is in range, so do a inorder traversal from that node and print the data.
    
    public static void pir(Node node, int d1, int d2){
        if(node == null) return;
        
        if(node.data > d1 && node.data > d2){
            pir(node.left, d1, d2);
        }
        
        else if(node.data < d1 && node.data < d2){
            pir(node.right, d1, d2);
        }
        else{
            pir(node.left, d1, d2);
            System.out.println(node.data);
            pir(node.right, d1, d2);
        }
    }

/*******************************************************************************************************/

//TARGET SUM PAIR IN BST: APPROACH 1: Time : O(nh), Space : O(h), h-> height

    public static void targetSumPair1(Node node, Node root, int target){
        if(node == null) return;
        
        int n1 = node.data;
        int n2 = target - node.data;
        
        //Work area should be inorder to maintain the order of increasing values while printing.
        //First we go to the left
        targetSumPair1(node.left, root, target);
        //In inorder if n1 is smaller then only we can print in order where smaller comes first so we go for this condition only.
        if(n1 < n2){
            boolean res = find(root, n2);       //We use find function to find the root. If present then pair is found so print.
            if(res == true){
                System.out.println(n1 + " " + n2);
            }
        }
        //Now go to right 
        targetSumPair1(node.right, root, target);
    }
    
    //Method 2 : Use ArrayList to store the values in inorder, so this will give values in sorted order. Now we just need to 
    //recall target sum pair question from array.
    
    
    /*Method 3 : Time : O(n), Space : O(h), h->height
    
    This is an important approach. We use two pointer method with state for solving this on BST. 
    
    APPROACH: We use Inoder traversal from left and right. It should be done in iterative manner so we use two stacks for both
    traversals from left and right side. 
    */
    
    public static class Pair{
        Node node;
        int state;
        
        Pair(Node node, int state){
            this.node = node;
            this.state = state;
        }
    }
    
    //Normal Traversal which returns value for left pointer.
    public static int inorderTraversal(Stack<Pair> st){
        while(st.size() > 0){
            Pair p = st.peek();
            
            if(p.state == 0) {  //Processing for left child
                if(p.node.left != null){    //If node is present than push it into stack
                    st.push(new Pair(p.node.left, 0));
                }
                p.state++;  //increase state so next time right child can be processed.
            }
            
            else if(p.state == 1){  //Processing for right child
                if(p.node.right != null){
                    st.push(new Pair(p.node.right, 0));
                }
                p.state++;
                //Now both child are processed so we return from here only, because if we return at last then first pop will happen
                //it means left pointer will also be changed. But we need that to be changed in invalid case only as in two pointer
                //approach, so return from here and at last return -1.
                return p.node.data;
            }
            else{
                st.pop();
            }
        }
        return -1;
    }
    
    //Reverse Inorder Traversal which returns value for right pointer
    public static int ReverseInorderTraversal(Stack<Pair> st){
        while(st.size() > 0){
            Pair p = st.peek();
            
            if(p.state == 0){ //For reverse inorder, process for right child first.
                if(p.node.right != null){
                    st.push(new Pair(p.node.right, 0));
                }
                p.state++;
            }
            else if(p.state == 1){
                if(p.node.left != null){
                    st.push(new Pair(p.node.left, 0));
                }
                p.state++;
                return p.node.data; //Return from here only to avoid removing element
            }
            else{
                st.pop();
            }
        }
        return -1;
    }
    
    //Main function with Two Pointer Approach on BST
    public static void targetSumPair2(Node node, int target){
        Stack<Pair> leftStack = new Stack<>();
        Stack<Pair> rightStack = new Stack<>();
        
        leftStack.push(new Pair(node, 0));
        rightStack.push(new Pair(node, 0));
        
        //left and right pointer are obtained here
        int left = inorderTraversal(leftStack);
        int right = ReverseInorderTraversal(rightStack);
        
        while(left < right){
            int sum = left + right;
            if(sum > target){
                right = ReverseInorderTraversal(rightStack);
            }
            else if(sum < target){
                left = inorderTraversal(leftStack);
            }
            else{
                System.out.println(left + " " + right);
                left = inorderTraversal(leftStack);
                right = ReverseInorderTraversal(rightStack);
            }
        }
    }
    
/*******************************************************************************************************/

//DELETE A NODE FROM BST AND MAINTAIN ITS PROPERTY:

    //Approach: Three conditions can be there for a node. 1. No child 2. Single Child 3. Two children
    public static Node remove(Node node, int data){
        if(node == null) return null;
        
        if(node.data == data){
            //if no child available then just remove the node.
            if(node.left == null && node.right == null){
                return null;
            }
            //If single child is there then remove node and add child in place of node.
            else if(node.left!=null && node.right==null){
                return node.left;
            }
            //single right child;
            else if(node.left==null && node.right!=null){
                return node.right;
            }
            //If both children are there then find max in the left side of the node and replace it with the node to be deleted.
            //Then remove the max node from left side. Max from left is selected to maintain the bst property that all the Elements
            //to the left are smaller and to the right are larger. So max from left will ensure that all elements in left are smaller.
            else{
                int lmax = max(node.left);
                node.data = lmax;
                node.left = remove(node.left, lmax);
                return node;
            }
        }
        
        //Traverse in left
        else if(node.data > data){
            node.left = remove(node.left, data);
        }
        //Traverse in right
        else{
            node.right = remove(node.right, data);
        }
    }

/*******************************************************************************************************/

	public static void main(String[] args) {
	    //INPUT IS SORTED ARRAY
		Integer[] arr = {12, 25, 30, 37, 50, 62, 70, 75, 87};
		
        Node root = constructor(arr, 0, arr.length-1);
        //display(root);
        targetSumPair2(root, 100);
        
	}
}



