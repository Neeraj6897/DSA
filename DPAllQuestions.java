import java.util.*;

public class Main
{
/***************************************************************************************
FIBNOCCI SERIES USING DP:
========================*/

    public static int fib_dp(int n, int[] dp){
    dp[0] = 0;
    dp[1] = 1;
    
    for(int i=2; i<=n; i++){
        dp[i] = dp[i-1] + dp[i-2];
    }
    return dp[n];
}

/**************************************************************************************
==================================
CLIMB STAIRS USING ALL APPROACHES:
==================================*/

    //Approach 1: First we solve this using recursion.
    public static int climbStairs_Rec(int n){
        if(n == 0){
            return 1;
        }
        
        int count = 0;
        for(int i=1; i<=3; i++){
            if(n - i >= 0){
                count += climbStairs_Rec(n-i);
            }
        }
        return count;
    }
    
    //Approach 2: Now we solve this using memoization. In this we avoid going into solved paths by storing the 
    //values in an array of size n+1.
    
    public static int climbStairs_Memoization(int n, int[] dp){
        if(n == 0){
            dp[0] = 1; //for base case we store the value in d[0] i.e. dp[n]
        }
        
        //Check if the value is already present in dp, it means it is already solved so return that value
        if(dp[n] != 0){
            return dp[n];
        }
        
        int count = 0; //Normal recursive code 
        for(int i=1; i<=3; i++){
            if(n-i >= 0){
                count += climbStairs_Memoization(n - i, dp);
            }
        }
        return dp[n] = count; //While returning we store the value of count obtained to our dp array.
    }
    
    //Approach 3: Tabulation method where we create a 
    public static int climbStairs_Tabulation(int n, int[] dp){
        dp[0] = 1; //one way possible to go to climb stair i.e. don't move
        
        for(int i=1; i<=n; i++){ //here we are going i to n by storing the values of previously calculated values.
            if(i >= 3){
                dp[i] = dp[i-1] + dp[i-2] + dp[i-3];
            }
            else if(i >=2){
                dp[i] = dp[i-1] + dp[i-2];
            }
            else{ //if i==1
                dp[i] = dp[i-1];
            }
        }
        return dp[n];
    }
    
    //Appraoch 4: Converting memoization to tabulation approach
    public static int climbStairs_MemToTab(int N, int[] dp){
        dp[0] = 1;
        for(int n=0; n<=N; n++){
            int count = 0;
            for(int j=1; j<=3; j++){
                if(n-j >= 3){
                    count += dp[n-j];
                }
            }
            dp[n] = count;
        }
        return dp[N];
    }

/**************************************************************************************
==================================
CLIMB STAIRS USING VARIABLE JUMPS:
==================================*/

    //Approach 1: using recursion
    // i-> current stair, n-> total stair, jumps[] -> jumps allowed at ith stair
    public static int climbStair_VarJumps_rec(int i, int n, int[] jumps) {
        if(i == n) {
            return 1;
        }
        int count = 0;
        for(int jump = 1; jump <= jumps[i] && jump + i <= n; jump++) {
            count += climbStair_VarJumps_rec(i + jump, n, jumps);
        }
        return count;
    }
    
    //Approach 2: Using memoization
    public static int climbStair_VarJumps_memo(int i, int n, int[] jumps, int[] dp) {
        if(i == n) {
            return dp[i] = 1;
        }

        if(dp[i] != 0) {
            return dp[i];
        }

        int count = 0;
        for(int jump = 1; jump <= jumps[i] && jump + i <= n; jump++) {
            count += climbStair_VarJumps_memo(i + jump, n, jumps, dp);
        }
        return dp[i] = count;
    }

