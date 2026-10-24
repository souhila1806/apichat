package com.apichat.apichat.tools;

import com.apichat.apichat.model.Contract;
import com.apichat.apichat.model.Invoice;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class InvoiceTool {

    @Tool(description = "returns unpaid invoices of a given clientId. If clientId is not provided dont hallucinate")
    public Invoice getInvoice(@ToolParam(required = true,description = "The clientId is madatory and should be in IDX_xxxxxxxx format.") String clientId){
        if (clientId == null || clientId.isBlank()) {
            throw new IllegalArgumentException("No clientId was provided. You must ask the user for their clientId before calling this tool again. Do not invent or guess a value.");
        }
        if (!clientId.matches("IDX_\\d{8}")) {
            throw new IllegalArgumentException("Invalid clientId format. Expected format: IDX_xxxxxxxx");
        }
        return Invoice.builder().dueOnDate(new Date()).amount(2000.0).build();
    }
}
