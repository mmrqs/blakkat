package fr.efrei.android.blakkat.ui.fragments;

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

public class SignInFragment extends Fragment {
    private SignInActionsListener signInActionsListener;
    private TextView editTextPseudo;

    /**
     * When the fragment is attached, casts the englobing context as listener
     * @param context parent activity
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.signInActionsListener = (SignInActionsListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SignInActionsListener");
        }
    }

    /**
     * Called when the fragment is attached for the first time
     * @return the layout of the fragment
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signin, container, false);
        editTextPseudo = view.findViewById(R.id.signin_editText_pseudo);

        view.findViewById(R.id.signin_button_signin)
                .setOnClickListener(v -> signIn(editTextPseudo.getText().toString()));
        view.findViewById(R.id.signin_button_signup)
                .setOnClickListener(v -> signUpRequest());

        editTextPseudo.requestFocus();
        return view;
    }

    /**
     * Asks the parent activity to try and sign the provided pseudo in
     * @param pseudo user-provided pseudo
     */
    private void signIn(String pseudo) {
        if(signInActionsListener.onSignIn(pseudo))
            Toaster.toast(this.getContext(), String.format(getResources()
                            .getString(R.string.pseudo_welcome), pseudo));
        else
            editTextPseudo.setError(getResources()
                    .getString(R.string.pseudo_wrong));
    }

    /**
     * Delegates the sign up request to the parent activity
     */
    private void signUpRequest() {
        signInActionsListener.onSignUpRequest();
    }

    /**
     * Allows to communicate the pseudo of the user trying to connect to the activity
     */
    public interface SignInActionsListener {
        boolean onSignIn(String pseudo);
        void onSignUpRequest();
    }
}
