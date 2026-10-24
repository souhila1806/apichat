package com.apichat.apichat.controller;

import com.apichat.apichat.tools.ContractTool;
import com.apichat.apichat.tools.InvoiceTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatClient chatClient;
    private final ChatMemory chatMemory;

    public ChatController(ChatClient chatClient, ContractTool contractTool, InvoiceTool invoiceTool, ChatMemory chatMemory) {
        this.chatMemory = chatMemory;
        this.chatClient = chatClient.mutate()
                .defaultSystem("""
                You are an assistant working for an entreprise that has contracts with clients. You can use the available tools to answer questions if necessary.
                Before calling any tool, check if you have every required parameter clearly stated by the user.
                If any required parameter is missing, do NOT call the tool. Instead, ask the user directly for that parameter.
                Never invent, guess, or fabricate a parameter value under any circumstance, even a plausible-looking one.
                Only call a tool once the user has explicitly provided all required values in their own message.
                """)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @GetMapping("/test")
    public String testChat(
            @RequestParam("input") String input,
            @RequestHeader String currentUserId,
            @RequestHeader String httpSessionId) {

        String conversationId = currentUserId + ":" + httpSessionId;
        System.out.println("Controller ChatMemory: "
                + System.identityHashCode(chatMemory));
        System.out.println("Conversation ID: " + conversationId);

        List<Message> messages = chatMemory.get(conversationId);

        System.out.println("Messages before: " + messages.size());
        messages.forEach(message ->
                System.out.println(message.getText()));

        String response = chatClient.prompt()
                .user(input)
                .advisors(a -> a.param(
                        ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();

        List<Message> after = chatMemory.get(conversationId);

        after.forEach(m -> System.out.println(m.getMessageType() + " -> " + m.getMetadata()));

        System.out.println("Messages after: " + after.size());
        after.forEach(message ->
                System.out.println(message.getText()));

        return response;
    }
}
