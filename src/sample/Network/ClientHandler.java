package sample.Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket clientSocket;

    ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            //Создаём каналы общения

            //канал для записи
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
            System.out.println("Канал для записи создан.");

            //канал для чтения
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            System.out.println("Канал для чтения создан");

            System.out.println();

            //пока сокет не закрыт, общаемся с клиентом
            while (!clientSocket.isClosed()) {
                System.out.println("Сервер начал читать канал...");

                //считываем полученные данные
                String entryData = input.readUTF();

                //если клиент отослал exit - клиент отключается
                if (entryData.equalsIgnoreCase("exit")) {
                    System.out.println("Клиент отключился.");
                    output.writeUTF("Прощание сервера: Надеюсь, в следующий раз ты не отправишь " + entryData);
                    output.flush();
                    Thread.sleep(2000);
                    input.close();
                    break;
                }

                //если клиент не отослал exit - продолжаем работу
                //отсылаем ответ сервера
                output.writeUTF("Ответ сервера: Да, я услышал " + entryData);
                System.out.println("Сервер отослал ответ клиенту: Да, я услышал " + entryData);

                //освобождаем буфер сообщений
                output.flush();
                System.out.println();
            }

            //если сокет закрылся - закрываем все соединения и каналы
            System.out.println("Закрываем все каналы общения...");
            input.close();
            System.out.println("Канал чтения закрыт.");
            output.close();
            System.out.println("Канал записи закрыт.");
        }
        catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
