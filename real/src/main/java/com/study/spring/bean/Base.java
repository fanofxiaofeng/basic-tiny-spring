package com.study.spring.bean;

import com.google.common.base.Joiner;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public abstract class Base {
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        String simpleName = this.getClass().getSimpleName();
        builder.append(simpleName);

        List<String> content = new LinkedList<>();
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                content.add(String.format("%s = %s", field.getName(), field.get(this)));
            } catch (IllegalAccessException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        Joiner joiner = Joiner.on(", ");
        builder.append(String.format("[%s]", joiner.join(content)));
        return builder.toString();
    }
}