    //Approach 3: Using Tabulation:
    public static int climbStair_VarJumps_tab(int i, int n, int[] dp, int[] jumps) {
        for(i = n; i >= 0; i--) {
            if(i == n) {
                dp[i] = 1;
                continue;
            }
    
            int count = 0;
            for(int jump = 1; jump <= jumps[i] && jump + i <= n; jump++) {
                count += dp[i + jump]; //climbStair_VarJumps_memo(i + jump, n, jumps, dp);
            }
            dp[i] = count;
        }
        return dp[0];
    }

/***************************************************************************************
================================
CLIMB STAIRS WITH MINIMUM JUMPS:
================================*/

    public static int climbStair_MinJumps_rec(int i, int n, int[] jumps){
        if(i == n){
            return 0;
        }
        
        int minJumps = (int)1e9;
        
        for(int jump=1; jump <= jumps[i] && jump+i <=n; jump++){
            minJumps = Math.min(minJumps, climbStair_MinJumps_rec(i+jumps, n, jumps);
        }
        return minJumps == 1e9 ? minJumps : minJumps+1; //This is to check if path exists, if no path then return minJumps i.e. infinity only
    }
    
    //Approach 2: Using memoization
    public static int climbStair_MinJumps_memo(int i, int n, int[] jumps, int[] dp){
        if(i == n){
            return dp[i] = 0;
        }
        
        if(dp[i] != 0){
            return dp[i];
        }
        
        int minJumps = (int)1e9;
        
        for(int jump=1; jump <= jumps[i] && jump+i <=n; jump++){
            minJumps = Math.min(minJumps, climbStair_MinJumps_memo(i+jump, n, jumps, dp));
        }
        return minJumps == 1e9 ? (dp[i] = minJumps) : (dp[i] = minJumps+1);
    }
    
    //Approach 3: Using Tabulation
    public static int climbStair_MinJumps_tab(int n, int[] jumps, int[] dp){
        for(int i=n; i>=0; i--){
            if(i == n){
                dp[i] = 0;
                continue;
            }
            
            int minJumps = (int)1e9;
        
            for(int jump=1; jump <= jumps[i] && jump+i <=n; jump++){
                minJumps = Math.min(minJumps, dp[i+jump]);
            }
            
            if(minJumps == 1e9){
                dp[i] = (int)1e9;
            }
            else{
                dp[i] = minJumps + 1;
            }
        }
        return dp[0];
    }
/***************************************************************************************
=================================
MINIMUM COST IN A MAZE TRAVERSAL:
=================================*/

    //Approach 1: Using recursion
    public static int MinCostMazePath(int[][] maze, int x, int y){
        if(x==maze.length-1 && y == maze[0].length-1){
            return maze[x][y];
        }
        
        int minCost = (int)1e9;
        //right call 
        if(y+1 < maze[0].length){
            minCost = Math.min(minCost, MinCostMazePath(maze, x, y+1));
        }
        
        //left call 
        if(x+1 < maze.length){
            minCost = Math.min(minCost, MinCostMazePath(maze, x+1, y));
        }
        return minCost + maze[x][y];
    }
    
    //Approach 2: Using memoization
    public static int MinCostMazePath_memo(int[][] maze, int x, int y, int[][] dp){
        if(x == maze.length-1 && y == maze[0].length-1){
            return dp[x][y] = maze[x][y];
        }
        
        if(dp[x][y] != 0){
            return dp[x][y];
        }
        
        int minCost = (int)1e9;
        //right call 
        if(y+1 < maze[0].length){
            minCost = Math.min(minCost, MinCostMazePath(maze, x, y+1));
        }
        
        //left call 
        if(x+1 < maze.length){
            minCost = Math.min(minCost, MinCostMazePath(maze, x+1, y));
        }
        return dp[x][y] = minCost + maze[x][y];
    }
    
    //Approach 3: Using Tabulation
    public static int MinCostMazePath_tab(int[][] maze, int[][] dp){
        for(int x = maze.length-1; x>=0; x--){ //Conversion of recursion to iteration
            for(int y=maze[0].length-1; y>=0; y--){
                
                if(x == maze.length-1 && y == maze[0].length-1){
                    dp[x][y] = maze[x][y];
                }
                
                else if(x == maze.length-1){ //last row, here we can include the value of next col only and we 
                    dp[x][y] = maze[x][y] + dp[x][y+1]; //are traversing from bottom-right to top-left, so next col 
                                                        // is already solved and we are using that value here.
                }
                
                else if(y == maze[0].length-1){ //last col, here we can include the value of next row only
                    dp[x][y] = maze[x][y] + dp[x+1][y];
                }
                
                else{//middle section
                    dp[x][y] = maze[x][y] + Math.min(dp[x][y+1], dp[x+1][y]);
                }
            }
        }
        return dp[0][0]; //and in the end we report we return top-left value which has minCost value in it.
    }
    
/***************************************************************************************/
//GOLDMINE PROBLEM

    //Approach 1: using recursion
    public static int goldMine_rec(int[][] mine){
        int maxProfit = 0;
        
        for(int i=0; i<mine.length; i++){
            maxProfit = Math.max(maxProfit, goldmineHelper(mine, i, 0));
        }
        
        return maxProfit;
    }
    
    public static int goldmineHelper(int[][] mine, int x, int y){
        if(y == mine[0].length-1){
            return mine[x][y];
        }
        
        int cost = 0;
        //top-right
        if(x-1 >= 0){
            cost = Math.max(cost, goldmineHelper(mine, x-1, y+1));
        }
        
        //right
        cost = Math.max(cost, goldmineHelper(mine, x, y+1));
        
        if(x+1 < mine.length){
            cost = Math.max(cost, goldmineHelper(mine, x+1, y+1));
        }
        
        return cost + mine[x][y];
    }
    
    //Approach 2: using memoization
    public static int goldMine_memo(int[][] mine, int[][] dp){
        int maxProfit = 0;
        
        for(int i=0; i<mine.length; i++){
            maxProfit = Math.max(maxProfit, goldmine_memo_Helper(mine, i, 0, dp));
        }
        
        return maxProfit;
    }
    
    public static int goldmine_memo_Helper(int[][] mine, int x, int y, int[][] dp){
        if(y == mine[0].length-1){
            return dp[x][y] = mine[x][y];
        }
        
        if(dp[x][y] != 0){
            return dp[x][y];
        }
        
        int cost = 0;
        //top-right
        if(x-1 >= 0){
            cost = Math.max(cost, goldmine_memo_Helper(mine, x-1, y+1, dp));
        }
        
        //right
        cost = Math.max(cost, goldmine_memo_Helper(mine, x, y+1, dp));
        
        if(x+1 < mine.length){
            cost = Math.max(cost, goldmine_memo_Helper(mine, x+1, y+1, dp));
        }
        
        return dp[x][y] = cost + mine[x][y];
    }
    
    //Approach 3: using Tabulation
    public static int goldMine_tab1(int[][] mine, int[][] dp){
        for(int y=mine[0].length-1; y>=0; y--){
            for(int x=0; x<mine.length; x++){
                if(y == mine[0].length-1){
                    dp[x][y] = mine[x][y];
                }
                
                else if(x == 0){
                    dp[x][y] = Math.max(dp[x][y+1], dp[x+1][y+1]) + mine[x][y];
                }
                
                else if(x == mine.length-1){
                    dp[x][y] = Math.max(dp[x][y+1], dp[x-1][y+1]) + mine[x][y];
                }
                
                else{
                    dp[x][y] = Math.max(dp[x][y+1], Math.max(dp[x+1][y+1], dp[x-1][y+1])) + mine[x][y];
                }
                res = Math.max(res, dp[x][y]);
            }
        }
        return res;
    }
    
    //Approach 4: Check approach 4 also, just combination of memo and tab
    
    
/***************************************************************************************
===================
TARGET SUM SUBSETS:
===================*/

    //Approach 1: Using recursion
    public static boolean targetSumSubset_rec(int[] arr, int indx, int target){
        if(target == 0){
            return true;
        }
        if(indx == arr.length){
            return false;
        }
        
        boolean res = false;
        if(target - arr[indx] >= 0){ //Making valid calls only
        res = targetSumSubset_rec(arr, indx+1, target - arr[indx]); //For including the index value i.e. yes call
        }
        res = res || targetSumSubset_rec(arr, indx+1, target); //no call i.e. not including index value in target
        
        return res;
    }
    
    //Approach 2: Using memoization
    
    public static boolean targetSumSubset_memo(int[] arr, int indx, int target, Boolean[][] dp){
        //Here 2D dp is there because change in indx and target is happening so we include the one which are changing
        //Boolean[][] dp = new Boolean[arr.length+1][target+1]; we declare it in main function and pass as argument
        if(target == 0){
            return dp[indx][target] = true;
        }
        if(indx == arr.length){
            return dp[indx][target] = false;
        }
        
        if(dp[indx][target] != null){
            return dp[indx][target];
        }
        
        boolean res = false;
        if(target - arr[indx] >= 0){
            //yes call
            res = targetSumSubset_memo(arr, indx+1, target - arr[indx], dp);
        }
        //no call if invalid
        res = res || targetSumSubset_memo(arr, indx+1, target, dp); 
        
        return dp[indx][target] = res;
    }
    
    //Approach 3: Using Tabulation
    public static boolean targetSumSubset_tab1(int[] arr, int target){
        Boolean[][] dp = new Boolean[arr.length+1][target+1];
        
        for(int indx=0; indx<=arr.length; indx++){ //row has arr elements
            for(int targ=0; targ<=target; targ++){ //col has all values till target
                if(targ == 0){ //It means top row is to be marked as true
                    dp[indx][targ] = true;
                }
                else if(indx == 0){ //Other cells in first row are to be marked as false
                    dp[indx][targ] = false;
                }
                else{
                    if(arr[indx-1] <= targ){ //this is valid case part where we make both yes and no call
                                            // and if any value is true from yes and no call then we put true in dp.
                        dp[indx][targ] = dp[indx-1][targ] || dp[indx-1][targ - arr[indx-1]];
                                         //no call part      //yes call part
                        
                    }
                    else{ //this is for no call only, so whatever was result from no call, we put it in dp
                        dp[indx][targ] = dp[indx-1][targ];
                    }
                }
            }
        }
        return dp[arr.length][target];
    }
    
    //Approach 4: Direction conversion from memoization:
    
    public static boolean targetSumSubset_tab2(int[] arr, int target){
        //Here 2D dp is there because change in indx and target is happening so we include the one which are changing
        Boolean[][] dp = new Boolean[arr.length+1][target+1]; 
        
        for(int indx=arr.length; indx>=0; indx--){
            for(int targ=0; targ<=target; targ++){
                if(targ == 0){
                    dp[indx][targ] = true;
                    continue;
                }
                if(indx == arr.length){
                    dp[indx][targ] = false;
                    continue;
                }
                
                boolean res = false;
                if(targ - arr[indx] >= 0){
                    //yes call
                    res = dp[indx+1][targ - arr[indx]];
                }
                //no call if invalid
                res = res || dp[indx+1][targ] ;
                dp[indx][targ] = res;
            }
        }
        return dp[0][target];
    }
    
/***************************************************************************************
========================
COIN CHANGE PERMUTATION: Infinite supply of coins is allowed
========================*/

    //Approach 1: Using recursion
    public static int coinChangePermutation_rec(int[] arr, int target){
        if(target == 0){
            return 1;
        }
        
        int count = 0;
        for(int i=0; i<arr.length; i++){
            if(target - arr[i] >= 0){
                count += coinChangePermutation_rec(arr, target - arr[indx]);
            }
        }
        return count;
    }
    
    //Approach 2: Using memoization
    public static int coinChangePermutation_memo(int[] arr, int target, int[] dp){
        if(target == 0){
            return dp[target] = 1;
        }
        
        if(dp[target] != 0){
            return dp[target];
        }
        
        int count = 0;
        for(int i=0; i<arr.length; i++){
            if(target - arr[i] >= 0){
                count += coinChangePermutation_memo(arr, target - arr[i], dp);
            }
        }
        return dp[target] = count;
    }

    //Approach 3: Using Tabulation --> conversion from memoization to tabulation
    public static int coinChangePermutation_tab1(int[] arr, int target, int[] dp){
        dp[0] = 1;
        
        for(int targ=1; targ<= target; targ++){
            for(int i=0; i<arr.length; i++){
                if(targ - arr[i] >= 0){
                    dp[targ] += dp[targ - arr[i]];
                }
            }
        }
        return dp[target];
    }
    
/***************************************************************************************
========================
COIN CHANGE COMBINATION: 
========================*/
//Infinite supply of coins is allowed, repetition of combination is not allowed(2,3,3) & (3,2,3) are considered same
    
    //Approach 1: Using recursion
    public static int coinChangeCombination_rec(int[] coins, int indx, int target){
        if(target == 0){
            return 1;
        }
        
        int count = 0;
        if(indx < coins.length){
            if(target - coins[indx] >= 0){
                count += coinChangeCombination_rec(coins, indx, target-coins[indx]);
            }
            count += coinChangeCombination_rec(coins, indx+1, target);
        }
        
        return count;
    }
    
    //Approach 2: Using memoization
    public static int coinChangeCombination_memo(int[] coins, int indx, int target, int[][] dp){
        if(target == 0){
            return dp[indx][target] = 1;
        }
        
        if(dp[indx][target] != 0){
            return dp[indx][target];
        }
        
        int count = 0;
        if(indx < coins.length){
            if(target - coins[indx] >= 0){
                count += coinChangeCombination_memo(coins, indx, target-coins[indx], dp);
            }
            count += coinChangeCombination_memo(coins, indx+1, target, dp);
        }
        
        return dp[indx][target] = count;
    }
    
    //Approach 3: Using tabulation: conversion from memoization to tabulation.
    public static int coinChangeCombination_tab1(int[] coins, int target, Integer[][] dp){
        for(int indx=coins.length; indx>=0; indx--){
            for(int targ=0; targ<=target; targ++){
                
                if(targ == 0){  //first col
                    dp[indx][targ] = 1;
                    continue;
                }
                
                if(indx == coins.length){ //last row
                    dp[indx][targ] = 0;
                    continue;
                }
                
                int count = 0;
                if(targ - coins[indx] >= 0){
                    count += dp[indx][targ-coins[indx]];
                }
                count += dp[indx+1][targ];
                dp[indx][targ] = count;
            }
        }
        
        // for(int i=0; i<dp.length; i++){
        //     for(int j=0; j<dp[0].length; j++){
        //         System.out.print(dp[i][j] + " ");
        //     }
        //     System.out.println();
        // }
        return dp[0][target];
    }

    
/***************************************************************************************
=====================
0/1 KNAPSACK PROBLEM:
=====================*/

    //Approach 1: Using recursion
    public static int knapsack01_rec(int[] arr, int[] wt, int indx, int cap){
        if(indx == -1){
            return 0;
        }
        
        int rres = 0;
        if(cap >= wt[indx]){
        //yes call and no call
        rres = arr[indx] + knapsack01_rec(arr, wt, indx-1, cap-wt[indx]);
        }
        int v2 = knapsack01_rec(arr, wt, indx-1, cap);
         
        return Math.max(rres, v2);
    }
    
    //Approach 2: Using memoization
    public static int knapsack01_memo(int[] arr, int[] wt, int cap, int indx, int[][] dp){
        if(indx == -1){
            return dp[indx+1][cap] = 0;
        }
        
        if(dp[indx+1][cap] != 0){
            return dp[indx+1][cap];
        }

        int rres = 0;
        if(cap >= wt[indx]){
        //yes call and no call
        rres = arr[indx] + knapsack01_memo(arr, wt, indx-1, cap-wt[indx]);
        }
        int v2 = knapsack01_memo(arr, wt, indx-1, cap);
        dp[indx+1][cap] = Math.max(rres, v2);
        return dp[indx+1][cap];
        
    }
    
    //Approach 3: Using Tabulation
    public static int knapsack01_tab(int[] arr, int[] wt, int Cap, int[][] dp){
        //first row and column should be zero.
        for(int indx=1; indx<=arr.length; indx++){
            for(int cap=1; cap<=Cap; cap++){
                if(cap >= wt[indx-1]){
                    int v1 = dp[indx-1][cap - wt[indx-1]] + arr[indx-1];
                    int v2 = dp[indx-1][cap];
                    dp[indx][cap] = Math.max(v1, v2);
                }
                else{
                    dp[indx][cap] = dp[indx-1][cap];
                }
            }
        }
        return dp[arr.length][Cap];
    }
/***************************************************************************************
===================
UNBOUNDED KNAPSACK:
===================*/

    public static int unboundedKnapsack_rec(int[] arr, int[] wt, int cap, int indx){
        if(cap == 0 || indx == -1){
            return 0;
        }
        
        int v1 = 0;
        if(cap >= wt[indx]){
            v1 = unboundedKnapsack_rec(arr, wt, cap-wt[indx], indx) + arr[indx];
        }    
        int v2 = unboundedKnapsack_rec(arr, wt, cap, indx-1);
        
        return Math.max(v1, v2);
    }
    
    public static int unboundedKnapsack_memo(int[] arr, int[] wt, int cap, int indx, int[][] dp){
        if(cap == 0 || indx == -1){
            return dp[indx+1][cap] = 0;
        }
        
        if(dp[indx+1][cap] != 0){
            return dp[indx+1][cap];
        }
        
        int v1 = 0;
        if(cap >= wt[indx]){
            v1 = unboundedKnapsack_memo(arr, wt, cap-wt[indx], indx, dp) + arr[indx];
        }    
        int v2 = unboundedKnapsack_memo(arr, wt, cap, indx-1, dp);
        
        return dp[indx+1][cap] = Math.max(v1, v2);
    }
    
    //Approach 3: Using Tabulation (2-D dp)
    public static int unboundedKnapsack_tab(int[] arr, int[] wt, int Cap, int[][] dp){
        for(int indx=1; indx<=arr.length; indx++){
            for(int cap=1; cap<=Cap; cap++){
                
                if(cap >= wt[indx-1]){
                    //yes call
                    dp[indx][cap] = Math.max(dp[indx][cap-wt[indx-1]] + arr[indx-1], dp[indx-1][cap]);
                }
                //no call
                else{
                    dp[indx][cap] = dp[indx-1][cap];
                }
            }
        }
        return dp[arr.length][Cap];
    }
    
    //Approach 4: Using Tabulation (1-D dp)
    public static int unboundedKnapsack_tab2(int[] wts, int[] vals, int cap) {
        int[] dp = new int[cap + 1];

        // outer loop for box
        // inner loop for cap
        for(int i = 0; i < wts.length; i++) {
            for(int c = wts[i]; c <= cap; c++) {
                // no call
                int v1 = dp[c];
                // yes call
                int v2 = dp[c - wts[i]] + vals[i];

                dp[c] = Math.max(v1, v2);
            }
        }
        return dp[cap];
    }

/***************************************************************************************
====================
FRACTIONAL KNAPSACK:
====================*/

    public static class FPair implements Comparable<FPair> {
        int val;
        int wt;
        Double frac;
        
        public FPair(int val, int wt){
            this.val = val;
            this.wt = wt;
            this.frac = val * 1.0 / wt;
        }
        
        public int compareTo(FPair other){
            
            //return this.frac - other.frac; This is not valid becoz we are comparing on fractional values here 
                                            // and it would have returned as equal for fractional answers
            if(this.frac > other.frac){
                return 1;
            }
            else if(this.frac < other.frac){
                return -1;
            }
            else{
                return 0;
            }
        }
    }
    
    public static void FractionalKnapsack(int[] arr, int[] wt, int cap){
        PriorityQueue<FPair> pq = new PriorityQueue<>(Collections.reverseOrder());
        //Add all elements of arr[] and wt[] to PriorityQueue
        for(int i=0; i<arr.length; i++){
            pq.add(new FPair(arr[i], wt[i]));
        }
        
        double mxVal = 0;
        while(pq.size() > 0 && cap>0){
            int rem = pq.remove();
            
            if(cap >= rem.wt){
                mxVal += rem.val;
                cap = cap - rem.wt;
            }
            else{
                mxVal += cap * rem.frac;
                cap = 0;
            }
        }
        System.out.println(mxVal);
    }
    
/***************************************************************************************
======================
Count Binary Strings: Print the number of binary strings of length n with no consecutive 0's.
======================*/

    //Approach 1: Using recursion
    public static int countBinaryStrings_rec(int n, int lastAddition, String asf){
        if(n == 0){
            //System.out.println(asf);
            return 1;
        }
        
        int count = 0;
        if(lastAddition == 1){
            //call for both '0' and '1' is possible
            count += countBinaryStrings_rec(n-1, 0, asf+'0'); //Call for 0 here, update lastAddition to '0'
        }
        count += countBinaryStrings_rec(n-1, 1, asf+'1'); //call for 1 here, update lastAddition to '1'
        
        return count;
    }
    
    //Approach 2: Using memoization
    public static int countBinaryStrings_memo(int n, int lastAddition, String asf, int[][] dp){
        if(n == 0){
            //System.out.println(asf);
            return dp[n][lastAddition] = 1;
        }
        
        if(dp[n][lastAddition] != 0){
            return dp[n][lastAddition];
        }
        
        int count = 0;
        if(lastAddition == 1){
            //call for both '0' and '1' is possible
            count += countBinaryStrings_memo(n-1, 0, asf+'0', dp); //Call for 0 here, update lastAddition to '0'
        }
        count += countBinaryStrings_memo(n-1, 1, asf+'1', dp); //call for 1 here, update lastAddition to '1'
        
        return dp[n][lastAddition] = count;
    }
    
    //Approach 3: Using Tabulation
    public static int countBinaryStrings_tab(int N){
        int[][] dp = new int[N+1][2];
        dp[0][0] = 1;
        dp[0][1] = 1;
        
        for(int n=1; n<=N; n++){
            dp[n][0] = dp[n-1][1];
            dp[n][1] = dp[n-1][0] + dp[n-1][1];
        }
        return dp[N][1];
    }
    
    //Approach 4: Using optimisation
    public static int countBinaryString_optimise(int n) {
        int zero = 1;
        int one = 1;

        for(int i = 2; i <= n; i++) {
            int n_zero = one;
            int n_one = one + zero;

            zero = n_zero;
            one = n_one;
        }
        return one + zero;
    }

/***************************************************************************************
================
COUNT ENCODINGS:
================*/

    public static int countEncoding_rec(String str, int indx){
        if(str.length() == indx){
            return 1;
        }
        
        if(str.charAt(indx) == '0'){
            return 0;
        }
        
        int count = 0;
        int n1 = str.charAt(indx) - '0';
        count += countEncoding_rec(str, indx+1);
        
        if(indx+1 < str.length()){
            int n = str.charAt(indx+1);
            int n2 = n1*10 + n1;
            if(n2 <= 26){
                count += countEncoding_rec(str, indx+2);
            }
        }
        return count;
    }
    
    //Approach 2: Using memoization
    public static int countEncoding_memo(String str, int indx, int[] dp){
        if(str.length() == indx){
            return dp[indx] = 1;
        }
        
        if(str.charAt(indx) == '0'){
            return 0;
        }
        
        if(dp[indx] != 0){
            return dp[indx];
        }
        
        int count = 0;
        int n1 = str.charAt(indx) - '0';
        count += countEncoding_memo(str, indx+1, dp);
        
        if(indx+1 < str.length()){
            int n = str.charAt(indx+1);
            int n2 = n1*10 + n1;
            if(n2 <= 26){
                count += countEncoding_memo(str, indx+2, dp);
            }
        }
        return dp[indx] = count;
    }
/***************************************************************************************
==========================
Count A+b+c+ Subsequences: For abcabc -> there are 7 subsequences. abc, abc, abbc, aabc, abcc, abc, abc.
==========================*/

    public static int ABCSubsequence(String str){
        int a_count = 0;
        int b_count = 0;
        int c_count = 0;
        
        for(int i=0; i<str.length(); i++){
            char ch = str.charAt(i);
            if(ch == 'a'){
                a_count = 2*a_count + 1; //(2*a_count means yes call of 'a' and no call of 'a' +1 for self
                                        // count of a where new sequence started from this new 'a'
            }
            
            else if(ch == 'b'){
                b_count = a_count + 2*b_count;
            }
            
            else if(ch == 'c'){
                c_count = b_count + 2*c_count;
            }
        }
        return c_count;
    }

/***************************************************************************************
===================================
Maximum Sum Non Adjacent Elements:
===================================*/

    public static int MaxSumNonAdjacent_rec(int[] arr, int indx, boolean lastCall){
        if(indx == -1){
            return 0;
        }
        
        int res = 0;
        if(lastCall == false){
            // res += MaxSumNonAdjacent(arr, indx-1, true); //yes call
            // res += MaxSumNonAdjacent(arr, indx-1, false); //no call
            res += Math.max(MaxSumNonAdjacent_rec(arr, indx-1, true) + arr[indx], MaxSumNonAdjacent_rec(arr, indx-1, false));
        }
        else{
            res += MaxSumNonAdjacent_rec(arr, indx-1, false);
        }
        return res;
    }
    
    
    public static int MaxSumNonAdjacent_memo(int[] arr, int indx, int lastCall, int[][] dp){
        if(indx == -1){
            return dp[indx+1][lastCall] = 0;
        }
        
        if(dp[indx+1][lastCall] != 0){
            return dp[indx+1][lastCall];
        } 
        
        int res = 0;
        if(lastCall == 0){
            // res += MaxSumNonAdjacent(arr, indx-1, true); //yes call
            // res += MaxSumNonAdjacent(arr, indx-1, false); //no call
            res += Math.max(MaxSumNonAdjacent_memo(arr, indx-1, 1, dp) + arr[indx], MaxSumNonAdjacent_memo(arr, indx-1, 0, dp));
        }
        else{
            res += MaxSumNonAdjacent_memo(arr, indx-1, 0, dp);
        }
        return dp[indx+1][lastCall] = res;
    }


    public static int MaxSumNonAdjacent_tab_optimised(int[] arr){
        int include = 0;
        int exclude = 0;
        
        for(int i=0; i<arr.length; i++){
            int n_include = exclude + arr[i];
            int n_exclude = Math.max(include, exclude);
            
            include = n_include;
            exclude = n_exclude;
        }
        return Math.max(include, exclude);
    }
    
/***************************************************************************************/


/***************************************************************************************/


/***************************************************************************************/


/***************************************************************************************/
    
	public static void main(String[] args) {
	    int n = 5;
		int[] dp = new int[n+1];
		System.out.System.out.println(fib_dp(n, dp));
	}
}




