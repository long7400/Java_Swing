package sample.connSQL;

import sample.U.AppContain;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class DBconnect {

    private static final Logger LOGGER = Logger.getLogger(DBconnect.class.getName());

    public static Connection getConnect(String strServer,String strDatabase)
    {
        Connection conn = null;
        String userName = "sa";
        String password = "123";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://"+strServer+":1433;databaseName="+strDatabase+";integrateSecruity=true";
            conn = java.sql.DriverManager.getConnection(connectionUrl,userName,password);

        }catch (SQLException e)
        {
            System.out.println("SQLException : " +e.toString());
        }catch (ClassNotFoundException cE)
        {

            LOGGER.info(AppContain.FAILS_MESSAGE);
        }
        return conn;
    }
    public static Connection getConnect()
    {
        Connection conn = getConnect("DESKTOP-QEN4LJI\\SQLEXPRESS","CAYCANH");
        return conn;
    }



}