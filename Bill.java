package splitwise;

import java.util.ArrayList;

public class Bill {
	
	private ArrayList<Grant> grants = new ArrayList<Grant>();
	private ArrayList<Purchase> purchases = new ArrayList<Purchase>();
	
	private int id;
	private String note;
	private int eventId;
	private String type;
	private String shareType;
	private double total;
	
	public Bill(int id, String note ,int eventId, String type, String shareType , double total, ArrayList<Grant> grants ,ArrayList<Purchase> purchases) {
		this.id = id;
		this.eventId=eventId;
		this.note = note;
		this.grants = grants;
		this.purchases = purchases;
		this.type=type;
		this.total=total;
		this.setTotal(purchases, grants);
		this.shareType = shareType;
	}
	public Bill(int id, String note ,int eventId, String type, String shareType, double total) {
		this.id = id;
		this.eventId=eventId;
		this.note = note;
		this.type=type;
		this.setTotal(purchases, grants);
		this.shareType = shareType;
	}
	
	public Bill(String type, String shareType,String note, int eventId) {
		this(0, note, eventId, type, shareType, 0);
		}
		
	
	public Bill() {}

	public ArrayList<Grant> getGrants() {
		return grants;
	}

	public ArrayList<Purchase> getPurchases() {
		return purchases;
	}

	public int getId() {
		return id;
	}

	public String getNote() {
		return note;
	}

	public int getEventId() {
		return eventId;
	}

	public String getType() {
		return type;
	}

	public double getTotal() {
		return total;
	}
	
	public void setTotal(ArrayList<Purchase> purchase, ArrayList<Grant>grant) {
	double total=0.0;
		if(purchase !=null) {
			for(Purchase pur:purchase)
				total +=pur.getAmount();
		}
		if(grant!=null) {
			for(Grant g:grant)
				total += g.getSum();
		}
		this.total = total;
	}
	
	public String getShareType() {
		return shareType;
	}
	
	public void setTotal(double total) {
		this.total=total;
	}
}
