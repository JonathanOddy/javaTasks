import proxy.CacheProxy;
import service.Service;
import service.ServiceImpl;
import java.lang.reflect.Proxy;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        Service service = new ServiceImpl();

        Service serviceProxy = (Service) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(), service.getClass().getInterfaces(), new CacheProxy(service)
        );

        String[] affairs = {"coding", "reading", "cooking"};
        double[] hours = {1, 2};

        IntStream.range(0,5).forEach(i -> work(serviceProxy, affairs, hours));
    }

    private static void work(Service serviceProxy, String[] affairs, double[] hours) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (String affair : affairs) {
            executorService.execute(() -> serviceProxy.work(affair));
            for (double hour : hours) {
                executorService.execute(() -> serviceProxy.run(affair, hour, LocalDate.now()));
            }
        }
        executorService.shutdown();
    }
}
