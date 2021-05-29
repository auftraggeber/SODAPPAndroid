package me.langner.jonas.sodapp.ui.activity;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import me.langner.jonas.sodapp.R;
import me.langner.jonas.sodapp.model.https.SimpleHTTPSRequest;
import me.langner.jonas.sodapp.model.parser.StratParser;
import me.langner.jonas.sodapp.model.rainbow.Operator;
import me.langner.jonas.sodapp.model.rainbow.Strat;
import me.langner.jonas.sodapp.model.user.UserData;
import me.langner.jonas.sodapp.ui.fragment.OperatorFragment;
import me.langner.jonas.sodapp.ui.fragment.UserOperatorFragment;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * Behandelt das UI für die Stratdarstellung.
 * @author Jonas Langner
 * @version 0.1.1
 * @since 0.1.1
 */
public class StratActivity extends AppCompatActivity {

    private static StratActivity current;
    private static Strat currentStrat;

    /**
     * Gibt die aktuellste Instanz zurück.
     * @return Die aktuellste Instanz.
     */
    public static StratActivity getCurrent() {
        return current;
    }

    public static void setCurrentStrat(Strat currentStrat) {
        StratActivity.currentStrat = currentStrat;

        if (current != null) ;
            //current.loadStrat();
    }

    public static Strat getCurrentStrat() {
        return currentStrat;
    }

    private TextView titleView;

    private UserOperatorFragment userOperatorFragment;
    private OperatorFragment[] operatorFragments = new OperatorFragment[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        current = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strat);

        titleView = (TextView) findViewById(R.id.actStrat_title);

        loadStrat();
    }

    /**
     * Zeigt Inhalte der Strat an.
     */
    private void displayStrat() {
        userOperatorFragment = (UserOperatorFragment) getSupportFragmentManager().findFragmentById(R.id.actStrat_userOperatorFragment);
        userOperatorFragment.setUserOperator(currentStrat.getUserOperator());

        operatorFragments = new OperatorFragment[] {
            (OperatorFragment) getSupportFragmentManager().findFragmentById(R.id.actStrat_operator1),
                    (OperatorFragment) getSupportFragmentManager().findFragmentById(R.id.actStrat_operator2),
                    (OperatorFragment) getSupportFragmentManager().findFragmentById(R.id.actStrat_operator3),
                    (OperatorFragment) getSupportFragmentManager().findFragmentById(R.id.actStrat_operator4),
                    (OperatorFragment) getSupportFragmentManager().findFragmentById(R.id.actStrat_operator5)
        };

        int index = 0;
        for (Operator operator : currentStrat.getOperators()) {

            operatorFragments[index].setOperator(operator);

            index++;
        }
    }

    /**
     * Lädt alle Inhalte der Strat aus dem Internet.
     */
    private void loadStrat() {
        if (currentStrat != null) {

            Map<String, String> postMap = UserData.USER_DATA.getURLMap();
            postMap.put("id_0", currentStrat.getId() + "");

            new SimpleHTTPSRequest(SimpleHTTPSRequest.MAIN_URL + "strat.php", SimpleHTTPSRequest.Method.POST, postMap) {
                @Override
                public void onResponse(int status, String response) {
                    System.out.println(status + ": " + response);
                    if (status == 200) {
                        new StratParser(response) {
                            @Override
                            public void onFinish() {
                                /* Details laden */
                                runOnUiThread(() -> {
                                    displayStrat();

                                });
                            }
                        };
                    }

                }
            };

            try {
                setTitle(URLDecoder.decode(currentStrat.getName(),"UTF-8"));
            }
            catch (UnsupportedEncodingException exception) {
                setTitle(currentStrat.getName());
            }

        }
    }

    public void setTitle(String title) {
        titleView.setText(title);
    }
}