public class MaxHeap {
    private int index;
    private int[]arr;
    public Heap(int size){
        this.arr = new int[size];
        this.index=0;
    }

    public void insert(int val){
        if(index==arr.length){
            throw new RuntimeException("Heap is full");
        }
        arr[index]=val;
        heapifyUp(index);
        index++;
    }

    private void heapifyUp(int index){
        int parentIndex = (index-1)/2;

        if(index>0 && arr[index]>arr[parentIndex]) {
            swap(parentIndex, index);
            heapifyUp(parentIndex);
        }
    }

    private void swap(int a,int b){
        int temp  = arr[a];
        arr[a]=arr[b];
        arr[b]=temp;
    }

    public void display() {
        for (int i = 0; i < index; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    public static void main(String[] args) {
        Heap heap = new Heap(10);

        heap.insert(10);
        heap.insert(20);
        heap.insert(5);
        heap.insert(30);
        heap.insert(15);

        heap.display();
    }
}