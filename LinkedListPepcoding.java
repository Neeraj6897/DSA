import java.util.*;

public class Main {
    
//STRUCTURE OF NODE OF A LINKEDLIST 
  public static class Node {
    int data;
    Node next;
  }

/**********************************************************************************************************/

//LINKEDLIST CLASS:

  public static class LinkedList {
    Node head;
    Node tail;
    int size;

/**********************************************************************************************************/

//ADD AT LAST:

    void addLast(int val) {
      Node temp = new Node();
      temp.data = val;
      temp.next = null;

      if (size == 0) {
        head = tail = temp;
      } else {
        tail.next = temp;
        tail = temp;
      }
      //To increase size for every addition.
      size++;
    }

/**********************************************************************************************************/
//SIZE OF LL:

    public int size() {
      return size;
    }

/**********************************************************************************************************/
//DISPLAY A LL:

    public void display() {
      for (Node temp = head; temp != null; temp = temp.next) {
        System.out.print(temp.data + " ");
      }
      System.out.println();
    }

/**********************************************************************************************************/
//REMOVE HEAD OF LL:

    public void removeFirst() {
      if (size == 0) {
        System.out.println("List is empty");
      } else if (size == 1) {
        head = tail = null;
        size = 0;
      } else {
        head = head.next;
        size--;
      }
    }

/**********************************************************************************************************/
//GET HEAD

    public int getFirst() {
      if (size == 0) {
        System.out.println("List is empty");
        return -1;
      } else {
        return head.data;
      }
    }

/**********************************************************************************************************/
//GET TAIL

    public int getLast() {
      if (size == 0) {
        System.out.println("List is empty");
        return -1;
      } else {
        return tail.data;
      }
    }

/**********************************************************************************************************/
//GET ELEMENT AT A GIVEN INDEX:

    public int getAt(int idx) {
      if (size == 0) {
        System.out.println("List is empty");
        return -1;
      } else if (idx < 0 || idx >= size) {
        System.out.println("Invalid arguments");
        return -1;
      } else {
        Node temp = head;
        for (int i = 0; i < idx; i++) {
          temp = temp.next;
        }
        return temp.data;
      }
    }

/**********************************************************************************************************/
//ADD AT FIRST:

    public void addFirst(int val) {
      Node temp = new Node();
      temp.data = val;
      temp.next = head;
      head = temp;

      if (size == 0) {
        tail = temp;
      }

      size++;
    }

/**********************************************************************************************************/
//ADD AT GIVE INDEX:

    public void addAt(int idx, int val) {
      if (idx < 0 || idx > size) {
        System.out.println("Invalid arguments");
      } else if (idx == 0) {
        addFirst(val);
      } else if (idx == size) {
        addLast(val);
      } else {
        Node node = new Node();     //Create a new node. 
        node.data = val;            //Assign value to it.

        Node temp = head;
        for (int i = 0; i < idx - 1; i++) {     //Iterate to the given index.
          temp = temp.next;
        }
        node.next = temp.next;      //Add between by setting links properly.

        temp.next = node;
        size++;
      }
    }

/**********************************************************************************************************/
//REMOVE TAIL:

    public void removeLast() {
      if (size == 0) {
        System.out.println("List is empty");
      } else if (size == 1) {
        head = tail = null;
        size = 0;
      } else {
        Node temp = head;
        for (int i = 0; i < size - 2; i++) {    //Iterate till second last element and then update tail
          temp = temp.next;
        }

        tail = temp;
        tail.next = null;
        size--;
      }
    }

/**********************************************************************************************************/
//REMOVE AT GIVEN LOCATION:

    public void removeAt(int idx) {
      if (idx < 0 || idx >= size) {
        System.out.println("Invalid arguments");
      } else if (idx == 0) {
        removeFirst();
      } else if (idx == size - 1) {
        removeLast();
      } else {
        Node temp = head;
        for (int i = 0; i < idx - 1; i++) {
          temp = temp.next;
        }

        temp.next = temp.next.next;
        size--;
      }
    }

/**********************************************************************************************************/

//REVERSE A LINKED LIST WITHOUT CHANGING THE REFERENCES i.e. SWAP VALUES:

    //Helper function to get node at any location.
    private Node getNodeAt(int idx) {
      Node temp = head;
      for (int i = 0; i < idx; i++) {
        temp = temp.next;
      }
      return temp;
    }

    //Reverse values using swapping
    public void reverseDI() {
      int li = 0;
      int ri = size - 1;
      while(li < ri){
        Node left = getNodeAt(li);
        Node right = getNodeAt(ri);

        int temp = left.data;
        left.data = right.data;
        right.data = temp;

        li++;
        ri--;
      }
    }

/**********************************************************************************************************/ 

//REVERSE A LINKEDLIST: REVERSE REFERENCES ALSO

    //Approach: We reverse the links by using three nodes = prev, cur, next.
    public void reversePI(){
      if(size <= 1){
        return;
      }

      Node prev = null;
      Node curr = head;
      while(curr != null){
        Node next = curr.next;  //assing next node to next.
        
        curr.next = prev;   //reverse the link by setting next of curr to pre
        prev = curr;    //update pre
        curr = next;    //update curr
      }

      Node temp = head; //In the end swap head and tail also.
      head = tail;
      tail = temp;
    }
  
/**********************************************************************************************************/  

//FINDING KTH ELEMENT FROM LAST:
  
