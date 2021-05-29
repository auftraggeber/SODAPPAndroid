package me.langner.jonas.sodapp.ui.fragment;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import me.langner.jonas.sodapp.R;
import me.langner.jonas.sodapp.model.rainbow.Operator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OperatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OperatorFragment extends Fragment {

    private ImageView operatorImage;
    private TextView operatorText;

    private Operator operator;

    public OperatorFragment() {
        // Required empty public constructor
    }

    /**
     * Lädt den Operator in die Sicht
     */
    private void displayOperator() {
        if (operator != null && operatorImage != null && operatorText != null) {
            Picasso.get().load(operator.getImageUrl()).into(operatorImage);
            operatorText.setText(operator.getName().toUpperCase());
        }
    }

    public void setOperator(Operator operator) {
        this.operator = operator;

        displayOperator();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment Operator.
     */
    // TODO: Rename and change types and number of parameters
    public static OperatorFragment newInstance() {
        OperatorFragment fragment = new OperatorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        operatorImage = view.findViewById(R.id.fragOperator_image);
        operatorText = view.findViewById(R.id.fragOperator_name);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_operator, container, false);
    }
}