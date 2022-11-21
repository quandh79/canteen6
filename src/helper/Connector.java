package helper;


import java.sql.*;
import java.util.ArrayList;

public class Connector {
    public final static String connectionString = "jdbc:mysql://localhost:3306/canteen1?allowMultiQueries=true";
    public final static String user = "root";
    public final static String pwd = "";// neu la xampp: "" , mamp: "root"
    private static Connector instance;
    Connection conn;

    private Connector() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(connectionString, user, pwd);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Connector getInstance() {
        if (instance == null) {
            instance = new Connector();
        }
        return instance;
    }

    public Statement getStatement() throws Exception {
        return conn.createStatement();
    }

    public ResultSet query(String sql) {
        try {
            return getStatement().executeQuery(sql);
        } catch (Exception e) {
            System.out.println(e + "err sql");
            return null;
        }
    }

    public Integer getKeysQuery(String sql) {
        Integer id = 0;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            while (rs.next()) {
                id = Integer.parseInt(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e + "err sql");
            return 0;
        }
        return id;
    }

    public boolean executeQuery(String sql) {
        try {
            getStatement().execute(sql);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public PreparedStatement getPreparedStatement(String sql) throws Exception {
        return conn.prepareStatement(sql);
    }

    public boolean execute(String sql, ArrayList parameters) {
        try {
            PreparedStatement pstm = getPreparedStatement(sql);
            for (int i = 0; i < parameters.size(); i++) {
                if (parameters.get(i) instanceof Integer) {
                    pstm.setInt(i + 1, (Integer) parameters.get(i));
                } else if (parameters.get(i) instanceof Double) {
                    pstm.setDouble(i + 1, (Double) parameters.get(i));
                } else {
                    pstm.setString(i + 1, (String) parameters.get(i));
                }
            }
            pstm.execute();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public ResultSet executeQuery(String sql, ArrayList parameters) {
        try {
            PreparedStatement pstm = getPreparedStatement(sql);
            for (int i = 0; i < parameters.size(); i++) {
                if (parameters.get(i) instanceof Integer) {
                    pstm.setInt(i + 1, (Integer) parameters.get(i));
                } else if (parameters.get(i) instanceof Double) {
                    pstm.setDouble(i + 1, (Double) parameters.get(i));
                } else {
                    pstm.setString(i + 1, (String) parameters.get(i));
                }
            }
            return pstm.executeQuery();
        } catch (Exception e) {
            return null;
        }
    }

}

