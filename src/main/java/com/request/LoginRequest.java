package com.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Value
public class LoginRequest {

    @NonNull private String id;
    @NonNull private String phoneNo;
    @NonNull private String customerNo;

    public LoginRequest(@JsonProperty(value= "id", required = true) String id,
                        @JsonProperty(value= "phoneNo", required = true) String phoneNumber,
                        @JsonProperty(value= "customerNo", required = true) String customerNo) {
        this.id = id;
        this.phoneNo = phoneNumber;
        this.customerNo = customerNo;
    }
}
