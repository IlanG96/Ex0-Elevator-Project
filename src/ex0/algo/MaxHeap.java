package ex0.algo;

// Java program to implement Max Heap

import java.util.ArrayList;

// Main class
public class MaxHeap {
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
    public int getBigChild(int node){
        if(getSize()<3){return getLeftChild(node);}
        if(getLeftChild(node)>getRightChild(node))return getLeftChild(node);
        return getRightChild(node);
    }


    /* Private Fields */
    private ArrayList<Integer> heap;
    private int size;
    /* Constructor */
    public MaxHeap() {
        this.heap = new ArrayList<Integer>();
        size=0;
    }
public int Getpos(int size){
       return heap.get(size-1);
}
    /* Private Methods */
    private int getParent(int node) {
        return this.heap.get(MaxHeap.parent(node));
    }

    private int getLeftChild(int node) {
        try {
            return this.heap.get(MaxHeap.leftChild(node));
        } catch (Exception e) {
            return (int)Double.NEGATIVE_INFINITY;
        }
    }

    private int getRightChild(int node) {
        try {
            return this.heap.get(MaxHeap.rightChild(node));
        } catch (Exception e) {
            return (int)Double.NEGATIVE_INFINITY;
        }
    }

    private void swap(int node1, int node2) {
        int tmp = this.heap.get(node1);
        this.heap.set(node1, this.heap.get(node2));
        this.heap.set(node2, tmp);
    }

    /* Public Methods */
    public void insert(int value) {
        int node = this.heap.size();
        size++;
        this.heap.add(value);

        while (this.getParent(node) < value) {
            int parent = MaxHeap.parent(node);
            this.swap(parent, node);
            node = parent;
        }
    }
public int peek(){
        return this.heap.get(0);
}
    public void deleteMax() {
        int out = this.heap.get(0);
        size--;
        int last = this.heap.remove(this.heap.size() - 1);
        int node = 0;
        if(size>0) {
            this.heap.set(node, last);

            int left = this.getLeftChild(node);
            int right = this.getRightChild(node);

            while (last < left || last < right) {
                int replacer;

                if (last < left && last < right)
                    replacer = (left > right)
                            ? MaxHeap.leftChild(node)
                            : MaxHeap.rightChild(node);
                else if (last < left)
                    replacer = MaxHeap.leftChild(node);
                else
                    replacer = MaxHeap.rightChild(node);

                this.swap(node, replacer);

                node = replacer;
                left = this.getLeftChild(node);
                right = this.getRightChild(node);
            }
        }
size=heap.size();

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