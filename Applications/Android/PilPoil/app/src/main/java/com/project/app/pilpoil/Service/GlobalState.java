package com.project.app.pilpoil.Service;

import android.app.Application;
import com.project.app.pilpoil.Metier.AuthTokenPojo;
import com.project.app.pilpoil.Metier.UserPojo;

public class GlobalState extends Application {
    public UserPojo currentUser = null;

    public AuthTokenPojo token;

    public UserPojo getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserPojo currentUser) {
        this.currentUser = currentUser;
    }

    public AuthTokenPojo getAuthTokenPojo() {
        return token;
    }

    public void setCurrentAuthTokenPojo(AuthTokenPojo token) { this.token = token; }
}
