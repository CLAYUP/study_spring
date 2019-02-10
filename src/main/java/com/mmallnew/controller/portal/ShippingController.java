package com.mmallnew.controller.portal;

import com.mmallnew.common.Const;
import com.mmallnew.common.ResponseCode;
import com.mmallnew.common.ServiceResponse;
import com.mmallnew.pojo.Shipping;
import com.mmallnew.pojo.User;
import com.mmallnew.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 地址
 *
 * @author ：Y.
 * @version :V1.0
 * @date ：Created in 10:47 2019/2/10
 */
@Controller
@RequestMapping("/shipping/")
public class ShippingController {

    @Autowired
    private IShippingService iShippingService;

    /**
     * 添加收货地址
     *
     * @return com.mmallnew.common.ServiceResponse
     * @author Y.
     * @date 11:03 2019/2/10
     */
    @RequestMapping("add.do")
    @ResponseBody
    public ServiceResponse add(HttpSession session, Shipping shipping) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return iShippingService.add(user.getId(), shipping);
        }
        return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                ResponseCode.NEED_LOGIN.getDesc());
    }

    /**
     * 删除收货地址
     *
     * @return com.mmallnew.common.ServiceResponse
     * @author Y.
     * @date 20:02 2019/2/10
     */
    @RequestMapping("delete.do")
    @ResponseBody
    public ServiceResponse delete(HttpSession session, Integer shippingId) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return iShippingService.delete(user.getId(), shippingId);
        }
        return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                ResponseCode.NEED_LOGIN.getDesc());
    }

    /**
     * 跟新收货地址
     *
     * @return com.mmallnew.common.ServiceResponse
     * @author Y.
     * @date 20:03 2019/2/10
     */
    @RequestMapping("update.do")
    @ResponseBody
    public ServiceResponse update(HttpSession session, Shipping shipping) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return iShippingService.update(user.getId(), shipping);
        }
        return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                ResponseCode.NEED_LOGIN.getDesc());
    }

    /**
     * 获取地址详情
     *
     * @return com.mmallnew.common.ServiceResponse
     * @author Y.
     * @date 20:03 2019/2/10
     */
    @RequestMapping("shippingDetail.do")
    @ResponseBody
    public ServiceResponse shippingDetail(HttpSession session, Integer shippingId) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return iShippingService.shippingDetail(user.getId(), shippingId);
        }
        return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                ResponseCode.NEED_LOGIN.getDesc());
    }

    /**
     * 获取地址列表页面
     *
     * @return com.mmallnew.common.ServiceResponse
     * @author Y.
     * @date 20:03 2019/2/10
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServiceResponse list(HttpSession session, @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return iShippingService.list(user.getId(), pageIndex, pageSize);
        }
        return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                ResponseCode.NEED_LOGIN.getDesc());
    }

}
