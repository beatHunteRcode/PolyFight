package sample.Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RunnableClientTester implements Runnable {

    private Socket clientSocket;
    //создаём конструктор, в котором сразу создаём сокет для каждого клиента
    public RunnableClientTester() {
        try {
            clientSocket = new Socket("localhost", MultiThreadServer.SERVER_PORT);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }
    @Override
    public void run() {
            try {
                //создаём каналы для записи и чтения
                DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
                System.out.println("Канал для записи создан");
                DataInputStream input = new DataInputStream(clientSocket.getInputStream());
                System.out.println("Канал для чтения создан");


                for (int i = 0; i < 5; i++) {
                    output.writeUTF("clientCommand " + i + 1);
                    output.flush();
                    Thread.sleep(100);
                    System.out.println("Клиент отослал команду " + i + 1);

                    System.out.println("Клиент ждёт ответа от сервера...");
                    Thread.sleep(1000);
                    String entryData = input.readUTF();
                    System.out.println("Ответ сервера: " + entryData);
                }

            }
            catch (IOException | InterruptedException exception) {
                exception.printStackTrace();
            }
    }
}
