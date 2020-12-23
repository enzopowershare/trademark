package com.cloudwise.trademark.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Enzo
 * @version 1.0
 * @date Created at 2020/12/16 15:19
 * @description 控制页面跳转
 * @modifiedBy
 */
@Controller
@RequestMapping("goto")
public class GotoController {

    @GetMapping("toLogin")
    public String toLogin() {
        return "login";
    }

    @GetMapping("toHome")
    public String toShow() {
        return "home";
    }

    @GetMapping("toDept")
    public String toDept() {
        return "dept";
    }

    @GetMapping("toMenu")
    public String toMenu() {
        return "menu";
    }

    @GetMapping("toUser")
    public String toUser() {
        return "user";
    }

    @GetMapping("toRole")
    public String toRole() {
        return "role";
    }

    @GetMapping("toProgress")
    public String toProgress() {
        return "progress";
    }

    @GetMapping("toCustom")
    public String toCustom() {
        return "custom";
    }

    @GetMapping("toCharts")
    public String toCharts() {
        return "businessStateView";
    }

    @GetMapping("toBusiness")
    public String toBusiness() {
        return "business";
    }

    @GetMapping("toUserinfo")
    public String toUserinfo() {
        return "userinfo";
    }

    @GetMapping("toDictionary")
    public String toDictionary() {
        return "dictionary";
    }

    @GetMapping("toDictionaryType")
    public String toDictionaryType() {
        return "dictionaryType";
    }
}