package com.aster.webchat.controller;

import com.github.binarywang.java.emoji.EmojiConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import com.aster.webchat.api.entity.GroupMsgContent;
import com.aster.webchat.api.entity.Message;
import com.aster.webchat.api.entity.User;
import com.aster.webchat.service.GroupMsgContentService;
// import com.aster.webchat.api.utils.TuLingUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

@Api(tags = "WebSocket API")
@Controller
public class WsController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 单聊的消息的接受与转发
     *
     * @param authentication
     * @param message
     */
    @ApiOperation("单聊的消息的接受与转发")
    @MessageMapping("/ws/chat")
    public void handleMessage(Authentication authentication, Message message) {
        User user = ((User) authentication.getPrincipal());
        message.setFromNickname(user.getNickname());
        message.setFrom(user.getUsername());
        message.setCreateTime(new Date());
        simpMessagingTemplate.convertAndSendToUser(message.getTo(), "/queue/chat", message);
    }

    @Autowired
    GroupMsgContentService groupMsgContentService;
    EmojiConverter emojiConverter = EmojiConverter.getInstance();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 群聊的消息接受与转发
     *
     * @param authentication
     * @param groupMsgContent
     */
    @ApiOperation("群聊的消息接受与转发")
    @MessageMapping("/ws/groupChat")
    public void handleGroupMessage(Authentication authentication, GroupMsgContent groupMsgContent) {
        User currentUser = (User) authentication.getPrincipal();
        // 处理emoji内容,转换成unicode编码
        groupMsgContent.setContent(emojiConverter.toHtml(groupMsgContent.getContent()));
        // 保证来源正确性，从Security中获取用户信息
        groupMsgContent.setFromId(currentUser.getId());
        groupMsgContent.setFromName(currentUser.getNickname());
        groupMsgContent.setFromProfile(currentUser.getUserProfile());
        groupMsgContent.setCreateTime(new Date());
        // 保存该条群聊消息记录到数据库中
        groupMsgContentService.insert(groupMsgContent);
        // 转发该条数据
        simpMessagingTemplate.convertAndSend("/topic/greetings", groupMsgContent);
    }

}
