package com.example.myshoppingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static SharedPreferences preferences = null;

    public ConfirmPurchaseDialog confirmPurchaseDialog = null;
    private EditText etSearch;
    private TextView tvMoney;
    private Button btnAddMoney, btnReset, btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Cart.InitCart(preferences);

        confirmPurchaseDialog = new ConfirmPurchaseDialog(this, this);  // Pass 'this' twice

        ShopDataManager.InitShopItems("",findViewById(R.id.itemListLayout ), this, confirmPurchaseDialog);

        tvMoney = findViewById(R.id.tvMoney);
        btnAddMoney = findViewById(R.id.btnAddMoney);
        btnReset = findViewById(R.id.btnReset);

        btn_logout = findViewById(R.id.btn_logout);

        updateMoneyDisplay();

        tvMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        btnAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This button will now trigger the TextView's click to show the dialog
                tvMoney.performClick();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart.ResetMoney();
                ShopDataManager.InitShopItems("",findViewById(R.id.itemListLayout), MainActivity.this, confirmPurchaseDialog);
                updateMoneyDisplay();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.apply();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        etSearch = findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Filter the shop items based on the search text
                filterShopItems(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    public void updateMoneyDisplay() {
        float moneyInDollars = Cart.money / 100.0f;
        tvMoney.setText(String.format(Locale.getDefault(), "$%.2f", moneyInDollars));
    }

    private void filterShopItems(String searchText) {
        ShopDataManager.InitShopItems(searchText,findViewById(R.id.itemListLayout ), this, confirmPurchaseDialog);
//
    }

}
