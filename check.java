import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int r = sc.nextInt();
        int c = sc.nextInt();
        int[][] board = new int[n][n];
        PrintKnightsTour(board, r, c, 1);
    }
    
    public static int[] rowdir = {-2, -1, 1, 2, 2, 1, -1, -2};
    public static int[] coldir = {1, 2, 2, 1, -1, -2, -2, -1};

    // public static void printKnightsTour(int[][] chess, int sr, int sc, int count) {
    //     if(count == chess.length * chess.length){
    //         chess[sr][sc] = count;
    //         displayBoard(chess);
    //         chess[sr][sc] = 0;
    //         return;
    //     }
    //     chess[sr][sc] = count;
    //     for(int d=0; d<rowdir.length; d++){
    //         int rr = sr + rowdir[d];
    //         int cc = sc + coldir[d];
                
    //         if(rr>=0 && cc>=0 && rr<chess.length && cc<chess.length && chess[rr][cc]==0){
                
    //             printKnightsTour(chess, rr, cc, count+1);
                
    //         }
    //     }
    //     chess[sr][sc] = 0;
        
    // }
    
    public static void PrintKnightsTour(int[][] chess, int row, int col, int stepNmbr){
        if(stepNmbr == chess.length*chess.length){
            chess[row][col] = stepNmbr;
            displayBoard(chess);
            chess[row][col] = 0;
            return;
        }
        chess[row][col] = stepNmbr;
        for(int i=0; i<rowdir.length; i++){
            int rr = row + rowdir[i];
            int cc = col + coldir[i];
            
            if(rr>=0 && rr<chess.length && cc>=0 && cc<chess[0].length && chess[rr][cc] == 0){
                
                PrintKnightsTour(chess, rr, col, stepNmbr+1);
                
            } 
        }
        chess[row][col] = 0;
    }

    public static void displayBoard(int[][] chess){
        for(int i = 0; i < chess.length; i++){
            for(int j = 0; j < chess[0].length; j++){
                System.out.print(chess[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();
    }
}