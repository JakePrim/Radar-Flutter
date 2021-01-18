package com.prim.controller;

import com.prim.pojo.QueryVO;
import com.prim.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

@Controller //bean 对象注入到了IOC容器
@RequestMapping("/user") //一级访问目录
public class UserController {

    //二级访问目录 http://localhost:8080/../user/quick?accountName=...
    @RequestMapping(path = "/quick", method = RequestMethod.GET, params = {"accountName"})
    public String quick() {
        //业务逻辑
        System.out.println("spring mvc quick start");
        //视图跳转 跳转的路径写在return中 逻辑视图名
        return "success";
    }

//    @RequestMapping("/test")
//    public String testRequestParam() {
//        return "/requestParam.jsp";
//    }

    /**
     * 基本类型的参数请求
     *
     * @param id       请求参数名必须和当前方法参数名一致
     * @param username 请求参数名必须和当前方法参数名一致
     * @return
     */
    @RequestMapping("/simpleParam")
    public String simpleParam(Integer id, String username) {
        System.out.println("id:" + id + " username:" + username);//id:1 username:'张三'
        return "success";
    }


    /**
     * 获取对象类型的请求参数
     *
     * @param user
     * @return
     */
    @RequestMapping("/objectParam")
    public String objectParam(User user) {
        System.out.println(user);
        return "success";
    }

    /**
     * 获取数组类型请求参数
     */
    @RequestMapping("/arrayParam")
    public String arrayParam(Integer[] ids) {
        System.out.println(Arrays.toString(ids));
        return "success";
    }

    /**
     * 获取复杂类型请求参数
     */
    @RequestMapping("/queryParam")
    public String queryParam(QueryVO queryVO) {
        System.out.println(queryVO);
        //QueryVO{keyword='汉仪折纸',
        // user=User{id=1, username='张飞'},
        // userList=[User{id=0, username='关羽'}, User{id=1, username='刘备'}],
        // userMap={
        // user1=User{id=10, username='曹操'}
        // }}
        return "success";
    }

    /**
     * 自定义类型转换器
     * 获取日期类型参数
     */
    @RequestMapping("/converterParam")
    public String converterParam(Date birthday) {
        System.out.println(birthday);//Mon Jan 02 00:00:00 CST 2012
        return "success";
    }

    /**
     * 演示@RequestParam注解
     *
     * @return
     */
    @RequestMapping("/requestParam")
    public String requestParam(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "5") Integer pageSize) {
        System.out.println("pageNum:" + pageNum);
        System.out.println("pageSize:" + pageSize);
        return "success";
    }

    /**
     * 获取请求头信息
     * cookie信息
     *
     * @return
     */
    @RequestMapping("/requestHeader")
    public String requestHeader(@RequestHeader("cookie") String cookie) {
        System.out.println("cookie:" + cookie);//cookie:JSESSIONID=331CC62BDC66ED7AF8DB7E169835371B
        return "success";
    }

    /**
     * 获取cookievalue
     *
     * @param sessionId
     * @return
     */
    @RequestMapping("/requestCookieValue")
    public String requestCookieValue(@CookieValue("JSESSIONID") String sessionId) {
        System.out.println("sessionId:" + sessionId);//sessionId:11B45213ED8B142DDE6BFE4F6C40E3C2
        return "success";
    }

    /**
     * 原始Servlet API的获取
     */
    @RequestMapping("/servletApi")
    public String servletApi(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
        System.out.println(request);
        System.out.println(response);
        System.out.println(httpSession);
        return "success";
    }

    /**
     * 通过原始ServletAPI 进行页面跳转
     */
    @RequestMapping("returnVoid")
    public void returnVoid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 借助request对象完成请求转发
        request.getRequestDispatcher("/WEB-INF/pages/success.jsp").forward(request, response);

        //2. 借助response对象完成重定向 两次请求 WEB-INF 是安全目录，不允许外部请求直接访问该目录资源，只可以进行服务器内部请求转发
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    /**
     * 演示forward关键字请求转发
     */
    @RequestMapping("/forward")
    public String forward(Model model) {
        //还想在模型中设置一些值 转发到jsp页面 可以获取到该值
        model.addAttribute("username", "张飞");

        //forward既可以转发到jsp，也可以转发到其他控制器方法
//        return "forward:/product/findAll";//转发到控制器方法
        return "forward:/WEB-INF/pages/success.jsp";//路径必须写全了 不写成：success
    }

    /**
     * 演示redirect关键字 重定向
     * 当遇到forward和redirect关键字，不会再走视图解析器
     */
    @RequestMapping("/redirect")
    public String redirect(Model model) {
        //底层还是使用的还是 request.setAttribute  而request域的范围是一次请求 所以在重定向的页面是取不到username
        model.addAttribute("username", "刘备");
        //http://localhost:8080/springmvc_quickstart/index.jsp?username=刘备
        return "redirect:/index.jsp";
    }

    /**
     * ModelAndView 进行页面跳转 方式一
     */
    @RequestMapping("/returnModelAndView")
    public ModelAndView returnModelAndView() {
        /**
         * model : 模型 - 就是封装存放数据
         * view : 视图 - 用来展示数据
         */
        ModelAndView modelAndView = new ModelAndView();
        //设置模型数据
        modelAndView.addObject("username", "关羽");
        //设置视图名称
        modelAndView.setViewName("success");//直接写逻辑视图名称即可 因为最终会通过视图解析器 去解析modeandview对象，
        // 会根据在SpringMVC中的核心配置的视图解析器配置，拼接前缀和后缀

        return modelAndView;
    }

    /**
     * ModelAndView 进行页面跳转 方式二 推荐使用
     * @return
     */
    @RequestMapping("/returnModelAndView2")
    public ModelAndView returnModelAndView2(ModelAndView modelAndView) {
        /**
         * model : 模型 - 就是封装存放数据
         * view : 视图 - 用来展示数据
         */
        //设置模型数据
        modelAndView.addObject("username", "关羽");
        //设置视图名称
        modelAndView.setViewName("success");//直接写逻辑视图名称即可 因为最终会通过视图解析器 去解析modeandview对象，
        // 会根据在SpringMVC中的核心配置的视图解析器配置，拼接前缀和后缀

        return modelAndView;
    }
}
