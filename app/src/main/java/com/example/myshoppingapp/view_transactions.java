package com.example.myshoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class view_transactions extends AppCompatActivity {

    Button back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction);

        back_btn = findViewById(R.id.btn_back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Find the LinearLayout in your layout XML where you want to display transactions
        LinearLayout transactionListLayout = findViewById(R.id.transaction_list);

        // Read and display transactions from the log file
        displayTransactions(transactionListLayout);
    }

    private void displayTransactions(LinearLayout layout) {
        // Get the path to the log file
        File logFile = new File(getApplicationContext().getFilesDir(), "transaction_log.txt");

        if (logFile.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(logFile));
                String line;

                while ((line = reader.readLine()) != null) {
                    // Create a TextView for each transaction
                    TextView transactionTextView = new TextView(this);
                    transactionTextView.setText(line);

                    // Add the TextView to the LinearLayout
                    layout.addView(transactionTextView);
                }

                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // If the log file does not exist, display a message indicating it's empty
            TextView emptyTextView = new TextView(this);
            emptyTextView.setText("Log file is empty.");

            // Add the TextView to the LinearLayout
            layout.addView(emptyTextView);
        }
    }

}
