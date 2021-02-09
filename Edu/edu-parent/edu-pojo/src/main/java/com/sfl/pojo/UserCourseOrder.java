package com.sfl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户课程订单表(UserCourseOrder)实体类
 *
 * @author sfl
 * @since 2021-02-07 08:47:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCourseOrder implements Serializable {
    private static final long serialVersionUID = -65104502377083609L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 用户id
     */
    private Object userId;
    /**
     * 课程id，根据订单中的课程类型来选择
     */
    private Object courseId;
    /**
     * 活动课程id
     */
    private Integer activityCourseId;
    /**
     * 订单来源类型: 1 用户下单购买 2 后台添加专栏
     */
    private Object sourceType;
    /**
     * 当前状态: 0已创建 10未支付 20已支付 30已取消 40已过期
     */
    private Object status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否删除
     */
    private Object isDel;
}
