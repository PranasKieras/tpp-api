package com.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Value
public class LoginRequest {

    @NonNull String personalId;
    @NonNull String bankLoginId;
    @NonNull String phoneNumber;

    public LoginRequest(@JsonProperty(value= "personalId", required = true) String personalId,
                        @JsonProperty(value= "bankLoginId", required = true) String bankLoginId,
                        @JsonProperty(value= "phoneNumber", required = true) String phoneNumber) {
        this.personalId = personalId;
        this.bankLoginId = bankLoginId;
        this.phoneNumber = phoneNumber;
    }
}
