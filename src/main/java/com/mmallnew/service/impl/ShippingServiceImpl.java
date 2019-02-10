package com.mmallnew.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmallnew.common.ServiceResponse;
import com.mmallnew.dao.ShippingMapper;
import com.mmallnew.pojo.Shipping;
import com.mmallnew.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 地址服务类
 *
 * @author ：Y.
 * @version :V1.0
 * @date ：Created in 10:48 2019/2/10
 */
@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {


    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ServiceResponse add(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if (rowCount > 0) {
            HashMap<Object, Object> result = Maps.newHashMap();
            result.put("shippingId", shipping.getId());
            return ServiceResponse.createBySuccess("新建地址成功", result);
        }
        return ServiceResponse.createByErrorMessage("新建地址失败");
    }

    @Override
    public ServiceResponse<String> delete(Integer userId, Integer shippingId) {
        int rowCount = shippingMapper.deleteByPrimaryKeyAndUserId(userId, shippingId);
        if (rowCount > 0) {
            return ServiceResponse.createBySuccessMessage("删除地址成功");
        }
        return ServiceResponse.createByErrorMessage("删除地址失败");
    }

    @Override
    public ServiceResponse<String> update(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int rowCount = shippingMapper.updateByShippingAndUserId(shipping);
        if (rowCount > 0) {
            return ServiceResponse.createBySuccessMessage("更新地址成功");
        }
        return ServiceResponse.createByErrorMessage("更新地址失败");
    }

    @Override
    public ServiceResponse shippingDetail(Integer userId, Integer shippingId) {
        Shipping shipping = shippingMapper.selectShippingByShippingIdAndUserId(userId, shippingId);
        if (shipping != null) {
            return ServiceResponse.createBySuccess("查询成功", shipping);
        }
        return ServiceResponse.createByErrorMessage("查询失败");
    }

    @Override
    public ServiceResponse list(Integer userId, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo<Shipping> pageInfo = new PageInfo<>(shippingList);
        return ServiceResponse.createBySuccess("查询成功", pageInfo);
    }

}
