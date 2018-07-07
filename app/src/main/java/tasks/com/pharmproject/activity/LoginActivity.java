package tasks.com.pharmproject.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BlurMaskFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.TextInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import tasks.com.pharmproject.R;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    EditText inputDialog;
    String mail,pass;
    Button btnRegester;
    FirebaseAuth auth;
    Button btnLogin;
    TextView textViewResetPassword;
    CheckBox showPasswordCheckBox , rememberPasswordCheckBox;
    private SharedPreferences loginPreferences;
    private Boolean saveLogin;
    private SharedPreferences.Editor loginPrefsEditor;

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent i = new Intent(LoginActivity.this, Main2Activity.class);
            startActivity(i);
            finish();
        }   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();

        initializeView();
        auth = FirebaseAuth.getInstance();


        showPasswordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    //hide password
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        //to save user data
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin) {
            email.setText(loginPreferences.getString("Email", ""));
            password.setText(loginPreferences.getString("password", ""));
            rememberPasswordCheckBox.setChecked(true);
        }

        rememberPasswordCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == rememberPasswordCheckBox) {
                    InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    input.hideSoftInputFromWindow(email.getWindowToken(), 0);

                    mail = email.getText().toString();
                    pass = password.getText().toString();

                    if (rememberPasswordCheckBox.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("Email", mail);
                        loginPrefsEditor.putString("password", pass);
                        loginPrefsEditor.commit();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }
                }
            }
        });

     /*   textViewResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
                             View mView=getLayoutInflater().inflate(R.layout.reset_password_dialog,null);
                final EditText emailAddress=(EditText)mView.findViewById(R.id.email);
                Button resetPass= (Button) mView.findViewById(R.id.btn_reset_password);
                Button backBtn= (Button)mView.findViewById(R.id.btn_back);
                resetPass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mail = emailAddress.getText().toString().trim();
                        if (TextUtils.isEmpty(mail)) {
                            Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        auth.sendPasswordResetEmail(mail)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(LoginActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                });
                    }
                });
                builder.setView(mView);
                final AlertDialog alertDialog=builder.create();
                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                builder.show();
            }
        }); */

        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setTitle("Resset Password");
        alertDialog.setMessage("We just need your registered Email Id to sent you password reset instructions.");
        inputDialog=new EditText(this);
        alertDialog.setView(inputDialog);
        alertDialog.setPositiveButton("Resset Password", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, int i) {
                String mail = inputDialog.getText().toString().trim();
                if (TextUtils.isEmpty(mail)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.sendPasswordResetEmail(mail)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                    dialogInterface.dismiss();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                    dialogInterface.dismiss();
                                }
                            }

                        });
            }

        });


       alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {
               dialogInterface.dismiss();

           }
       });


        textViewResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  alertDialog.show();

            }
        });


    }

    public void initializeView(){
        email= (EditText) findViewById(R.id.edit_email);
        password= (EditText) findViewById(R.id.edit_password);
        showPasswordCheckBox= (CheckBox) findViewById(R.id.check_show_password);
        rememberPasswordCheckBox= (CheckBox) findViewById(R.id.check_remember_me);
        btnLogin= (Button) findViewById(R.id.btn_login);
        btnRegester= (Button) findViewById(R.id.btn_register);
        textViewResetPassword= (TextView) findViewById(R.id.txt_reset_passowrd);
    }

    public void onClickBtnLogin(View v) {

         mail = email.getText().toString();
         pass = password.getText().toString();

        if (TextUtils.isEmpty(mail)) {
            Toast.makeText(getApplicationContext(), " Please enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            if (password.length() < 6) {
                                email.setError("Password too short, enter minimum 6 characters!");
                            } else {
                                Toast.makeText(LoginActivity.this, "Authentication failed, check your email and password or sign up", Toast.LENGTH_LONG).show();
                            }
                        }
                            else
                            {
                              /*  Intent i = new Intent(LoginActivity.this, Main2Activity.class);
                                startActivity(i);*/
                                Toast.makeText(getApplicationContext(),"connect",Toast.LENGTH_LONG).show();

                            }
                        }
                });


    }
    public void onClickSignUpBtn(View v){
        Intent i=new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(i);

    }

    protected void applyBlurMaskFilter(TextView tv, BlurMaskFilter.Blur style) {
        float radius = tv.getTextSize() / 10;
        BlurMaskFilter filter = new BlurMaskFilter(radius, style);
        // Set the TextView layer type
        tv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        // Finally, apply the blur effect on TextView text
        tv.getPaint().setMaskFilter(filter);
    }

}

