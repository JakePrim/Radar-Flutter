<template>
    <!-- <div v-if="isShow"> -->
        <div>
        <div class="login-banner">
            <img
                src="../assets/public-class/logo-edu.png"
                alt="logo-edu"
                class="edu-logo"
            >
            <span class="login-txt">登录后，可同步多端学习记录</span>
            <button
                class="login-btn"
                @click="redirectLogin"
            >
                登录
            </button>
            <img
                src="../assets/public-class/top-login-bg.png"
                alt="bg"
                class="bg bg-left"
            >
            <img
                src="../assets/public-class/top-login-bg.png"
                alt="bg"
                class="bg bg-right"
            >
        </div>
        <div class="login-banner-blank" />
    </div>
</template>

<script>
// import { OSName } from '../../js/userAgent/browser-name-and-OS';
// import { isApp } from '../../js/userAgent/getVersion';
export default {
    name: 'TopLoginBanner',
    data() {
        return {};
    },
    computed: {
        // isShow() {
        //     let isMobile = ['Android', 'iOS'].indexOf(OSName) !== -1;
        //     let isLogin = window.userInfo && window.userInfo.id;
        //     let isLgApp = isApp();
        //     // return isMobile && !isLgApp && !isLogin;
        //     return false;
        // }
    },
    methods: {
        redirectLogin() {
            window.open('/frontLogin.do', '_self');
        }
    }
};
</script>
<style lang="less" scoped>
    .login-banner-blank {
        height: 90px;
    }
    .login-banner {
       overflow: hidden;
       font-size: 0;
       height: 90px;
       position: fixed;
       width: 100%;
       top: 0;
       z-index: 4;
       background: #fff;
       -webkit-transform: translateZ(0);
       border-bottom: 0.5px solid #E8E9EB;
    }
    .bg {
        position: absolute;
        width: 120px;
        height: 100%;
        top: 0;
        z-index: -1;
    }
    .bg-left {
        left: 0;
        transform: rotate(180deg);
    }
    .bg-right {
        right: 0;
    }
    .edu-logo {
        float: left;
        width: 84px;
        height: 84px;
        margin-left: 36px;
    }
    .login-txt {
        font-size: 30px;
        color: #333333;
        margin-left: 16px;
        float: left;
        margin-top: 24px;
    }
    .login-btn {
        float: right;
        background: #E5F7F3;
        border-radius: 2px;
        width: 122px;
        font-size: 30px;
        color: #00B38A;
        text-align: center;
        font-weight: 400;
        line-height: 30px;
        padding: 15px 0;
        margin-right: 40px;
        margin-top: 15px;
        border: none;
        outline: none;
        &:active {
            background: none;
        }
    }
</style>