class Node<T>{
    T data;
    Node<T> next;

    public Node() {
        data = null;
        next = null;
    }
    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }
}

public class LinkedList<T> {
    Node<T> head;
    int size;

    public LinkedList() {
        head = null;
        size = 0;
    }

    public int size() {
        return size;
    }

public Node<T> getHead() {
        return head;
    }



}