package com.request;

import lombok.*;

@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Data
public class LoginRequest {
    @NonNull private String id;
    @NonNull private String phoneNumber;
    @NonNull private String customerNo;
}
