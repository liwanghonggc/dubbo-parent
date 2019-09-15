package com.alibaba.dubbo.demo.provider.adaptive;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;

/**
 * @author lwh
 * @date 2019-09-14
 * @desp
 */
@SPI("dubbo")
public interface AdaptiveExt {

    @Adaptive("t")
    String echo(String msg, URL url);
}
