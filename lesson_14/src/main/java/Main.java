import proxy.CacheProxy;
import service.Service;
import service.ServiceImpl;

import java.lang.reflect.Proxy;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        Service service = new ServiceImpl();

        Service serviceProxy = (Service) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(), service.getClass().getInterfaces(), new CacheProxy(service)
        );

        String[] affairs = {"coding", "reading", "cooking"};
        double[] hours = {1, 2};

        work(serviceProxy, affairs, hours);
        work(serviceProxy, affairs, hours);
    }

    private static void work(Service serviceProxy, String[] affairs, double[] hours) {
        for (String affair : affairs) {
            serviceProxy.work(affair);
            for (double hour : hours) {
                serviceProxy.run(affair, hour, LocalDate.now());
            }
        }
        System.out.println();
    }
}
