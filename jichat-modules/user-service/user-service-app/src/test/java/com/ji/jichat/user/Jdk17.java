package com.ji.jichat.user;

import org.junit.jupiter.api.Test;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

/**
 * jdk 17:模块化功能
 *
 * @author jishenglong on 2022/6/17 11:32
 */
public class Jdk17 {


    @Test
    public void nullPointerException() {
//        Java 17 提供了更详细的 NullPointerException 信息，指出具体的空值来源，这有助于调试空指针问题
        String str = null;
        str.toString(); // 将抛出带有详细信息的 NullPointerException
    }

    @Test
    public void RandomGenerator() {
//        Java 17 增强了伪随机数生成器的支持，提供了新的接口 RandomGenerator，以及一些常用的随机数生成算法（如 LXM、Xoshiro 和 SplitMix）。
//        RandomGenerator 是在 Java 17 中引入的新的随机数生成器接口。它的出现旨在提供一个更现代化、灵活性更高的随机数生成方式，解决传统 java.util.Random 类的部分局限性。
        RandomGenerator generator = RandomGeneratorFactory.of("Xoshiro256PlusPlus").create();
        System.out.println(generator.nextInt());

    }


    @Test
    public void text() {
//        文本块在 Java 13 引入，但在 Java 17 中进一步增强。文本块可以包含多行字符串内容，并支持格式化和转义
        String json = """
                {
                    "name": "John",
                    "age": 30
                }
                """;
        System.out.println(json);
    }


}
