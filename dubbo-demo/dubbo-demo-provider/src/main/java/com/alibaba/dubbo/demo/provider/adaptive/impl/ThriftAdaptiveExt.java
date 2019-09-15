package com.alibaba.dubbo.demo.provider.adaptive.impl;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.demo.provider.adaptive.AdaptiveExt;

/**
 * @author lwh
 * @date 2019-09-14
 * @desp
 */
@Adaptive
public class ThriftAdaptiveExt implements AdaptiveExt {
    @Override
    public String echo(String msg, URL url) {
        return "thrift";
    }
}
