package com.ji.jichat.chat;

public class GenericObject<T> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static void main(String[] args) {
        // 使用泛型对象
        GenericObject<String> stringObject = new GenericObject<>();
        stringObject.setData("Hello, Generics!");
        System.out.println("String Data: " + stringObject.getData());

        GenericObject<Integer> integerObject = new GenericObject<>();
        integerObject.setData(42);
        System.out.println("Integer Data: " + integerObject.getData());

        GenericObject<Double> doubleObject = new GenericObject<>();
        doubleObject.setData(3.14);
        System.out.println("Double Data: " + doubleObject.getData());
    }
}
