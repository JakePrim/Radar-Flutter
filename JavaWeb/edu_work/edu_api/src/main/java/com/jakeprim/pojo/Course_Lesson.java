package com.jakeprim.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/*
* 课时信息类
* */
@Data
public class Course_Lesson  implements Serializable {

  //课时id
  @JSONField(ordinal = 1)
  private int id;

  //课程id
  @JSONField(ordinal = 2)
  private int course_id;

  //章节id
  @JSONField(ordinal = 3)
  private int section_id;

  //课时主题
  @JSONField(ordinal = 4)
  private String theme;

  //课程时长
  @JSONField(ordinal = 5)
  private int duration;

  //是否免费
  @JSONField(ordinal = 6)
  private int is_free;

  //课时排序
  @JSONField(ordinal = 7)
  private int orderNum;

  //课时状态,0-隐藏，1-未发布，2-已发布
  @JSONField(ordinal = 8)
  private int status;

  //创建时间
  @JSONField(ordinal = 9)
  private String create_time;

  //修改时间
  @JSONField(ordinal = 10)
  private String update_time;

  //是否删除
  @JSONField(ordinal = 11)
  private int isDel;

  @Override
  public String toString() {
    return "Course_Lesson{" +
            "id=" + id +
            ", course_id=" + course_id +
            ", section_id=" + section_id +
            ", theme='" + theme + '\'' +
            ", duration=" + duration +
            ", is_free=" + is_free +
            ", orderNum=" + orderNum +
            ", status=" + status +
            ", create_time='" + create_time + '\'' +
            ", update_time='" + update_time + '\'' +
            ", isDel=" + isDel +
            '}';
  }

  @JSONField(serialize = false)
  private String start_img_url; //课时背景图
  @JSONField(serialize = false)
  private String text_content;  //课时内容
  @JSONField(serialize = false)
  private String markdown_text_content; //课时内容（markdown文本）
  @JSONField(serialize = false)
  private String transcode; //转码集合
  @JSONField(serialize = false)
  private String resource_url;  //课程资源路径
  @JSONField(serialize = false)
  private int last_operator_id; //最后操作者id
  @JSONField(serialize = false)
  private String ali_file_url;  //阿里云视频文件URL
  @JSONField(serialize = false)
  private String ali_file_dk; //阿里云视频文件的DK
  @JSONField(serialize = false)
  private String ali_file_edk;  //阿里云视频文件的EDK
  @JSONField(serialize = false)
  private String ali_file_vid; //阿里云视频资源文件ID
  @JSONField(serialize = false)
  private int is_timing_publish; //是否定时发布
  @JSONField(serialize = false)
  private String publish_time;  //定时发布时间

}
