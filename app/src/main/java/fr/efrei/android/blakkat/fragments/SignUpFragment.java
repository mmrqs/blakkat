package fr.efrei.android.blakkat.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.helpers.Toaster;

public class SignUpFragment extends Fragment {
    private SignUpActionsListener signUpActionsListener;
    private TextView editTextPseudo;

    /**
     * When the fragment is attached, casts the englobing context as listener
     * @param context parent activity
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.signUpActionsListener = (SignUpActionsListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SignUpActionsListener");
        }
    }

    /**
     * Called when the fragment is attached for the first time
     * @return the layout of the fragment
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup, container, false);

        editTextPseudo = view.findViewById(R.id.signup_editText_pseudo);

        view.findViewById(R.id.signup_button_signup)
                .setOnClickListener(v -> signUp(editTextPseudo.getText().toString()));
        view.findViewById(R.id.signup_button_signin)
                .setOnClickListener(v -> signInRequest());

        editTextPseudo.requestFocus();
        return view;
    }

    /**
     * Asks the parent activity to try and sign the provided pseudo in
     * @param pseudo provided pseudo
     */
    private void signUp(String pseudo) {
        if(signUpActionsListener.onSignUp(pseudo))
            Toaster.toast(this.getContext(), String.format(getResources()
                            .getString(R.string.pseudo_created), pseudo));
        else
            editTextPseudo.setError(getResources()
                    .getString(R.string.pseudo_exists));
    }

    /**
     * Delegates the sign up request to the parent activity
     */
    private void signInRequest() {
        signUpActionsListener.onSignInRequest();
    }

    /**
     * Allows to communicate the pseudo of the user trying to connect to the activity
     */
    public interface SignUpActionsListener {
        boolean onSignUp(String pseudo);
        void onSignInRequest();
    }
}
