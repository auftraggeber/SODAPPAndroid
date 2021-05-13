package me.langner.jonas.sodapp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import me.langner.jonas.sodapp.model.parser.InformationParser;
import me.langner.jonas.sodapp.model.rainbow.Operator;
import me.langner.jonas.sodapp.model.rainbow.Strat;
import me.langner.jonas.sodapp.R;
import me.langner.jonas.sodapp.model.https.SimpleHTTPSRequest;
import me.langner.jonas.sodapp.model.rainbow.GameMap;
import me.langner.jonas.sodapp.model.user.UserData;

import java.util.ArrayList;

/**
 * Behandelt die Login-Aktivität.
 * @author Jonas Langner
 * @version 0.1.1
 * @since 0.1.1
 */
public class MainActivity extends AppCompatActivity {

    private EditText userTextField, passwordTextField;
    private Button loginButton;
    private Intent homeIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initElements();
        initListener();
    }

    /**
     * Verknüpft Elemente aus Activity mit Objekten.
     */
    private void initElements() {
        userTextField = findViewById(R.id.actMain_userNameField);
        passwordTextField = findViewById(R.id.actMain_passwordField);
        loginButton = findViewById(R.id.actMain_loginButton);
    }

    /**
     * Registriert alle Events.
     */
    private void initListener() {
        if (loginButton != null && userTextField != null && passwordTextField != null) {
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.equals(loginButton) && (v.getTag() == null || !v.getTag().equals("loading"))) {
                        if (userTextField != null && passwordTextField != null && userTextField.getText() != null && passwordTextField != null) {
                            onLoginClick(userTextField.getText().toString(), passwordTextField.getText().toString());

                            if (getCurrentFocus() != null) {
                                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                            }
                        }
                    }
                }
            });
        }
    }

    private void example() {
        Strat strat = Strat.getStrat(1,"TEST", GameMap.getGameMap("Bank", "https://sod.clan.rip/img/Bank.png"), Operator.Role.DEFENDER);
        strat.addOperator(Operator.getOperator("Pulse", "https://sod.clan.rip/img/operator/Pulse.png"), new ArrayList<String>());
    }

    /**
     * Wird aufgerufen, wenn der Nutzer einen Login ausgelöst hat.
     * @param userName Der Name des Nutzers.
     * @param password Das Passwort des Nutzers.
     */
    private void onLoginClick(String userName, String password) {

        UserData.USER_DATA.setUserName(userName);
        UserData.USER_DATA.setPassword(password);

        loginButton.setTag("loading");
        loginButton.setText(R.string.loading);

        final MainActivity mainActivity = this;

        new SimpleHTTPSRequest(SimpleHTTPSRequest.MAIN_URL + "index.php", SimpleHTTPSRequest.Method.POST, UserData.USER_DATA.getURLMap()) {
            @Override
            public void onResponse(int status, final String response) {
                new InformationParser(response) {

                    @Override
                    public void onFinish() {
                        runOnUiThread(new Runnable() {
                            @SuppressLint("StaticFieldLeak")
                            @Override
                            public void run() {
                                loginButton.setTag("done");
                                loginButton.setText(R.string.anmelden);

                                if (UserData.USER_DATA.isLoggedIn()) {
                                    if (homeIntent == null)
                                        homeIntent = new Intent(mainActivity, HomeActivity.class);

                                    startActivity(homeIntent);
                                    finish();
                                }
                            }
                        });
                    }
                };
            }
        };
    }

    private void change() {

    }

    @Override
    public void onBackPressed() {
        return;
    }
}