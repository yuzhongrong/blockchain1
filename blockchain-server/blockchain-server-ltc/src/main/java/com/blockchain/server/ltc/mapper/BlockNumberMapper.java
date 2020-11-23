package com.blockchain.server.ltc.mapper;

import com.blockchain.server.ltc.entity.BlockNumber;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * BlockNumberMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:16
 */
@Repository
public interface BlockNumberMapper extends Mapper<BlockNumber> {

}