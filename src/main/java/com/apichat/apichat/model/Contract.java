package com.apichat.apichat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class Contract {
    private Date creationDate;
    private Boolean isActive;
    private String fullName;
}
