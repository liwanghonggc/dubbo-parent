package com.alibaba.dubbo.demo.provider.spi;

/**
 * @author lwh
 * @date 2019-09-13
 * @desp
 */
public class Bumblebee implements Robot{
    @Override
    public void sayHello() {
        System.out.println("Hello, I am Bumblebee.");
    }
}
