package Controller;

import Model.Employee;
import Model.Item;
import Model.ItemDetails;
import Model.Orders;
import db.DbConnection;
import tm.EmoloyeeTM;
import tm.ItemTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemController {
    public boolean SaveItem(Item i) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO item VALUES(?,?,?,?)");
        stm.setObject(1, i.getItemId());
        stm.setObject(2, i.getItemName());
        stm.setObject(3, i.getItemPrice());
        stm.setObject(4, i.getQtyOnHand());
        return stm.executeUpdate() > 0;
    }

    public Item getItems(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM item WHERE item_Id=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4)
            );

        } else {
            return null;
        }
    }
    public static List<ItemTM> getAllItems() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM item").executeQuery();
        List<ItemTM> Item = new ArrayList<>();
        while (rst.next()) {
            Item.add(new ItemTM(
                    rst.getString("item_Id"), rst.getString("Item_Name"), rst.getInt("qty_On_Hand"),
                   rst.getDouble("item_Price")
            ));
        }
        return Item;
    }
    public boolean updateItem(Item i) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE item SET Item_Name=?, item_Price=?, qty_On_Hand=? WHERE item_Id=?");
        stm.setObject(1,i.getItemName());
        stm.setObject(2,i.getItemPrice());
        stm.setObject(3,i.getQtyOnHand());
        stm.setObject(4,i.getItemId());
        return stm.executeUpdate()>0;
    }

    public boolean deleteItem(ItemTM i) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM item WHERE item_Id=?");
        stm.setObject(1,i.getItemId());
        return stm.executeUpdate()>0;
    }

    public String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT order_Id FROM orders ORDER BY order_Id DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "O-00"+tempId;
            }else if(tempId<=99){
                return "O-0"+tempId;
            }else{
                return "O-"+tempId;
            }

        }else{
            return "O-001";
        }
    }

    public boolean placeOrder(Orders order,String date) {
        Connection con=null;
        try {
            con= DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm =con.
                    prepareStatement("INSERT INTO orders SET order_Id=?,cost=?");

            stm.setObject(1, order.getOrderId());
            stm.setObject(2, order.getCost());


            if (stm.executeUpdate() > 0){
                if (saveOrderDetail(order.getOrderId(), order.getItems(),date)){
                    con.commit();
                    return true;
                }else{
                    con.rollback();
                    return false;
                }
            }else{
                con.rollback();
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

    private boolean saveOrderDetail(String orderId, ArrayList<ItemDetails> items,String date) throws SQLException, ClassNotFoundException {
        for (ItemDetails temp : items
        ) {
            PreparedStatement stm = DbConnection.getInstance().
                    getConnection().
                    prepareStatement("INSERT INTO order_detail SET order_Id=?,item_Id=?,qty=?,price=?,date=?");
            stm.setObject(1, orderId);
            stm.setObject(2, temp.getItemCode());
            stm.setObject(3, temp.getQtyOfSell());
            stm.setObject(4, temp.getUnitPrice());
            stm.setObject(5,date);
            if (stm.executeUpdate() > 0) {

                if (updateQty(temp.getItemCode(), temp.getQtyOfSell())){

                }else{
                    return false;
                }

            } else {
                return false;
            }
        }
        return true;
    }
    private boolean updateQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection()
                .prepareStatement
                        ("UPDATE item SET qty_On_Hand=(qty_On_Hand-" + qty
                                + ") WHERE item_Id='" + itemCode + "'");
        return stm.executeUpdate()>0;
    }

}
