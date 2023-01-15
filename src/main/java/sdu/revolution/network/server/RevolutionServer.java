package sdu.revolution.network.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RevolutionServer extends Thread {

    public static class Logger {
        public static void info(Object x) {
            System.out.println("\033[0m[\033[31mRevolution Server\033[0m] \033[33m" + new Date() + "\033[0m | Info : \033[32m" + x.toString() + "\033[0m");
        }

        public static void info(Object object, Object x) {
            System.out.println("\033[0m[\033[31mRevolution Server\033[0m] \033[33m" + new Date() + "\033[0m | Info from " + object.getClass().getSimpleName() + " : \033[32m" + x.toString() + "\033[0m");
        }

        public static void info(Class<?> object, Object x) {
            System.out.println("\033[0m[\033[31mRevolution Server\033[0m] \033[33m" + new Date() + "\033[0m | Info from " + object.getSimpleName() + " : \033[32m" + x.toString() + "\033[0m");
        }
    }

    public Socket server;
    private DataInputStream in;
    private DataOutputStream out;

    public static List<PlayerEntity> playerList;

    public RevolutionServer(Socket server) throws IOException {
        this.server = server;
    }

    public boolean register(String args) {
        boolean canBeEstablished = true;
        for (var i : playerList) {
            if (i.name.equalsIgnoreCase(args)) {
                canBeEstablished = false;
                break;
            }
        }
        if (!canBeEstablished)
            return false;
        playerList.add(new PlayerEntity(args, server.getRemoteSocketAddress()));
        Logger.info(args + " from " + server.getRemoteSocketAddress() + " have registered.");
        return true;
    }

    public int receiveMessage() throws IOException {
        String val = in.readUTF();
        if (val.equalsIgnoreCase("goodbye"))
            return 0;
        boolean status = false;
        List<String> args = List.of(val.split(" "));
        switch (args.get(0)) {
            case "register" -> {
                status = register(args.get(1));
            }
        }
        return status ? 1 : -1;
    }

    public void run() {
        for (; ; ) {
            try {
                in = new DataInputStream(server.getInputStream());
                out = new DataOutputStream(server.getOutputStream());
                while (true) {
                    int status = receiveMessage();
                    if (status == 0)
                        break;
                    if (status == 1)
                        out.writeUTF("success");
                    else
                        out.writeUTF("failed");
                }
                server.close();
            } catch (SocketTimeoutException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

        Logger.info("Server exited normally.");
    }

    public static void main(String[] args) {
        playerList = new ArrayList<>();
        try {
            @SuppressWarnings("resource")
            ServerSocket socketServer = new ServerSocket(47332);
            Logger.info("Waiting for connection, port: " + socketServer.getLocalPort() + ".");
            //noinspection InfiniteLoopStatement
            for (; ;) {
                Socket socket = socketServer.accept();
                Logger.info("Connection established with client " + socket.getRemoteSocketAddress() + ".");
                new RevolutionServer(socket).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
