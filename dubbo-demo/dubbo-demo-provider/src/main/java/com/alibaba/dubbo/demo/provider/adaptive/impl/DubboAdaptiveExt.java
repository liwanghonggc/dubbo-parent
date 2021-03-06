package com.alibaba.dubbo.demo.provider.adaptive.impl;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.demo.provider.adaptive.AdaptiveExt;

/**
 * @author lwh
 * @date 2019-09-14
 * @desp
 */
public class DubboAdaptiveExt implements AdaptiveExt {

    private AdaptiveExt adaptiveExt;

    public void setAdaptiveExt(AdaptiveExt adaptiveExt) {
        this.adaptiveExt = adaptiveExt;
    }

    @Override
    public String echo(String msg, URL url) {
        System.out.println(this.adaptiveExt.echo(msg, url));
        return "dubbo";
    }
}
