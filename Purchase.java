package splitwise;



public class Purchase {
	
	private int id;
	private String name;
	private double amount; 
	private int billId;
	
	public Purchase(int id, String name, double amount, int billId ) {
		this.name = name;
		this.amount = amount;
		this.billId = billId;
	}
	
	public Purchase(int billId, String name, String type, double amount ) {
		this(0,name,amount,billId);
		
	}
	public Purchase(String name, String type, double amount ) {
		this(0,name,amount,0);
		
	}
	public Purchase() {}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getAmount() {
		return amount;
	}

	public int getBillId() {
		return billId;
	}
	void setBillId(int i) {
		this.billId = i;
	}
	
	
	

}
