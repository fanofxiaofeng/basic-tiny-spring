package com.study.spring.util;

import java.util.*;

/**
 * 通过深度优先搜索来完成拓扑排序
 */
public class TopologicalSort {

    /**
     * 其中保存邻接表的信息
     */
    private Map<Class, List<Class>> adjacentMap;

    /**
     * 进行深度优先搜索时, 已完成操作的节点会被依次放置在其中
     * run()方法运行结束时, 倒序后的 post 就是一个拓扑排序
     * 但是由于本项目需要的是拓扑排序结果的倒序, 所以原始顺序的 post 刚好满足要求
     */
    private List<Class> post = new LinkedList<>();

    /**
     * 已经被访问过的节点(一个节点就是一个类)
     */
    private Set<Class> visited = new HashSet<>();

    public TopologicalSort(Map<Class, List<Class>> adjacentMap) {
        this.adjacentMap = adjacentMap;
    }

    /**
     * 进行拓扑排序, 并将拓扑排序的结果的倒序返回
     *
     * @return 拓扑排序的结果的倒序
     */
    public List<Class> run() {
        // 假设所有会用到的节点都会出现在邻接表中
        if (adjacentMap == null || adjacentMap.size() == 0) {
            throw new RuntimeException("this.adjacentMap should not be null or empty!");
        }

        Set<Class> keySet = adjacentMap.keySet();

        for (Class clazz : keySet) {
            explore(clazz);
        }

        return post;
    }

    /**
     * 从指定的起始节点开始进行深度优先搜索
     *
     * @param start 指定的起始节点
     */
    private void explore(Class start) {
        if (visited.contains(start)) {
            return;
        }

        visited.add(start);
        if (!adjacentMap.containsKey(start)) {
            post.add(start);
            return;
        }

        for (Class to : adjacentMap.get(start)) {
            if (visited.contains(to)) {
                continue;
            }
            explore(to);
        }
        post.add(start);
    }
}
