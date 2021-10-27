package ex0.algo;

import java.util.ArrayList;

public class MinHeap {
    /* Static Methods */
    private static int parent(int node) {
        return (node - 1) / 2;
    }

    private static int leftChild(int node) {
        return node * 2 + 1;
    }

    private static int rightChild(int node) {
        return node * 2 + 2;
    }

    /* Private Fields */
    private ArrayList<Integer> heap;
    private int size;

    /* Constructor */
    public MinHeap() {
        this.heap = new ArrayList<Integer>();
        size=0;
    }
    public int GetPos(int node){
        return heap.get(node-1);
    }
    /* Private Methods */
    private int getParent(int node) {
        return this.heap.get(MinHeap.parent(node));
    }

    private int getLeftChild(int node) {
        try {
            return this.heap.get(MinHeap.leftChild(node));
        } catch (Exception e) {
            return (int)Double.POSITIVE_INFINITY;
        }
    }

    private int getRightChild(int node) {
        try {
            return this.heap.get(MinHeap.rightChild(node));
        } catch (Exception e) {
            return (int)Double.POSITIVE_INFINITY;
        }
    }
    public int getBigChild(int node){
        if(getSize()<3){return getLeftChild(node);}
        if(getLeftChild(node)>getRightChild(node))return getLeftChild(node);
        return getRightChild(node);
    }

    private void swap(int node1, int node2) {
        int tmp = this.heap.get(node1);
        this.heap.set(node1, this.heap.get(node2));
        this.heap.set(node2, tmp);
    }

    /* Public Methods */
    public void insert(int value) {
        int node = this.heap.size();
        this.heap.add(value);
        size++;

        while (this.getParent(node) > value) {
            int parent = MinHeap.parent(node);
            this.swap(parent, node);
            node = parent;
        }
    }

    public void deleteMin() {
        int out = this.heap.get(0);
        size--;

        int last = this.heap.remove(this.heap.size() - 1);
        int node = 0;
        if (size>0) {
            this.heap.set(node, last);

            int left = this.getLeftChild(node);
            int right = this.getRightChild(node);

            while (last > left || last > right) {
                int replacer;

                if (last > left && last > right)
                    replacer = (left < right)
                            ? MinHeap.leftChild(node)
                            : MinHeap.rightChild(node);
                else if (last > left)
                    replacer = MinHeap.leftChild(node);
                else
                    replacer = MinHeap.rightChild(node);

                this.swap(node, replacer);

                node = replacer;
                left = this.getLeftChild(node);
                right = this.getRightChild(node);
            }
        }

    }
    public int peek(){
        return this.heap.get(0);
    }
    /*Custom get size */
    public int getSize(){return size;}
    /* Utility Methods */
    public void print() {
        int row = 1;
        int count = 0;

        for (int i: this.heap) {
            if (count == row) {
                System.out.println();
                count = 0;
                row++;
            }

            System.out.printf("%d ", i);
            count++;
        }
    }
}
