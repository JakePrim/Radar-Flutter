package com.prim.controller;

import com.prim.domain.Account;
import com.prim.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/findAll")
    public String findAll(Model model) {
        List<Account> list = accountService.findAll();
        model.addAttribute("list", list);
        return "list";
    }

    @RequestMapping("/save")
    public String save(Account account) {
        accountService.save(account);
        //跳转到 findAll方法重新查询一次数据库进行数据展示
        return "redirect:/account/findAll";
    }

    /**
     * 数据回显
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/findById")
    public String findById(Integer id, Model model) {
        Account account = accountService.findById(id);
        //存到model中
        model.addAttribute("account", account);
        return "update";
    }

    @RequestMapping("/update")
    public String update(Account account) {
        accountService.update(account);
        return "redirect:/account/findAll";
    }

    @RequestMapping("/delete")
    public String delete(Integer id) {
        accountService.delete(id);
        return "redirect:/account/findAll";
    }

    /**
     * 批量删除
     *
     * @return
     */
    @RequestMapping("/deleteBatch")
    public String deleteBatch(Integer[] ids) {
        accountService.deleteBatch(ids);
        return "redirect:/account/findAll";
    }
}
