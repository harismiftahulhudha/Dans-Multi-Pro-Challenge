package co.harismiftahulhudha.dansmultiprochallenge.main.ui.fragment.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.ArrayList;
import java.util.List;

import co.harismiftahulhudha.dansmultiprochallenge.BuildConfig;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.base.BaseFragment;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.result.Status;
import co.harismiftahulhudha.dansmultiprochallenge.databinding.FragmentLoginBinding;
import co.harismiftahulhudha.dansmultiprochallenge.main.ui.activity.viewmodel.MainViewModel;
import co.harismiftahulhudha.dansmultiprochallenge.main.ui.fragment.viewmodel.LoginViewModel;
import co.harismiftahulhudha.dansmultiprochallenge.main.util.DialogLoadingHelper;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends BaseFragment {
    private FragmentLoginBinding binding;
    private BeginSignInRequest signInRequest;
    private SignInClient oneTapClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private LoginViewModel viewModel;
    private DialogLoadingHelper loadingHelper;
    private CallbackManager callbackManager;
    private final ActivityResultLauncher<IntentSenderRequest> startGoogleLoginLauncher = registerForActivityResult(
            new ActivityResultContracts.StartIntentSenderForResult(),
            new ActivityResultCallback<>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        viewModel.setStatusLogin(Status.LOADING.toString());
                        Intent data = result.getData();
                        if (data != null) {
                            try {
                                final SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                                final String idToken = credential.getGoogleIdToken();
                                final AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
                                FirebaseAuth.getInstance().signInWithCredential(firebaseCredential)
                                        .addOnCompleteListener((task) -> {
                                            viewModel.setStatusLogin(Status.SUCCESS.toString());
                                        }).addOnFailureListener((exception) -> {
                                            exception.printStackTrace();
                                            viewModel.setStatusLogin(Status.ERROR.toString());
                                            if (exception.getLocalizedMessage() != null) {
                                                Toast.makeText(requireContext(), exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                            } catch (ApiException e) {
                                viewModel.setStatusLogin(Status.ERROR.toString());
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            });

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        initComponents();
        subscribeListeners();
        subscribeObservers();
    }

    @Override
    public void initComponents() {
        MainViewModel activityViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        activityViewModel.setShowMenu(false);
        loadingHelper = new DialogLoadingHelper(requireContext());
        oneTapClient = Identity.getSignInClient(requireContext());
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                                .setSupported(true)
                                .setServerClientId(BuildConfig.SERVER_CLIENT_ID)
                                .setFilterByAuthorizedAccounts(false)
                                .build()
                )
                .build();
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                viewModel.setStatusLogin(Status.LOADING.toString());
                final AuthCredential credential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener((task) -> {
                            viewModel.setStatusLogin(Status.SUCCESS.toString());
                        }).addOnFailureListener((exception) -> {
                            exception.printStackTrace();
                            viewModel.setStatusLogin(Status.ERROR.toString());
                            if (exception.getLocalizedMessage() != null) {
                                Toast.makeText(requireContext(), exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(@NonNull FacebookException e) {

            }
        });
    }

    @Override
    public void subscribeListeners() {
        binding.btnLoginGoogle.setOnClickListener((v) -> {
            oneTapClient.beginSignIn(signInRequest)
                    .addOnSuccessListener((result) -> {
                        IntentSenderRequest intentSenderRequest = new IntentSenderRequest.Builder(result.getPendingIntent()).build();
                        startGoogleLoginLauncher.launch(intentSenderRequest);
                    })
                    .addOnFailureListener((exception) -> {
                        exception.printStackTrace();
                        if (exception.getLocalizedMessage() != null) {
                            Toast.makeText(requireContext(), exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });
        binding.btnLoginFacebook.setOnClickListener((v) -> {
            final List<String> list = new ArrayList<>();
            list.add("email");
            list.add("public_profile");
            list.add("user_photos");
            LoginManager.getInstance().logInWithReadPermissions(this, callbackManager, list);
        });
    }

    @Override
    public void subscribeObservers() {
        viewModel.observeStatusLogin.observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                if (data.equals(Status.LOADING.toString())) {
                    loadingHelper.show(null, null, false);
                } else if (data.equals(Status.SUCCESS.toString())) {
                    loadingHelper.hide();
                    Navigation.findNavController(binding.btnLoginGoogle).navigate(LoginFragmentDirections.actionLoginFragmentToRecruitmentFragment());
                } else {
                    loadingHelper.hide();
                }
            }
        });
    }
}