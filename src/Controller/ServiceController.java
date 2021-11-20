package Controller;

import Model.ServiceDetails;
import Model.Services;
import db.DbConnection;
import tm.ServiceTM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceController {

    public boolean SaveItem(Services s) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO service VALUES(?,?,?,?)");
        stm.setObject(1, s.getSId());
        stm.setObject(2, s.getSName());
        stm.setObject(3,s.getSprice());
        stm.setObject(4,s.getSdescription());
        return stm.executeUpdate() > 0;
    }

    public static List<ServiceTM> getAllServices() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM service").executeQuery();
        List<ServiceTM> service = new ArrayList<>();
        while (rst.next()) {
            service.add(new ServiceTM(
                    rst.getString("service_Id"), rst.getString("service_Name"), rst.getDouble("service_Price"),
                    rst.getString("service_Description")
            ));
        }
        return service;
    }

    public Services getServices(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM service WHERE service_Id=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Services(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getString(4)
            );

        } else {
            return null;
        }
    }

    public boolean updateService(Services s) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE service SET service_Name=?, service_Price=?, service_Description=? WHERE service_Id=?");
        stm.setObject(1,s.getSName());
        stm.setObject(2,s.getSprice());
        stm.setObject(3,s.getSdescription());
        stm.setObject(4,s.getSId());
        return stm.executeUpdate()>0;
    }

    public boolean deleteService(ServiceTM s) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM service WHERE service_Id=?");
        stm.setObject(1,s.getSid());
        return stm.executeUpdate()>0;
    }
    public List<ServiceDetails> getServicesDetailTable(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM service_details WHERE app_No=?");
        stm.setObject(1,id);
        ResultSet rst = stm.executeQuery();
        List<ServiceDetails> details=new ArrayList<>();
        while (rst.next()){
            details.add(new ServiceDetails(rst.getString(1),rst.getString(2),rst.getDouble(3)));
        }
        return details;

    }
    public ArrayList<String> GetServiceName(String sid) throws SQLException, ClassNotFoundException {
        ArrayList<String> Namelist=new ArrayList<>();
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT service_Name FROM service WHERE service_Id=?");
        stm.setString(1, sid);

        ResultSet rst = stm.executeQuery();
        while (rst.next()){
            Namelist.add(rst.getString(1));
        }
        return Namelist;
    }

}
