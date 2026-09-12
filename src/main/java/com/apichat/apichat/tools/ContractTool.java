package com.apichat.apichat.tools;

import com.apichat.apichat.model.Contract;
import com.apichat.apichat.model.ContractApiResponse;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ContractTool {

    @Tool(description = "returns contract information of a given msisdn. If msisdn is not provided dont hallucinate")
    public Contract getContract(@ToolParam(required = true,description = "The msisdn is madatory and should be in 2135xxxxxxxx format.") String msisdn){
        if (msisdn == null || msisdn.isBlank()) {
            throw new IllegalArgumentException("No msisdn was provided. You must ask the user for their msisdn before calling this tool again. Do not invent or guess a value.");
        }
        if (!msisdn.matches("2135\\d{8}")) {
            throw new IllegalArgumentException("Invalid msisdn format. Expected format: 2135xxxxxxxx (12 digits starting with 2135).");
        }
        // simulate api call
        ContractApiResponse contractApiResponse= ContractApiResponse.builder().creationDate(new Date()).fullName("LASNAMI fella").status(2).build();
        return Contract.builder().creationDate(contractApiResponse.getCreationDate()).isActive(contractApiResponse.isActive()).fullName(contractApiResponse.getFullName()).build();
    }
}
