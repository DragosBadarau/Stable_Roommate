package utils;


import commands.Command;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertStudentDAO implements Command {

    private int id;
    private String name;
    private int points;
    private String gender;
    private int P1;
    private int P2;
    private int P3;
    private int P4;
    private int P5;
    private Connection conn;

    public InsertStudentDAO(int id, String name, int points, String gender, int P1, int P2, int P3, int P4, int P5, Connection conn) {
        this.id = id;
        this.name = name;
        this.points = points;
        this.gender = gender;
        this.P1 = P1;
        this.P2 = P2;
        this.P3 = P3;
        this.P4 = P4;
        this.P5 = P5;
        this.conn = conn;
    }

    @Override
    public String run() {
        try {
            Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery(
                    "INSERT INTO STUDENTS VALUES(" + "'" + id + "','" + name + "','" + points + "','" + gender + "','" + P1 + "','" + P2 + "','" + P3 + "','" + P4 + "','" + P5 + "')"
            );
            set.close();
            statement.close();
            return "succ";
        }
        catch (SQLException e) {
            return "exception";
        }
    }
}