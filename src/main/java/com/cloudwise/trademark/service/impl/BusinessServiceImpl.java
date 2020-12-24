package com.cloudwise.trademark.service.impl;

import com.cloudwise.trademark.entity.Business;
import com.cloudwise.trademark.dao.BusinessDao;
import com.cloudwise.trademark.service.BusinessService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * (TblBusiness)表服务实现类
 *
 * @author makejava
 * @since 2020-12-22 11:12:23
 */
@Service("businessService")
public class BusinessServiceImpl implements BusinessService {
    @Resource
    private BusinessDao businessDao;

    /**
     * 通过ID查询单条数据
     *
     * @param businessId 主键
     * @return 实例对象
     */
    @Override
    public Business queryById(Integer businessId) {
        return this.businessDao.queryById(businessId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Business> queryAllByLimit(int offset, int limit) {
        return this.businessDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param business 实例对象
     * @return 实例对象
     */
    @Override
    public Business insert(Business business) {
        this.businessDao.insert(business);
        return business;
    }

    /**
     * 修改数据
     *
     * @param tb 实例对象
     * @return 实例对象
     */
    @Override
    public Business update(Business tb) {
        this.businessDao.update(tb);
        return this.queryById(tb.getBusinessId());
    }

    /**
     * 通过主键删除数据
     *
     * @param businessId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer businessId) {
        return this.businessDao.deleteById(businessId) > 0;
    }

    /**
     * @param tb offset   limit
     * @return
     * @create by: Back
     * @description: 包含分页和条件的查询，无参数传入则为查询全部
     * @create time: 2020/12/24 9:32
     */
    @Override
    public List<Business> queryAllByConditionAndLimit(Business tb, int offset, int limit) {
        List<Business> businesses = this.businessDao.queryAllByConditionAndLimit(tb, offset, limit);
        for (Business business : businesses) {
            String status =  businessDao.findTypeById(business.getBusinessId());
            business.setStatus(status);
        }
        return businesses;
    }

    /**
     * @param tb
     * @return
     * @create by: Back
     * @description: 根据传入实体类计算所查询出的数据条数
     * @create time: 2020/12/24 9:34
     */

    @Override
    public long getCount(Business tb) {
        return this.businessDao.getCount(tb);
    }

    /**
     * @return
     * @create by: Back
     * @description: 查询字典表，并返回对应List
     * @create time: 2020/12/24 9:35
     */
    @Override
    public List<Map<String, Object>> findAllDictionary() {
        return this.businessDao.findAllDictionary();
    }
}