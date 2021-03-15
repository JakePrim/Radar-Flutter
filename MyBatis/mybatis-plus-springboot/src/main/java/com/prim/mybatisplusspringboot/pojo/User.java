package com.prim.mybatisplusspringboot.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: MyBatis-PlusDemo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-15 11:29
 * @PackageName: com.prim.pojo
 * @ClassName: User.java
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
//@TableName("user")
public class User extends Model<User> implements Serializable {
    //    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    @TableField(select = false) // 查询不返回该字段的值
    private String password;

    @TableField(value = "birthday") //对应表中字段 解决字段名不一致问题
    private String birth;

    @TableField(exist = false) //表示该字段在数据库表中不存在
    private String address;

    @Version //关键
    @TableField(fill = FieldFill.INSERT) //标记为填充字段 具体填充的内容需要实现MetaObjectHandler
    private Integer version;

    @TableLogic //标记为逻辑删除字段
    private Integer isDelete;
}
