package com.example.machathon;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.MyViewHolder> {

    private List<String> shoppingList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
        }
    }

    public ShoppingListAdapter(List<String> shoppingList) {
        this.shoppingList = shoppingList;
    }

    @NonNull
    @Override
    public ShoppingListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_list_row, parent, false);

        return new ShoppingListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListAdapter.MyViewHolder myViewHolder, int position) {

        String shoppingListItem = shoppingList.get(position);
        myViewHolder.title.setText(shoppingListItem);
    }

    @Override
    public int getItemCount() {
        return shoppingList.size();
    }
}
