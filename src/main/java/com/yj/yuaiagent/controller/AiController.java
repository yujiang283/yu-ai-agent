package com.yj.yuaiagent.controller;

import com.yj.yuaiagent.agent.YuManus;
import com.yj.yuaiagent.app.LoveApp;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class AiController {

    @Resource
    private LoveApp loveApp;

    @Resource
    private ToolCallback[] allTools;

    @Resource
    private ChatModel dashscopeChatModel;

    /**
     * 恋爱大师应用同步调用
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping("/love_app/chat/sync")
    public String doChatWithLoveApp(String message,String chatId) {
        return loveApp.doChat(message,chatId);
    }

    /**
     * 恋爱大师应用异步调用(实现1)
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping(value = "/love_app/chat/sse" ,produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> doChatWithLoveAppSSE(String message, String chatId) {
        return loveApp.doChatByStream(message, chatId);
    }

    /**
     * 恋爱大师应用异步调用（实现2）
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping(value = "/love_app/chat/server_sent_event")
    public Flux<ServerSentEvent<String>> doChatWithLoveAppServerEvent(String message, String chatId) {
        return loveApp.doChatByStream(message, chatId)
                .map(chunk ->ServerSentEvent.<String>builder()
                        .data(chunk)
                        .build());
    }

    /**
     * 恋爱大师应用异步调用（实现3）
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping(value = "/love_app/chat/sse_emitter")
    public SseEmitter doChatWithLoveAppEmitter(String message, String chatId) {
        SseEmitter emitter = new SseEmitter(180000L);
        loveApp.doChatByStream(message, chatId)
                .subscribe(chunk ->{
                    try{
                        emitter.send(chunk);
                    } catch (Exception e) {
                        emitter.completeWithError(e);
                    }
                },emitter::completeWithError,emitter::complete);
        return emitter;
    }

    /**
     * 跟超级智能体对话
     * @param message
     * @return
     */
    @GetMapping(value = "/manus/chat")
    public SseEmitter doChatWithManus(String message) {
        YuManus manus = new YuManus(allTools,dashscopeChatModel);
        return manus.runStream(message);
    }
}
