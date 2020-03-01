package com.dao.entity;

import lombok.NonNull;
import lombok.Value;

@Value
public class PSUser {
    @NonNull String personalId;
    @NonNull String bankLoginId;
    @NonNull String phoneNumber;
    @NonNull String loginToken;
}
