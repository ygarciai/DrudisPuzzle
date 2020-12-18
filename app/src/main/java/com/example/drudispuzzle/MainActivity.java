package com.example.drudispuzzle;

import android.content.Context;
import android.content.Intent;
import android.content.res.loader.AssetsProvider;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.security.cert.CertificateException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.jakewharton.rxbinding3.widget.RxTextView;

import org.reactivestreams.Subscription;

import java.io.IOException;
import java.net.URL;
import java.security.PublicKey;
import java.util.concurrent.TimeUnit;



import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 0;
    private FirebaseAuth mAuth;



    EditText nombre;
    WebView webView;
    ApplicationLifecycleHandler controleventos;

    AudioManager audioManager,mAudioManager;
    AudioManager.OnAudioFocusChangeListener audioListener;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener;
    private AudioFocusChangeListenerImpl mAudioFocusChangeListener;
    private boolean mFocusGranted, mFocusChanged;
    int result=0;

    public String email,password;

    public static final String TAG = "MyActivity";
    GoogleSignInClient mGoogleSignInClient;
    View vista;
    @BindView(R.id.salir) Button btn2;
    @BindView(R.id.botoninicio) Button btn;
    @BindView(R.id.btn_Logueo) Button btnLoguear;
    @BindView(R.id.btn_Crear) Button btnCrear;
    public @BindView(R.id.editTextUsuario) EditText editUser;
    public @BindView(R.id.editTextPassword) EditText editPassword;
    @BindView(R.id.toolbar) Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        controleventos=new ApplicationLifecycleHandler();
        controleventos.onActivityStarted(this);
        mAuth = FirebaseAuth.getInstance();


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btnLoguear.setOnClickListener(this);
        btnCrear.setOnClickListener(this);
        btnLoguear.setEnabled(false);
        btnCrear.setEnabled(false);


        setSupportActionBar(toolbar);

        if (getIntent().getBooleanExtra("EXIT", false))
        {
            finish();
        }

        // añadimos la base de datos SQLite
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this, "bd player", null, 1);

        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyManager.listen(mPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mAudioFocusChangeListener = new AudioFocusChangeListenerImpl();

        result = mAudioManager.requestAudioFocus(mAudioFocusChangeListener,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        switch (result) {
            case AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
                mFocusGranted = true;
                break;
            case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
                mFocusGranted = false;
                break;
        }

        ApplicationLifecycleHandler handler = new ApplicationLifecycleHandler();
        registerActivityLifecycleCallbacks(handler);
        registerComponentCallbacks(handler);
        editPassword.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                controlBoton();
            }
        });
        editUser.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                controlBoton();
            }
        });

    }

    public void controlBoton(){
        Disposable ComprobacionLogueo = RxTextView.textChanges(editUser)
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws  Exception{
                        //Add your logic to work on the Charsequence
                        email = editUser.getText().toString();
                        password=editPassword.getText().toString();
                        if ((email.length() > 6) && (password.length()>6)) {
                            btnLoguear.setEnabled(true);
                            btnCrear.setEnabled(true);
                        }else {
                            btnLoguear.setEnabled(false);
                            btnCrear.setEnabled(false);
                        }
                    }
                });
    }

    private class AudioFocusChangeListenerImpl implements AudioManager.OnAudioFocusChangeListener {
        @Override
        public void onAudioFocusChange(int focusChange) {
            mFocusChanged = true;
            //Log.i(TAG, "Focus changed");

            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    //mp.start();
                    ApplicationLifecycleHandler.mp1.start();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    //mp.pause();
                    ApplicationLifecycleHandler.mp1.pause();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    //mp.pause();
                    ApplicationLifecycleHandler.mp1.pause();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    //mp.pause();
                    ApplicationLifecycleHandler.mp1.pause();
                    break;
            }
        }
    }

    private final PhoneStateListener mPhoneListener=new PhoneStateListener(){
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            // Call receive state
            try {
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        //mp.pause();
                        ApplicationLifecycleHandler.mp1.pause();
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        //mp.start();
                        ApplicationLifecycleHandler.mp1.start();
                        break;
                    default:
                }
            } catch (Exception e) {
            }
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu_wv, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "Opening Help", Toast.LENGTH_SHORT).show();
                setContentView(R.layout.webview);

                webView = (WebView) this.findViewById(R.id.webviewhtml);
                webView.setInitialScale(1);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.getSettings().setUseWideViewPort(true);
                webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
                webView.setScrollbarFadingEnabled(false);
                webView.loadUrl("file:///android_asset/webview/index.html");

                return true;
            case R.id.Item2:
                Toast.makeText(this, "Goodbye", Toast.LENGTH_SHORT).show();
                finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(final View view) {
        vista=view;
        switch (view.getId()) {
            case R.id.salir:
                finish();
                break;
            case R.id.botoninicio:
                Intent intent = new Intent(view.getContext(), SelectionActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.btn_Logueo:
                email = (String) editUser.getText().toString();
                password = (String) editPassword.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                           @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                    Toast.makeText(MainActivity.this, "Authentication Success.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(view.getContext(), SelectionActivity.class);
                                    startActivityForResult(intent, 0);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });
                break;
            case R.id.btn_Crear:

                email = (String) editUser.getText().toString();
                password = (String) editPassword.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    Toast.makeText(MainActivity.this, "Creation Succes.",
                                            Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Creation  failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }

                                // ...
                            }
                        });
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            Log.w(TAG, "signInResult:Ok");
            Intent intent = new Intent(vista.getContext(), SelectionActivity.class);
            startActivityForResult(intent, 0);
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
}
