package com.example.ahmed.ismaliacityapp.auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmed.ismaliacityapp.utils.NetworkUtils;
import com.example.ahmed.ismaliacityapp.utils.Constant;
import com.example.ahmed.ismaliacityapp.R;
import com.example.ahmed.ismaliacityapp.ui.MainActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.aviran.cookiebar2.CookieBar;

import java.util.Arrays;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {
    private EditText etEmail, etPassword;
    LinearLayout layout;
    private Button btnLogin, btnRegister;
    private TextView tvForgetPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TwitterLoginButton twitterLoginButton;
    private DatabaseReference mDatabaseUsers;

    // GOOGLE
    private static final String TAG = "GoogleActivity";
    private static final int RC_GOOGLE_LOGIN = 9001;//Request code used to invoke sign in user.
    private GoogleApiClient mGoogleApiClient;    /* Client used to interact with Google APIs. */

    // FACEBOOK
    private ImageView mFacebookLogin;
    private CallbackManager mFacebookCallbackManager;

    private ImageView googleImg;
    private CookieBar backCookieBar;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSoftKeyboard();
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child(Constant.DatabaseUsers);
        mDatabaseUsers.keepSynced(true);//ensures database offline capabilities
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }
        };
        initViews();
        googleInit();
        fbInit();
        twitter();
    }

    private void initViews() {
        progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        layout = findViewById(R.id.loginLayout);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        tvForgetPassword = findViewById(R.id.tv_forget);

        mFacebookLogin = findViewById(R.id.fbimage);
        twitterLoginButton = findViewById(R.id.twitter);
        googleImg = findViewById(R.id.google_sign_in);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        tvForgetPassword.setOnClickListener(this);
        mFacebookLogin.setOnClickListener(this);
        twitterLoginButton.setOnClickListener(this);
        googleImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                registerIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(registerIntent);
                break;
            case R.id.tv_forget:
                sendForgetPasswordEmail();
                break;
            case R.id.google_sign_in:
                mGoogleApiClient.clearDefaultAccountAndReconnect();
                signUpGoogle();
                break;
            case R.id.fbimage:
                fb();
                break;
        }
    }

    //google sign in
    private void signUpGoogle() {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (!NetworkUtils.checkConnectivity(getApplicationContext())) {
                            Snackbar.make(layout, "No Internet Connection!, Try Again", Snackbar.LENGTH_SHORT).show();

                        } else {
                            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                            startActivityForResult(signInIntent, RC_GOOGLE_LOGIN);
                        }

                    }
                }, 3000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GOOGLE_LOGIN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.d(TAG, "onActivityResult: error " + e.getMessage());
                Snackbar.make(layout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();

            }
        }

        // Pass the activity result back to the Facebook SDK
        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    // If sign in fails, display a message to the user.
                    Snackbar.make(layout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        });

    }

    private void fb() {
        LoginManager.getInstance()
                .logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "user_location", "user_birthday", "public_profile", "user_friends"));

    }

    private void twitter() {
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_KEY),
                        getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.d(TAG, "twitterLogin:success" + result);
                handleTwitterSession(result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.w(TAG, "twitterLogin:failure", exception);

            }
        });
    }

    private void handleTwitterSession(TwitterSession session) {
        progressDialog.setMessage("Checking LOGIN.....");
        progressDialog.show();
        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success");
                    Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_LONG).show();
                    checkUserExist();
                    progressDialog.dismiss();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Failed276" + task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    //**** updateUI(null);
                }
            }
        });
    }

    // [START auth_with_Facebook]
    private void fbInit() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        mFacebookCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("facebook:token", AccessToken.getCurrentAccessToken().getToken());
                AccessToken.getCurrentAccessToken().getToken();
                signInWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });

    }

    private void signInWithFacebook(AccessToken token) {
        Log.d(TAG, "signInWithFacebook:" + token.getToken());
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        final String tokenString = token.getToken();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
//
//                        startActivity(new Intent(SignUp09.this, Dashboard.class));
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Sorry for inconvenience,Please try again..", Toast.LENGTH_SHORT).show();

                        } else {
                            //signInFacebook(tokenString);
                            Toast.makeText(LoginActivity.this, "Welcome..!", Toast.LENGTH_SHORT).show();
                            checkUserExist();
                        }
                    }


                });
    }

    private void hideSoftKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void googleInit() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Snackbar.make(layout, connectionResult.getErrorMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        //  setButtonText("Sign In with Google");

    }

    private void sendForgetPasswordEmail() {
        String email = etEmail.getText().toString();
        if (email.isEmpty()) {
            etEmail.setError(getString(R.string.enter_email));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.email_not_formatted));
        } else {
            progressDialog.setMessage("Check loading...");
            progressDialog.show();
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, R.string.check_your_mail, Toast.LENGTH_SHORT).show();
                    } else if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                        Toast.makeText(LoginActivity.this, R.string.user_not_found, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, R.string.error_in_connection, Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
            });
        }
    }

    private void login() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        if (email.isEmpty()) {
            etEmail.setError(getString(R.string.enter_email));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.email_not_formatted));
        } else if (password.isEmpty()) {
            etPassword.setError(getString(R.string.enter_password));
        } else if (etPassword.length() < 6) {
            etPassword.setError(getString(R.string.password_length_error));
        } else {
            new SpotsDialog(this, R.style.Custom_login).show();

        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    if (task.getException() instanceof FirebaseAuthInvalidUserException
                            || task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(LoginActivity.this, R.string.wrong_email_or_password, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, R.string.error_in_connection, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    checkUserExist();
                }
            }
        });
    }

    private void checkUserExist() {
//        progressDialog.setMessage("loading...");
//        progressDialog.show();
        if (mAuth.getCurrentUser() != null) {


            final String user_id = mAuth.getCurrentUser().getUid();
            //check if the user is already in the database
            //use the "addValueEventListener" to retrieve a single data from the database,
            // use onChildEventListener to retrieve multiple data
            mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(user_id)) {
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        //clear flags so the user won't be able to go back to the previous page
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(mainIntent);

                    } else {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }


    @Override
    public void onBackPressed() {
        if (backCookieBar != null) {
            finish();
        } else {
            backCookieBar = CookieBar.build(LoginActivity.this)
                    .setLayoutGravity(Gravity.BOTTOM)
                    .setTitle("Hit the back button again to quit.")
                    .setDuration(7000)
                    .setMessageColor(R.color.purple)
                    .show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        Log.i(TAG, "onStart: Connecting to Google Play Services");
        // Connect to Play Services
        GoogleApiAvailability gAPI = GoogleApiAvailability.getInstance();
        int resultCode = gAPI.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            gAPI.getErrorDialog(this, resultCode, 1).show();
        } else {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        if (mGoogleApiClient.isConnected()) {
            Log.i(TAG, "onStop: Disconnecting from Google Play Services");
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnection is connected:");

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Connection was suspended for some reason");
        mGoogleApiClient.connect(); //reconnected
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult.getErrorCode());
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
}
