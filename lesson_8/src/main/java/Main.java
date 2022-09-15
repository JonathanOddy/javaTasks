import proxy.CacheProxy;
import service.Service;
import service.ServiceImpl;

import java.io.IOException;
import java.lang.reflect.Proxy;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Service service = new ServiceImpl();

        Service serviceProxy = (Service) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(), service.getClass().getInterfaces(), new CacheProxy(service)
        );

//        serviceProxy.work("coding");
//        serviceProxy.work("coding");
//        serviceProxy.work("reading");
//        serviceProxy.work("learning");
        serviceProxy.work("1");
        serviceProxy.work("2");
        serviceProxy.work("3");
        serviceProxy.work("4");
        serviceProxy.work("5");
        serviceProxy.work("6");
        serviceProxy.work("7");


//        serviceProxy.run("cooking",1.,LocalDate.now());
//        serviceProxy.run("cooking",2.,LocalDate.now());
//        serviceProxy.run("cooking",1.,LocalDate.now());


    }

}
