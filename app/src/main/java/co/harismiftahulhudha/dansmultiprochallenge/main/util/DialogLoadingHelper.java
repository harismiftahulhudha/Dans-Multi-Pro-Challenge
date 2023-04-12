package co.harismiftahulhudha.dansmultiprochallenge.main.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import co.harismiftahulhudha.dansmultiprochallenge.R;
import co.harismiftahulhudha.dansmultiprochallenge.databinding.DialogLoadingHelperBinding;

public class DialogLoadingHelper {
    private final Context context;
    private Dialog dialog;

    public DialogLoadingHelper(Context context) {
        this.context = context;
    }

    public void show(String title, String message, boolean cancelable) {
        if (dialog == null) {
            dialog = new Dialog(context);
            final DialogLoadingHelperBinding binding = DialogLoadingHelperBinding.inflate(LayoutInflater.from(context));
            dialog.setContentView(binding.getRoot());
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCancelable(cancelable);
            dialog.show();
            if (title != null) {
                binding.txtTitle.setVisibility(View.VISIBLE);
                binding.txtTitle.setText(title);
            } else {
                binding.txtTitle.setVisibility(View.GONE);
            }
            if (message != null) {
                binding.txtMessage.setText(message);
            } else {
                binding.txtMessage.setText(context.getResources().getString(R.string.please_wait));
            }
        }
    }

    public boolean isShowing() {
        if (dialog != null) {
            return dialog.isShowing();
        } else {
            return false;
        }
    }

    public void hide() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
