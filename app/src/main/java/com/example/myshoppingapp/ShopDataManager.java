package com.example.myshoppingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ShopDataManager {
    static public ArrayList<ShopData> data = new ArrayList<>(
            Arrays.asList(
                    new ShopData("Bone", R.drawable.bone, "Good chew toy.", 1),
                    new ShopData("Carrot", R.drawable.carrot, "Good chew.", 1),
                    new ShopData("Dog", R.drawable.dog, "Chews toy.", 2),
                    new ShopData("Flame", R.drawable.flame, "It burns.", 1),
                    new ShopData("Grapes", R.drawable.grapes, "You eat them.", 1),
                    new ShopData("House", R.drawable.house, "As opposed to home.", 100),
                    new ShopData("Lamp", R.drawable.lamp, "It lights.", 2),
                    new ShopData("Mouse", R.drawable.mouse, "Not a rat.", 1),
                    new ShopData("Nail", R.drawable.nail, "Hammer required.", 1),
                    new ShopData("Penguin", R.drawable.penguin, "Find Batman.", 10),
                    new ShopData("Rocks", R.drawable.rocks, "Rolls.", 1),
                    new ShopData("Star", R.drawable.star, "Like the sun but further away.", 25),
                    new ShopData("Toad", R.drawable.toad, "Like a frog.", 1),
                    new ShopData("Van", R.drawable.van, "Has four wheels.", 10),
                    new ShopData("Wheat", R.drawable.wheat, "Some breads have it.", 1),
                    new ShopData("Yak", R.drawable.yak, "Yakity Yak Yak.", 15)
            )
    );



    static public void InitShopItems(String searchText, LinearLayout itemListLayout, Context context, ConfirmPurchaseDialog confirmPurchaseDialog) {
        LayoutInflater inflater = LayoutInflater.from(context);

        itemListLayout.removeAllViews();

        for (int i = 0; i < data.size(); ++i) {
            if (!data.get(i).purchased ) { // Only add items that haven't been purchased

                final int cur_index = i;
                final int cur_id = data.get(i).id;

                View myShopItem = inflater.inflate(R.layout.shop_item, null);

                View.OnClickListener click = new View.OnClickListener() {
                    private int index = cur_index;
                    private int id = cur_id;

                    private LinearLayout mylayout = itemListLayout;

                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(context.getApplicationContext(), "Toast "+index+" id="+id, Toast.LENGTH_SHORT).show();
                        confirmPurchaseDialog.show();
                        confirmPurchaseDialog.SetDetails(index, id, mylayout);
                    }
                };

                Button name = myShopItem.findViewById(R.id.nameButton);
                name.setText(data.get(i).name);
                name.setOnClickListener(click);

                Button cost = myShopItem.findViewById(R.id.costButton);
                cost.setText("$" + data.get(i).cost);
                cost.setOnClickListener(click);

                ImageButton imageButton = myShopItem.findViewById(R.id.imageButton);
                imageButton.setImageResource(data.get(i).imageResource);
                imageButton.setOnClickListener(click);

                TextView hidden = myShopItem.findViewById(R.id.hiddenId);
                hidden.setText("" + data.get(i).id);


                if (!Objects.equals(searchText, "")){
                    String itemName = data.get(i).name.toLowerCase();
                    String itemDescription = data.get(i).description.toLowerCase();

                    if (itemName.contains(searchText.toLowerCase()) || itemDescription.contains(searchText.toLowerCase())) {
                        itemListLayout.addView(myShopItem);
                    }
                }else {
                    itemListLayout.addView(myShopItem);
                }

            }
        }
    }
}
