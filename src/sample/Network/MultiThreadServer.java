package sample.Network;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadServer {

    public static final int SERVER_PORT = 1337;
    //устанавливаем кол-во клиентов, которые смогут подключиться к сокету
    static ExecutorService executorServiceForClient = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws IOException {
        //создаём сокет, по которому будет присоединяться клиент с портом 1337
        try(ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            //создаём возможность ввода с клавиатуры
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {

            while (!serverSocket.isClosed()) {
                //проверяем, поступили ли команды от консоли сервера(пополнился ли bufferedReader)
                if (bufferedReader.ready()) {
                    System.out.println("Сервер начал читать канал...");
                    String serverCommand = bufferedReader.readLine();
                    if (serverCommand.equalsIgnoreCase("exit")) {
                        System.out.println("Сервер заканчивает работу...");
                        break;
                    }
                }

                //Если команд от сервера нет, начинаем ждать подключения клиента
                Socket clientSocket = serverSocket.accept();

                //После того, как клиент подключился к сокету, сервер направляет его в отдельный Thread
                //Общение с каждым клиентом происходит через класс ClientHandler
                executorServiceForClient.execute(new ClientHandler(clientSocket));
                System.out.println("Соединение подтверждено.");
            }

            //После того, как сокет сервера закрылся, закрываем все Thread'ы
            executorServiceForClient.shutdown();
        }
    }
}
