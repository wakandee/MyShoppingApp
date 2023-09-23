package com.example.myshoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class view_transactions extends AppCompatActivity {

    Button back_btn;
    LinearLayout transactionListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction);
        back_btn = findViewById(R.id.btn_back);
        transactionListLayout = findViewById(R.id.transaction_list);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Read and display transactions
        readAndDisplayTransactions();
    }

    private void readAndDisplayTransactions() {
        // Get the path to the transaction log file
        File logFile = new File(getApplicationContext().getFilesDir(), "transaction_log.txt");

        // Check if the file is empty
        if (logFile.length() == 0) {
            // Display a message when the file is empty
            TextView emptyTextView = new TextView(this);
            emptyTextView.setText("No transaction records exist.");
            transactionListLayout.addView(emptyTextView);
        } else {
            // Read and display transactions when the file is not empty
            try {
                BufferedReader reader = new BufferedReader(new FileReader(logFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    // Create a TextView for each transaction and add it to the layout
                    TextView transactionTextView = new TextView(this);
                    transactionTextView.setText(line);
                    transactionListLayout.addView(transactionTextView);
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
