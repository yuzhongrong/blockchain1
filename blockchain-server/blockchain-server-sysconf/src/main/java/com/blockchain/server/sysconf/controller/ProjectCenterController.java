package com.blockchain.server.sysconf.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.sysconf.controller.api.SysconfImageApi;
import com.blockchain.server.sysconf.controller.api.SysconfNoticeApi;
import com.blockchain.server.sysconf.entity.ProjectCenterInfo;
import com.blockchain.server.sysconf.service.ProjectCenterService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 项目中心
 *
 */
@RestController
@RequestMapping("/projectCenter")
public class ProjectCenterController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectCenterController.class);

    @Autowired
    private ProjectCenterService projectCenterService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = SysconfImageApi.SystemImageList.METHOD_TITLE_NAME, notes = SysconfImageApi.SystemImageList.METHOD_TITLE_NOTE)
    public ResultDTO list(@ApiParam(SysconfNoticeApi.SystemNoticeList.METHOD_API_STATUS) @RequestParam(required = false, value = "status") String status,
                          @ApiParam(SysconfNoticeApi.SystemNoticeList.METHOD_API_STATUS) @RequestParam(required = false, value = "currencyName") String currencyName,
                          @ApiParam(SysconfImageApi.SystemImageList.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX, required = false) Integer pageNum,
                          @ApiParam(SysconfImageApi.SystemImageList.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE, required = false) Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return generatePage(projectCenterService.list(status,currencyName));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    @ResponseBody
    public ResultDTO add(ProjectCenterInfo projectCenterInfo) {
        projectCenterService.add(projectCenterInfo);
        return ResultDTO.requstSuccess();
    }

    /**
     * 修改
     */
    @PostMapping("/edit")
    @ResponseBody
    public ResultDTO editSave(ProjectCenterInfo projectCenterInfo) {
        projectCenterService.update(projectCenterInfo);
        return ResultDTO.requstSuccess();
    }
    /**
     * 修改
     */
    @PostMapping("/editStatus")
    @ResponseBody
    public ResultDTO editStatus(@ApiParam(SysconfImageApi.UpdateSystemImageStatus.METHOD_API_STATUS) @RequestParam("status") String status,
                                @ApiParam(SysconfImageApi.UpdateSystemImageStatus.METHOD_API_ID) @RequestParam("id") String id,
                                HttpServletRequest request) {
        projectCenterService.updateStatus(status, id);
        return ResultDTO.requstSuccess();
    }

    /**
     * 删除
     */
    @PostMapping( "/remove")
    @ResponseBody
    public ResultDTO remove(@ApiParam(SysconfImageApi.DeleteImage.METHOD_API_ID) @RequestParam("id") String id)
    {
        projectCenterService.delete(id);
        return ResultDTO.requstSuccess();
    }


}
