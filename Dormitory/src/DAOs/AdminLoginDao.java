package DAOs;

import HandlingMethods.StringHandling;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminLoginDao {
    Connection con;
    public AdminLoginDao() {
        con = new DBConn().getCon();
    }

    public boolean checkLogin(String user, String pass) {
        boolean check = false;
        try {

            String sql = "SELECT * FROM Administrators WHERE Username='"+user+"'";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            //System.out.println(sql);
            if (resultSet.next()) {

                if(resultSet.getString(2).equals(pass))
                    check = true;
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return check;
    }
//    public void Close(){
//        try {
//            con.close();
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//    }
}