    //Approach: Use slow and fast pointer method
    public int kthFromLast(int k){
      Node slow = head;
      Node fast = head;
      //Update fast till k, now the difference between slow and fast is of k nodes. If we iterate fast till last, slow.data
      //will give us our required kth last element.
      for(int i=0; i<k; i++){
          fast = fast.next;
      }
      while(fast.next != null){
          slow = slow.next;
          fast = fast.next;
      }
      return slow.data;
    }

/**********************************************************************************************************/  

//FINDING MID OF A LINKED LIST:

    //Approach: Use slow and fast pointer method
    
    public int mid1(){
      Node slow = head;
      Node fast = head.next;
      while(fast != null && fast.next != null){
          slow = slow.next;
          fast = fast.next.next;
      }
      return slow.data;
    }
    
    public int mid2(){
      Node slow = head;
      Node fast = head;
      while(fast != null && fast.next != null){
          slow = slow.next;
          fast = fast.next.next;
      }
      return slow.data;
    }

/**********************************************************************************************************/ 

//MERGE TWO SORTED LINKED LISTS WITHOUT USING EXTRA SPACE:

    public static LinkedList mergeTwoSortedLists(LinkedList list1, LinkedList list2){
        Node head1 = list1.head;
        Node head2 = list2.head;
        
        Node temp1 = head1;
        Node temp2 = head2;
        
        //Create a dummy node: This creating dummy node is an important concept and is used in many problems. In the end we
        //return dummy.next which can be the head of any new LinkedList.
        Node dummy = new Node();
        Node temp = dummy;
        
        //Iterate it till none of the list is traversed completely.
        while(temp1 != null && temp2 != null){
            if(temp1.data < temp2.data){      //If temp1.data is larger then we will add smaller element to dummy node refrenced by temp.
                temp.next = temp1;      
                temp = temp.next;   //Update temp to next 
                temp1 = temp1.next;       //Update temp1 to next.
            }
            else if(temp1.data > temp2.data){ //Similar approach if temp2.data is smaller, then add this into temp
                temp.next = temp2;
                temp = temp.next;
                temp2 = temp2.next;
            }
        }
        
        if(temp1 == null){      //If temp1 is traversed completely then add remaining elements of temp2 to temp.
            temp.next = temp2;
        }
        
        else if(temp2 == null){ //Similarly for temp2
            temp.next = temp1  ;
        }
        
        //Now we create a new LinkedList and assign dummy.next to the head of this LinkedList. Now this will point to our
        //temp LinkedList which has merged data in sorted order.
        LinkedList res = new LinkedList();
        res.head = dummy.next;
        
        //Iterate below to find tail and size of temp list and add that to res.size and res.tail.
        int sz = 0;
        temp = dummy;
        Node tail = null;
        while(temp.next != null){
            sz++;
            temp = temp.next;
        }
        res.tail = temp;
        res.size = sz;
        
        return res;     //At last return res LinkedList as a sorted and merged LinkedList.
    }

/**********************************************************************************************************/    

//REMOVE DUPLICATES FROM A SORTED LINKEDLIST:      ***NOT A TESTED CODE***

    public void removeDuplicates() {
        Node temp = this.head;
        Node itr = temp.next;

        while(itr != null) {
            if(temp.data == itr.data) {
                itr = itr.next;
            } else {
                temp.next = itr;
                temp = temp.next;
                itr = itr.next;
            }
        }
    }
/**********************************************************************************************************/

//ARRANGE ODD ELEMENTS FIRST THEN EVEN ELEMENTS IN A LINKEDLIST:

    public static void oddEven(){
        //Create two dummy nodes and then set first for odd and second for even elements. In the end combine both in 
        //respective orders.
        Node oddEle = new Node();
        Node temp1 = oddEle;
        
        Node evenEle = new Node();
        Node temp2 = evenEle;
        
        Node itr = this.head;
        
        while(itr != null){
            if(itr.data % 2 == 0){     //Processing for even node
                temp2.next = itr;   
                temp2 = temp2.next;
            }
            else{               //Processing for odd node
                temp1.next = itr;
                temp1 = temp1.next;
            }
            itr = itr.next;     //Update itr to next node
        }
        
        //Now for odd -> even arrangement, add head of evenEle to tail of oddEle.
        temp1.next = evenEle.next;
        temp2.next = null;
        
        //for void return type, manage LinkedList like below:
        this.head = oddEle.head;
        this.tail = temp2.next == null ? temp2 : temp1;
    }

/**********************************************************************************************************/

//K-REVERSE A LINKEDLIST: The function is expected to tweak the list such that all groups of k elements in the list 
//get reversed and linked. If the last set has less than k elements, leave it as it is (don't reverse

