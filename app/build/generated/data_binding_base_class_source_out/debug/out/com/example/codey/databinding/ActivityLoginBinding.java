// Generated by view binder compiler. Do not edit!
package com.example.codey.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.codey.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityLoginBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button buttonLogin;

  @NonNull
  public final EditText loginPassword;

  @NonNull
  public final EditText loginUsername;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final TextView signupRedirect;

  private ActivityLoginBinding(@NonNull ConstraintLayout rootView, @NonNull Button buttonLogin,
      @NonNull EditText loginPassword, @NonNull EditText loginUsername,
      @NonNull ConstraintLayout main, @NonNull TextView signupRedirect) {
    this.rootView = rootView;
    this.buttonLogin = buttonLogin;
    this.loginPassword = loginPassword;
    this.loginUsername = loginUsername;
    this.main = main;
    this.signupRedirect = signupRedirect;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_login, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLoginBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.button_login;
      Button buttonLogin = ViewBindings.findChildViewById(rootView, id);
      if (buttonLogin == null) {
        break missingId;
      }

      id = R.id.login_password;
      EditText loginPassword = ViewBindings.findChildViewById(rootView, id);
      if (loginPassword == null) {
        break missingId;
      }

      id = R.id.login_username;
      EditText loginUsername = ViewBindings.findChildViewById(rootView, id);
      if (loginUsername == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.signup_redirect;
      TextView signupRedirect = ViewBindings.findChildViewById(rootView, id);
      if (signupRedirect == null) {
        break missingId;
      }

      return new ActivityLoginBinding((ConstraintLayout) rootView, buttonLogin, loginPassword,
          loginUsername, main, signupRedirect);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
