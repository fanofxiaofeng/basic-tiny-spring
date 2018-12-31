package com.study.spring.util;

import java.util.*;

public class TopologicalSort {

    private Map<Class, List<Class>> adjacentMap;

    private List<Class> post = new LinkedList<>();

    private Set<Class> visited = new HashSet<>();


    public TopologicalSort(Map<Class, List<Class>> adjacentMap) {
        this.adjacentMap = adjacentMap;
    }

    public List<Class> run() {
        if (adjacentMap == null || adjacentMap.size() == 0) {
            throw new RuntimeException("this.adjacentMap should not be null or empty!");
        }

        Set<Class> keySet = adjacentMap.keySet();

        for (Class clazz : keySet) {
            explore(clazz);
        }

        return post;
    }


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
