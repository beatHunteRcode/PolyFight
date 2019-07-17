package sample.Network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int SERVER_PORT = 1488;


    public static void main(String[] args) throws IOException, InterruptedException {
        try(ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) { //открываем сокет на порту 1488
            Socket clientSocket = serverSocket.accept();                 //создаём принимающий клиентский сокет
            System.out.println("Клиент подключился");                   //подтверждаем подключение

            // как только выполнится строчка выше (клиент присоединиться к сокету сервера), пойдёт выполнение программы

            //создадим каналы для общения в сокете (между клиентом и сервером)
            DataInputStream input = new DataInputStream(clientSocket.getInputStream()); //канал записи в сокет
            System.out.println("Data Input Stream created");
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream()); //канал чтения из сокета
            System.out.println("Data Output Stream created");
            System.out.println();
            //следующий код будет выполняться, пока сокет не закроется (клиент не отключится)
            while (!clientSocket.isClosed()) {
                System.out.println("Сервер читает данные из канала...");
                String entryData = input.readUTF();
                System.out.println("Сервер прочитал: " + entryData);

                if (entryData.equalsIgnoreCase("exit")) {
                    System.out.println("Сервер отправил ответ: " + entryData);
                    output.writeUTF("Ответ сервера: Да, я услышал " + entryData);
                    output.flush();
                    System.out.println("Клиент разорвал соединение.");

                    //приказываем спать потоку, чтобы все соединения успели разорваться
                    Thread.sleep(3000);
                    input.close();
                    break;
                }
                System.out.println("Сервер отправил ответ: Да, я услышал " + entryData);
                System.out.println();
                output.writeUTF("Ответ сервера: Да, я услышал " + entryData);
                output.flush();
                //после того, как даннные записались в буфер с помощью метода writeUTF(), они не отправялются,
                //пока буфер не заполнится. Метод flush() позволяет отправить данные, которые уже есть в буфере
                //не дожидаясь полного заполнения буфера.
            }
            System.out.println("Закрываем все каналы...");
            input.close();
            output.close();
            System.out.println("Все соединения и каналы успешно закрыты!");

        }
    }
}



