package com.jakeprim.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 课程类
 */
@Data
public class Course implements Serializable {

    //使用 JSONField 设置ordinal的值,来对转换成的JSON数据进行排序
    //课程ID
    @JSONField(ordinal = 1)
    private int id;

    //课程名称
    @JSONField(ordinal = 2)
    private String course_name;

    //课程介绍
    @JSONField(ordinal = 3)
    private String brief;

    //讲师名称
    @JSONField(ordinal = 4)
    private String teacher_name;

    //讲师介绍
    @JSONField(ordinal = 5)
    private String teacher_info;

    //课程原价
    @JSONField(ordinal = 6)
    private double price;

    //原价标签
    @JSONField(ordinal = 7)
    private String price_tag;

    //课程优惠价
    @JSONField(ordinal = 8)
    private double discounts;

    //课程概述
    @JSONField(ordinal = 9)
    private String preview_first_field;

    //课程概述第二个字段
    @JSONField(ordinal = 10)
    private String preview_second_field;

    //分享图片url
    @JSONField(ordinal = 11)
    private String course_img_url;

    //分享标题
    @JSONField(ordinal = 12)
    private String share_title;

    //分享描述
    @JSONField(ordinal = 13)
    private String share_description;

    //课程描述
    @JSONField(ordinal = 14)
    private String course_description;

    //排序
    @JSONField(ordinal = 15)
    private int sort_num;

    //课程状态,0-草稿,1-上架
    @JSONField(ordinal = 16)
    private int status;

    //创建时间
    @JSONField(ordinal = 17)
    private String create_time;

    //修改时间
    @JSONField(ordinal = 18)
    private String update_time;

    //是否删除
    @JSONField(ordinal = 19)
    private int isDel;

    @JSONField(ordinal = 20)
    private String share_image_title; //分享图title


    //使用JSONField(serialize = false)排除不需要转换的字段

    @JSONField(serialize = false)
    private int total_course_time; //课时数

    @JSONField(serialize = false)
    private int sales; //显示销量

    @JSONField(serialize = false)
    private int actual_sales; //真实销量

    @JSONField(serialize = false)
    private int is_new; //是否新品

    @JSONField(serialize = false)
    private String is_new_des; //广告语

    @JSONField(serialize = false)
    private int last_operator_id; //最后操作者

    @JSONField(serialize = false)
    private int total_duration; //总时长

    @JSONField(serialize = false)
    private long course_type; //课程类型

    @JSONField(serialize = false)
    private String last_notice_time;  //最后课程最近通知时间

    @JSONField(serialize = false)
    private long is_gray; //是否是灰度课程

    @JSONField(serialize = false)
    private long grade; //级别

    //添加list集合 章节表 1:N
    private List<Course_Section> sectionList = new ArrayList<>();


}
