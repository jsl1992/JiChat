package com.ji.jichat.chat.kit;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;

import java.util.*;

/**
 * 一致性hash
 *
 * @author jisl on 2023/12/27 10#08
 **/
public class ConsistentHashing {

    private final static TreeMap<Long, String> RING = new TreeMap<>();
    private final static List<String> NODES = new ArrayList<>();
    private static final int VIRTUAL_NODES = 10; // 每个物理节点对应的虚拟节点数量
    private final static String DELIMITER = "#";

    public static void addNode(String node) {
//        真实节点对应虚拟节点，增加hash均衡。
        for (int i = 0; i < VIRTUAL_NODES; i++) {
            String virtualNode = node + DELIMITER + i; // 创建虚拟节点
            long hash = hash(virtualNode);
            RING.put(hash, node);
        }
    }

    public static void addNode(List<String> nodes) {
//        节点变化，先清空旧的节点。再添加。
        NODES.clear();
        NODES.addAll(nodes);
//        维护的一致性hash也是
        RING.clear();
        for (String node : nodes) {
            addNode(node);
        }
    }

    public static void removeNode(String node) {
        for (int i = 0; i < VIRTUAL_NODES; i++) {
            String virtualNode = node + DELIMITER + i; // 创建虚拟节点
            long hash = hash(virtualNode);
            RING.remove(hash);
        }
    }

    private static String getNode() {
        return NODES.get(RandomUtil.randomInt(NODES.size()));
    }


    public static String getNode(String data) {
        if (RING.isEmpty()) {
            return getNode();
        }
        long hash = hash(data);
//        获取比当前hash小的key
        long key = Objects.isNull(RING.floorKey(hash)) ? RING.firstKey() : RING.floorKey(hash);
        String node = RING.get(key);
        return node;
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

    private static void showMap(ArrayList<String> dataList) {
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
