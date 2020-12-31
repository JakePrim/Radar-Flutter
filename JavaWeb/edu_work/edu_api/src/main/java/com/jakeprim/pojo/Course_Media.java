package com.jakeprim.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 课程媒体表
 * */
@Data
public class Course_Media  implements Serializable {

  //课程媒体主键ID
  @JSONField(ordinal = 1)
  private int id;

  //课程id
  @JSONField(ordinal = 1)
  private int course_id;

  //章节id
  @JSONField(ordinal = 2)
  private int section_id;

  //课时id
  @JSONField(ordinal = 3)
  private int lesson_id;

  //封面图Url
  @JSONField(ordinal = 4)
  private String cover_image_url;

  //媒体类型，0-音频，1-视频
  @JSONField(ordinal = 5)
  private int media_type;

  //状态
  @JSONField(ordinal = 6)
  private int status;

  //创建时间
  @JSONField(ordinal = 7)
  private String create_time;

  //修改时间
  @JSONField(ordinal = 8)
  private String update_time;

  //是否删除
  @JSONField(ordinal = 9)
  private int isDel;

  @Override
  public String toString() {
    return "Course_Media{" +
            "id=" + id +
            ", course_id=" + course_id +
            ", section_id=" + section_id +
            ", lesson_id=" + lesson_id +
            ", cover_image_url='" + cover_image_url + '\'' +
            ", media_type=" + media_type +
            ", status=" + status +
            ", create_time='" + create_time + '\'' +
            ", update_time='" + update_time + '\'' +
            ", isDel=" + isDel +
            '}';
  }

  @JSONField(serialize = false)
  private long channel; //媒体渠道
  @JSONField(serialize = false)
  private String duration;  //时长
  @JSONField(serialize = false)
  private String file_id;  //媒体资源文件ID
  @JSONField(serialize = false)
  private String file_url;  //媒体文件URL
  @JSONField(serialize = false)
  private String file_edk;  //媒体资源文件对应的EDK
  @JSONField(serialize = false)
  private double file_size; //文件大小MB
  @JSONField(serialize = false)
  private String file_name; //文件名称
  @JSONField(serialize = false)
  private String file_dk; //媒体资源文件对应的DK
  @JSONField(serialize = false)
  private long last_operator_id;  //最后操作者ID
  @JSONField(serialize = false)
  private long duration_num;  //时长，秒数

}
