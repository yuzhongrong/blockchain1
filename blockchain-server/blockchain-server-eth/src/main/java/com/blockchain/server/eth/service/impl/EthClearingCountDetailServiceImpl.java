package com.blockchain.server.eth.service.impl;


import com.blockchain.server.eth.common.constants.tx.DetailTxConstants;
import com.blockchain.server.eth.common.enums.EthWalletEnums;
import com.blockchain.server.eth.common.exception.EthWalletException;
import com.blockchain.server.eth.entity.EthClearingCountDetail;
import com.blockchain.server.eth.entity.EthClearingCountTotal;
import com.blockchain.server.eth.mapper.EthClearingCountDetailMapper;
import com.blockchain.server.eth.service.IEthClearingCountDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 财务详情记录——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
@Service
public class EthClearingCountDetailServiceImpl implements IEthClearingCountDetailService {

    @Autowired
    EthClearingCountDetailMapper ethClearingCountDetailMapper;


    @Override
    public List<EthClearingCountDetail> selectByTotalId(String totalId) {
        EthClearingCountDetail detail = new EthClearingCountDetail();
        detail.setTotalId(totalId);
        return ethClearingCountDetailMapper.select(detail);
    }

    @Override
    @Transactional
    public void insert(EthClearingCountTotal total, Map<String, BigDecimal> fromMap, Map<String, BigDecimal> toMap) {
        insert(total, fromMap, DetailTxConstants.FROM);
        insert(total, toMap, DetailTxConstants.TO);
    }

    @Override
    @Transactional
    public void insert(EthClearingCountTotal countTotal, Map<String, BigDecimal> map, String type) {
        for (Map.Entry<String, BigDecimal> dataMap : map.entrySet()) {
            EthClearingCountDetail detail = new EthClearingCountDetail();
            detail.setId(UUID.randomUUID().toString());
            detail.setTotalId(countTotal.getId());
            detail.setCreateTime(countTotal.getCreateTime());
            BigDecimal transferAmount = type.equalsIgnoreCase(DetailTxConstants.FROM) ?
                    dataMap.getValue().negate() : dataMap.getValue();
            detail.setTransferAmount(transferAmount);
            detail.setTransferType(dataMap.getKey());
            int row = ethClearingCountDetailMapper.insert(detail);
            if (row == 0) {
                throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
            }
        }
    }

    @Override
    @Transactional
    public void insert(String totalId, String txType, BigDecimal amount, Date endDate) {
        EthClearingCountDetail detail = new EthClearingCountDetail();
        detail.setId(UUID.randomUUID().toString());
        detail.setTotalId(totalId);
        detail.setTransferType(txType);
        detail.setTransferAmount(amount);
        detail.setCreateTime(endDate);
        int row = ethClearingCountDetailMapper.insert(detail);
        if (row == 0) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

}
