package com.alibaba.dubbo.demo.provider.activate.impl;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.demo.provider.activate.ActivateExt;

/**
 * @author lwh
 * @date 2019-10-05
 * @desp
 */
@Activate(value = {"value"}, group = {"group"})
public class ValueActivateExtImpl implements ActivateExt {

    @Override
    public String echo(String msg) {
        return msg;
    }
}
