package me.langner.jonas.sodapp.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import me.langner.jonas.sodapp.R;
import me.langner.jonas.sodapp.model.rainbow.GameMap;
import me.langner.jonas.sodapp.model.rainbow.Operator;
import me.langner.jonas.sodapp.model.rainbow.Strat;
import me.langner.jonas.sodapp.ui.activity.HomeActivity;
import me.langner.jonas.sodapp.ui.activity.StratActivity;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Behandelt die Einträge der Strategienliste.
 * @author Jonas Langner
 * @version 0.1.1
 * @since 0.1.1
 */
public class StratAdapter extends RecyclerView.Adapter {

    private HomeActivity activity;
    private List<Strat> strats = new ArrayList<>();

    /**
     * Erstellt einen neuen Handler.
     * @param activity Die zugehörige Aktivität.
     */
    public StratAdapter(HomeActivity activity) {
        this.activity = activity;
    }

    /**
     * Erneuert die Listeneinträge.
     * @param map Die aktuell gewählte Map.
     */
    public void updateStrats(GameMap map) {
        strats = new ArrayList<>();

        notifyDataSetChanged();

        for (Strat strat: map.getStrats()) {
            if (strat.getRole().equals(Operator.Role.ATTACKER))
                strats.add(0,strat);
            else
                strats.add(strat);
        }

        notifyDataSetChanged();
    }

    /**
     * Struktur eines Eintrags.
     */
    private class StratItemViewHolder extends RecyclerView.ViewHolder {

        private Strat mItem;
        private final TextView mTitle, mRole;
        private final ImageView[] mImages;

        public StratItemViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.fragStratListItem_title);
            mRole = itemView.findViewById(R.id.fragStratListItem_role);

            mImages = new ImageView[]{
                    itemView.findViewById(R.id.fragStratListItem_image1),
                    itemView.findViewById(R.id.fragStratListItem_image2),
                    itemView.findViewById(R.id.fragStratListItem_image3),
                    itemView.findViewById(R.id.fragStratListItem_image4),
                    itemView.findViewById(R.id.fragStratListItem_image5)
            };

            itemView.setOnClickListener((view) -> {
                StratActivity.setCurrentStrat(mItem);
                activity.startActivity(HomeActivity.getStratIntent());
            });
        }

        /**
         * Definiert das aktuelle Item.
         * @param mItem Die Definition.
         */
        public void setItem(Strat mItem) {
            this.mItem = mItem;

            try {
                mTitle.setText(URLDecoder.decode(mItem.getName(),"UTF-8"));
            }
            catch (Exception ex) {
                mTitle.setText("FEHLER");
            }
            mRole.setText(mItem.getRole().toString());

            /* Farbe setzen */
            int color = Color.rgb(240, 176, 0);

            if (mItem.getRole().equals(Operator.Role.ATTACKER)) {
                color = Color.rgb(0, 164, 240);
            }

            mTitle.setTextColor(color);
            mRole.setTextColor(color);

            int index = 0;

            for (Operator operator: mItem.getOperators()) {
                Picasso.get().load(operator.getImageUrl()).into(mImages[index]);
                index++;
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.strat_list_item
                , parent, false);
        return new StratItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof StratItemViewHolder) {
            StratItemViewHolder mapItemViewHolder = (StratItemViewHolder) holder;

            mapItemViewHolder.setItem(strats.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return strats.size();
    }
}
