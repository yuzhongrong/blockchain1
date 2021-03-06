package com.blockchain.server.otc.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MarketUserHandleLog 数据传输类
 *
 * @version 1.0
 * @date 2019-07-13 11:48:11
 */
@Table(name = "otc_market_user_handle_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketUserHandleLog extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "sys_user_id")
    private String sysUserId;
    @Column(name = "ip_address")
    private String ipAddress;
    @Column(name = "before_status")
    private String beforeStatus;
    @Column(name = "after_status")
    private String afterStatus;
    @Column(name = "create_time")
    private java.util.Date createTime;

}