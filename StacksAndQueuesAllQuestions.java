import java.util.*;

public class Main
{

/****************************************************************************************************
==================================================================================
FIND DUPLICATE BRACKETS: Return true if extra brackets found, else return false:
==================================================================================

((a + b) + (c + d)) -> false
(a + b) + ((c + d)) -> true     */

    public static boolean duplicateBrackets(String str){
        Stack<Character> st = new Stack<>();
        
        //Approach here is we add elements to the stack until ')' is encountered. After closing bracket encountered, we pop elements
        //from stack until '(' encountered. We pop '(' also. Now if again at the top we find '(' it means that there were no 
        //elements present between these two brackets so there are duplicate brackets present and we return true.
        
        for(int i=0; i<str.length(); i++){      
            char ch = str.charAt(i);            
            
            if(ch != ')' ){     //If ch is not closing bracket then we push it to stack.
                st.push(ch);
            }
            else{               //If closing bracket is found then we pop while opening bracket is found.
                if(st.peek() == '(' ){  //This check is necessary here to avoid EmptyStack error.
                    return true;
                }
                
                while(st.peek() != '(' ){
                    st.pop();
                }
                st.pop();   //Pop opening bracket also, now again it goes into else and if at top we find '(' then return true.
                
            }
        }
        return false;   //if we reach till here then return false.
    }

/***************************************************************************************************
==================================================================
 BALANCED BRACKETS: Return true if found balanced else false.
==================================================================

[(a + b) + {(c + d) * (e / f)}] -> true
[(a + b) + {(c + d) * (e / f)]} -> false        */

    public static boolean balancedBracket(String str) {
        Stack<Character> st = new Stack<>();
    
        //Approach: if opening brackets are found then add in stack. When any closing brackets found, then check if at top of 
        //stack same opening bracket is present. If not then return false.
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            if (ch == '(' || ch == '{' || ch == '[') {
                st.push(ch);
            } 
            
            else if (ch == ')') {
                if (st.isEmpty() || st.peek() != '('){
                    return false;
                }
                st.pop();
            } 
            
            else if (ch == ']') {
                if (st.size() == 0 || st.peek() != '['){
                    return false;
                }
                st.pop();
            } 
            
            else if (ch == '}') {
                if (st.size() == 0 || st.peek() != '{'){
                    return false;
                }
                st.pop();
            } 
        }
        return st.size() == 0;  //By the time we reach here, if stack is empty it means all brackets are balanced true is returned else false
    }


/***************************************************************************************************
=====================================================
//FIND NEXT GREATER ELEMENT TO THE RIGHT OF AN ARRAY:
=====================================================*/

    //Approach: Here we use index and push it in the stack. Now we traverse from 1 to arr.length, if any element greater than 
    //index at peek is found than we make that element as an answer for that index.
    
    public static int[] NextGreaterElement(int[] arr){
        Stack<Integer> st = new Stack<>();
        int[] ans = new int[arr.length];
        st.push(0); //Push index 0 in the stack.
   
        for(int i=1; i<arr.length; i++){    //Iterate from index 1 to last
            while(st.size() > 0 && arr[st.peek()] < arr[i]){    //If any element greater is found then traversed element is poped
                ans[st.pop()] = arr[i];             //and at that poped index in ans array we make that arr[i]th element as answer.
        }   
        st.push(i); //Then we push this index to the stack
        }
        while(st.size() > 0){
            ans[st.pop()] = -1; //If no greater element is found in the right then we place -1 for it.
        }
        return ans;
    }
/***************************************************************************************************
=====================================================
//FIND NEXT SMALLER ELEMENT TO THE RIGHT OF AN ARRAY:
=====================================================*/

    public static int[] NextSmallerElement(int[] arr){
        Stack<Integer> st = new Stack<>();
        int[] ans = new int[arr.length];
        
        st.push(0);
        for(int i=1; i<arr.length; i++){
            while(st.size()>0 && arr[st.peek()] > arr[i]){
                ans[st.pop()] = arr[i];
            }
            st.push(i);
        }
        while(st.size() > 0){
            ans[st.pop()] = -1;
        }
        return ans;
    }
/***************************************************************************************************
=====================================================
//FIND NEXT GREATER ELEMENT TO THE LEFT OF AN ARRAY:
=====================================================*/
    
    public static int[] PreviousGreaterElement(int[] arr){
        Stack<Integer> st = new Stack<>();
        int[] ans = new int[arr.length];
        
        st.push(arr.length-1);
        for(int i=arr.length-2; i>=0; i--){
            while(st.size()>0 && arr[st.peek()] < arr[i]){
                ans[st.pop()] = arr[i];
            }
            st.push(i);
        }
        while(st.size() > 0){
            ans[st.pop()] = -1;
        }
        return ans;
    }
    
