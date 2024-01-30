package com.ji.jichat.user.test;

import com.alibaba.fastjson.JSON;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Builder(toBuilder = true)
@SuperBuilder
 class ParentClass {
    private String parentProperty;

    @Builder.Default()
    private String name="ji";
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
 class ChildClass extends ParentClass {
    private String childProperty;
}

public class BuilderExample {
    public static void main(String[] args) {
        ChildClass childObject = ChildClass.builder()
                .childProperty("ChildValue")
                .parentProperty("ParentValue")
                .build();

        System.out.println(childObject.getParentProperty());  // Output: ParentValue
        System.out.println(childObject.getChildProperty());  // Output: ChildValue
        System.out.println(JSON.toJSONString(childObject));
    }
}
