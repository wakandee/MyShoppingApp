package com.example.myshoppingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static SharedPreferences preferences = null;

    public ConfirmPurchaseDialog confirmPurchaseDialog = null;
    private EditText etSearch;
    private TextView tvMoney;
    private Button btnAddMoney, btnReset, btn_logout,btn_view_history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Cart.InitCart(preferences);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_sort_by);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        confirmPurchaseDialog = new ConfirmPurchaseDialog(this, this);  // Pass 'this' twice

        ShopDataManager.InitShopItems("",findViewById(R.id.itemListLayout ), this, confirmPurchaseDialog);

        tvMoney = findViewById(R.id.tvMoney);
        btnAddMoney = findViewById(R.id.btnAddMoney);
        btnReset = findViewById(R.id.btnReset);
        btn_view_history = findViewById(R.id.btn_view_history);

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
                // Get the path to the log file
                File logFile = new File(getApplicationContext().getFilesDir(), "transaction_log.txt");

                // Call the function to clear the file's content
                clearFileContent(logFile);


            }
        });

        btn_view_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This button will tae one to the view transactions page
                Intent intent  = new Intent(getApplicationContext(), view_transactions.class);
                startActivity(intent);
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

        // Set a default selection for the Spinner
        spinner.setSelection(0, false); // Select the first item ("Sort By") without triggering the listener

        // Add an item selection listener to the Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Get the selected item
                String selectedItem = adapterView.getItemAtPosition(position).toString();

                // Determine the sorting option based on the selected item
                ShopDataManager.SortOption sortOption;
                if (selectedItem.equals("Name")) {
                    sortOption = ShopDataManager.SortOption.NAME;
                } else if (selectedItem.equals("Cost")) {
                    sortOption = ShopDataManager.SortOption.COST;
                } else {
                    sortOption = ShopDataManager.SortOption.ORIGINAL;
                }

                // Set the sorting option in ShopDataManager
                ShopDataManager.setSortOption(sortOption);

                // Refresh the item list with the selected sorting option
                refreshItemList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle this method if needed
            }
        });

    }
    private void clearFileContent(File file) {
        try {
            // Open the file in write mode, which clears existing content
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Add a method to refresh the item list based on the current sorting option
    private void refreshItemList() {
        // Sort the data based on the current sorting option
        ShopDataManager.sortData();

        ShopDataManager.InitShopItems(etSearch.getText().toString(), findViewById(R.id.itemListLayout), MainActivity.this, confirmPurchaseDialog);
        //Toast.makeText(MainActivity.this, "Refresh", Toast.LENGTH_SHORT).show();
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
