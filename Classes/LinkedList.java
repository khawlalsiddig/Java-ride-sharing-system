class Node<T>{
    T data;
    Node next;

    public Node() {
        data = null;
        next = null;
    }
    public Node(T data, Node next) {
        this.data = data;
        this.next = next;
    }
}

public class LinkedList<T> {
    Node head;
    int size;

    public LinkedList() {
        head = null;
        size = 0;
    }

    public int size() {
        return size;
    }





}