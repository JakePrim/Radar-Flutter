package com.prim.lucenedemo.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * 招聘信息
 *
 * @TableName job_info
 */
@Data
@TableName(value = "job_info")
public class JobInfo implements Serializable {
    /**
     * 主键 id
     */
    @Id
    private Long id;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司联系方式
     */
    private String companyAddr;

    /**
     * 公司信息
     */
    private String companyInfo;

    /**
     * 职位名称
     */
    private String jobName;

    /**
     * 工作地点
     */
    private String jobAddr;

    /**
     * 职位信息
     */
    private String jobInfo;

    /**
     * 薪资范围，最小
     */
    private Integer salaryMin;

    /**
     * 薪资范围，最大
     */
    private Integer salaryMax;

    /**
     * 招聘信息详情页
     */
    private String url;

    /**
     * 职位最近发布时间
     */
    private String time;

    private static final long serialVersionUID = 1L;
}