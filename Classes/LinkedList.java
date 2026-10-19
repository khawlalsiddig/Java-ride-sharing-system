class Node<T>{
    T data;
    Node<T> next;

    public Node() {
        data = null;
        next = null;
    }
    public Node(T data) {
        this.data = data;
        this.next = null;
    }
}

public class LinkedList<T> {
    Node<T> head, current;

    public LinkedList() {
        head = null;
        current = null;
    }

    public void findFirst() {
        current = head;
    }
   
    public void findNext() {
        current = current.next;
    }
   
    public T retrieve() {
        return current.data;
    }
   
    public void update(T e) {
        current.data = e;
    }
   
   public void insert(T e){
       Node<T> n = new Node(e);
        if (head == null) {
            head = current = n;
      } else {
            n.next = current.next;
            current.next = n;
            current = current.next;
      }
    }
    public void remove( ){
        if(current == head) head  = head.next;
        else {

            Node<T> temp = head;
            while (temp.next != current)
                temp = temp.next;
            temp.next = current.next;
        }
      if (current.next != null) current = current.next;
      else current = head;
   }

   
   public boolean full() {
       return false;
}

public boolean empty() {
    return head == null;
}
    
   public boolean last(){
      return current.next == null;
   }



}
