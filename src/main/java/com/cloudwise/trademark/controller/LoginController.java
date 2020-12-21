package com.cloudwise.trademark.controller;

import com.cloudwise.trademark.entity.LayUiTree;
import com.cloudwise.trademark.entity.Menu;
import com.cloudwise.trademark.entity.User;
import com.cloudwise.trademark.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author Enzo
 * @version 1.0
 * @data Created at 2020/12/17 22:36
 * @description login controller
 * @modifiedBy
 */
@Controller
public class LoginController {
    @Autowired
    private MenuService menuService;

    @PostMapping("login")
    public ModelAndView login(String username, String password) {
        ModelAndView modelAndView = new ModelAndView();
        //获取shiro的连接器
        Subject subject = SecurityUtils.getSubject();
        //构建登录令牌
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            modelAndView.addObject("message", "account not found");
            modelAndView.setViewName("login");
        } catch (IncorrectCredentialsException e) {
            modelAndView.addObject("message", "incorrect password");
            modelAndView.setViewName("login");
        }
        //在右上角显示用户名和照片
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        modelAndView.addObject("user", user);
        // 在左侧显示树状的菜单导航。根据登录的用户名，查询该用户的所有菜单。
        List<LayUiTree> layUiTreeList = menuService.queryAllTreeByLoginName(username);
        modelAndView.addObject("layUiTreeList", layUiTreeList);
        //查询该用户有权限的所有菜单
        final List<Menu> menusByLoginName = menuService.findMenusByLoginName(username);
        modelAndView.addObject("menus", menusByLoginName);
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping("logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }
}