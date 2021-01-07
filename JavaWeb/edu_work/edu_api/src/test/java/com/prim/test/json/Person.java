package com.prim.test.json;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    //name 指定字段名 ordinal 指定输出顺序
    @JSONField(name = "USERNAME",ordinal = 1)
    private String username;

    @JSONField(name = "AGE",ordinal = 2)
    private int age;

    //不进行 序列化
    @JSONField(serialize = false)
    private String birthday;
}
