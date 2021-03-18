package generator.domain;

import java.io.Serializable;

/**
 * 招聘信息
 * @TableName job_info
 */
public class JobInfo implements Serializable {
    /**
     * 主键 id
     */
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

    /**
     * 主键 id
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键 id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 公司名称
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 公司名称
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 公司联系方式
     */
    public String getCompanyAddr() {
        return companyAddr;
    }

    /**
     * 公司联系方式
     */
    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    /**
     * 公司信息
     */
    public String getCompanyInfo() {
        return companyInfo;
    }

    /**
     * 公司信息
     */
    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
    }

    /**
     * 职位名称
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * 职位名称
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * 工作地点
     */
    public String getJobAddr() {
        return jobAddr;
    }

    /**
     * 工作地点
     */
    public void setJobAddr(String jobAddr) {
        this.jobAddr = jobAddr;
    }

    /**
     * 职位信息
     */
    public String getJobInfo() {
        return jobInfo;
    }

    /**
     * 职位信息
     */
    public void setJobInfo(String jobInfo) {
        this.jobInfo = jobInfo;
    }

    /**
     * 薪资范围，最小
     */
    public Integer getSalaryMin() {
        return salaryMin;
    }

    /**
     * 薪资范围，最小
     */
    public void setSalaryMin(Integer salaryMin) {
        this.salaryMin = salaryMin;
    }

    /**
     * 薪资范围，最大
     */
    public Integer getSalaryMax() {
        return salaryMax;
    }

    /**
     * 薪资范围，最大
     */
    public void setSalaryMax(Integer salaryMax) {
        this.salaryMax = salaryMax;
    }

    /**
     * 招聘信息详情页
     */
    public String getUrl() {
        return url;
    }

    /**
     * 招聘信息详情页
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 职位最近发布时间
     */
    public String getTime() {
        return time;
    }

    /**
     * 职位最近发布时间
     */
    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        JobInfo other = (JobInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCompanyName() == null ? other.getCompanyName() == null : this.getCompanyName().equals(other.getCompanyName()))
            && (this.getCompanyAddr() == null ? other.getCompanyAddr() == null : this.getCompanyAddr().equals(other.getCompanyAddr()))
            && (this.getCompanyInfo() == null ? other.getCompanyInfo() == null : this.getCompanyInfo().equals(other.getCompanyInfo()))
            && (this.getJobName() == null ? other.getJobName() == null : this.getJobName().equals(other.getJobName()))
            && (this.getJobAddr() == null ? other.getJobAddr() == null : this.getJobAddr().equals(other.getJobAddr()))
            && (this.getJobInfo() == null ? other.getJobInfo() == null : this.getJobInfo().equals(other.getJobInfo()))
            && (this.getSalaryMin() == null ? other.getSalaryMin() == null : this.getSalaryMin().equals(other.getSalaryMin()))
            && (this.getSalaryMax() == null ? other.getSalaryMax() == null : this.getSalaryMax().equals(other.getSalaryMax()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCompanyName() == null) ? 0 : getCompanyName().hashCode());
        result = prime * result + ((getCompanyAddr() == null) ? 0 : getCompanyAddr().hashCode());
        result = prime * result + ((getCompanyInfo() == null) ? 0 : getCompanyInfo().hashCode());
        result = prime * result + ((getJobName() == null) ? 0 : getJobName().hashCode());
        result = prime * result + ((getJobAddr() == null) ? 0 : getJobAddr().hashCode());
        result = prime * result + ((getJobInfo() == null) ? 0 : getJobInfo().hashCode());
        result = prime * result + ((getSalaryMin() == null) ? 0 : getSalaryMin().hashCode());
        result = prime * result + ((getSalaryMax() == null) ? 0 : getSalaryMax().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", companyName=").append(companyName);
        sb.append(", companyAddr=").append(companyAddr);
        sb.append(", companyInfo=").append(companyInfo);
        sb.append(", jobName=").append(jobName);
        sb.append(", jobAddr=").append(jobAddr);
        sb.append(", jobInfo=").append(jobInfo);
        sb.append(", salaryMin=").append(salaryMin);
        sb.append(", salaryMax=").append(salaryMax);
        sb.append(", url=").append(url);
        sb.append(", time=").append(time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}