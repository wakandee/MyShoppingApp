package com.example.myshoppingapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Window;


import androidx.annotation.NonNull;

public class ConfirmPurchaseDialog extends Dialog {

    private MainActivity mainActivity;

    public ConfirmPurchaseDialog(@NonNull Context context, MainActivity mainActivity) {
        super(context);
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_layout);

        Button no = findViewById(R.id.cancelButton);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void SetDetails(final int itemIndex, final int itemId, final LinearLayout parentLayout) {
        Button yes = findViewById(R.id.purchaseButton);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemCost = ShopDataManager.data.get(itemIndex).cost * 100;

                if (Cart.money >= itemCost) {
                    Cart.AddMoney(-itemCost);
                    mainActivity.updateMoneyDisplay();
                    ShopDataManager.data.get(itemIndex).purchased = true;

                    for (int i = 0; i < parentLayout.getChildCount(); ++i) {
                        View v = parentLayout.getChildAt(i);
                        TextView hidden = v.findViewById(R.id.hiddenId);
                        int layoutID = Integer.parseInt("" + hidden.getText());
                        if (layoutID == itemId) {
                            parentLayout.removeViewAt(i);
                            break;
                        }
                    }
                    Toast.makeText(getContext(), "Purchase Successful!", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Not enough money!", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });

        TextView title = findViewById(R.id.nameText);
        title.setText(ShopDataManager.data.get(itemIndex).name);

        TextView desc = findViewById(R.id.description);
        desc.setText(ShopDataManager.data.get(itemIndex).description);

        TextView price = findViewById(R.id.cost);
        price.setText("$" + ShopDataManager.data.get(itemIndex).cost);

        ImageButton image = findViewById(R.id.itemImage);
        image.setImageResource(ShopDataManager.data.get(itemIndex).imageResource);
    }
}
