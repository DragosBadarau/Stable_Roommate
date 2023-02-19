package server;

import commands.Command;
import commands.Login;
import commands.Register;
import utils.Database;
import commands.ReadCSV;

import java.io.*;
import java.net.*;

public class ClientThread extends Thread
{
    private static Integer TIMEOUT_TIME = 3600_000;
    private Socket socket;
    private Database dbc;

    public ClientThread(Socket socket, Database dbc)
    {
        this.socket = socket;
        this.dbc = dbc;
    }

    public void run()
    {
        try
        {
            socket.setSoTimeout(TIMEOUT_TIME);
            while (true)
            {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                String request = in.readLine();

                if (request == null)
                    break;

                PrintWriter out = new PrintWriter(socket.getOutputStream());
                if (request.equals("stop"))
                {
                    out.println("Server stopped");
                    out.flush();
                    System.exit(0);
                } else
                {
                    String response = interpretCommand(request);
                    out.println(response);
                    out.flush();
                }
            }
        }
        catch (SocketTimeoutException e)
        {
            System.out.printf("%d seconds passed since last request. Timed out.\n", TIMEOUT_TIME / 1000);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private String interpretCommand(String string) {
        String[] command = string.trim().toLowerCase().split(" ");
        Command com;
        String result;
        switch (command[0])
        {
            case "register":
                com = new Register(command[1], this.dbc.getConnection());
                result = com.run();
                if(result.equals("succ")) {
                    System.out.println("Registered " + command[1]);
                    return "Registered successfully";
                }
                else {
                    System.err.println("Could not register " + command[1]);
                    return "Could not register";
                }
            case "login":
                com = new Login(command[1],  this.dbc.getConnection());
                result = com.run();
                if(result.equals("succ")) {
                    System.out.println("Logged in " + command[1]);
                    return "Logged in as " + command[1];
                }
                else {
                    System.err.println("Could not log in " + command[1]);
                    return "Could not log in";
                }
            case "insert":
                com = new ReadCSV(this.dbc.getConnection());
                result = com.run();
                if(result.equals("succ")) {
                    System.out.println("Data inserted successfully.");
                    return "Data inserted successfully.";
                }
                else {
                    System.err.println("Could not insert data. Check scripts and try again.");
                    return "Could not insert data. Check scripts and try again.";
                }
            default:
                System.err.println("Command not recognized");
                return "Command not recognized";
        }
    }
}