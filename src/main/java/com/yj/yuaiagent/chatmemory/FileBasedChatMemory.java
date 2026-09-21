package com.yj.yuaiagent.chatmemory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.objenesis.strategy.StdInstantiatorStrategy;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 基于文件持久化记忆的Chat memory
 */
public class FileBasedChatMemory implements ChatMemory {

    private final String Base_DIR;

    //窗口大小,构造时传入
    private final int maxMessages;

    private static final ThreadLocal<Kryo> KRYO_HOLDER = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false);

        //设置实例化策略
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
        return kryo;
    });



    public FileBasedChatMemory(String dir,int maxMessages){
        this.Base_DIR = dir;
        this.maxMessages  = maxMessages;
        File baseDir = new File(dir);
        if (!baseDir.exists()){
            baseDir.mkdirs();
        }
    }


    @Override
    public void add(String conversationId, List<Message> messages) {
            List<Message> conversationMessage = getOrCreateConversation(conversationId);
            conversationMessage.addAll(messages);
            saveConversation(conversationId, conversationMessage);
    }

    @Override
    public List<Message> get(String conversationId) {
        List<Message> allMessages = getOrCreateConversation(conversationId);
        return allMessages.stream()
                .skip(Math.max(0,allMessages.size() - maxMessages))
                .toList();
    }


    @Override
    public void clear(String conversationId) {
        File file = getConversationFile(conversationId);
        if (file.exists()){
            file.delete();
        }
    }


    private List<Message> getOrCreateConversation(String conversationId){
        File file = getConversationFile(conversationId);
        List<Message> messages = new ArrayList<>();
        if (file.exists()){
            try(Input input = new Input(new FileInputStream(file))){
                messages = KRYO_HOLDER.get().readObject(input, ArrayList.class);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return messages;
    }

    private void saveConversation(String conversationId,List<Message> messages){
        File file = getConversationFile(conversationId);
        try(Output output = new Output(new FileOutputStream(file))){
            KRYO_HOLDER.get().writeObject(output,messages);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getConversationFile(String conversationId) {
        return new File(Base_DIR,conversationId+".kryo");
    }


}
