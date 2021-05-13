package me.langner.jonas.sodapp.ui.activity;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import me.langner.jonas.sodapp.R;
import me.langner.jonas.sodapp.model.rainbow.Strat;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        current = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strat);

        titleView = (TextView) findViewById(R.id.actStrat_title);

        loadStrat();
    }

    private void loadStrat() {
        if (currentStrat != null) {
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