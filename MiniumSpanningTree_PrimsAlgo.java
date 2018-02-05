import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Scanner;
 
class Nodes{
	int value;
	int keyvalue;
	Nodes parentnode;
 
	public Nodes(int value) {
		this.value = value;
		this.keyvalue = Integer.MAX_VALUE;
		this.parentnode = null;
	}
}
 
class MinHeap {
	int capacity;
	Nodes sample[];
	int position[];
	int size;
 
	public MinHeap(int capacity) {
		this.capacity = capacity;
		this.size = capacity;
		this.sample = new Nodes[capacity];
		this.position = new int[size];
	}
 
	void swap(Nodes[] sample, int i, int j) {
		Nodes temporary = sample[i];
		sample[i] = sample[j];
		sample[j] = temporary;
 
		int temporaryIndex = position[sample[i].value];
		position[sample[i].value] = position[sample[j].value];
		position[sample[j].value] = temporaryIndex;
	}
 
	int parentnode(int i) {
		return (i-1)/2;
	}
 
	int leftnode(int i) {
		return 2*i + 1;
	}
 
	int rightnode(int i) {
		return 2*i + 2;
	}
 
	Nodes extractMinimum() {
		if(size <= 0) {
			System.out.println("underflow");
			return null;
		}
		if(size == 1) {
			size--;
			return sample[0];
		}
 
		Nodes root = sample[0];
 
		sample[0] = sample[size-1];
 
		// change indices		
		position[sample[size-1].value] = 0;
		position[root.value] = size-1;
 
		size--;
		minimumHeapify(0);
 
		return root;
	}
 
	void minimumHeapify(int i) {
		int l = leftnode(i);
		int r = rightnode(i);
		int smallest = i;
 
		if(l < size && sample[l].keyvalue < sample[smallest].keyvalue)
			smallest = l;
		if(r < size && sample[r].keyvalue < sample[smallest].keyvalue)
			smallest = r;
		if(smallest != i) {
			swap(sample,i,smallest);
			minimumHeapify(smallest);
		}
	}
 
	void fixUpwards(int i) {	
		while(i != 0 && sample[i].keyvalue < sample[parentnode(i)].keyvalue) {
			swap(sample,i,parentnode(i));
			i = parentnode(i);
		}
	}
 
	void decreasekeyvalue(Nodes v, int newkeyvalue) {
		int index = position[v.value];
		if(sample[index].keyvalue < newkeyvalue) {
			System.out.println("New keyvalue is greater.");
			return;
		}
 
		sample[index].keyvalue = newkeyvalue;
		fixUpwards(index); 
	}
 
	boolean isInMinHeap(Nodes node) {
		if(position[node.value] < size)
			return true;
		return false;
	}
}
 
class MyLinkedList extends LinkedList<Nodes> {
}
 
class MysampleayList extends LinkedList<Integer> {
}
 
class Graph {
	int V;
	MyLinkedList[] lists;
	MysampleayList[] weights;
 
	public Graph(int V) {
		this.V = V;
		this.lists = new MyLinkedList[V];
		this.weights = new MysampleayList[V];
 
		for(int i=0;i<V;i++) {
			lists[i] = new MyLinkedList();
			weights[i] = new MysampleayList();
		}
 
		for(int i=0;i<V;i++) {
			Nodes node = new Nodes(i);
			lists[i].add(node);
		}
	}
 
	void addEdge(int u, int v, int weight) {
		lists[u].add(lists[v].getFirst());
		lists[v].add(lists[u].getFirst());
		weights[u].add(weight);
		weights[v].add(weight);
	}
 
	void printGraph() {
		for(MyLinkedList l : lists) {
			int i = 0;
			for(Nodes node : l) {
				if(i == 0)
					System.out.print((node.value + 1) + " : ");
				else
					System.out.print((node.value + 1) + "(" + weights[l.getFirst().value].get(i-1) + ") ");
				i++;
			}
			System.out.println();
		}
	}
 
	void printMinSpanTree() {
			
		for(MyLinkedList l : lists) {
			Nodes node = l.getFirst();
			int i = 1;
			if(node.parentnode != null)	{
				System.out.println((node.parentnode.value+1) + " " + (node.value+1));
				i++;
		}
		}
		/*
		for(MyLinkedList l : lists) {
			
			for(Node node : l) {
					System.out.print((node.parentnode.value + 1) + " " + (node.value+1));
			System.out.println();
		}*/
	}
 
	void primMinSpanTree() throws FileNotFoundException {
		//BufferedReader buffread = new BufferedReader(new FileWriter("output.txt"));
		MinHeap minheap = new MinHeap(V);
 
		lists[0].getFirst().keyvalue = 0;
		lists[0].getFirst().parentnode = null;
 
		for(int i=0;i<V;i++) {
			minheap.sample[i] = lists[i].getFirst();
			minheap.position[i] = i;
		}
 
		int answer = 0;
		while(minheap.size > 0) {
			Nodes u = minheap.extractMinimum();
			answer += u.keyvalue;
 
			int i=0;
			for(Nodes v : lists[u.value]) {
				if(i == 0) {
					i++;
					continue;
				}
 
				if(minheap.isInMinHeap(v) && weights[u.value].get(i-1) < v.keyvalue) {
					v.keyvalue = weights[u.value].get(i-1);
					v.parentnode = u;
					minheap.decreasekeyvalue(v,v.keyvalue);
				}
				i++;
			}
		}
		PrintStream output = new PrintStream(new FileOutputStream("output.txt"));
		System.out.println(answer);
		System.out.println();
		printMinSpanTree();
		//System.setOut(output);
		//output.close();
	}
}
 
class MST_Manisha_Tadikonda_50207628{
	static void printsample(int[] sample) {
		for(int i : sample)
			System.out.print(i + " ");
		System.out.println();
	}
 
	public static void main(String[] args) throws Exception //IOException, NullPointerException, Exception 
	{
		PrintStream output = new PrintStream(new FileOutputStream("output.txt"));
		System.setOut(output);
		/*
		Scanner sc = new Scanner(new FileReader("F:/Algos/test1"));
		//BufferedReader br= new BufferedReader((new FileReader("F:/Algos/input.txt")));
		//int i=1;
		//int[] integers = new int[3];
		
		Graph g = new Graph(V);
		while(sc.hasNext())
		// while((i = sc.nextInt()) != null)
			/*for(int j=0;j<3;j++)
			{
				integers[j] = sc.nextInt();
			}
           g.addEdge(sc.nextInt(),sc.nextInt(),sc.nextInt());
			
		
		g.printGraph();
		*/

		Scanner sc = new Scanner(new FileReader("input.txt"));
		int V = sc.nextInt();
		int edges = sc.nextInt();
		
	//  System.out.println("V: "+V+" Edges: "+edges);
		
		Graph g = new Graph (V);
		
		int i = 1;
		
		while( sc.hasNext() )
		{
	
			g.addEdge(sc.nextInt()-1,sc.nextInt()-1,sc.nextInt());
		//	g.printGraph();
			
		}
		

		//g.printGraph();
		
		g.primMinSpanTree();	
		
	}
}
