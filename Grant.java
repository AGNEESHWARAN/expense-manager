package splitwise;

public class Grant {
	private int id;
	private double sum;
	private int billId;
	
	public Grant(int id,double sum,int billId) {
		this.sum = sum;
		this.billId = billId;
		
		this.id = id;

	}
	public Grant(double sum, int billId) {
		this(0,sum, billId);
	}

	public int getId() {
		return id;
	}

	public double getSum() {
		return sum;
	}
	public int getBillId() {
		return billId;
	}

}
