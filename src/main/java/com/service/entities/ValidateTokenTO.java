package com.service.entities;

import lombok.NonNull;
import lombok.Value;

@Value
public class ValidateTokenTO {
    @NonNull String personalId;
    @NonNull String bankLoginId;
    @NonNull String phoneNumber;
    @NonNull String loginToken;
}
