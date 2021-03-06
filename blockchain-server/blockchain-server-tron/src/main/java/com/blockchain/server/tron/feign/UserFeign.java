package com.blockchain.server.tron.feign;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

/**
 * 用户微服务
 *
 * @author huangxl
 * @create 2019-02-28 17:39
 */
@FeignClient("dapp-user-server")
public interface UserFeign {
    @PostMapping("/backend/user/inner/user/listUserInfo")
    ResultDTO<Map<String, UserBaseInfoDTO>> userInfos(@RequestBody Set<String> list);

    @PostMapping("/backend/user/inner/user/selectUserInfoByUserName")
    ResultDTO<UserBaseInfoDTO> selectUserInfoByUserName(@RequestParam("userName") String userName);

    /**
     * 通过userId获取用户信息
     * @param userId
     * @return
     */
    @PostMapping("/backend/user/inner/user/selectUserInfo")
    ResultDTO<UserBaseInfoDTO> selectUserInfoById(@RequestParam("userId") String userId);
}
