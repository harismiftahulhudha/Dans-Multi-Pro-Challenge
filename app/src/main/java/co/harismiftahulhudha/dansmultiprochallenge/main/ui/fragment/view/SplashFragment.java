package co.harismiftahulhudha.dansmultiprochallenge.main.ui.fragment.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;

import co.harismiftahulhudha.dansmultiprochallenge.core.util.base.BaseFragment;
import co.harismiftahulhudha.dansmultiprochallenge.databinding.FragmentSplashBinding;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashFragment extends BaseFragment {
    private FragmentSplashBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSplashBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private FirebaseAuth auth;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (auth.getCurrentUser() != null) {
                Navigation.findNavController(view).navigate(SplashFragmentDirections.actionSplashFragmentToRecruitmentFragment());
            } else {
                Navigation.findNavController(view).navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment());
            }
        }, 2000);
    }
}