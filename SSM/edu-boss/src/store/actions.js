import { TokenService, UserService } from "../services";
import {
  CHANGE_TITLE,
  CHANGE_SESSION,
  TOGGLE_SIDEBAR_COLLAPSE,
  CHANGE_BREADCRUMBS,
  CHANGE_SIDERBAR_MENU
} from "./mutation-types";

import { axios } from "../utils";

/**
 * @type {import('vuex/types').ActionTree<typeof import('./state').default>}
 */
const actions = {
  /**
   * 改变页面标题
   */
  changeTitle: ({ commit }, title) => {
    commit(CHANGE_TITLE, title);
  },

  /**
   * 用户登录
   */
  createToken: async ({ commit }, { username, password }) => {
    //请求后台登录接口
    const res = await TokenService.userLogin({
      phone: username.trim(),
      password: password.trim()
    });
    console.log("res:");
    console.log(res);
    //判断结果不等于1,登录失败
    // if (res.state !== 1) {
    //   return Promise.resolve(res);
    // }

    //获取到content
    const result = res.content;

    console.log("content:" + result);

    //将token保存
    commit(CHANGE_SESSION, {
      accessToken: result.access_token
    });

    //将用户信息保存
    commit(CHANGE_SESSION, { user: result.user });
    return res;
  },

  /**
   * 获取当前登录用户权限
   */
  getUserPermissions: async ({ commit }) => {
    //1.请求后台 获取当前用户的权限
    const res = await UserService.getUserPermissions();

    //2.判断
    if (!res.success) {
      //获取失败直接返回 false
      return res.success;
    }

    //3.获取数据成功,取出菜单 与 资源列表
    const { menuList, resourceList } = res.content;

    //4.下面的代码 就是在生成树形结构的菜单
    let menus = [];
    const formatMenu = treeData => {
      if (treeData.length > 0) {
        return treeData.map(item => formatMenu(item));
      }
      const result = {};

      //shown等于表示可以显示,将内容保存
      if (treeData.shown == 1) {
        result.id = treeData.id;
        result.text = treeData.name;
        result.label = treeData.name;
        result.name = treeData.href;
        result.icon = treeData.icon;
        result.shown = treeData.shown;
      } else {
        return "";
      }

      //获取子节点
      if (treeData.subMenuList) {
        result.children = [];
        treeData.subMenuList.forEach(item => {
          formatMenu(item) && result.children.push(formatMenu(item));
        });

        if (result.children.length === 0) {
          delete result.children;
        }
      }
      return result;
    };

    const memusMap = {};

    const splapMenu = treeData => {
      if (treeData.length > 0) {
        return treeData.map(item => splapMenu(item));
      }
      const result = {};
      result.id = treeData.id;
      result.text = treeData.name;
      result.label = treeData.name;
      result.name = treeData.href;
      result.icon = treeData.icon;
      result.shown = treeData.shown;
      result.name && (memusMap[result.name] = result);

      if (treeData.subMenuList) {
        result.children = [];
        treeData.subMenuList.forEach(item => {
          result.children.push(splapMenu(item));
        });
      }
      return result;
    };

    splapMenu(menuList);

    menus = formatMenu(menuList);
    commit(CHANGE_SIDERBAR_MENU, menus);

    return { menus, resourceList, menuList, memusMap };
  },

  /**
   * 检查客户端令牌是否可用
   */
  checkToken: async ({ commit, getters }) => {
    //取出token
    const token = getters.session.accessToken;

    if (!token) {
      //不可用
      return Promise.resolve(false);
    }

    return Promise.resolve(true);
  },

  /**
   * 删除客户端令牌
   */
  deleteToken: async ({ commit, getters }) => {
    // await TokenService.delete(getters.session.accessToken)
    commit(CHANGE_SESSION, { accessToken: null });
    await Promise.resolve();
  },

  /**
   * 获取当前登录用户信息
   */
  getCurrentUser: async ({ commit, getters }) => {
    //取出用户信息
    const user = getters.session.user;
    return user;
  },

  /**
   * 切换边栏的展开收起
   */
  toggleSidebarCollapse: ({ commit }) => {
    commit(TOGGLE_SIDEBAR_COLLAPSE);
  },

  /**
   * 修改面包屑导航
   */
  changeBreadcrumbs: ({ commit }, breadcrumbs) => {
    commit(CHANGE_BREADCRUMBS, breadcrumbs);
  }

  /**
   * 本地定时更新token
   */
  // refreshToken: async ({ commit, state }) => {
  //   const { refreshToken } = state.session;
  //   //const res = await TokenService.fetchUpdateToken(refreshToken);
  //   const res = getResourceToken();
  //   if (!res) {
  //     return Promise.resolve();
  //   }
  //   const result = JSON.parse(res.content);
  //   commit(CHANGE_SESSION, {
  //     accessToken: result.access_token,
  //     refreshToken: result.refresh_token
  //   });
  //   return Promise.resolve();
  // }
};

