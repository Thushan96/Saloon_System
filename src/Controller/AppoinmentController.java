package Controller;

import Model.Appointment;
import Model.Services;
import db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppoinmentController {
    public boolean addtoServiceDetails(double price,String Num,String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO service_details VALUES(?,?,?)");
        stm.setObject(1,id);
        stm.setObject(2,Num);
        stm.setObject(3,price);
        return stm.executeUpdate() > 0;

    }

    public Services serviceDetails(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM SERVICE WHERE service_Name=?");
        stm.setObject(1,id);
        ResultSet rst = stm.executeQuery();

        while (rst.next()){
            return new Services(rst.getString(1),rst.getString(2),rst.getDouble(3));
        }
        return null;
    }



    public String getAppointmentId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT app_No FROM appoinment ORDER BY app_No DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "A-00"+tempId;
            }else if(tempId<=99){
                return "A-0"+tempId;
            }else{
                return "A-"+tempId;
            }

        }else{
            return "A-001";
        }
    }

    public Double getTotal(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT service_Price FROM service WHERE service_Name=?");
        stm.setObject(1,id);
        ResultSet rst = stm.executeQuery();
        while (rst.next()){
            return rst.getDouble(1);
        }
        return null;
    }
    public  String GetEmpName(String name) throws SQLException, ClassNotFoundException {
        String Empid=null;
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT emp_Id FROM employee WHERE emp_Name=?");
       stm.setObject(1,name);
        ResultSet rst = stm.executeQuery();

        while (rst.next()){
            Empid=rst.getString(1);
        }
        return Empid;
    }

    public boolean placeAppoinment(String AppNo, String CNic, String empId, Double total, Double discount, String date)  {
        Connection con=null;
        try {
             con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.prepareStatement("INSERT INTO appoinment SET  app_No=?, customer_NIC=?, employee_Id=?, price=?, discount=?,date=? ");

            stm.setObject(1, AppNo);
            stm.setObject(2, CNic);
            stm.setObject(3, empId);
            stm.setObject(4, total);
            stm.setObject(5, discount);
            stm.setObject(6, date);
            int i = stm.executeUpdate();
            if (i > 0) {
                con.setAutoCommit(true);
                return true;
            } else {
                con.setAutoCommit(false);
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;

    }
    public Double SearchDicount(String id) throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT discount FROM appoinment WHERE app_No=?");
        stm.setObject(1,id);
        ResultSet rst = stm.executeQuery();
        Double discount = null;
        while (rst.next()){
           discount=rst.getDouble(1);
        }
        return discount;
    }
    public String SearchEmpId(String id) throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT employee_Id FROM appoinment WHERE app_No=?");
        stm.setObject(1,id);
        ResultSet rst = stm.executeQuery();
        String Empid = null;
        while (rst.next()){
            Empid=rst.getString(1);
        }

        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement("SELECT emp_Name FROM employee WHERE emp_Id=?");
        statement.setObject(1,Empid);
        String PersonName=null;
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            PersonName=resultSet.getString(1);
        }

        return PersonName;
    }
}
