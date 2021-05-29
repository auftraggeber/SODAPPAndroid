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
 * Use the {@link UserOperatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserOperatorFragment extends Fragment {

    private Operator userOperator = null;
    private ImageView operatorImage;
    private TextView operatorName;

    public UserOperatorFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment userOperator.
     */
    public static UserOperatorFragment newInstance() {
        UserOperatorFragment fragment = new UserOperatorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Lädt den aktuellen Operator in die Sicht.
     */
    private void displayOperator() {
        if (operatorName != null && operatorImage != null) {

            if (userOperator != null) {
                Picasso.get().load(userOperator.getImageUrl()).into(operatorImage);
                operatorName.setText(userOperator.getName().toUpperCase());

                if (getView() != null)
                    getView().setVisibility(View.VISIBLE);
            }
            else {
                if (getView() != null)
                    getView().setVisibility(View.GONE);
            }
        }
    }

    public void setUserOperator(Operator userOperator) {
        this.userOperator = userOperator;

        displayOperator();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (view != null) {
            operatorImage = getView().findViewById(R.id.fragUserOperator_image);
            operatorName = getView().findViewById(R.id.fragUserOperator_name);

            displayOperator();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_operator, container, false);
    }
}