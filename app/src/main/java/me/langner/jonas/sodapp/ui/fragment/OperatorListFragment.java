package me.langner.jonas.sodapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.langner.jonas.sodapp.R;
import me.langner.jonas.sodapp.model.rainbow.Strat;
import me.langner.jonas.sodapp.ui.adapter.OperatorStratListAdapter;

/**
 * A fragment representing a list of Items.
 */
public class OperatorListFragment extends Fragment {

    private int mColumnCount = 1;
    private Strat strat;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OperatorListFragment(Strat strat) {
        this.strat = strat;
    }

    @SuppressWarnings("unused")
    public static OperatorListFragment newInstance(Strat strat) {
        OperatorListFragment fragment = new OperatorListFragment(strat);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_operator_list_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new OperatorStratListAdapter(strat));
        }
        return view;
    }
}