    //Approach is to create a new LinkedList and add the elements to it in the required manner. 
    public void kReverse(int k) {
        LinkedList prev = null;

        while(this.size() > 0) {
            LinkedList curr = new LinkedList();
            if(this.size >= k) {
                // removeFirst from this, addfirst in curr. It means it is getting added in reverse order.
                for(int i = 0; i < k; i++) {
                    int data = this.getFirst();
                    this.removeFirst();
                    curr.addFirst(data);
                }
            } else {
                // removeFirst from this, addLast in curr. It means no reversing is there
                while(this.size() > 0) {
                    int data = this.getFirst();
                    this.removeFirst();
                    curr.addLast(data);
                }
            }

            if(prev == null) {
                // change the reference of prev and curr
                prev = curr;
            } else {
                prev.tail.next = curr.head;
                prev.tail = curr.tail;
                prev.size += curr.size;
            }
        }
        this.head = prev.head;
        this.tail = prev.tail;
        this.size = prev.size;
    }

/**********************************************************************************************************/

//IS A LINKEDLIST PALINDROME:

    //Approach: Use slow and fast pointer method to reach to the mid of the LinkedList. Then, reverse the right part of
    //LinkedList and then match for the values. If equal then palindrom else false.
    
    //Here we will use functions to find mid and reversing it.
    public Node getMid(Node node){
        Node slow = node;
        Node fast = node.next;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    
    //Reversing and returning the Node
    public Node reverseForPalindrom(Node node){
        Node pre = null;
        Node cur = node;
        while(cur != null){
            Node next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }
    
    //Main for checking Palindrom list:
    public boolean isPalindrom(){
        Node head1 = this.head;     //If function is static then we can'nt use this.head directly.
        Node mid = getMid(head1);       //Getting the mid
        
        Node head2 = mid.next;
        mid.next = null;        //Breaking the connection here with second half
        
        head2 = reverseForPalindrom(head2);     //Reversing the second half for comparison
        
        Node temp1 = head1;
        Node temp2 = head2;
        
        boolean res = true;
        while(temp1 != null && temp2 != null){
            if(temp1.data != temp2.data){
                res = false;
                break;
            }
            temp1 = temp1.next;
            temp2 = temp2.next;
        }
        
        //To maintain the original list, reverse back to this second half and attach to the first half.
        head2 = reverseForPalindrom(head2);
        mid.next = head2;   //Joining back here with the second half
        
        return res;
    }

/**********************************************************************************************************/

//FOLD A LINKEDLIST:    I/P: 1->2->3->4->5 and O/P: 1->5->2->4->3

    //Approach used here will be similar to isPalindrom. Get mid and then reverse second half. Then add first and second half
    //one by one. This will give us a folded list.
    public void fold(){
        Node head1 = this.head;
        Node mid = getMid(head1);   //Get mid
        
        Node head2 = mid.next;
        mid.next = null;    //Break connection to make two separate lists
        
        head2 = reverseForPalindrom(head2); //Reverse second part
        
        Node temp1 = head1;
        Node temp2 = head2;
        
        Node pre = head1;   //This is to keep track of tail, it can be achieved without this also.
        
        while(temp1 != null && temp2 != null){  //Iterate and add element wise
            Node n1 = temp1.next;
            Node n2 = temp2.next;
            
            t1.next = t2;
            t1 = n1;
            
            pre = t1 == null ? pre : t1;    //If null found, then don't update pre
            
            t2.next = n1;
            t2 = n2;
            
            pre = t2 == null ? pre : t2;
        }
        
        this.head = head1;
        this.tail = pre;
    }

/**********************************************************************************************************/

//ADD TWO LINKEDLIST:

    public static LinkedList addTwoLists(LinkedList one, LinkedList two) {
      Node head1 = one.head;
      Node head2 = two.head;
      
      head1 = reversePointer(head1);    //Start processing from reverse.
      head2 = reversePointer(head2);
      
      Node i = head1;
      Node j = head2;
      
      int carry = 0;
      LinkedList res = new LinkedList();
      
      while(i != null || j != null || carry != 0){  //Important condition, keep carry also in condition
        int ival = i == null ? 0 : i.data;  //If i is null then value is 0 else i.data
        int jval = j == null ? 0 : j.data;  //If j is null then value is 0 else j.data
        
        i = i == null ? null : i.next;  //If i==null then don't update i else update i to i.next
        j = j == null ? null : j.next;  //If j==null then don't update j else update j to j.next
        
        int sum = ival + jval + carry;
        int val = sum%10;
        carry = sum/10;
        res.addFirst(val);
      }
      
      head1 = reversePointer(head1);    //Reverse back to original form.
      head2 = reversePointer(head2);
      
      return res;   //return res LinkedList which has sum.
    }

/*******************************************  ***************************************************************/
}
    public static void main(String[] args) {
		LinkedList list1 = new LinkedList();
		LinkedList list2 = new LinkedList();
        list1.addLast(10);
        list1.addLast(30);
        list1.addLast(30);
        list1.addLast(50);
        list1.addLast(50);
        list1.addLast(50);
        list1.addLast(50);

        // list1.display();
        // list2.display();
        // LinkedList l3 = new LinkedList();
        // l3 = mergeTwoSortedLists(list1, list2);
        list1.display();
        
	}
}





