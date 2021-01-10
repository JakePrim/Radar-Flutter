import Layout from "../views/Layout.vue";

/**
 * @type {import('vue-router/types/router').RouteConfig[]}
 */
const routes = [
  {
    path: "/",
    component: Layout,

    children: [
      {
        path: "/courses",
        name: "Courses",
        component: () =>
          import(/* webpackChunkName: 'courses' */ "../views/Courses.vue")
      },
      {
        path: "/",
        name: "Home",
        component: () =>
          import(/* webpackChunkName: 'courses' */ "../views/Courses.vue")
      },

      //添加课程的路由
      {
        path: "/courses/:courseId", //路径,携带参数: 课程ID
        name: "CourseItem",
        component: () =>
          //路由导航到的组件
          import(/* webpackChunkName: 'courses' */ "../views/CourseItem.vue")
      },

      //内容管理的路由
      {
        path: "/courses/:courseId/tasks",
        name: "CourseTasks",
        meta: { requireAuth: true },
        component: () =>
          import(/* webpackChunkName: 'courses' */ "../views/CourseTasks.vue")
      }
    ]
  },
  {
    path: "*",
    component: () =>
      import(/* webpackChunkName: 'common' */ "../views/Error.vue")
  }
];

export default routes;
