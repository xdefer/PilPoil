package com.project.app.pilpoil.Metier;

import java.io.Serializable;
import java.math.BigInteger;

public class AuthTokenPojo implements Serializable {

    private BigInteger expires;
    private String token;

    /**
     *
     * @return
     * The expires
     */
    public BigInteger getExpires() {
        return expires;
    }

    /**
     *
     * @param expires
     * The expires
     */
    public void setExpires(BigInteger expires) {
        this.expires = expires;
    }

    /**
     *
     * @return
     * The token
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     * The token
     */
    public void setToken(String token) {
        this.token = token;
    }
}