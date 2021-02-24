<template>
  <div id="videoDetail">
    <div class="detail-container">
      <div class="left-container top-container">
        <nav class="nav">
          <div class="nav-back" @click="goBack">
            <span class="kw-icon-back"> &lt; </span>
            <span style="">&nbsp;返回</span>
          </div>
          <span style="position:absolute;left:80px;">
            文案高手的18项修炼 > 从小白到文案高手</span
          >
        </nav>
        <div class="video-content calc">
          <div class="video-container">
            <div
              id="player-video-vontainer"
              class="player-container video-container"
            >
              <div
                style="min-width:750px;min-height:550px;"
                class="video"
                @click="play"
              >
                <video
                  style="width: 100%; height: 100%;"
                  id="myvideo"
                  src="https://video.pearvideo.com/mp4/adshort/20200901/cont-1693694-15359233_adpkg-ad_hd.mp4"
                  @canplay="getInit"
                  @timeupdate="handlerNowTime"
                />
                <div class="controls">
                  <div class="con_left">
                    <!-- 播放 -->
                    <i
                      :class="{
                        'el-icon-video-play': !isplay,
                        'el-icon-video-pause': isplay,
                      }"
                      @click="play"
                      style="font-size:40px"
                    ></i>
                    <span>{{ nowTime }}/{{ totalTime }}</span>
                  </div>
                  <div class="con_right">
                    <!-- 音量 -->
                    <i
                      class="el-icon-headset"
                      @click="play"
                      style="font-size:40px"
                    ></i>
                    <!-- 全屏 -->
                    <i
                      class="el-icon-monitor"
                      @click="play"
                      style="font-size:40px"
                    ></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="right-container bottom-container">
        <div style="height: 100%;">
          <div class="detail-part-content-title">课程目录</div>
          <div class="study-percent-tip">
            <div class="week-progress">
              <img
                class="video-book"
                src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADgAAAA4CAYAAACohjseAAADHUlEQVRoQ+2a30sUURTHv3dMS60Xg8AIQV+ChCBmJqInqXAx6iXah6CghzCkiMz8C3qJfllRhNJDEEWwEUFFJBW9KMbMEAkWvbjgg0FRYO76o92dE1Ma686d8c4y7rrDncfdc+85n3t+zL1zD0PEHxZxPkjASvewsAd1XW8lohNE1A6gCcD6EsOnAEwwxgYZY3cNwxgT0b8sYDwer0kmk9eIqAuAIjJpCWRsxtid5ubmc4lE4refPl9AB258fPwlgD0lMLoYFW9bWlo6/CB9ATVNu0VEp4rRXKoxjLHbpmme9tLnCejknG3bo6soLL0YbEVRtnvlpCegpml9RHSWM2sGwCvG2KSIl4ioU0SuUIYxNpD/GxFtBhADUM2RvW6aZjdPjyegqqpOldpWMCijKEqbYRjDokarqkqisvlylmW5bNN1fbdt2+84kJ8sy2oNCjjNeRU8tyzrYBCDwwR09Kqq+gzAgQIbUpZlbQgK6Fp5J2xM0zxZTkBN0/p5Yc/zuGOnX4hKQBFPhh2i0oMFqx5qiNJIx5acbV8CoY1AjY6uXWd+iDjaJTNycyN33IUHqZkX7+frCv9c8RxcgPtIRA35ysMGvPgojafDcy74FQfMDsceEtGRQs3RARyKTS6GpYgH69b9y46ZOf4+wCtEy+bBzFA711KeB/fuqEH3ofq/gH1P0njzwX3iqUjApk1V6I3XQ9+6dDtpfMngciKNiW+5/86vKMC11QzH22txbF8t1lTxi2o2B9x/PYt7g7OYzxAqBvD8wDR6DtejsUHsY8DXnzauPk7jSid3a4lVl4NFvQR9BknAldrJeFVR6cGAKyBDVIbowgrwznF+J3qZgwFzzUtc5qDMQZmD/M+GssjIIiO2ArKKyioqq6isomLVokgpWWQCFJlAF6CletH39P/C0Jhzi77kKeoCNNAVdikAR5NZdN2YQs52xXfwK+ygTQj7d9Ycra5irlufIlPNNez7VA4jnzM8ODDGgjchRL6NxFnCSDcCOYCRb+VahIxsM15+pke2nTKsKliueZbtFy2XYWHplYBhrWS55vkDgsoDZgBZavwAAAAASUVORK5CYII="
                alt=""
              />
              <span class="progress-label">文案高手的18项修炼</span>
            </div>
          </div>
          <div class="detail-part-content-pc calc detail-part-content">
            <div class="content-fold">
              <div class="content-fold-img"></div>
            </div>
            <div class="content-container">
              <div>
                <div class="content-label">
                  <div class="content-label-title single-line">第一章</div>
                  <img
                    class="arrow-icon"
                    src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACQAAAAkBAMAAAATLoWrAAAAGFBMVEVHcEz///////////////////////////8dS1W+AAAAB3RSTlMAgwWyMeGgBsFrrQAAAFVJREFUKM9jYBiugMUBQ0i8EF2EsbxcAF1ReXkhuqJiczRl4uVGyqjKgIoUmFCVARUxMKAoAyliYEBRBlaEqiwcpAikrBShKglCqyFUMcEYCgwjBAAAeaoQrHtg6QoAAAAASUVORK5CYII="
                    alt=""
                  />
                </div>

                <div class="content-sections">
                  <!-- 未播放的视频 -->
                  <div class="content-section" @click="playLesson">
                    <div class="section-item clearfix">
                      <span class="kw-icon-video section-type-icon fl"
                        ><i class="el-icon-video-play"></i
                      ></span>
                      <span class="section-dec need-update">1.概述</span>
                      <span class="section-status-icon pause-play">未解锁</span>
                    </div>
                    <div class="section-duration">
                      <span>时长：0</span>
                    </div>
                  </div>
                  <!-- 正在播放的视频 -->
                  <div
                    class="content-section content-section-choose"
                    style=" color: #00b38a;"
                    @click="playLesson"
                  >
                    <div class="section-item clearfix">
                      <span class="kw-icon-video section-type-icon fl"
                        ><i class="el-icon-video-play"></i
                      ></span>
                      <span class="section-dec">2.起步</span>
                      <span class="section-status-icon pause-play"></span>
                    </div>
                    <div class="section-duration">
                      <span>时长：0</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "videoDetail",
  components: {},
  data() {
    return {
      myvideo: null, // 播放器对象
      isplay: false, //是否在播放
      nowTime: "00:00", //当前播放时间
      totalTime: "00:00", //总时长
    };
  },
  computed: {},
  created() {},
  mounted() {
    this.myvideo = document.getElementById("myvideo");
  },
  methods: {
    play() {
      this.isplay = !this.isplay;
      if (this.isplay) {
        this.myvideo.play();
      } else {
        this.myvideo.pause();
      }
    },
    // 获取视频的时间是秒为单位，格式化城00：00的格式
    formatTime(time) {
      let mm = Math.floor((time % 3600) / 60);
      let ss = Math.floor(time % 60);
      mm = mm < 10 ? "0" + mm : mm;
      ss = ss < 10 ? "0" + ss : ss;
      return `${mm}:${ss}`;
    },
    //获取初始化信息
    getInit() {
      if (!this.myvideo) {
        //获取失败，显示0
        this.totalTime = this.formatTime(0);
      } else {
        //获取视频总时长
        this.totalTime = this.formatTime(this.myvideo.duration);
      }
    },
    //播放时显示当前播放时间和总时长
    handlerNowTime() {
      if (!this.myvideo) {
        this.nowTime = `${this.formatTime(0)}`;
      }
      this.nowTime = this.formatTime(this.myvideo.currentTime || 0);
    },
    //返回
    goBack() {
      this.$router.push({ name: "Course" });
    },
    //播放课程
    playLesson() {
      alert("播放视频！");
    },
  },
};
</script>

