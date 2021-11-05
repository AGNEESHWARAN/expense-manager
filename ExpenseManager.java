package splitwise;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
		sql="insert into Split(payee,payer,amount,bill_Id) values('"+payer+"','"+payer+"','"+amount+"','"+billId+"')";
		int res = dm.addRecord(sql);
		return res;
	}
	
	
}