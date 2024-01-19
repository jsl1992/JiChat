package com.ji.jichat.chat.kit;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性hash
 *
 * @author jisl on 2023/12/27 10#08
 **/
public class  ConsistentHashing {

    private final static SortedMap<Long, String> ring = new TreeMap<>();
    private static final int VIRTUAL_NODES = 500; // 每个物理节点对应的虚拟节点数量

    public static void addNode(String node) {
//        真实节点对应虚拟节点，增加hash均衡。
        for (int i = 0; i < VIRTUAL_NODES; i++) {
            String virtualNode = node + "#" + i; // 创建虚拟节点
            long hash = hash(virtualNode);
            ring.put(hash, virtualNode);
        }
    }

    public static void removeNode(String node) {
        for (int i = 0; i < VIRTUAL_NODES; i++) {
            String virtualNode = node + "#" + i; // 创建虚拟节点
            long hash = hash(virtualNode);
            ring.remove(hash);
        }
    }

    public static String getNode(String data) {
        if (ring.isEmpty()) {
            return null;
        }

        long hash = hash(data);
        SortedMap<Long, String> tailMap = ring.tailMap(hash);

        if (tailMap.isEmpty()) {
            return ring.get(ring.firstKey());
        }
        return tailMap.get(tailMap.firstKey()).split("#")[0];
    }

    private static long hash(String key) {
        final long p = 16777619L;
        long hash = 2166136261L;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash ^ key.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return Math.abs(hash);
    }

    public static void showMap(ArrayList<String> dataList) {
        final HashMap<String, Integer> cnt = new HashMap<>();
        for (String data : dataList) {
            String node = getNode(data);
            cnt.put(node, cnt.getOrDefault(node, 0) + 1);
        }
        System.out.println(cnt);
    }

    public static void main(String[] args) {

        // 添加节点

        ConsistentHashing.addNode("Node1");
        ConsistentHashing.addNode("Node2");
        ConsistentHashing.addNode("Node3");
        ConsistentHashing.addNode("Node4");
        ConsistentHashing.addNode("Node5");

        // 数据映射到节点
        final ArrayList<String> dataList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            String data = "Data" + i;
            dataList.add(IdUtil.getSnowflake(RandomUtil.randomInt(32), 1).nextIdStr());
        }
        ConsistentHashing.showMap(dataList);
        System.out.println("=========addNode==============");
        ConsistentHashing.addNode("NodeX");
        ConsistentHashing.showMap(dataList);

        System.out.println("=========removeNode==============");
        ConsistentHashing.removeNode("Node1");
        ConsistentHashing.showMap(dataList);
    }
}
