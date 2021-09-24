import java.util.HashMap;
import java.util.Map;

public class LFUCache {
    final int capacity;
    int currentSize;
    int minFreq;
    Map<Integer,Node> map;
    Map<Integer,DoubleLinkedList> frequencyMap;
    public LFUCache(int capacity) {
        this.capacity=capacity;
        this.currentSize=0;
        this.minFreq=0;
        this.map=new HashMap<>();
        this.frequencyMap=new HashMap<>();
    }

    public int get(int key) {
        Node node=map.get(key);
        if(node==null)
        {
            return -1;
        }
        updateNode(node);
        return node.value;
    }

    public void put(int key, int value) {
        if(capacity==0)
        {
            return;
        }

        if(map.containsKey(key))
        {
            Node node=map.get(key);
            node.value=value;
            updateNode(node);
        }
        else
        {
            currentSize++;
            if(currentSize>capacity)
            {
                DoubleLinkedList minFreqList=frequencyMap.get(minFreq);
                map.remove(minFreqList.tail.prev.key);
                minFreqList.removeNode(minFreqList.tail.prev);
                currentSize--;
            }
            minFreq=1;
            Node newNode=new Node(key,value);
            DoubleLinkedList curList=frequencyMap.get(1);
            if(curList==null)
            {
                curList=new DoubleLinkedList();
            }
            curList.addNode(newNode);
            map.put(key,newNode);
            frequencyMap.put(1,curList);
        }
    }
    public void updateNode(Node node)
    {
        int curFreq=node.frequency;
        DoubleLinkedList curList=frequencyMap.get(curFreq);
        curList.removeNode(node);

        if(curFreq==minFreq && curList.listSize==0)
        {
            minFreq++;
        }
        node.frequency++;
        DoubleLinkedList newList=frequencyMap.get(node.frequency);
        if(newList==null)
        {
            newList= new DoubleLinkedList();
        }
        newList.addNode(node);
        frequencyMap.put(node.frequency,newList);
    }
}
