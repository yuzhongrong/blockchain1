package com.blockchain.server.otc.service.impl;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.server.otc.common.enums.MarketUserEnums;
import com.blockchain.server.otc.dto.marketuserhandlelog.ListMarketUserHandleLogResultDTO;
import com.blockchain.server.otc.entity.MarketUserHandleLog;
import com.blockchain.server.otc.feign.UserFeign;
import com.blockchain.server.otc.mapper.MarketUserHandleLogMapper;
import com.blockchain.server.otc.service.MarketUserHandleLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MarketUserHandleLogServiceImpl implements MarketUserHandleLogService {

    @Autowired
    private MarketUserHandleLogMapper marketUserHandleLogMapper;
    @Autowired
    private UserFeign userFeign;

    @Override
    @Transactional
    public String insertUserHandleLog(String ipAddress, String sysUserId, String userId, String beforeStatus, String afterStatus) {
        MarketUserHandleLog marketUserHandleLog = new MarketUserHandleLog();
        Date now = new Date();
        String handleLogId = UUID.randomUUID().toString();
        marketUserHandleLog.setId(handleLogId);
        marketUserHandleLog.setIpAddress(ipAddress);
        marketUserHandleLog.setSysUserId(sysUserId);
        marketUserHandleLog.setUserId(userId);
        marketUserHandleLog.setBeforeStatus(null);
        marketUserHandleLog.setAfterStatus(MarketUserEnums.STATUS_MARKET.getValue());
        marketUserHandleLog.setCreateTime(now);
        marketUserHandleLogMapper.insertSelective(marketUserHandleLog);
        return handleLogId;
    }

    @Override
    public List<ListMarketUserHandleLogResultDTO> list(String userName, String beginTime, String endTime) {
        //查询参数有用户信息时
        if (StringUtils.isNotBlank(userName)) {
            return listByUser(userName, beginTime, endTime);
        } else {
            //查询参数没有用户信息时
            return listByCondition(beginTime, endTime);
        }
    }

    /***
     * 根据用户查询
     * @param userName
     * @param beginTime
     * @param endTime
     * @return
     */
    private List<ListMarketUserHandleLogResultDTO> listByUser(String userName, String beginTime, String endTime) {
        //调用feign查询用户信息
        UserBaseInfoDTO userBaseInfoDTO = selectUserByUserName(userName);
        //用户信息等于空，返回没有userid的查询数据
        if (userBaseInfoDTO == null) {
            return marketUserHandleLogMapper.list(null, beginTime, endTime);
        }
        //查询列表
        List<ListMarketUserHandleLogResultDTO> resultDTOS = marketUserHandleLogMapper.list(userBaseInfoDTO.getUserId(), beginTime, endTime);
        //设置用户信息
        for (ListMarketUserHandleLogResultDTO resultDTO : resultDTOS) {
            resultDTO.setUserName(userBaseInfoDTO.getMobilePhone());
            resultDTO.setRealName(userBaseInfoDTO.getRealName());
        }
        return resultDTOS;
    }

    /***
     * 根据条件查询
     * @param beginTime
     * @param endTime
     * @return
     */
    private List<ListMarketUserHandleLogResultDTO> listByCondition(String beginTime, String endTime) {
        //查询列表
        List<ListMarketUserHandleLogResultDTO> resultDTOS = marketUserHandleLogMapper.list(null, beginTime, endTime);
        //封装userId集合，用于一次性查询用户信息
        Set userIds = new HashSet();
        //封装用户id
        for (ListMarketUserHandleLogResultDTO resultDTO : resultDTOS) {
            userIds.add(resultDTO.getUserId());
        }
        //防止用户ids为空
        if (userIds.size() == 0) {
            return resultDTOS;
        }
        //调用feign一次性查询用户信息
        Map<String, UserBaseInfoDTO> userInfos = listUserInfos(userIds);
        //防止返回用户信息为空
        if (userInfos.size() == 0) {
            return resultDTOS;
        }
        //设置用户信息
        for (ListMarketUserHandleLogResultDTO resultDTO : resultDTOS) {
            //根据用户id从map中获取用户数据
            UserBaseInfoDTO user = userInfos.get(resultDTO.getUserId());
            //防空
            if (user != null) {
                resultDTO.setUserName(user.getMobilePhone());
                resultDTO.setRealName(user.getRealName());
            }
        }
        //返回列表
        return resultDTOS;
    }

    /***
     * 根据userName查询用户信息
     * @param userName
     * @return
     */
    private UserBaseInfoDTO selectUserByUserName(String userName) {
        ResultDTO<UserBaseInfoDTO> resultDTO = userFeign.selectUserInfoByUserName(userName);
        return resultDTO.getData();
    }

    /***
     * 根据id集合查询多个用户信息
     * @param userIds
     * @return
     */
    private Map<String, UserBaseInfoDTO> listUserInfos(Set<String> userIds) {
        ResultDTO<Map<String, UserBaseInfoDTO>> resultDTO = userFeign.listUserInfo(userIds);
        return resultDTO.getData();
    }
}
