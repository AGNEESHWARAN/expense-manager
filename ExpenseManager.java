package splitwise;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ExpenseManager {
	DatabaseManager dm = new DatabaseManager();
	String sql;
	
	public int addBill(Bill bill) throws SQLException {
		sql = "insert into Bill(note,event_id,bill_type,share_type,total) values('"+bill.getNote()+"',"
				+ "'"+bill.getEventId()+"',"
						+ "'"+bill.getType()+"',"
								+ "'"+bill.getShareType()+"',"
								+ "'"+bill.getTotal()+"')";
		System.out.println(sql);
		int res = dm.addRecord(sql);
		return res;
	}
	
	public int addPurchase(int billId,Purchase purchase) throws SQLException {
		sql="insert into Purchase(product_name,total_amount,bill_id) values('"+purchase.getName()+"',"
				+ "'"+purchase.getAmount()+"',"
						+ "'"+billId+"')";
		
		int res = dm.addRecord(sql);
		return res;
	}
	
	public int addGrant(int billId,Grant grant) throws SQLException {
		sql="insert into Grants_table(total_amount,bill_id) values('"+grant.getSum()+"',"
						+ "'"+billId+"')";
		
		int res = dm.addRecord(sql);
		return res;
	}
	
	public void updateBill(int id, double total) throws SQLException {
		sql ="update Bill set total='"+total+"' where bill_id='"+id+"'";
		dm.updateRecord(sql);
	}
	
	public ArrayList<Bill> getBill(int eventId) throws SQLException {
		Bill bill = null;
		ArrayList<Bill>bills =new ArrayList<Bill>();
		
		
		sql = "select * from Bill where event_id='"+eventId+"'";
		ResultSet rs = dm.getRecords(sql);
		while(rs.next()) {
			ArrayList<Purchase> purchases = new ArrayList<Purchase>();
			ArrayList<Grant> grants = new ArrayList<Grant>();
			String note = rs.getString(2);
			int id = rs.getInt(1);
			String billType = rs.getString(4);
			double total = rs.getDouble(5);
			String shareType = rs.getString(6);
			sql ="select * from Purchase where bill_id='"+id+"'";
			ResultSet rs1 = dm.getRecords(sql);
			
			while(rs1.next()) {
				purchases.add(new Purchase(rs1.getInt(1), rs1.getString(2), rs1.getDouble(3), rs1.getInt(4)));
			}
			sql ="select * from Grants_table where bill_id='"+id+"'";
			ResultSet rs2 = dm.getRecords(sql);
			while(rs2.next()) {
				grants.add(new Grant(rs2.getInt(1), rs2.getDouble(2), rs2.getInt(3)));
			}
			bill = new Bill(id, note, eventId, billType, shareType, total, grants, purchases);
			bills.add(bill);
		}
		return bills;
	}
	
	public int addExpense(int billId, int userId, double amount, double debt, double credit ) throws SQLException {
		sql ="insert into Expenses(bill_id,user_id,amount_payed,debt,credit) values('"+billId+"',"
				+ "'"+userId+"','"+amount+"','"+debt+"','"+credit+"')";
		int res = dm.addRecord(sql);
		return res;
	}
	
	public int addTOSplitRecord(int payee, int payer, double amount, int billId) throws SQLException {
		sql="insert into Split(payee,payer,amount,bill_Id) values('"+payee+"','"+payer+"','"+amount+"','"+billId+"')";
		int res = dm.addRecord(sql);
		return res;
	}
	
	public HashMap<Integer,Double> getAllCredits(int userId) throws SQLException, ExpenseException{
		HashMap<Integer,Double> res = new HashMap<Integer,Double>();
		sql ="select * from Split where payee ='"+userId+"'";
		ResultSet rs = dm.getRecords(sql);
		
		while(rs.next()) {
			if(res.containsKey(rs.getInt(3))) {
				Double amt = res.get(rs.getInt(3));
				res.replace(rs.getInt(3), amt+rs.getDouble(4));
			}
			else
			res.put(rs.getInt(3),rs.getDouble(4));
			
		}
		if(res.isEmpty())
			throw new ExpenseException("Record not found!");
		return res;
	}
	public HashMap<Integer,Double> getAllDebts(int userId) throws SQLException, ExpenseException{
		HashMap<Integer,Double> res = new HashMap<Integer,Double>();
		sql ="select * from Split where payer ='"+userId+"'";
		ResultSet rs = dm.getRecords(sql);
		
		while(rs.next()) {
			if(res.containsKey(rs.getInt(2))) {
				Double amt = res.get(rs.getInt(2));
				res.replace(rs.getInt(2), amt+rs.getDouble(4));
			}
			else
			res.put(rs.getInt(2),rs.getDouble(4));
			
		}
		if(res.isEmpty())
			throw new ExpenseException("Records not found!");
		return res;
	}
	
	public HashMap<Integer,Double> gettAllExpenses(int usrId) throws SQLException, ExpenseException{
		HashMap<Integer,Double> res = new HashMap<Integer,Double>();
		sql ="select * from Expenses where user_id='"+usrId+"'";
		ResultSet st = dm.getRecords(sql);
		Double amount=0.0;
		while(st.next()) {
		amount = st.getDouble(4);
		int billId = st.getInt(2);
		sql ="select * from Bill where bill_id='"+billId+"'";
		ResultSet st2 = dm.getRecords(sql);
		st2.next();
		Integer eventId = st2.getInt(3);
		if(res.containsKey(eventId)) {
			amount +=res.get(eventId);
			res.replace(eventId, amount);
		}else {
			res.put(eventId,amount);
		}
		}
		if(res.isEmpty())
			throw new ExpenseException("No records found!!");
		return res;
	}
}

@SuppressWarnings("serial")
class ExpenseException extends Exception{
	
	ExpenseException(){}
	ExpenseException(String message){
		super(message);
	}
	
	boolean validate(Object obj) {
		if(obj!=null)
			return true;
		return false;
	}
}
