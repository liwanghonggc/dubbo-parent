package com.alibaba.dubbo.demo.provider.adaptive.impl;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.demo.provider.adaptive.AdaptiveExt;

/**
 * @author lwh
 * @date 2019-09-15
 * @desp
 */
public class AdaptiveExtWrapper2 implements AdaptiveExt {

    private AdaptiveExt adaptiveExt;

    public AdaptiveExtWrapper2(AdaptiveExt adaptiveExt) {
        this.adaptiveExt = adaptiveExt;
    }

    @Override
    public String echo(String msg, URL url) {
        // do something,实现了AOP
        System.out.println("before2");

        adaptiveExt.echo(msg, url);

        System.out.println("after2");
        // do something
        return "wrapper";
    }
}