<style lang="less">
.video {
  position: relative;
  video {
    width: 100%;
    height: 100%;
  }
  .controls {
    width: 100%;
    height: 40px;
    position: absolute;
    bottom: 0;
    left: 0;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: space-between;
    align-items: center;
    span {
      padding: 0 5px;
      color: #fff;
    }
  }
}

.detail-container {
  height: 100%;
}
* {
  -webkit-tap-highlight-color: transparent;
}
* {
  margin: 0;
  padding: 0;
}
div {
  display: block;
}
.left-container {
  margin-right: 380px;
  -webkit-transition: margin-right 0.3s ease 0s;
  transition: margin-right 0.3s ease 0s;
  height: 100%;
  overflow-y: auto;
}
.left-container .nav {
  height: 60px;
  line-height: 60px;
  background: #32373e;
  font-size: 14px;
  font-weight: 400;
  color: #fff;
  padding-left: 80px;
  position: relative;
}

.left-container .video-content {
  position: relative;
  background: #272c30;
  overflow-x: hidden;
}
.calc {
  height: calc(100% - 60px);
}
.left-container .nav-back {
  position: absolute;
  top: 1px;
  left: 10px;
  cursor: pointer;
  &:hover {
    color: #00b38a;
  }
}
[class*=" kw-icon-"],
[class^="kw-icon-"] {
  font-family: kw-icon !important;
  speak: none;
  font-style: normal;
  font-weight: 400;
  -webkit-font-feature-settings: normal;
  font-feature-settings: normal;
  font-variant: normal;
  text-transform: none;
  line-height: 1;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
.video-container {
  position: relative;
  height: 100%;
  background: #272c30;
}
.right-container {
  position: absolute;
  top: 0;
  right: 0;
  width: 380px;
  height: 100%;
  background-color: #32373e;
  -webkit-transition: right 0.3s ease 0s;
  transition: right 0.3s ease 0s;
}
.detail-part-content-title {
  height: 60px;
  line-height: 60px;
  background: #40464d;
  font-size: 18px;
  font-weight: 500;
  color: #fff;
  padding-left: 20px;
  position: relative;
  z-index: 10;
  text-align: left;
}
.study-percent-tip {
  display: none;
}
img {
  border: 0;
  vertical-align: top;
  display: inline-block;
}
.detail-part-content-pc .content-fold {
  position: absolute;
  left: -24px;
  top: 50%;
  -webkit-transform: translateY(-50%);
  transform: translateY(-50%);
  width: 24px;
  height: 100px;
  background-color: rgba(50, 55, 62, 0.7);
  border-radius: 8px 0 0 8px;
  z-index: 1;
}
.detail-part-content-pc .content-fold-img {
  background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACQAAAAkBAMAAAATLoWrAAAAGFBMV…AoAyliYEBRBlaEqiwcpAikrBShKglCqyFUMcEYCgwjBAAAeaoQrHtg6QoAAAAASUVORK5CYII=)
    no-repeat;
  width: 18px;
  height: 18px;
  position: absolute;
  left: 50%;
  top: 50%;
  -webkit-transform: translate(-50%, -50%) rotate(90deg);
  transform: translate(-50%, -50%) rotate(90deg);
  background-size: contain;
  -webkit-transition: all 0.3s ease 0s;
  transition: all 0.3s ease 0s;
}
.detail-part-content-pc .content-label {
  position: relative;
  height: 60px;
  line-height: 60px;
  font-size: 14px;
  font-weight: 400;
  color: #fff;
  padding: 0 10px;
  background: #2b3035;
  border-radius: 4px;
  margin-top: 10px;

  text-align: left;
  text-indent: 10px;
}
.detail-part-content-pc .content-label-title {
  width: 310px;
  height: 60px;
}
.single-line {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.detail-part-content-pc .arrow-icon {
  width: 18px;
  height: 18px;
  position: absolute;
  top: 21px;
  right: 10px;
  -webkit-transition: all 0.3s ease;
  transition: all 0.3s ease;
  -webkit-transform: rotate(180deg);
  transform: rotate(180deg);
}
img {
  border: 0;
  vertical-align: top;
  display: inline-block;
}
.detail-part-content-pc .content-sections {
  -webkit-transition: all 1s ease;
  transition: all 1s ease;
}
.detail-part-content-pc .content-section {
  padding: 15px 10px;
  color: #fff;
  font-size: 14px;
  min-height: 40px;
  border-bottom: 1px solid #272c30;
  font-family: none;
  opacity: 0.7;
}
.detail-part-content-pc .section-item {
  position: relative;
  text-align: left;
}
.detail-part-content-pc .section-type-icon {
  font-size: 16px;
  vertical-align: middle;
  margin: 4px 10px 0 0;
}

.fl {
  float: left;
}
.detail-part-content-pc .need-update {
  color: #989b9e;
}

.detail-part-content-pc .section-dec {
  max-width: 270px;
  display: inline-block;
  vertical-align: bottom;
  word-break: break-all;
}
.detail-part-content-pc .section-status-icon {
  font-size: 12px;
  position: absolute;
  right: 0;
  top: 1px;
  color: #989b9e;
}
.detail-part-content-pc .section-duration {
  font-size: 12px;
  margin-top: 4px;
  padding-left: 26px;
  color: hsla(0, 0%, 100%, 0.7);
  text-align: left;
}
.detail-part-content-pc .content-section-choose .pause-play {
  width: 18px;
  height: 18px;
  background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABIAAAFoCAYAAABaAKtEAAAEPUlEQVR4Xu1dPW8TQRB9ZwLBTkF+AeIf0CMK6JAiGyr+A10UyU6gOmhCYomvAokCRAOFm0S6CEVponQUSCDRgpQCCYmCLwWRKIkPXcBRvN7zzk42x+5l3ObueWbu3tuZ2VkngqNP5AgHBQEt3B/HyPY3AA9Rb00Os364RQKUxa4/Rsl82hdYdowECGoI2MF2AtSJT6Fa21JpUxBpLTRGLDIHa3iMOveqqO7MAtEa6s0FUUhggIwibHzxT+bXkeIZGq07vXeLt4o4EbbMBCdAopBm4cm5IjTNTtMIiw/OYDvdwvWp3yErpCRauqfH1KP2O6D7AvXpdpmEzZlCvno0it3NTckh7YXSd4XUrKBUJ4dzjYpirI7YWW3hCpnMxahErzHRWhYZyenWOMnYnAmbM6DV+DQ2allC0dec8p39FiRVLxXXzMGzj9FqPIKNsYvo7nzC1Zsf9DJi/mIgp861t4gFpLuJBJS0nwNYQb35cs9LPhChoUmzSIA0XXf7PJsUbMqbXZJS1MJVe9IWGyP/c8i95bk6o2Zn7ipIDZI9RXKstAcicY2SHguQeVus2BhZyIZkbPbB8l1ql+5ewG765WBuSHVyuIywO6MUGclMXGpPopu+RaO1JsXx8SqOpcqmcnTgOt/1iO2YOoDiDEjVoyMXNiepHylhp0qtM4vUatzYq83bXS9p18+ZsDkDkp0atpCUQWrjuILzo2dRHf2JK1NfQ20f6p4hqcxSb9RV1Cwgdh9StYgNlCh71HwgaWgaG5psETmytEYsMjfrnMUoaV9Sd2Co2AVtiuvYn6393a0bh6+ySQpJSUYFyLf2obOnRuWDsRYRoAKFjZIfkUpRSloTKBCFIiTXBAglnRhP4hpQ+yXTPvbiXYYqm8V+i1DZb/iyLArjKMwe18Ye900FsfqQkoz6loxaUEK99NjISLgxWpg9d/g+JKkxTo2RcRfiyIB6M+wVLB+cDjefX8vLs9NoDo3mzP6frV3j79QQ+pCkp0ZNj42uhQkkE+NUtg1cVxbxN77Z1AiVAKi3TT/+YxOX4+yo2d+PtWv7wpbeRn06dgAUzaLevOUASPnhEr5rtkCikP+xx0Yl8UBRY3GjpMf2weKtIsncNVTSj5iYed/7yn4gdc4x7/iikf1hkpak2eKaeV2jxEhySHva/7uDx37N14UGFMZRmBI3NDPXluYX0Y0SNJpP9asIhf3OpJY0NEixKNAWK1tEZNqHErrQFDKcH5wwLkcU0voptX4ppDOp9Q+IJP4UmpPeIwEqcydCMzfJE39jcaxG0X370LrHRvjlCv/YLxaZN8WlymarNo/9xrSGbU9hCTt7tkZ1jQ0UbupnfPyFu6aevWHNH/kntZ3OCVTXd4aPMVKCTar7/SethYXHVtjCiNGbJyfx+fuE3U4N+7CgM4oIkO4/fNiPw5L0yL9gW3BLvdQ/PfoDa+yS8O1qaS0AAAAASUVORK5CYII=)
    no-repeat;
  background-size: cover;
  background-position: 0 0;
}
.detail-part-content-pc .content-section:last-of-type {
  border-bottom: none;
  margin-bottom: 25px;
}
.detail-part-content-pc .content-section:hover {
  background: #2b3035;
  cursor: pointer;
}

.detail-part-content-pc .content-section-choose .section-status-icon,
.detail-part-content-pc .content-section-choose {
  color: #00b38a;
}
.kw-icon-video:before {
  // content: "\E903";
}
.detail-part-content-pc .content-section {
  padding: 15px 10px;
  color: #fff;
  font-size: 14px;
  min-height: 40px;
  border-bottom: 1px solid #272c30;
  font-family: none;
  opacity: 0.7;
}
</style>
