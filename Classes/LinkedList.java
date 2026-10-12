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
public boolean isEmpty() {
        return size == 0;
    }

    // Insert an element at the end
    public void add(T data) {
        add(size, data);
    }

    // Insert an element at a specific index
    public void add(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> n = new Node<>(data, null);

        // Insert at the beginning
        if (index == 0) {
            n.next = head;
            head = n;
        } else {
            Node<T> current = head;

            // Find the node before the insertion position
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }

            n.next = current.next;
            current.next = n;
        }

        size++;
    }

    // Get the element at a specific index
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.data;
    }

    // Remove and return the element at an index
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> node;

        if (index == 0) {
            node = head;
            head = head.next;
        } else {
            Node<T> current = head;

            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }

            node = current.next;
            current.next = node.next;
        }

        size--;
        return node.data;
    }

    // Remove all elements
    public void clear() {
        head = null;
        size = 0;
    }
}
