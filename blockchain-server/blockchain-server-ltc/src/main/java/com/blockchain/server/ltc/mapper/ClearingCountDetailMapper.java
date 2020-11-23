package com.blockchain.server.ltc.mapper;

import com.blockchain.server.ltc.entity.ClearingCountDetail;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * ClearingCountDetail 数据访问类
 * @date 2019-02-16 15:44:06
 * @version 1.0
 */
@Repository
public interface ClearingCountDetailMapper extends Mapper<ClearingCountDetail> {
}