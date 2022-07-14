package com.aster.webchat.controller;

import com.aster.webchat.api.entity.GroupMsgContent;
import com.aster.webchat.api.entity.RespBean;
import com.aster.webchat.api.entity.RespPageBean;
import com.aster.webchat.service.GroupMsgContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Api(tags = "聊天记录API")
@RestController
@RequestMapping("/groupMsgContent")
public class GroupMsgContentController {
    /**
     * 服务对象
     */
    @Resource
    private GroupMsgContentService groupMsgContentService;

    @ApiOperation("获取所有群聊记录")
    @GetMapping("/")
    private List<GroupMsgContent> getAllGroupMsgContent() {
        return groupMsgContentService.queryAllByLimit(null, null);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("根据id查询单挑记录")
    @GetMapping("selectOne")
    public GroupMsgContent selectOne(Integer id) {
        return this.groupMsgContentService.queryById(id);
    }

    /**
     * 分页返回数据
     *
     * @param page      页数
     * @param size      单页大小
     * @param nickname  发送者昵称
     * @param type      消息类型
     * @param dateScope 发送时间范围
     * @return
     */
    @ApiOperation("分页获取数据")
    @GetMapping("/page")
    public RespPageBean getAllGroupMsgContentByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                    @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                    String nickname, Integer type,
                                                    Date[] dateScope) {
        return groupMsgContentService.getAllGroupMsgContentByPage(page, size, nickname, type, dateScope);
    }

    /**
     * 根据id删除单条记录
     *
     * @param id
     * @return
     */
    @ApiOperation("根据id删除单条记录")
    @DeleteMapping("/{id}")
    public RespBean deleteGroupMsgContentById(@PathVariable Integer id) {
        if (groupMsgContentService.deleteById(id)) {
            return RespBean.ok("删除成功！");
        } else {
            return RespBean.error("删除失败！");
        }
    }

    @ApiOperation("删除多条记录")
    @DeleteMapping("/")
    public RespBean deleteGroupMsgContentByIds(Integer[] ids) {
        if (groupMsgContentService.deleteGroupMsgContentByIds(ids) == ids.length) {
            return RespBean.ok("删除成功！");
        } else {
            return RespBean.error("删除失败！");
        }
    }


    @ApiOperation("下载记录")
    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {

        groupMsgContentService.handleDownload(response);

    }
}