function getUserInfo() {
  return {
    state: 1,
    message: "success",
    content: {
      isUpdatedPassword: true,
      userName: "15510792995",
      portrait: null
    },
    success: true
  };
}

function getResourceToken() {
  return {
    state: 1,
    message: "success",
    content:
      '{"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMTAwMDMwMDE2IiwidXNlcl9uYW1lIjoiMTU1MTA3OTI5OTUiLCJzY29wZSI6WyJyZWFkIl0sIm9yZ2FuaXphdGlvbiI6IjE1NTEwNzkyOTk1IiwiZXhwIjoxNTk3MjA2MDY2LCJhdXRob3JpdGllcyI6WyJBRE1JTiJdLCJqdGkiOiJUV0Fxbm9GZnZwNi1UMFlMNXpaLTlUOE8yLW8iLCJjbGllbnRfaWQiOiJ0ZXN0X2NsaWVudCJ9.IQywo8Gbg8OTYRM-X8N-HTCg-739rXaeP7V7UySz41E","token_type":"bearer","refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMTAwMDMwMDE2IiwidXNlcl9uYW1lIjoiMTU1MTA3OTI5OTUiLCJzY29wZSI6WyJyZWFkIl0sIm9yZ2FuaXphdGlvbiI6IjE1NTEwNzkyOTk1IiwiYXRpIjoiVFdBcW5vRmZ2cDYtVDBZTDV6Wi05VDhPMi1vIiwiZXhwIjoxNTk3MzA2ODY2LCJhdXRob3JpdGllcyI6WyJBRE1JTiJdLCJqdGkiOiJqQWQ4QzJvTHRrRXhMUVNmZUVLVHN4WHNpeEEiLCJjbGllbnRfaWQiOiJ0ZXN0X2NsaWVudCJ9.xoabCnmW_ltJn4IMCOvK_FlvuSdpgeblyxOAFm18xW4","expires_in":7199,"scope":"read","user_id":"100030016","organization":"15510792995","jti":"TWAqnoFfvp6-T0YL5zZ-9T8O2-o"}',
    success: true
  };
}

function login() {
  return {
    state: 1,
    message: "success",
    content:
      '{"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMTAwMDMwMDE2IiwidXNlcl9uYW1lIjoiMTU1MTA3OTI5OTUiLCJzY29wZSI6WyJyZWFkIl0sIm9yZ2FuaXphdGlvbiI6IjE1NTEwNzkyOTk1IiwiZXhwIjoxNTk3MjA4NjAwLCJhdXRob3JpdGllcyI6WyJBRE1JTiJdLCJqdGkiOiIwbWwxc1VyN2dOdnIxaFRvM09EY3NvNklfMTAiLCJjbGllbnRfaWQiOiJ0ZXN0X2NsaWVudCJ9.mQ5nWspQHLlsgOhDhwPRX3mbSnsWHuBkmgXhbVb5JmQ","token_type":"bearer","refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMTAwMDMwMDE2IiwidXNlcl9uYW1lIjoiMTU1MTA3OTI5OTUiLCJzY29wZSI6WyJyZWFkIl0sIm9yZ2FuaXphdGlvbiI6IjE1NTEwNzkyOTk1IiwiYXRpIjoiMG1sMXNVcjdnTnZyMWhUbzNPRGNzbzZJXzEwIiwiZXhwIjoxNTk3MzA5NDAwLCJhdXRob3JpdGllcyI6WyJBRE1JTiJdLCJqdGkiOiJNQ1hiYjBTckdyU1ZUTjB6Q1Y2a3RpUk9jUTQiLCJjbGllbnRfaWQiOiJ0ZXN0X2NsaWVudCJ9.2t2J8lC3ulQ_ytEHTZ2qSxW8XLlcFgThbdFwHk7RcZA","expires_in":6389,"scope":"read","user_id":"100030016","organization":"15510792995","jti":"0ml1sUr7gNvr1hTo3ODcso6I_10"}',
    success: true
  };
}

function userLogin(user) {
  const data = {};

  axios
    .get("/user/login", {
      params: {
        phone: user.phone,
        password: user.password
      }
    })
    .then(res => {
      console.log(res.data.content);
      this.data = res.data.content;
    })
    .catch(err => {
      console.log("加载数据失败! ! !");
    });

  return JSON.parse(data);
}

function toLogin() {
  alert("fdgjhdskjhfkuds");
  window.location.href = "www.baidu.com";
}

export default actions;
