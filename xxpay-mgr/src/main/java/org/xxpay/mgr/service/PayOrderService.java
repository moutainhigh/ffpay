package org.xxpay.mgr.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xxpay.dal.dao.mapper.PayOrderMapper;
import org.xxpay.dal.dao.model.PayOrder;
import org.xxpay.dal.dao.model.PayOrderExample;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dingzhiwei on 17/5/7.
 */
@Service
public class PayOrderService {

    @Autowired
    private PayOrderMapper payOrderMapper;

    public PayOrder selectPayOrder(String payOrderId) {
        return payOrderMapper.selectByPrimaryKey(payOrderId);
    }

    public List<PayOrder> getPayOrderList(int offset, int limit, PayOrder payOrder) {
        PayOrderExample example = new PayOrderExample();
        example.setOrderByClause("createTime DESC");
        example.setOffset(offset);
        example.setLimit(limit);
        PayOrderExample.Criteria criteria = example.createCriteria();
        setCriteria(criteria, payOrder);
        return payOrderMapper.selectByExample(example);
    }
    public List<PayOrder> getPayOrderList(PayOrder payOrder) {
        PayOrderExample example = new PayOrderExample();
        example.setOrderByClause("createTime DESC");
        example.setOffset(null);
        example.setLimit(null);
        PayOrderExample.Criteria criteria = example.createCriteria();
        setCriteria(criteria, payOrder);
        return payOrderMapper.selectByExample(example);
    }
    public List<PayOrder> getYestardayPayOrderList(String mchId) {
        return payOrderMapper.selectYestardayOrder(mchId);
    }
    public  List<PayOrder> selectOrderStatusCount(Integer OrderNumber) {
        return payOrderMapper.selectOrderStatusCount(OrderNumber);
    }


    public Integer count(PayOrder payOrder) {
        PayOrderExample example = new PayOrderExample();
        PayOrderExample.Criteria criteria = example.createCriteria();
        setCriteria(criteria, payOrder);
        return payOrderMapper.countByExample(example);
    }

    void setCriteria(PayOrderExample.Criteria criteria, PayOrder payOrder) {
        if(payOrder != null) {
            if(StringUtils.isNotBlank(payOrder.getMchId())) criteria.andMchIdEqualTo(payOrder.getMchId());
            if(StringUtils.isNotBlank(payOrder.getPayOrderId())) criteria.andPayOrderIdEqualTo(payOrder.getPayOrderId());
            if(StringUtils.isNotBlank(payOrder.getMchOrderNo())) criteria.andMchOrderNoEqualTo(payOrder.getMchOrderNo());
            if(StringUtils.isNotBlank(payOrder.getChannelOrderNo())) criteria.andChannelOrderNoEqualTo(payOrder.getChannelOrderNo());
            if(payOrder.getStatus() != null && payOrder.getStatus() != -99) criteria.andStatusEqualTo(payOrder.getStatus());
            if(payOrder.getCreateTime()!=null) criteria.andCreateTimeBetween(payOrder.getCreateTime(),payOrder.getUpdateTime());
        }
    }

}
