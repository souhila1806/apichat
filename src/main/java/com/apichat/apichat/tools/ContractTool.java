package com.apichat.apichat.tools;

import com.apichat.apichat.model.Contract;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ContractTool {

    @Tool(description = "returns contract information of a given client id. If client id is not provided dont hallucinate")
    public Contract getContract(@ToolParam(description = "id of the client in the contract.") String clientId){
        if (clientId == null || clientId.isBlank()) {
            throw new IllegalArgumentException("No clientId was provided. You must ask the user for their clientId before calling this tool again. Do not invent or guess a value.");
        }
        if (!clientId.matches("IDX_\\d{8}")) {
            throw new IllegalArgumentException("Invalid clientId format. Expected format: IDX_xxxxxxxx.");
        }
        // simulate api response
        return Contract.builder().creationDate(new Date()).fullName("LASNAMI fella").build();
    }
}
