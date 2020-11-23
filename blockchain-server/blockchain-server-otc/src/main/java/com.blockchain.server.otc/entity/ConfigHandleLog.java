package com.blockchain.server.otc.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ConfigHandleLog 数据传输类
 *
 * @version 1.0
 * @date 2019-05-06 16:39:22
 */
@Table(name = "otc_config_handle_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigHandleLog extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "sys_user_id")
    private String sysUserId;
    @Column(name = "ip_address")
    private String ipAddress;
    @Column(name = "data_key")
    private String dataKey;
    @Column(name = "before_value")
    private String beforeValue;
    @Column(name = "after_value")
    private String afterValue;
    @Column(name = "before_status")
    private String beforeStatus;
    @Column(name = "after_status")
    private String afterStatus;
    @Column(name = "create_time")
    private java.util.Date createTime;

}