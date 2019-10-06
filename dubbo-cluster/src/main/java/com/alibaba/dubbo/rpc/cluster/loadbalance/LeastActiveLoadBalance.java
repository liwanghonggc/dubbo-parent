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
import com.alibaba.dubbo.rpc.RpcStatus;

import java.util.List;
import java.util.Random;

/**
 * LeastActiveLoadBalance
 * 该负载均衡策略基于最少活跃调用数算法,某个服务活跃调用数越小,表明该服务提供者效率越高,
 * 也就表明单位时间内能够处理的请求更多.此时应该选择该类服务器.实现很简单,就是每一个服
 * 务都有一个活跃数active来记录该服务的活跃值,每收到一个请求,该active就会加1,,没完成一个
 * 请求,active就会减1.在服务运行一段时间后,性能好的服务提供者处理请求的速度更快,因此活跃
 * 数下降的也越快,此时这样的服务提供者能够优先获取到新的服务请求.除了最小活跃数,还引入了权
 * 重值,也就是当活跃数一样的时候,选择利用权重法来进行选择,如果权重也一样,那么随机选择一个.
 */
public class LeastActiveLoadBalance extends AbstractLoadBalance {

    public static final String NAME = "leastactive";

    private final Random random = new Random();

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        // 获得服务长度
        int length = invokers.size();
        // 最小的活跃数
        int leastActive = -1;
        // 具有相同“最小活跃数”的服务者提供者(以下用Invoker代称)数量
        int leastCount = 0;
        // leastIndexs用于记录具有相同“最小活跃数”的Invoker在invokers列表中的下标信息
        int[] leastIndexs = new int[length];

        // 总的权重
        int totalWeight = 0;

        // 第一个最小活跃数的Invoker权重值,用于与其他具有相同最小活跃数的Invoker的权重进行对比,
        // 以检测是否“所有具有相同最小活跃数的Invoker的权重”均相等
        int firstWeight = 0;

        // 是否权重相同
        boolean sameWeight = true;
        for (int i = 0; i < length; i++) {
            Invoker<T> invoker = invokers.get(i);
            // 获取Invoker对应的活跃数
            int active = RpcStatus.getStatus(invoker.getUrl(), invocation.getMethodName()).getActive();
            // 获得该服务的权重
            int weight = invoker.getUrl().getMethodParameter(invocation.getMethodName(), Constants.WEIGHT_KEY, Constants.DEFAULT_WEIGHT);
            // 发现更小的活跃数,重新开始, Restart, when find a invoker having smaller least active value.
            if (leastActive == -1 || active < leastActive) {
                // 记录当前最小的活跃数
                leastActive = active;
                // 更新leastCount为1
                leastCount = 1;
                // 记录当前下标值到 leastIndexs 中
                leastIndexs[0] = i;
                totalWeight = weight;
                firstWeight = weight;
                sameWeight = true;
            }
            // 如果当前Invoker的活跃数active与最小活跃数leastActive相同
            else if (active == leastActive) {
                // 在leastIndexs中记录下当前Invoker在invokers集合中的下标
                leastIndexs[leastCount++] = i;
                // 累加权重
                totalWeight += weight; 
                if (sameWeight && i > 0
                        && weight != firstWeight) {
                    sameWeight = false;
                }
            }
        }
        // 当只有一个Invoker具有最小活跃数,此时直接返回该Invoker即可
        if (leastCount == 1) {
            // If we got exactly one invoker having the least active value, return this invoker directly.
            return invokers.get(leastIndexs[0]);
        }
        // 有多个Invoker具有相同的最小活跃数,但它们之间的权重不同
        if (!sameWeight && totalWeight > 0) {
            // If (not every invoker has the same weight & at least one invoker's weight>0), select randomly based on totalWeight.
            // 随机生成一个数字
            int offsetWeight = random.nextInt(totalWeight);
            // Return a invoker based on the random value.
            // 相关算法可以参考RandomLoadBalance
            for (int i = 0; i < leastCount; i++) {
                int leastIndex = leastIndexs[i];
                offsetWeight -= getWeight(invokers.get(leastIndex), invocation);
                if (offsetWeight <= 0)
                    return invokers.get(leastIndex);
            }
        }
        // If all invokers have the same weight value or totalWeight=0, return evenly.
        // 如果权重一样,则随机取一个
        return invokers.get(leastIndexs[random.nextInt(leastCount)]);
    }
}
