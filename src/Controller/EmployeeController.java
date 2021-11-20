package Controller;

import Model.Employee;
import db.DbConnection;
import tm.EmoloyeeTM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController {

    public boolean SaveEmployee(Employee e) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO employee VALUES(?,?,?,?,?,?,?)");
        stm.setObject(1, e.getRole());
        stm.setObject(2, e.getId());
        stm.setObject(3, e.getName());
        stm.setObject(4, e.getAddress());
        stm.setObject(5, e.getContact());
        stm.setObject(6, e.getSalary());
        stm.setObject(7, e.getPassword());
        return stm.executeUpdate() > 0;
    }

    public static List<EmoloyeeTM> getAllEmployees() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM employee").executeQuery();
        List<EmoloyeeTM> employee = new ArrayList<>();
        while (rst.next()) {
            employee.add(new EmoloyeeTM(
                    rst.getString("emp_Id"), rst.getString("emp_Name"), rst.getString("emp_Contact"),
                    rst.getString("emp_Address"), rst.getDouble("emp_Basic_Salary")
            ));
        }
        return employee;
    }

    public Employee getEmployee(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM employee WHERE emp_Id=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Employee(
                rst.getString(1),
                rst.getString(2),
                rst.getString(3),
                rst.getString(4),
                rst.getString(5),
                rst.getDouble(6),
                rst.getString(7)
            );

        } else {
            return null;
        }
    }
    public boolean updateEmployee(Employee e) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE employee SET emp_Role=?, emp_Name=?, emp_Address=?, emp_Contact=?, emp_Basic_Salary=?, emp_Password=? WHERE emp_Id=?");
        stm.setObject(1,e.getRole());
        stm.setObject(2,e.getName());
        stm.setObject(3,e.getAddress());
        stm.setObject(4,e.getContact());
        stm.setObject(5,e.getSalary());
        stm.setObject(6,e.getPassword());
        stm.setObject(7,e.getId());
        return stm.executeUpdate()>0;
    }
    public boolean deleteEmployee(EmoloyeeTM e) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM employee WHERE emp_Id=?");
        stm.setObject(1,e.getId());
        return stm.executeUpdate()>0;
    }

}
