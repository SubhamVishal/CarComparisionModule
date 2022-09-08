package com.intuit.car.comparison.store;

import lombok.Data;

import java.util.*;

public class CarStoreCache {
    private Map<String, ClusterNode> clusterCache = new HashMap<>();
    private Map<String, Set<String>> reverseIndex = new HashMap<>();

    private CarStoreCache() {

    }

    private static final class InstanceHolder {
        static final CarStoreCache INSTANCE = new CarStoreCache();
    }

    public static CarStoreCache getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public Set<String> getCarCluster(String carId) {
        if (!this.clusterCache.containsKey(carId)) {
            return null;
        } else {
            long currentTimeEpoch = System.currentTimeMillis();
            ClusterNode clusterNode = clusterCache.get(carId);
            if (currentTimeEpoch > clusterNode.getStoreTime() + clusterNode.getExpireAfterMillis()) {
                clusterCache.remove(carId);
                return null;
            } else {
                return clusterNode.getValues();
            }
        }
    }

    public void removeCarIdFromCluster(String carId) {
        if (clusterCache.containsKey(carId)) {
            clusterCache.remove(carId);
        }
        if (reverseIndex.containsKey(carId)) {
            Set<String> clusterIds = reverseIndex.get(carId);
            for (String clusterId : clusterIds) {
                removeCarIdFromCluster(clusterId, carId);
            }
        }
    }

    private void removeCarIdFromCluster(String clusterId, String carIdToRemove) {
        if (!this.clusterCache.containsKey(clusterId)) {
            return;
        } else {
            ClusterNode clusterNode = clusterCache.get(clusterId);
            clusterNode.getValues().remove(carIdToRemove);
        }
    }

    public void putClusterToCache(String carId, Set<String> similarCarIds, long expireAfterMillis) {
        ClusterNode clusterNode = new ClusterNode();
        clusterNode.setStoreTime(System.currentTimeMillis());
        clusterNode.setKey(carId);
        clusterNode.setValues(similarCarIds);
        clusterNode.setExpireAfterMillis(expireAfterMillis);
        clusterCache.put(carId, clusterNode);
        for (String similarCarId : similarCarIds) {
            if (!reverseIndex.containsKey(similarCarId)) {
                reverseIndex.put(similarCarId, new HashSet<>());
            }
            reverseIndex.get(similarCarId).add(carId);
        }
    }

    @Data
    static class ClusterNode {
        private String key;
        private long storeTime;
        private long expireAfterMillis;
        private Set<String> values;
    }
}
