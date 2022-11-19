package dao.impls;

import dao.interfaces.IRepository;
import entities.Products;
import helper.Connector;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ProductsRepository implements IRepository<Products> {

    @Override
    public ArrayList<Products> all() {
        ArrayList<Products> pr = new ArrayList<>();
        try {
            String sql_txt = "SELECT * FROM products";
            Connector conn = Connector.getInstance();
            ResultSet rs = conn.query(sql_txt);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Float price = rs.getFloat("price");
                Products p = new Products(id, name, price);
                pr.add(p);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return pr;
    }

    @Override
    public Products create() {
        return new Products();
    }

    @Override
    public boolean update(Products products) {
        try {
            String sql_txt = "update products set name=?, quantity=?,price=? where id=?";
            Connector conn = Connector.getInstance();
            ArrayList arr = new ArrayList();
            arr.add(products.getName());
            arr.add(products.getQuantity());
            arr.add(products.getPrice());
            arr.add(products.getId());
            if (conn.execute(sql_txt, arr)) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Products products) {
        try {
            String sql_txt = "delete from products where id=?";
            Connector conn = Connector.getInstance();
            ArrayList arr = new ArrayList();
            arr.add(products.getId());
            if (conn.execute(sql_txt, arr)) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public Products findOne(Integer id) {
        try {
            String sql_txt = "select * from products where id=?";
            Connector conn = Connector.getInstance();
            ArrayList arr = new ArrayList();
            arr.add(id);
            ResultSet rs = conn.executeQuery(sql_txt, arr);
            while (rs.next()) {
                int Id = rs.getInt("id");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");
                return new Products(Id, name, quantity, price);
            }
        } catch (Exception e) {
        }
        return null;
    }

}
