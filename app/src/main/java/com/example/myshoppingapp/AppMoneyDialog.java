package com.example.myshoppingapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Locale;

public class AppMoneyDialog extends Dialog {

    private Button btnConfirmAddAmount, btnCancelAddAmount;
    private EditText txtAmount;
    private MainActivity activity;

    public AppMoneyDialog(Context context, MainActivity activity) {
        super(context);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.addmoneydialog);

        txtAmount = findViewById(R.id.amount_input);
        btnConfirmAddAmount = findViewById(R.id.confirm_add_button);
        btnCancelAddAmount = findViewById(R.id.cancel_add_button);

        btnConfirmAddAmount.setOnClickListener(v -> {
            try {
                float addedAmount = Float.parseFloat(txtAmount.getText().toString());
                int addedAmountInCents = (int) (addedAmount * 100);
                Cart.AddMoney(addedAmountInCents);
                activity.updateMoneyDisplay();  // Update the money display in MainActivity
                Toast.makeText(getContext(), String.format(Locale.getDefault(), "Added $%.2f", addedAmount), Toast.LENGTH_SHORT).show();
                dismiss();
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Please enter a valid amount", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancelAddAmount.setOnClickListener(v -> dismiss());
    }
}
