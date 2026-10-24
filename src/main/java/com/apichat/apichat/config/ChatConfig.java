package com.apichat.apichat.config;

import com.apichat.apichat.tools.ContractTool;
import com.apichat.apichat.tools.InvoiceTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    @Bean
    public ChatMemory chatMemory() {
        return new CustomChatMemory(
                MessageWindowChatMemory.builder().maxMessages(20).build());
    }

    @Bean
    public ChatClient chatClient(
            ChatModel chatModel,
            ChatMemory chatMemory,
            ContractTool contractTool,
            InvoiceTool invoiceTool) {

        System.out.println("Configured ChatMemory: "
                + System.identityHashCode(chatMemory));

        return ChatClient.builder(chatModel)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory)
                                .build()
                )
                .defaultTools(contractTool, invoiceTool)
                .build();
    }
}