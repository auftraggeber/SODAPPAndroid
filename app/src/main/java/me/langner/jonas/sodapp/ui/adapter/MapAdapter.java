package me.langner.jonas.sodapp.ui.adapter;

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
import me.langner.jonas.sodapp.ui.activity.HomeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Behandelt die Map-Einträge der Liste.
 * @author Jonas Langner
 * @version 0.1.1
 * @since 0.1.1
 */
public class MapAdapter extends RecyclerView.Adapter {

    private static MapAdapter adapter;

    private static List<GameMap> maps = new ArrayList<>();

    /**
     * Erneuert die Liste.
     */
    public static void updateMaps() {
        maps = new ArrayList<>();

        for (GameMap map: GameMap.getMaps()) {
            System.out.println(map.getName());

            if (!map.getStrats().isEmpty())
                maps.add(map);
        }


        adapter.notifyDataSetChanged();
    }

    public static MapAdapter getAdapter() {
        if (adapter == null)
            adapter = new MapAdapter();

        return adapter;
    }

    /**
     * Struktur eines Eintrags.
     */
    private class MapItemViewHolder extends RecyclerView.ViewHolder {

        private GameMap mItem;
        private final TextView mText, mMapCountField;
        private final ImageView mImage;

        public MapItemViewHolder(@NonNull View itemView) {
            super(itemView);

            mText = itemView.findViewById(R.id.fragMapListItem_title);
            mMapCountField = itemView.findViewById(R.id.fragMapListItem_count);
            mImage = itemView.findViewById(R.id.fragMapListItem_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeActivity.getHomeActivity().showStrats(mItem);
                }
            });
        }

        public void setItem(GameMap mItem) {
            this.mItem = mItem;

            mText.setText(mItem.getName());
            mMapCountField.setText(mItem.getStrats().size() + "");
            Picasso.get().load(mItem.getImageUrl()).into(mImage);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_list_item
                , parent, false);
        return new MapItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MapItemViewHolder) {
            MapItemViewHolder mapItemViewHolder = (MapItemViewHolder) holder;

            mapItemViewHolder.setItem(maps.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return maps.size();
    }
}
