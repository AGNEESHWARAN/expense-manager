package splitwise;



import java.util.HashMap;
import java.util.Iterator;

public class GroupEvent extends Event implements Split {
	
	private Group group;
	
	public GroupEvent(int id, String name,Group group,String type) {
		super(id, name,type);
		this.group=group;
		
	}
	public GroupEvent(String name, Group group,String type) {
		this(0, name, group, type);
	}
	public GroupEvent() {
		// TODO Auto-generated constructor stub
	}
	public GroupEvent(String name,String type) {
		super(name,type);
	}
	private HashMap<Integer,Double> payee;// = new HashMap<String, Double>();
	private HashMap<Integer,Double> payer ;// new HashMap<String, Double>();
	
	public Group getGroup() {
		return group;
	}
	
	
	
	@Override
	public HashMap<Integer, HashMap<Integer, Double>> splitEqual(Integer[] p, Double[] arr, Double total) {
		
		payee = new HashMap<Integer, Double>();
		payer = new HashMap<Integer, Double>();
		
		Double share =  total/arr.length;
		
		for(int i=0;i<arr.length;i++) {
			if(arr[i]<share) {
			//	System.out.println(p[i]);
				this.payer.put(p[i],share -arr[i] );
			}
			else if(arr[i]>share) {
				this.payee.put(p[i],arr[i]-share);
			}
		}
		System.out.println(payee);
		
		HashMap<Integer,HashMap<Integer,Double>> res = new HashMap<Integer,HashMap<Integer,Double>>();
		
		Iterator<Integer> iteS1 = payee.keySet().iterator();
		Iterator<Integer>iteS2 ;
		while(iteS1.hasNext()){
			Integer p1=iteS1.next();
			Double balance = payee.get(p1);
			
			iteS2 = payer.keySet().iterator();
			HashMap<Integer,Double> temp = new HashMap<Integer, Double>();
			
			while(iteS2.hasNext() && balance>0) {
				Integer p2=iteS2.next();
				Double amt = payer.get(p2);
				
				if(amt <= balance && amt>0) {
					//System.out.println(p2+"--"+amt +"   ----- "+p1+"--"+balance);
					balance -= amt;
					payer.replace(p2, 0.0);
					temp.put(p2,amt);
					
				}
				
				else if(amt>balance) {
					amt = amt-balance;
					temp.put(p2,balance);
					balance= 0.0;
					payer.replace(p2, amt);
					//System.out.println(p2+"--"+amt +"   ----- "+p1+"--"+balance);	
				}
			}
			res.put(p1,temp);
		}
		
		//System.out.println(res);
		return res;
	}

	@Override
	public HashMap<Integer, HashMap<Integer, Double>> splitByPercentage(Integer[] p, Double[] arr, Double total, Double[] share) {
		
			
		payee = new HashMap<Integer, Double>();
		payer = new HashMap<Integer, Double>();
		Double per =  (total/100);
		for(int i=0;i<share.length;i++) {
			float amt =(float) (per *share[i]);
			if(amt<arr[i]) {
				//System.out.println(amt+"--"+arr[i]+"--"+(arr[i]-amt));
				payee.put(p[i], arr[i]-amt);
			}
			else if(amt>arr[i]) {
				//System.out.println(amt+"--"+arr[i]+"--"+(amt-arr[i]));
				payer.put(p[i], amt-arr[i]);
			}
		}
		
		HashMap<Integer,HashMap<Integer,Double>> res = new HashMap<Integer,HashMap<Integer,Double>>();
		
		Iterator<Integer> iteS1 = payee.keySet().iterator();
		Iterator<Integer>iteS2 ;
		while(iteS1.hasNext()){
			Integer p1=iteS1.next();
			Double balance = payee.get(p1);
			
			iteS2 = payer.keySet().iterator();
			HashMap<Integer,Double> temp = new HashMap<Integer, Double>();
			
			while(iteS2.hasNext() && balance>0) {
				Integer p2=iteS2.next();
				Double amt = payer.get(p2);
				
				if(amt <= balance && amt>0) {
					//System.out.println(p2+"--"+amt +"   ----- "+p1+"--"+balance);
					balance -= amt;
					payer.replace(p2,0.0);
					temp.put(p2,amt);
					
				}
				
				else if(amt>balance) {
					amt = amt-balance;
					temp.put(p2, balance);
					balance= 0.0;
					payer.replace(p2, amt);
					//System.out.println(p2+"--"+amt +"   ----- "+p1+"--"+balance);	
				}
			}
			res.put(p1,temp);
		}
		//System.out.println(res);
		return res;

	}
	
	boolean isMember(int id) {
		boolean res = false;
		for(User u : this.getGroup().getUsers()) {
			if(u.getId()==id)
				res=true;
		}
		return res;
		
	}
	
	void setGroup(Group group) {
		this.group=group;
		
	}
	
}
