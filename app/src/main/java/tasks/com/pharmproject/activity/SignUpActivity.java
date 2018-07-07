package tasks.com.pharmproject.activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import tasks.com.pharmproject.R;
import tasks.com.pharmproject.model.UserInformation;
/*
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;*/

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthCredential;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import tasks.com.pharmproject.activity.Main2Activity;

public class SignUpActivity extends AppCompatActivity {

    Button btnSignUp, btnGmailLogin, btnFacebookLogin;
    TwitterLoginButton btnTwitterLogin;
    EditText editTextEmail, editTextPassword, editTextConfirmPassword;
    MaterialBetterSpinner citySpinner;
    ArrayAdapter<String> adapterSpinner;
    ArrayList englishGov;
    ArrayList arabicGov;
    UserInformation user;
    private ProgressBar progressBar;
    //  com.facebook.login.LoginManager fbLoginManager;
//    CallbackManager callbackManager;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
   FirebaseAuth.AuthStateListener firebaseAuthListner;
    private final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    String email;
    String password;
    String confirmPassword;
    String name = "";
    String avatar = "";
    String phone = "";
    static String city = "";
    public static String uId = "";
    boolean b = false;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the Twitter login button.
        btnTwitterLogin.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET));
        TwitterConfig twitterConfig = new TwitterConfig.Builder(this).twitterAuthConfig(authConfig).build();
        Twitter.initialize(twitterConfig);
        setContentView(R.layout.activity_sign_up);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(SignUpActivity.this, Main2Activity.class));
            finish();
        }
        firebaseAuth = FirebaseAuth.getInstance();
        initView();
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        englishGov = new ArrayList();
        arabicGov = new ArrayList();
        handleSpinner();


    }

    public void onClockBtnSignUp(View v) {
        progressBar.setVisibility(View.VISIBLE);
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();
        confirmPassword = editTextConfirmPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            progressBar.setVisibility(View.INVISIBLE); //To Hide ProgressBar
            editTextEmail.setError(" enter your Email");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            progressBar.setVisibility(View.INVISIBLE); //To Hide ProgressBar
            editTextPassword.setError("enter your password");
            return;
        }
        if (password.length() < 6) {
            progressBar.setVisibility(View.INVISIBLE); //To Hide ProgressBar
            editTextPassword.setError("password should be 6 chars or more");
            return;
        }
        if (!b) {
            progressBar.setVisibility(View.INVISIBLE); //To Hide ProgressBar
            Snackbar.make(v, "select your  country ", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return;
        }


        if (validate(email, password, confirmPassword)) {

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
//                                        name = firebaseAuth.getCurrentUser().getDisplayName().toString();
                                uId = firebaseAuth.getCurrentUser().getUid();
                                final UserInformation userInformation = new UserInformation(name, email, avatar, city, phone, uId);
                                firebaseAuth.getCurrentUser().sendEmailVerification()
                                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    progressBar.setVisibility(View.INVISIBLE); //To Hide ProgressBar
                                                    databaseReference = FirebaseDatabase.getInstance().getReference("UsersInformation");
                                                    databaseReference.child("Users").child(uId).setValue(userInformation);
                                                    Toast.makeText(SignUpActivity.this, "Verification sent to :" + firebaseAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(SignUpActivity.this, Main2Activity.class));
                                                    finish();
                                                } else {
                                                    Toast.makeText(SignUpActivity.this, "false to sent Verification  ", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                                    progressBar.setVisibility(View.INVISIBLE); //To Hide ProgressBar
                                    editTextEmail.setError("malformed-wrong email");
                                } catch (FirebaseAuthUserCollisionException existEmail) {
                                    progressBar.setVisibility(View.INVISIBLE); //To Hide ProgressBar
                                    editTextEmail.setError("really exist email");
                                } catch (Exception e) {
                                }
                            }
                        }
                    });


        } else {
            progressBar.setVisibility(View.INVISIBLE); //To Hide ProgressBar
            Toast.makeText(SignUpActivity.this, "Invalid email or not match password", Toast.LENGTH_SHORT).show();
        }
    }


    public void onClickTwitterBtn(View v) {
        btnTwitterLogin.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;
                Toast.makeText(SignUpActivity.this, "Signed in twitter succesfully" + result.data, Toast.LENGTH_SHORT).show();


                login(session);

            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(SignUpActivity.this, "Faiure to login to twitter", Toast.LENGTH_SHORT).show();
            }

        });

    }
    public void login(TwitterSession session){
        String username=session.getUserName ();
        Intent intent=new Intent ( SignUpActivity.this,Main2Activity.class );
        intent.putExtra ( "username",username );
        startActivity ( intent );

    }

    private void signInFirebaseWithTwitterSession(TwitterSession session){
        AuthCredential credential=TwitterAuthProvider.getCredential(session.getAuthToken().token,session.getAuthToken().secret);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(SignUpActivity.this,"Signed in successfully to firesbase with twitter session",Toast.LENGTH_SHORT).show();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(!task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this,"Authentication failed",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//    }

    private void initView() {
        editTextEmail = (EditText) findViewById(R.id.edit_email);
        editTextPassword = (EditText) findViewById(R.id.edit_password);
        editTextConfirmPassword = (EditText) findViewById(R.id.edit_repeat_password);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        btnGmailLogin = (Button) findViewById(R.id.gmail_login);
        btnTwitterLogin = (TwitterLoginButton) findViewById(R.id.twitter_login);
        citySpinner = (MaterialBetterSpinner) findViewById(R.id.city_spinner);

    }

    private boolean validate(String emailStr, String password, String repeatPassword) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return password.length() > 0 && repeatPassword.equals(password) && matcher.find();
    }



    public void handleSpinner() {
        if (Locale.getDefault().getLanguage().equals("en")) {
            String[] arr1 = getResources().getStringArray(R.array.EnglishGov);
            for (int x = 0; x < arr1.length; x++) {
                city = arr1[x];
                if (city.length() > 0 && !englishGov.contains(city)) {
                    englishGov.add(city);
                }
            }
            Collections.sort(englishGov, String.CASE_INSENSITIVE_ORDER);
            adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, englishGov);
            citySpinner.setAdapter(adapterSpinner);
            citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    city = englishGov.get(i).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });


        } else {
            String[] arr2 = getResources().getStringArray(R.array.arabicGov);
            for (int x = 0; x < arr2.length; x++) {
                city = arr2[x];
                if (city.length() > 0 && !arabicGov.contains(city)) {
                    arabicGov.add(city);
                }
            }
            Collections.sort(arabicGov, String.CASE_INSENSITIVE_ORDER);
            adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arabicGov);
            citySpinner.setAdapter(adapterSpinner);
            citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    city = arabicGov.get(i).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }

            });

        }
        citySpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                b = true;
            }
        });
    }

/*      handleFacebookLogin();
        btnFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbLoginManager.logInWithReadPermissions(SignUpActivity.this, Arrays.asList("email", "public_profile", "user_birthday"));
            }

        });*/

  /*private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), R.string.firebase_error_login, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }*/

  /*  public void handleFacebookLogin() {
        fbLoginManager = com.facebook.login.LoginManager.getInstance();
        callbackManager = CallbackManager.Factory.create();
        fbLoginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }*/


}



