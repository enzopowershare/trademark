package com.cloudwise.trademark.controller;

import com.cloudwise.trademark.entity.Dictionary;
import com.cloudwise.trademark.entity.PageBean;
import com.cloudwise.trademark.entity.ReturnBean;
import com.cloudwise.trademark.entity.Business;
import com.cloudwise.trademark.service.BusinessService;
import com.cloudwise.trademark.service.DictionaryService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

/**
 * (TblBusiness)表控制层
 *
 * @author makejava
 * @since 2020-12-22 11:12:23
 */
@RestController
@RequestMapping("business")
public class BusinessController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private BusinessService BusinessService;
    @Resource
    private DictionaryService dictionaryService;

    @GetMapping("toBusiness")
    public ModelAndView toBusiness() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("manage/business");
        return modelAndView;
    }

    /**
     * 通过主键查询单条数据
     *
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ReturnBean selectOne(Integer businessId) {
        Business business = this.BusinessService.queryById(businessId);
        List<Business> list = new ArrayList<>();
        list.add(business);
        return returnSuccess(list);
    }


    /**
     * @param pageBean,tb
     * @return
     * @create by: Back
     * @description: 条件查询
     * @create time: 2020/12/24 9:40
     */

    @GetMapping("queryByCondition")
    public ReturnBean queryByCondition(PageBean pageBean, Business tb) {
        //计算分页参数
        int offset = getOffset(pageBean);
        List<Business> tbList = BusinessService.queryAllByConditionAndLimit(tb, offset, pageBean.getLimit());
        long count = BusinessService.getCount(tb);
        //返回json结果
        ReturnBean returnBean = returnSuccess(tbList);
        returnBean.setCount(count);
        return returnBean;
    }

    /**
     * @param tb
     * @return
     * @create by: Back
     * @description: 添加业务
     * @create time: 2020/12/24 9:40
     */

    @PostMapping("add")
    public ReturnBean addBusiness(Business tb) {
        try {
            tb.setCreateTime(new Date());
            Business emp1 = BusinessService.insert(tb);
            return returnSuccess(emp1);
        } catch (Exception e) {
            return returnFail(null);
        }
    }

    /**
     * @param tb
     * @return
     * @create by: Back
     * @description: 编辑业务
     * @create time: 2020/12/24 9:41
     */

    @PostMapping("edit")
    public ReturnBean editBusiness(Business tb) {
        try {
            tb.setUpdateTime(new Date());
            Business blist1 = BusinessService.update(tb);
            return returnSuccess(blist1);
        } catch (Exception e) {
            return returnFail(null);
        }
    }

    /**
     * @return
     * @create by: Back
     * @description: 查询字典，填充下拉列表
     * @create time: 2020/12/24 9:41
     */

    @GetMapping("findAllDictionary")
    public List<Map<String, Object>> findAllDictionary() {
        List<Map<String, Object>> allDictionary = BusinessService.findAllDictionary();
        return allDictionary;
    }

    /**
     * @param mv:
     * @return org.springframework.web.servlet.ModelAndView
     * @create by: IvanZ
     * @description : 显示客户信息和业务信息
     * @create time: 2020/12/23 15:56
     */
    @GetMapping("showCustomAndBusiness")
    public ModelAndView showCustomAndBusiness(int customId, ModelAndView mv) {
        mv.addObject("customId", customId);
        mv.setViewName("manage/business");
        return mv;
    }

    /**
     * @param :
     * @return com.cloudwise.trademark.entity.ReturnBean
     * @create by: IvanZ
     * @description : 业务量走势图
     * @create time: 2020/12/25 20:36
     */
    @GetMapping("showBusinessChart")
    public ReturnBean showBusinessChart() {
        Map<String, Object> map = BusinessService.showBusinessChart();
        return returnSuccess(map);
    }

    /**
     * @param :
     * @return com.cloudwise.trademark.entity.ReturnBean
     * @create by: IvanZ
     * @description : 业绩排行榜
     * @create time: 2020/12/25 20:36
     */
    @GetMapping("showPerformanceRanking")
    public ReturnBean showPerformanceRanking() {
        Map<String, Object> map = BusinessService.showPerformanceRanking();
        return returnSuccess(map);
    }

    /**
     * @param :
     * @return com.cloudwise.trademark.entity.ReturnBean
     * @create by: IvanZ
     * @description : 得到首页数据
     * @create time: 2020/12/28 20:06
     */
    @GetMapping("getIndexData")
    public ReturnBean getIndexData() {
        List<Integer> indexData = BusinessService.getIndexData();
        return returnSuccess(indexData);
    }

    /**
     * @param :
     * @return com.cloudwise.trademark.entity.ReturnBean
     * @create by: ydq
     * @description : 业务分析
     * @create time: 2020/12/29 11:57
     */
    @GetMapping("businessEchart")
    public ReturnBean businessEchart() {
        List<Long> longs = BusinessService.businessEchart();
        return returnSuccess(longs);
    }

    /**
     * @return
     * @create by: ydq
     * @description: 方法作用：业务分析按代理人
     * @create time: 2020/12/30 20:03
     * @param:
     */
    @GetMapping("showAgentChart")
    public ReturnBean showAgentChart(String startTime, String endTime) {
        Map<String, Object> map = BusinessService.showAgentChart(startTime, endTime);
        return returnSuccess(map);
    }

    /**
     * @return
     * @create by: ydq
     * @description: 方法作用：业务分析按时间
     * @create time: 2020/12/30 20:33
     * @param:
     */
    @GetMapping("showBusinessChartByAgent")
    public ReturnBean showBusinessChartByAgent(String loginName) {
        Map<String, Object> map = BusinessService.showBusinessChartByAgent(loginName);
        return returnSuccess(map);
    }

    /**
     * @param :
     * @return com.cloudwise.trademark.entity.ReturnBean
     * @create by: IvanZ
     * @description : 进度分析，以代理人为横轴
     * @create time: 2020/12/29 11:57
     */
    @GetMapping("progressEchartName")
    public ReturnBean progressEchartName(String progressId, String startTime, String endTime) {

        if (startTime == null || "".equals(startTime)) {
            startTime = "2000-01-01";
        }
        if (endTime == null || "".equals(endTime)) {
            endTime = "2100-01-01";
        }
        if (progressId != null && !"".equals(progressId)) {
            int i = Integer.parseInt(progressId);
            Dictionary dictionary = dictionaryService.queryById(i);

            Map<String, Object> map = BusinessService.progressEchartName(startTime, endTime, dictionary.getDictionaryName());
            return returnSuccess(map);
        } else {
            Map<String, Object> map = BusinessService.progressEchartName(startTime, endTime, null);
            return returnSuccess(map);
        }
    }


    /**
     * @return
     * @create by: Back
     * @description: 进度分析，以时间为横轴
     * @create time: 2020/12/30 10:12
     */
    @GetMapping("progressEchartTime")
    public ReturnBean progressEchartTime(String loginName, String processId) {

        if (processId != null && !"".equals(processId)) {
            int i = Integer.parseInt(processId);
            Dictionary dictionary = dictionaryService.queryById(i);
            Map<String, Object> map = BusinessService.progressEchartTime(loginName, dictionary.getDictionaryName());
            return returnSuccess(map);
        } else {
            Map<String, Object> map = BusinessService.progressEchartTime(loginName, null);
            return returnSuccess(map);
        }


    }

    /**
     * @return
     * @create by: Back
     * @description: 查询所有进度类型
     * @create time: 2020/12/30 14:10
     */
    @GetMapping("selectProcessType")
    public ReturnBean selectProcessType(PageBean pageBean, com.cloudwise.trademark.entity.Dictionary dictionary) {
        int offset = getOffset(pageBean);
        List<Dictionary> dictionaries = dictionaryService.queryAllByConditionAndLimit(dictionary, offset, pageBean.getLimit());
        long rowCount = dictionaryService.getRowCount(dictionary);
        return returnSuccess(dictionaries, rowCount);
    }

    /**
     * @param :
     * @return com.cloudwise.trademark.entity.ReturnBean
     * @create by: IvanZ
     * @description : 回访分析根据代理人
     * @create time: 2020/12/29 11:57
     */
    @GetMapping("visitEchartName")
    public ReturnBean visitEchartName(String startTime, String endTime) {
        if (startTime == null || "".equals(startTime)) {
            startTime = "2000-01-01";
        }
        if (endTime == null || "".equals(endTime)) {
            endTime = "2100-01-01";
        }
        Map<String, Object> map = BusinessService.showVisitEchartName(startTime, endTime);
        return returnSuccess(map);
    }

    /**
     * @param :
     * @return com.cloudwise.trademark.entity.ReturnBean
     * @create by: IvanZ
     * @description : 回访分析根据时间
     * @create time: 2020/12/30 10:04
     */
    @GetMapping("visitEchartTime")
    public ReturnBean visitEchartTime(String loginName) {
        if (loginName == null || "".equals(loginName)) {
            loginName = "enzo";
        }
        Map<String, Object> map = BusinessService.showVisitEchartTime(loginName);
        return returnSuccess(map);
    }

    /**
     * @param :
     * @return :
     * @create by : ydq
     * @description: 方法作用 ：根据邮箱返回所有业务
     * @create time : 2021/1/11 15:48
     */
    @GetMapping("showBusinessByEmail")
    public ReturnBean showBusinessByEmail(String email) {
        //计算分页参数
        List<Business> tbList = BusinessService.showBusinessByEmail(email);
        //返回json结果
        ReturnBean returnBean = returnSuccess(tbList);
        return returnBean;
    }
}