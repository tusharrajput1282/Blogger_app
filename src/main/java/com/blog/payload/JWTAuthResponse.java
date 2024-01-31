package com.blog.payload;

public class JWTAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";                         //This is the Token type
    public JWTAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public String getTokenType() {
        return tokenType;
    }

}
