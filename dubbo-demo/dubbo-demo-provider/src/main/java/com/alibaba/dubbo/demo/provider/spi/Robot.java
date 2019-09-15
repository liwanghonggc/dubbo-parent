package com.alibaba.dubbo.demo.provider.spi;

import com.alibaba.dubbo.common.extension.SPI;

/**
 * @author lwh
 * @date 2019-09-13
 * @desp
 */
@SPI
public interface Robot {
    void sayHello();
}