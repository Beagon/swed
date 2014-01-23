package database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.DatabaseMetaData;

public class Sqlite {
        public int iTimeout = 30;
        public Statement stmt;
        
    public Sqlite(String DatabaseName) throws Exception {
        String sqlDriverName = "org.sqlite.JDBC";
        Class.forName(sqlDriverName);
        String sTempDb = DatabaseName + ".db";
        String sJdbc = "jdbc:sqlite";
        String sDbUrl = sJdbc + ":" + sTempDb;
        Connection conn = DriverManager.getConnection(sDbUrl);
        stmt = conn.createStatement();
        stmt.setQueryTimeout(iTimeout);
    }
    
    public String GetText(int ID, String TableName, String ColumnName){
            String query = "SELECT " + ColumnName + " from " + TableName + " WHERE `id` = '" + ID + "';";
            String result = "Hi, this message means that something is wrong.";
            try{
            ResultSet rs = stmt.executeQuery(query);
            result = rs.getString(ColumnName);
            }catch (Exception ignore){
                System.out.println(ignore.getMessage());
                //System.out.println(query);
                result = "Hi, this message means you've encountered a bug.";
            }
        
        return result;
    }
    
}