/***************************************************************************************************
=====================================================
//FIND NEXT SMALLER ELEMENT TO THE LEFT OF AN ARRAY:
=====================================================*/

    public static int[] PreviousSmallerElement(int[] arr){
        Stack<Integer> st = new Stack<>();
        int[] ans = new int[arr.length];
        
        st.push(arr.length-1);
        for(int i=arr.length-2; i>=0; i--){
            while(st.size()>0 && arr[st.peek()] > arr[i]){
                ans[st.pop()] = arr[i];
            }
            st.push(i);
        }
        while(st.size() > 0){
            ans[st.pop()] = -1;
        }
        return ans;
    }

/****************************************************************************************************
===================================================================================================
STOCK SPAN: defined as the number of days passed between the current day and the first day before today 
when price was higher than today.
I/P: [2 5 9 3 1 12 6 8 7], O/P: [1, 2, 3, 1, 1, 6, 1, 2, 1]
===================================================================================================*/

    //Approach: This question is based on PreviousGreaterElement. We calulate it based on index.
    public static int[] stockSpan(int[] arr){
        Stack<Integer> st = new Stack<>();
        int[] ans = new int[arr.length];
        st.push(arr.length-1);  //Pushed the last index into Stack
        
        for(int i=arr.length-1; i>=0; i--){
            while(st.size()>0 && arr[st.peek()] < arr[i]){
                ans[st.pop()] = i; //Important: we add index here because in question we need to calculate based on this only
            }
            st.push(i);
        }
        while(st.size()>0){
            ans[st.pop()] = -1;
        }
        
        for(int i=0; i<ans.length; i++){ //Right now ans[] = {-1 -1 -1 2 3 -1 5 5 7} but we need [1 2 3 1 1 6 1 2 1]
            ans[i] = i - ans[i];    //So we make this operation for our result
        }
        return ans;
    }
/****************************************************************************************************/
//DAILY TEMPRATURE: ans[i] is the number of days you have to wait after the ith day to get a warmer temperature
//Leetcode: 739

    public int[] dailyTemperatures(int[] arr) {
        int[] res = new int[arr.length];
        Stack<Integer> st = new Stack<>();
        st.push(0);

        for (int i = 1; i < arr.length; i++) {
            while (st.size() > 0 && arr[st.peek()] < arr[i]) {
                res[st.pop()] = i;
            }
            st.push(i);
        }
        while (st.size() > 0) {
            res[st.pop()] = arr.length;
        }

        for (int i = 0; i < res.length; i++) {
            res[i] = arr.length - res[i];   //Another Important change
        }

        return res;
    }
/****************************************************************************************************/

//RIGHT GREATER INDEX:

    public static int[] rightGreaterIndex(int[] arr){
        Stack<Integer> st = new Stack<>();
        int[] ans = new int[arr.length];
        
        st.push(0);
        for(int i=1; i<arr.length; i++){
            while(st.size()>0 && ans[st.peek()] < arr[i]){
                ans[st.peek()] = i;
            }
            st.push(i);
        }
        while(st.size()>0){
            ans[st.pop()] = arr.length;
        }
        return ans;
    }

/****************************************************************************************************/
==========================
//SLINDING WINDOW MAXIMUM:
==========================

    //Approach: Here we will use rightGreaterIndex function
    public static int[] slidingWindowMax(int[] arr, int k){
        int[] rgi = rightGreaterIndex(arr);
        
        ArrayList<Integer> ans = new ArrayList<>();
        
        int j = 0;
        for(int i=0; i<=arr.length - k; i++){
            if(i > j){
                j = i;
            }
            while(i+k > rgi[j]){    //Here if next greater element is outside window it means in this window this number is
                j = rgi[j];         //maximum and we update j
            }
            ans.add(arr[j]);    //Here we add that next greater to our ans[]. 
        }
        
    }
/****************************************************************************************************/
	public static void main(String[] args) {
		String str = "((a + b) + (c + d))";
		//System.out.println(duplicateBrackets(str));
		int[] a = {2, 5, 9, 3, 1, 12, 6, 8, 7};
		int[] ans = dailyTemperatures(a);
		for(int i=0; i<ans.length; i++){
		    System.out.print(ans[i]+" ");
		}
	}
}




