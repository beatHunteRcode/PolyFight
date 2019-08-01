package sample.Network;

import java.io.*;
import java.net.Socket;

public class Client {


    public static void main(String[] args) throws IOException, InterruptedException {
            try(Socket clientSocket = new Socket("localhost", Server.SERVER_PORT)) {

                //System.in позволяет считывать ввод данных с клавиатуры
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

                //создаём каналы для чтения и записи
                DataInputStream input = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

                System.out.println("Клиент присоединился к серверу. Порт: " + Server.SERVER_PORT);

                //Если канал живой (часть отвечающая за запись данных жива) - отсылаем данные на сервер
                while (!clientSocket.isOutputShutdown()) {
                    if (bufferedReader.ready()) {
                        System.out.println("Клиент начал записывать данные в буфер...");
                        String clientData = bufferedReader.readLine();
                        output.writeUTF(clientData);
                        output.flush();
                        System.out.println("Клиент отправил данные: " + clientData);
                        //ставим поток на паузу, чтобы сервер успел прочитать данные
                        Thread.sleep(2000);

                        if (clientData.equalsIgnoreCase("exit")) {
                            System.out.println("Клиент разорвал соединение.");
                            //ставим поток на паузу, чтобы сервер успел отослать ответ, если он будет
                            Thread.sleep(2000);

                            //последний раз проверяем, не ответит ли нам что-нибудь сервер
                            checkServerData(input);
                            output.close();
                            break;
                        }
                        System.out.println("Клиент отправил данные и ждет ответа от сервера...");
                        //ставим клиент на паузу, чтобы сервер успел ответить
                        Thread.sleep(2000);
                        checkServerData(input);
                    }
                }
                System.out.println("Клиент отсоединился. Закрываем все каналы...");
                input.close();
                System.out.println("Все каналы клиента закрыты");


            }
    }

    public static void checkServerData(DataInputStream input) throws IOException {
        System.out.println("Клиент считывает данные с сервера...");
        String serverInput = input.readUTF();
        System.out.println(serverInput);
        System.out.println();
    }
}
