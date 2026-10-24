package com.apichat.apichat.config;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;

import java.util.List;

public class CustomChatMemory implements ChatMemory {

    private final MessageWindowChatMemory messageWindowChatMemory;
    public CustomChatMemory(MessageWindowChatMemory build) {
        messageWindowChatMemory=build;
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        messages.forEach(message -> {
            if (message.getMessageType()== MessageType.ASSISTANT){
                message.getMetadata().remove("reasoningContent");
            }
        });
        messageWindowChatMemory.add(conversationId,messages);
    }

    @Override
    public List<Message> get(String conversationId) {
        return messageWindowChatMemory.get(conversationId);
    }

    @Override
    public void clear(String conversationId) {
        messageWindowChatMemory.clear(conversationId);

    }
}
