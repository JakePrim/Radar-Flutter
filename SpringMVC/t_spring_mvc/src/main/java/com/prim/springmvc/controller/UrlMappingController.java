package com.prim.springmvc.controller;

import com.prim.springmvc.pojo.Form;
import com.prim.springmvc.pojo.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * @author prim
 * URL 映射
 */
@Controller
@RequestMapping("/um") //@RequestMapping 增加通用的前缀 当前类的所有的mapping RequestMapping推荐的使用方式
public class UrlMappingController {

    @GetMapping("/g")
//    @RequestMapping(value = "/g", method = RequestMethod.GET) //如果作用在方法上，当前的方法不再区分get/post请求 所有的请求都会访问到
    //也可以通过method 属性设置请求方式
    @ResponseBody
    public String getMapping(@RequestParam("manager_name") String managerName) {//RequestParam 映射请求参数名 注入到后面到参数中managerName
        //这样参数命名不需要和请求参数一致了
        return "This is get method -> " + managerName;
    }

    @PostMapping("/p")
    @ResponseBody
    public String postMapping(String username, Long password) {//直接通过方法接受参数 命名保持一致
        //简化了request.getParameter
        return "This is post method -> " + username + ":" + password;
    }

    //    @PostMapping("/p1")
    @ResponseBody
    public String postMapping1(User user, String username) {//使用JavaBean作为参数 Spring mvc 会自动给Bean赋值 寻找同名的请求参数
        //username 参数Spring MVC也会一并赋值 只要命名和请求参数一致
        return "This is java bean post -> " + user.toString() + ":" + username;
    }

    /**
     * 获取日期格式数据
     * 通过注解：@DateTimeFormat
     * 设置全局默认的时间格式转换器 这样就不用在每个方法和参数中设置注解等
     *
     * @param user
     * @param username
     * @param time
     * @return
     */
//    @PostMapping("/p1")
    @ResponseBody
    public String dateMapping1(User user, String username, @DateTimeFormat(pattern = "yyyy-MM-dd") Date time) {//使用JavaBean作为参数 Spring mvc 会自动给Bean赋值 寻找同名的请求参数
        //username 参数Spring MVC也会一并赋值 只要命名和请求参数一致
        return "This is java bean post -> " + user + ":" + username;
    }

    /**
     * 使用全局的时间格式转换器 自动捕获Date类型
     * 如果即配置了时间格式转换器 又配置注解。默认使用转换器的逻辑
     *
     * @param user
     * @param username
     * @param time
     * @return
     */
//    @PostMapping("/p1")
    @ResponseBody
    public String dateMapping2(User user, String username, Date time) {//使用JavaBean作为参数 Spring mvc 会自动给Bean赋值 寻找同名的请求参数
        //username 参数Spring MVC也会一并赋值 只要命名和请求参数一致
        System.out.println("user:" + user);
        return "This is java bean post -> " + user;
    }

    /**
     * 获取复合数据 - 数组
     *
     * @param name
     * @param course
     * @param purpose
     * @return
     */
//    @PostMapping("/apply")
    @ResponseBody
    public String postMapping2(String name, String course, Integer[] purpose) {
        for (Integer integer : purpose) {
            System.out.println("purpose:" + integer);
        }
        return "This is java bean post -> " + name + " course:" + course + " purpose:" + purpose.toString();
    }

    /**
     * 获取复合数据 - List 注意：List 一定要添加@RequestParam注解
     *
     * @param name
     * @param course
     * @param purpose
     * @return
     */
//    @PostMapping("/apply")
    @ResponseBody
    public String postMapping2(String name, String course, @RequestParam List<Integer> purpose) {
        for (Integer integer : purpose) {
            System.out.println("purpose:" + integer);
        }
        return "This is java bean post -> " + name + " course:" + course + " purpose:" + purpose.toString();
    }

    /**
     * 获取复合数据 - javabean
     */
//    @PostMapping("/apply")
    @ResponseBody
    public String postMapping2(Form form) {
        for (Integer integer : form.getPurpose()) {
            System.out.println("purpose:" + integer);
        }
        return "获取复合数据 - javabean This is java bean post -> " + form.getName() + " course:" + form.getCourse() + " purpose:" + form.getPurpose().toString();
    }

    /**
     * 关联对象 - 用于复杂的表单处理
     * javabean中 还有一个对象。这种关联对象如何给内部对象赋值
     */
    @PostMapping("/apply")
    @ResponseBody
    public String postMapping3(Form form) {
        for (Integer integer : form.getPurpose()) {
            System.out.println("purpose:" + integer);
        }
        System.out.println("IDCard:" + form.getIdCard());
        return "获取复合数据 - javabean This is java bean post -> " + form.getName() + " course:" + form.getCourse() + " purpose:" + form.getPurpose().toString();
    }


    //    @PostMapping("/p1")
    @ResponseBody
    public String responseBody1(User user, String username, Date time) {//使用JavaBean作为参数 Spring mvc 会自动给Bean赋值 寻找同名的请求参数
        //username 参数Spring MVC也会一并赋值 只要命名和请求参数一致
        System.out.println("user:" + user);
//        return "<h1>This ResponseBody</h1>";
        return "<h1>This ResponseBody</h1>";
    }

    /**
     * ModelAndView 将界面与数据进行绑定
     *
     * @param userId
     * @return
     */
//    @GetMapping("/view")
    public ModelAndView showView(Integer userId) {
        //默认是请求转发的方式跳转页面 forward
        //redirect: 使用重定向方式跳转
//        ModelAndView modelAndView = new ModelAndView("redirect:/view.jsp");
        ModelAndView modelAndView = new ModelAndView();
        //也可以setViewName设置
        //如果没有设置前缀 如果没有/默认 是访问 @RequestMapping("/um") /um/view.jsp
        //一般是不推荐这样写的 最好写明全路径
        modelAndView.setViewName("view.jsp");
        User user = new User();
        if (userId == 1) {
            user.setUsername("lily");
        } else if (userId == 2) {
            user.setUsername("smith");
        }
        //设置数据 在视图展示 addObject 将数据设置在请求request的中
        modelAndView.addObject("u", user);
        return modelAndView;
    }

    //

    /**
     * String 与 ModelMap
     * Controller方法返回String的情况
     * 1. 方法被@ResponseBody描述，SpringMVC直接响应String字符串本身
     * 2. 方法不存在@ResponseBody,则SpringMVC处理Spring指代的视图
     *
     * @param modelMap
     * @return
     */
    @PostMapping("/view")
    public String showView(Float height, Float weight, ModelMap modelMap) {
        //代表显示哪个视图`
        String view = "/um/view.jsp";
        float bmi = weight / ((height * height) / 100);
        if (bmi < 19) {
            modelMap.addAttribute("u", "多吃点，太瘦了，注意加强营养");
        } else if (bmi > 25) {
            modelMap.addAttribute("u", "该减肥了！注意加强锻炼");
        } else {
            modelMap.addAttribute("u", "体重正常，注意保持");
        }
        return view;
    }
}
