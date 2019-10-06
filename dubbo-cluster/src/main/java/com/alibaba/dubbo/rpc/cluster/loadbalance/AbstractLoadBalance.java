/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.rpc.cluster.loadbalance;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.cluster.LoadBalance;

import java.util.List;

/**
 * AbstractLoadBalance
 * Dubbo提供了4种负载均衡实现:
 *
 * RandomLoadBalance: 基于权重随机算法
 * LeastActiveLoadBalance: 基于最少活跃调用数算法
 * ConsistentHashLoadBalance: 基于hash一致性
 * RoundRobinLoadBalance: 基于加权轮询算法
 * 
 * 该类实现了LoadBalance接口,是负载均衡的抽象类,提供了权重计算的功能
 *
 */
public abstract class AbstractLoadBalance implements LoadBalance {

    static int calculateWarmupWeight(int uptime, int warmup, int weight) {
        // 计算权重(uptime / warmup) * weight,进度百分比 * 权重
        int ww = (int) ((float) uptime / ((float) warmup / (float) weight));
        // 权重范围为[0, weight]之间
        return ww < 1 ? 1 : (ww > weight ? weight : ww);
    }

    /**
     * 该方法是选择一个invoker,关键的选择还是调用了doSelect方法,不过doSelect是一个抽象方法,由上述四种负载均衡策略来各自实现
     */
    @Override
    public <T> Invoker<T> select(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        // 如果invokers为空则返回空
        if (invokers == null || invokers.isEmpty())
            return null;
        // 如果invokers只有一个服务提供者,则返回一个
        if (invokers.size() == 1)
            return invokers.get(0);
        // 调用doSelect进行选择
        return doSelect(invokers, url, invocation);
    }

    protected abstract <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation);

    /**
     * 该方法是获得权重的方法,计算权重在calculateWarmupWeight方法中实现,该方法考虑到了jvm预热的过程
     * @return
     */
    protected int getWeight(Invoker<?> invoker, Invocation invocation) {
        // 获得weight配置,即服务权重,默认为100
        int weight = invoker.getUrl().getMethodParameter(invocation.getMethodName(), Constants.WEIGHT_KEY, Constants.DEFAULT_WEIGHT);
        if (weight > 0) {
            // 获得启动时间戳
            long timestamp = invoker.getUrl().getParameter(Constants.REMOTE_TIMESTAMP_KEY, 0L);
            if (timestamp > 0L) {
                // 获得启动总时长
                int uptime = (int) (System.currentTimeMillis() - timestamp);
                // 获得预热需要总时长,默认为10分钟
                int warmup = invoker.getUrl().getParameter(Constants.WARMUP_KEY, Constants.DEFAULT_WARMUP);
                // 如果服务运行时间小于预热时间,则重新计算服务权重,即降权
                if (uptime > 0 && uptime < warmup) {
                    weight = calculateWarmupWeight(uptime, warmup, weight);
                }
            }
        }
        return weight;
    }

}
