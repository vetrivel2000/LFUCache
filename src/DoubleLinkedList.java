public class DoubleLinkedList {
    int listSize;
    Node head,tail;
    public DoubleLinkedList()
    {
        this.listSize=0;
        this.head=new Node(0,0);
        this.tail=new Node(0,0);
        head.next=tail;
        tail.prev=head;
    }
    public void addNode(Node node)
    {
        Node nextNode=head.next;
        head.next=node;
        node.prev=head;
        node.next=nextNode;
        nextNode.prev=node;
        listSize++;
    }
    public void removeNode(Node node)
    {
        Node nextNode=node.next;
        Node prevNode=node.prev;
        prevNode.next=nextNode;
        nextNode.prev=prevNode;
        listSize--;
    }
}
