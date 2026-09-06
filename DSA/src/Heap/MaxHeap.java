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
    public int remove(){
        if(index==0){
            throw new RuntimeException("Heap is empty");
        }

        int removed = arr[0];
        arr[0]=arr[index-1];
        index--;
        heapifyDown(0);

        return removed;
    }

    private void heapifyDown(int i) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        int largest = i;

        if (left < index && arr[left] > arr[largest]) {
            largest = left;
        }
        if (right < index && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(largest, i);
            heapifyDown(largest);
        }
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