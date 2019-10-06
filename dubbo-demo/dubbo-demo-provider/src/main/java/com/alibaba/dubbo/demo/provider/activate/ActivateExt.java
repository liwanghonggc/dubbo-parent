package com.alibaba.dubbo.demo.provider.activate;

import com.alibaba.dubbo.common.extension.SPI;

/**
 * @author lwh
 * @date 2019-10-05
 * @desp
 */
@SPI
public interface ActivateExt {

    String echo(String msg);
}
