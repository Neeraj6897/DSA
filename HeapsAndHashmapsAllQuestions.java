import java.util.*;

public class Main
{

//PRINT K LARGEST ELEMENTS USING HEAP i.e. PRIORITY QUEUE    

    public static void PrintKLargestElements(int[] arr, int k){
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int n = arr.length;
        
        //First add q elements to min heap
        for(int i=0; i<k; i++){
            pq.add(arr[i]);
        }
        
        //Now if next element in the array is larger than peek of PriorityQueue(which is minimum of in PriorityQueue) then we will remove peek and add this array
        //element to PriorityQueue.
        for(int i = k; i<n; i++){
            if(pq.peek() <= arr[i]){
                pq.remove();
                pq.add(arr[i]);
            }
        }
        
        for(int i=0; i<k; i++){
            System.out.println(pq.remove());
        }
    }

/*********************************************************************************************************/

/*SORT AN ARRAY WHICH IS K SORTED: It means its elements are at most k distance away from their sorted position.

I/P : {30, 10, 40, 20, 50, 70, 80, 60, 90}
    Time complexity: O(nlogk), Space = O(k)
    Approach: We use priority queue for this. */
    
    public static void SortKSortedArray(int[] arr, int k){
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int n = arr.length;
        //First add k elements to the PriorityQueue
        for(int i=0; i<=k; i++){
            pq.add(arr[i]);
        }
        
        //Then from k to n, keep on removing min and adding next. In this way, we will keep on printing elements in increasing order.
        for(int i=k+1; i<n; i++){
            System.out.println(pq.remove());
            pq.add(arr[i]);
        }
      
        //Print remaining elements of PriorityQueue.
        while(pq.size()>0){
          System.out.println(pq.remove());
        }
    }
    
/*********************************************************************************************************/


/*********************************************************************************************************/


/*********************************************************************************************************/
	public static void main(String[] args) {
		int[] arr = {13, 12, 62, 22, 15, 37, 98, 67, 31, 84, 99};
		PrintKLargestElements(arr, 4);
	}
}

