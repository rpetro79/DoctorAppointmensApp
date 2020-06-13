package com.example.assignment.firebase;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class FirebaseConnection {
    private FirebaseAuth auth;
    private FirebaseUser user;
    private PropertyChangeSupport pcSupport;
    private static FirebaseConnection instance;

    private FirebaseConnection()
    {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        pcSupport = new PropertyChangeSupport(this);
    }

    public static FirebaseConnection getInstance()
    {
        if(instance == null)
            instance = new FirebaseConnection();
        return instance;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public void createAccount(String email, String password)
    {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user  = auth.getCurrentUser();
                            pcSupport.firePropertyChange("userCreated", null, user);
                        } else {
                            pcSupport.firePropertyChange("authenticationError", "", null);
                        }

                        // ...
                    }
                });

    }

    public String getUserId()
    {
        return user.getUid();
    }

    public void signIn(String email, String password)
    {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user = auth.getCurrentUser();
                            pcSupport.firePropertyChange("userAuthenticated",null, user);
                        } else {
                            pcSupport.firePropertyChange("authenticationError", "", null);
                        }

                        // ...
                    }
                });
    }
    public void addListener(String name, PropertyChangeListener listener) {
        if (name == null)
            pcSupport.addPropertyChangeListener(listener);
        else pcSupport.addPropertyChangeListener(name, listener);
    }

    public void userSignOut()
    {
        auth.signOut();
    }
}
