package server;


import utils.Database;

import java.net.*;
import java.io.*;
import java.sql.*;


public class Server
{
    public static final int PORT = 8081;
    private Database connection;

    public Server() throws IOException, SQLException {
        connection = Database.getDBInstance();
        Database.runScript(); //-> this command is executed only once, in the beginning stage

        ServerSocket serverSocket = null;

        try
        {
            serverSocket = new ServerSocket(PORT);
            while (true)
            {
                System.out.println("Waiting for a client");
                Socket socket = serverSocket.accept();
                new ClientThread(socket, connection).start();
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            if (serverSocket != null)
                serverSocket.close();
        }
    }
}