package utils;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Database instance;

    private static final String url ="jdbc:oracle:thin:@localhost:1521:xe";
    private static final String user="student";
    private static final String password="STUDENT";

    private Connection connection;

    private Database() throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
    }

    public static Database getDBInstance() throws SQLException {
        if(instance == null)
            instance = new Database();
        return instance;
    }

    public Connection getConnection(){
        return connection;
    }

    public static void runScript() throws SQLException {
        ScriptRunner sr = new ScriptRunner(DriverManager.getConnection(url,user,password));
        sr.setSendFullScript(true);
        Reader reader = null;
        try {
            reader = new BufferedReader(new FileReader("E:\\FACULTATE\\Anul2Sem2\\ProgramareAvansata\\Proiect\\Stable\\src\\main\\java\\utils\\createTables.sql"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sr.runScript(reader);
    }
}
