package sample.Network;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//класс для имитации множественного подключения к серверу
public class Main {
    public static void main(String[] args) throws InterruptedException {

        //создаем пул Thread'ов и играничиваем его 10-ю Thread'ами
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        //запускаем цикл, в котором каждые 2 секунды к серверу будет подключаться один клиент
        for (int i = 0; i < 10; i++) {
            executorService.execute(new RunnableClientTester());
            i++;
            Thread.sleep(2000);
        }

        //как только все клиенты подключились - заканичваем работу
        executorService.shutdown();
    }
}
