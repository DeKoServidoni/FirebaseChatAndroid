package com.dekoservidoni.firebasechat.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dekoservidoni.firebasechat.ActivityCallback;
import com.dekoservidoni.firebasechat.R;
import com.dekoservidoni.firebasechat.utils.Constants;
import com.dekoservidoni.firebasechat.utils.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Class responsible to be the login screen of the application
 */
public class LoginFragment extends Fragment {

    /** UI Components **/
    private AutoCompleteTextView mEmail;
    private EditText mPassword;
    private View mProgressView;
    private View mLoginFormView;

    /** Activity callback **/
    private ActivityCallback mCallback;

    /** Firebase objects **/
    private FirebaseAuth mAuth;

    /**
     * Create a instance of this fragment
     *
     * @return fragment instance
     */
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    /// Lifecycle methods

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        mEmail = (AutoCompleteTextView) root.findViewById(R.id.username);
        mPassword = (EditText) root.findViewById(R.id.password);

        final Button signInButton = (Button) root.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.closeKeyboard(getContext(), signInButton);
                attemptLogin();
            }
        });

        final Button createAccount = (Button) root.findViewById(R.id.create_account_button);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.closeKeyboard(getContext(), createAccount);
                mCallback.openCreateAccount();
            }
        });

        mLoginFormView = root.findViewById(R.id.login_form);
        mProgressView = root.findViewById(R.id.login_progress);

        mAuth = FirebaseAuth.getInstance();
        Utils.closeKeyboard(getContext(), mEmail);

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (ActivityCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    /// Private methods

    private void attemptLogin() {

        // Reset errors.
        mEmail.setError(null);
        mPassword.setError(null);

        // Store values at the time of the login attempt.
        String username = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mEmail.setError(getString(R.string.error_empty));
            mEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            mPassword.setError(getString(R.string.error_password));
            mPassword.requestFocus();
            return;
        }

        login();
    }

    private void login() {
        showProgress(true);

        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                if (mCallback != null) {
                    Utils.saveLocalUser(getContext(), Constants.DEFAULT_USER,
                            mEmail.getText().toString(),
                            authResult.getUser().getUid());

                    mCallback.openChat();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showProgress(false);
                Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showProgress(boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
    }
}
