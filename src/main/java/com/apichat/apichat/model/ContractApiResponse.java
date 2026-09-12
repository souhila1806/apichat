package com.apichat.apichat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ContractApiResponse {

    private Integer status;
    private Date creationDate;
    private String fullName;

    public boolean isActive(){return this.status.equals(2);}
}
