package co.harismiftahulhudha.dansmultiprochallenge.main.ui.activity.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import java.util.Objects;

import co.harismiftahulhudha.dansmultiprochallenge.R;
import co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.entity.RecruitmentEntity;
import co.harismiftahulhudha.dansmultiprochallenge.core.domain.payload.RecruitmentQueryPayload;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.base.BaseActivity;
import co.harismiftahulhudha.dansmultiprochallenge.databinding.ActivityMainBinding;
import co.harismiftahulhudha.dansmultiprochallenge.main.ui.activity.viewmodel.MainViewModel;
import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private NavHostFragment navHostFragment;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initComponents();
        subscribeObservers();
    }

    @Override
    public void initComponents() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.mainHostFragment);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
        NavigationUI.setupWithNavController(binding.bnvMain, navController);
    }

    @Override
    public void subscribeObservers() {
        viewModel.isShowMenu.observe(this, isShowMenu -> {
            if (isShowMenu) {
                binding.bnvMain.setVisibility(View.VISIBLE);
            } else {
                binding.bnvMain.setVisibility(View.GONE);
            }
        });
//        viewModel.getListRecruitment(1, new RecruitmentQueryPayload("", "", ""));
//        viewModel.recruitments.observe(this, data -> {
//            if (data != null) {
//                switch (data.status) {
//                    case ERROR:
//                        break;
//                    case LOADING:
//                        break;
//                    case SUCCESS:
//                        if (data.data != null) {
//                            for (RecruitmentEntity model : data.data) {
//                                Log.d(TAG, "subscribeObservers: getList " + model.getCompany());
//                            }
//                        }
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
    }
}