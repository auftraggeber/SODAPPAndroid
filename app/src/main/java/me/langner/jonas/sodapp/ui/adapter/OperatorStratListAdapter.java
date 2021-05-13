package me.langner.jonas.sodapp.ui.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.langner.jonas.sodapp.R;
import me.langner.jonas.sodapp.model.rainbow.Operator;
import me.langner.jonas.sodapp.model.rainbow.Strat;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Strat}.
 * TODO: Replace the implementation with code for your data type.
 */
public class OperatorStratListAdapter extends RecyclerView.Adapter<OperatorStratListAdapter.OperatorViewHolder> {

    private Strat strat;
    private List<Operator> operators = new ArrayList<>();

    public OperatorStratListAdapter(Strat strat) {
        this.strat = strat;

        for (Operator op : strat.getOperators())
            operators.add(op);
    }

    @Override
    public OperatorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_operator_list_item, parent, false);
        return new OperatorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OperatorViewHolder holder, int position) {
        if (holder instanceof OperatorViewHolder) {
            OperatorViewHolder operatorViewHolder = (OperatorViewHolder) holder;
            operatorViewHolder.setOperator(operators.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return operators.size();
    }

    /**
     * Items der Liste mit ihren Elementen.
     */
    public class OperatorViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mOperatorName;
        public Operator mItem;

        public OperatorViewHolder(View view) {
            super(view);
            mView = view;
            mOperatorName = view.findViewById(R.id.fragOperatorListItem_name);
        }

        public void setOperator(Operator operator) {
            if (operator == null)
                return;

            this.mItem = operator;

            mOperatorName.setText(mItem.getName());
        }

    }
}