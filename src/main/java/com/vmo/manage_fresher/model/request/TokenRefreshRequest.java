package com.vmo.manage_fresher.model.request;

import javax.validation.constraints.NotBlank;

public class TokenRefreshRequest {
    @NotBlank(message = "refreshToken must not be blank")
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
