package dao.impls;

import dao.interfaces.IRepository;
import entities.Bill;
import entities.Products;
import helper.Connector;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BillsRepository implements IRepository<Bill> {
    @Override
    public ArrayList<Bill> all() {
        ArrayList<Bill> billArrayList = new ArrayList<>();
        try {
            String sql_txt = "SELECT * FROM bill";
            Connector conn = Connector.getInstance();
            ResultSet rs = conn.query(sql_txt);
            while (rs.next()) {
                try{
                    int id = rs.getInt("id");
                    Date datetime = rs.getDate("datetime");
                    Float total = rs.getFloat("total");
                    Bill b = new Bill(id, new java.util.Date(datetime.getTime()) , total);
                    billArrayList.add(b);
                }catch (Exception e){

                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return billArrayList;
    }

    @Override
    public Bill create() {
        Bill b = new Bill();
        try {
            String sql_txt = "insert into bill(datetime, total) values(null, null);";
            Connector conn = Connector.getInstance();
            Integer id = conn.getKeysQuery(sql_txt);
            b = new Bill(id, null, 0);
        } catch (Exception e) {
            System.out.println(e);
        }
        return b;
    }

    @Override
    public boolean update(Bill bill) {

        return false;
    }

    @Override
    public boolean delete(Bill bill) {
        return false;
    }

    @Override
    public Bill findOne(Integer id) {
        return null;
    }

    public ArrayList<Bill> findById(Integer billId) {
        String sql_txt = "SELECT * FROM bill where id =" + billId;
        ArrayList<Bill> billArrayList = new ArrayList<>();
        try {
            Connector conn = Connector.getInstance();
            ResultSet rs = conn.query(sql_txt);
            while (rs.next()) {
                try{
                    int id = rs.getInt("id");
                    Date datetime = rs.getDate("datetime");
                    Float total = rs.getFloat("total");
                    Bill b = new Bill(id, new java.util.Date(datetime.getTime()) , total);
                    billArrayList.add(b);
                }catch (Exception e){

                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return billArrayList;
    }

    public boolean uploadDetail(Bill bill, ArrayList<Products> productsArrayList){
        try{
            String updateBill = "update bill set datetime ='" + new java.sql.Date(bill.getDatetime().getTime()) + "',total ="+bill.getTotal()+" where id ="+bill.getId()+";";
            String insertDetail = "insert into billdetails (billID, productID, quantity, subtotal) values";
            ArrayList<String> detail = new ArrayList<>();
            for (Products p: productsArrayList) {
                detail.add("("+ bill.getId() +","+p.getId()+","+p.getQuantity()+","+p.getSubtotal()+")");
            }
            String sql_txt = updateBill + insertDetail + String.join(",", detail);
            Connector conn = Connector.getInstance();
            System.out.println(sql_txt);
            return conn.executeQuery(sql_txt);
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
    public ArrayList<Products> getDetail(Bill bill){
        ArrayList<Products> pr = new ArrayList<>();
        try{
            String sql_txt ="SELECT * FROM `products` join billdetails ON products.id =billdetails.productID WHERE billdetails.billID =" + bill.getId();
            Connector conn = Connector.getInstance();
            ResultSet rs = conn.query(sql_txt);
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Float price = rs.getFloat("price");
                Integer qty = rs.getInt("quantity");
                Products p = new Products(id, name, price, qty);
                pr.add(p);
            }
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return pr;
    }
}
