package com.blockchain.server.cmc.mapper;

import com.blockchain.server.cmc.dto.BtcWalletOutDTO;
import com.blockchain.server.cmc.entity.BtcWalletOut;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * BtcWalletOutMapper 数据访问类
 * @date 2019-02-16 15:08:16
 * @version 1.0
 */
@Repository
public interface BtcWalletOutMapper extends Mapper<BtcWalletOut> {
    List<BtcWalletOutDTO> listByTokenSymbol(@Param("tokenSymbol") String tokenSymbol);
}