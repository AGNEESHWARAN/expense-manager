package splitwise;


public class CustomList {

	Node head = null;
	
	Node current = null;
	
	int size=0;
	
	public void add(String type, String field ,int val) {
		
		if(this.head ==null) {
			this.head = new Node(this.size, type, field, val);
			this.current = this.head;
		}
		
		else{
			
			Node temp = new Node(this.size, type, field, val);
			this.current.next = temp;
			this.current = this.current.next;
		}
		
		this.size++;
	}
	
	public void add(String type, String field ,double val) {
		
		if(this.head ==null) {
			this.head = new Node(this.size, type, field, val);
			this.current = this.head;
		}
		
		else{
			
			Node temp = new Node(this.size, type, field, val);
			this.current.next = temp;
			this.current = this.current.next;
		}
		
		this.size++;
	}
	
	public void add(String type, String field ,String val) {
		
		if(this.head ==null) {
			this.head = new Node(this.size, type, field, val);
			this.current = this.head;
		}
		
		else{
			
			Node temp = new Node(this.size, type, field, val);
			this.current.next = temp;
			this.current = this.current.next;
		}
		
		this.size++;
	}
	
	public void traverse() {
		Node start = this.head;
		
		while(start!=null) {
			if(start.type.equalsIgnoreCase("int"))
				System.out.println(start.field+" "+start.intVal);
			start = start.next;
		}
	}
	
	
	public Node getByIndex(Integer ind) {
		
		Node start = this.head;
		
		if(ind <= this.size) {
			while(start!=null) {
				if(start.index==ind)
					return start;
					
				start = start.next;
				
			}
		}
		return null;
	}
	
	public void clear() {
		this.head=null;
		this.size=0;
		this.current=null;
	}
	
}






class Node {
	Integer intVal;
	
	String stringVal;
	
	Double doubleVal;
	
	String field;
	
	String type;
	
	Integer index;
	
	Node next=null;
	
	Node(Integer index, String type, String field){
		this.index=index;
		this.type=type;
		this.field=field;
	}
	
	Node(Integer index,String type, String field, int intVal){
		this(index, type,field);
		this.intVal = Integer.valueOf(intVal);
	}
	
	Node(Integer index, String type, String field, double doubleVal){
		this(index, type, field);
		this.doubleVal = Double.valueOf(doubleVal);
	}
	
	Node(Integer index, String type, String field, String stringVal){
		this(index, type, field);
		this.stringVal=stringVal;
	}
}
