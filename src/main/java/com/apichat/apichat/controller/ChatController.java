package com.apichat.apichat.controller;

import com.apichat.apichat.tools.ContractTool;
import com.apichat.apichat.tools.InvoiceTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder, ContractTool contractTool, InvoiceTool invoiceTool) {
        this.chatClient = chatClientBuilder
                .defaultSystem("""
                You are a telecom assistant. You have tools available to answer questions.
                Before calling any tool, check if you have every required parameter clearly stated by the user.
                If any required parameter is missing, do NOT call the tool. Instead, ask the user directly for that parameter.
                Never invent, guess, or fabricate a parameter value under any circumstance, even a plausible-looking one.
                Only call a tool once the user has explicitly provided all required values in their own message.
                """)
                .defaultTools(contractTool,invoiceTool)
                .build();
    }

    @GetMapping("/test")
    public String testChat(@RequestParam(name = "input") String input) {
        return chatClient.prompt()
                .system("You are a telecom assistant...")
                .user("Question: " + input)
                .call()
                .content();
    }
}
