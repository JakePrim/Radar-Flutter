<template>
  <div class="footer-pc">
    <ul>
      <li>
        <a
          target="_blank"
          href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=11010802024043"
          rel="nofollow"
          ><em>&copy;</em>2020 拉勾网 京ICP备14023790号-2
        </a>
      </li>
      <li>
        <a
          target="_blank"
          href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=11010802024043"
          rel="nofollow"
          ><i class="police-logo" />京公网安备 11010802024043号</a
        >
      </li>
      <li>
        <div class="report-wrapper">
          <i class="report-logo" style="margin:0px;" />
          <div class="report-content">
            <p>违法和不良信息举报</p>
            <p>电话: 4006 2828 35</p>
            <p>邮箱: cc@lagou.com</p>
          </div>
        </div>
      </li>
      <li>
        <img
          src="../../assets/course-list/footer_lagou_icon.png"
          alt="lagou-icon"
        />
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "Footer",
};
</script>

<style lang="less" scoped>
.footer-pc {
  line-height: 100px;
  margin-top: 60px;
  font-size: 14px;
  color: #999;
  position: relative;
  border-top: 1px solid #ededed;
  a {
    text-decoration: none;
    &:hover {
      color: #555;
    }
    &:link {
      color: #999;
    }
  }
  ul {
    width: 1200px;
    height: 100px;
    margin: 0 auto;
    li {
      width: 236px;
      display: inline-block;
      height: 100px;
      overflow: hidden;
      position: relative;
      &:first-child {
        width: 250px;
      }
      .police-logo {
        background: url("../../assets/course-list/logo-sprite.png") 105px -696px;
        display: inline-block;
        width: 15px;
        height: 15px;
        margin-right: 5px;
      }
      .report-wrapper {
        border: 1px solid #d5dadf;
        border-radius: 1px;
        width: 207px;
        height: 58px;
        line-height: 20px;
        margin-top: 20px;
        .report-content {
          margin-top: -24px;
          margin-left: 35px;
        }
        .report-logo {
          background: url("../../assets/course-list/logo-sprite.png") 124px
            236px;
          display: inline-block;
          width: 20px;
          height: 24px;
          position: relative;
          left: -80px;
          top: 18px;
          // margin: 17px 0 0 20px;
        }
        p {
          margin-top: 2px;
          font-size: 12px;
          line-height: 1;
          color: #999;
        }
      }
      &:last-child {
        float: right;
      }
    }
  }
  img {
    position: absolute;
    top: 40px;
    right: 0;
  }
}

.report-content p {
  margin: 0px;
  padding: 0px;
}
</style>
