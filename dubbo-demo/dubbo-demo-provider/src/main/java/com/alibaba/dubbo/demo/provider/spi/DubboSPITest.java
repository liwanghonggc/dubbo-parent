package com.alibaba.dubbo.demo.provider.spi;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import org.junit.Test;

import java.util.ServiceLoader;

/**
 * @author lwh
 * @date 2019-09-13
 * @desp
 */
public class DubboSPITest {

    @Test
    public void sayHello() throws Exception {
        ServiceLoader<Robot> serviceLoader = ServiceLoader.load(Robot.class);
        System.out.println("Java SPI");
        serviceLoader.forEach(Robot::sayHello);
    }

    @Test
    public void sayHelloDubbo() throws Exception {
        ExtensionLoader<Robot> extensionLoader =
                ExtensionLoader.getExtensionLoader(Robot.class);
        Robot optimusPrime = extensionLoader.getExtension("optimusPrime");
        optimusPrime.sayHello();
        Robot bumblebee = extensionLoader.getExtension("bumblebee");
        bumblebee.sayHello();
    }
}
