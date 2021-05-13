package me.langner.jonas.sodapp.ui.activity;

import android.content.Intent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.langner.jonas.sodapp.R;
import me.langner.jonas.sodapp.model.rainbow.GameMap;
import me.langner.jonas.sodapp.ui.adapter.MapAdapter;
import me.langner.jonas.sodapp.ui.adapter.StratAdapter;

/**
 * Bildet das UI für die Listen ab.
 * @author Jonas Langner
 * @version 0.1.1
 * @since 0.1.1
 */
public class HomeActivity extends AppCompatActivity {

    private RecyclerView view;
    private StratAdapter currentStratAdapter;
    private static HomeActivity homeActivity;
    private static Intent stratIntent;

    public static HomeActivity getHomeActivity() {
        return homeActivity;
    }

    public static Intent getStratIntent() {
        return stratIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        homeActivity = this;
        stratIntent = new Intent(this, StratActivity.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        view = (RecyclerView) findViewById(R.id.actHome_list);

        setDecoration();

        setMapAdapter();
    }

    /**
     * Setzt den Stil der Liste.
     */
    private void setDecoration() {
        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dec = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        dec.setDrawable(getDrawable(R.drawable.divider_line));
        view.addItemDecoration(dec);
    }

    /**
     * Setzt die Maps in die Liste.
     */
    private void setMapAdapter() {
        view.setAdapter(MapAdapter.getAdapter());
        MapAdapter.updateMaps();

        ((TextView) findViewById(R.id.actHome_listTitle)).setText("Karten");

        currentStratAdapter = null;
    }

    /**
     * Setzt Strats in die Liste.
     * @param map Die Map der Strats.
     */
    private void setStratAdapter(GameMap map) {
        /* wird demnächst implementiert */
        currentStratAdapter = new StratAdapter(this);
        view.setAdapter(currentStratAdapter);
        currentStratAdapter.updateStrats(map);

        ((TextView) findViewById(R.id.actHome_listTitle)).setText(map.getName());
    }

    public void showStrats(GameMap map) {
        setStratAdapter(map);
    }

    public StratAdapter getCurrentStratAdapter() {
        return currentStratAdapter;
    }

    @Override
    public void onBackPressed() {

        if (currentStratAdapter != null)
            setMapAdapter();
    }
}