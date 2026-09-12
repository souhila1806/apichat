package com.apichat.apichat.tools;

import com.apichat.apichat.model.Contract;
import com.apichat.apichat.model.Invoice;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class InvoiceTool {

    @Tool(description = "returns unpaid invoices of a given msisdn. If msisdn is not provided dont hallucinate")
    public Invoice getInvoice(@ToolParam(required = true,description = "The msisdn is madatory and should be in 2135xxxxxxxx format.") String msisdn){
        if (msisdn == null || msisdn.isBlank()) {
            throw new IllegalArgumentException("No msisdn was provided. You must ask the user for their msisdn before calling this tool again. Do not invent or guess a value.");
        }
        if (!msisdn.matches("2135\\d{8}")) {
            throw new IllegalArgumentException("Invalid msisdn format. Expected format: 2135xxxxxxxx (12 digits starting with 2135).");
        }
        return Invoice.builder().dueOnDate(new Date()).amount(2000.0).build();
    }
}
