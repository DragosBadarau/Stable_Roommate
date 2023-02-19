package utils;
import commands.Command;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertRoomDAO implements Command {

    private int id_room, id1, id2;
    private String name, gen;
    private Connection conn;

    public InsertRoomDAO(int id_room, String name, int id1, int id2, String gen, Connection conn) {
        this.id_room = id_room;
        this.name = name;
        this.id1 = id1;
        this.id2 = id2;
        this.gen = gen;
        this.conn = conn;
    }

    @Override
    public String run() {
        try {
            Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery(
                    "INSERT INTO ROOMS VALUES(" + "'" + id_room + "','" + name + "','" + id1 + "','" + id2 + "','" + gen + "')"
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