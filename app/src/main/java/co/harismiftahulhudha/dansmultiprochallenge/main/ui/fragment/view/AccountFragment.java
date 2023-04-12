package co.harismiftahulhudha.dansmultiprochallenge.main.ui.fragment.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.harismiftahulhudha.dansmultiprochallenge.R;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.base.BaseFragment;
import co.harismiftahulhudha.dansmultiprochallenge.databinding.FragmentAccountBinding;
import co.harismiftahulhudha.dansmultiprochallenge.main.ui.activity.viewmodel.MainViewModel;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AccountFragment extends BaseFragment {
    private FragmentAccountBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initComponents();
        subscribeListeners();
    }

    @Override
    public void initComponents() {
        MainViewModel activityViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        activityViewModel.setShowMenu(true);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (auth != null && user != null) {
            if (user.getDisplayName() != null && !user.getDisplayName().equals("")) {
                binding.txtUserName.setText(user.getDisplayName());
            } else {
                binding.txtUserName.setText(user.getEmail());
            }
            if (user.getPhotoUrl() != null) {
                Glide.with(binding.getRoot().getContext())
                        .load(user.getPhotoUrl())
                        .placeholder(R.drawable.default_person)
                        .error(R.drawable.default_person)
                        .into(binding.imgUserPhoto);
            }
        }
    }

    @Override
    public void subscribeListeners() {
        binding.btnLogout.setOnClickListener((v) -> {
            if (auth != null) {
                auth.signOut();
            }
            Navigation.findNavController(v).navigate(AccountFragmentDirections.actionAccountFragmentToLoginFragment());
        });
    }
}