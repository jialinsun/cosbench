/** 
 
Copyright 2013 Intel Corporation, All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. 
*/ 

package com.intel.cosbench.bench;

import com.intel.cosbench.utils.MapRegistry.Item;


/**
 * The class represents the overall performance metrics per each type.
 * 
 * @author ywang19, qzheng7
 *
 */
public class Metrics implements Item, Cloneable {

    private String name; /* metrics id */

    /* Type */

    private String opType; /* operation type */
    private String sampleType; /* sample type */

    /* Status */

    private int sampleCount; /* number of successful samples */
    private int totalSampleCount; /* total operations issued */
    private long byteCount; /* total bytes transferred */
    private int workerCount; /* total workers involved */

    /* Metrics */

    private double avgResTime; /* average response time */
    private double throughput; /* operation throughput */
    private double bandwidth; /* network bandwidth */

    /* Latency Details */
    private Histogram latency; /* detailed latency metrics */

    public Metrics() {
        /* empty */
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public int getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(int sampleCount) {
        this.sampleCount = sampleCount;
    }

    public int getTotalSampleCount() {
        return totalSampleCount;
    }

    public void setTotalSampleCount(int totalSampleCount) {
        this.totalSampleCount = totalSampleCount;
    }

    public long getByteCount() {
        return byteCount;
    }

    public void setByteCount(long byteCount) {
        this.byteCount = byteCount;
    }

    public int getWorkerCount() {
        return workerCount;
    }

    public void setWorkerCount(int workerCount) {
        this.workerCount = workerCount;
    }

    public double getAvgResTime() {
        return avgResTime;
    }

    public void setAvgResTime(double avgResTime) {
        this.avgResTime = avgResTime;
    }

    public double getThroughput() {
        return throughput;
    }

    public void setThroughput(double throughput) {
        this.throughput = throughput;
    }

    public double getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(double bandwidth) {
        this.bandwidth = bandwidth;
    }

    public Histogram getLatency() {
        return latency;
    }

    public void setLatency(Histogram latency) {
        this.latency = latency;
    }

    @Override
    public Metrics clone() {
        try {
            return (Metrics) super.clone();
        } catch (CloneNotSupportedException e) {
        }
        return this;
    }

    public static String getMetricsType(String opType, String sampleType) {
        return opType + "-" + sampleType;
    }

    public static Metrics newMetrics(String type) {
        String[] types = type.split("-");
        Metrics metrics = new Metrics();
        metrics.setName(type);
        metrics.setOpType(types[0]);
        metrics.setSampleType(types[1]);
        return metrics;
    }

    public static Metrics convert(Mark mark, long window) {
        int sps = mark.getSampleCount();
        int tsps = mark.getTotalSampleCount();
        long rtSum = mark.getRtSum();
        long bytes = mark.getByteCount();
        String type = getMetricsType(mark.getOpType(), mark.getSampleType());
        Metrics metrics = newMetrics(type);
        metrics.setSampleCount(sps);
        metrics.setTotalSampleCount(tsps);
        metrics.setByteCount(bytes);
        metrics.setWorkerCount(1);
        metrics.setAvgResTime(rtSum > 0 ? ((double) rtSum) / sps : 0);
        metrics.setThroughput(sps > 0 ? ((double) sps) / window * 1000 : 0);
        metrics.setBandwidth(bytes > 0 ? ((double) bytes) / window * 1000 : 0);
        return metrics;
    }

}
