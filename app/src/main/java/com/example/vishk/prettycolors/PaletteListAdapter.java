package com.example.vishk.prettycolors;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class PaletteListAdapter extends RecyclerView.Adapter<PaletteListAdapter.ViewHolder> {

    private List<PaletteItem> paletteItems;

    public PaletteListAdapter(List<PaletteItem> paletteItems) {
        this.paletteItems = paletteItems;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.palette_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final PaletteItem paletteItem = paletteItems.get(position);
        holder.textViewTitle.setText(paletteItem.getTitle());
        holder.colorView1.setBackgroundColor(Color.HSVToColor(paletteItem.getColor1()));
        holder.colorView2.setBackgroundColor(Color.HSVToColor(paletteItem.getColor2()));
        holder.colorView3.setBackgroundColor(Color.HSVToColor(paletteItem.getColor3()));

        holder.textViewTitle.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                try {
                    Log.i("CharSequenceS", s.toString());

                    paletteItem.setTitle(s.toString());
                } catch (Exception e) {
                    Log.e("TITLE", "failed!!!!");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return paletteItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewTitle;
        public View colorView1;
        public View colorView2;
        public View colorView3;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            colorView1 = itemView.findViewById(R.id.colorView1);
            colorView2 = itemView.findViewById(R.id.colorView2);
            colorView3 = itemView.findViewById(R.id.colorView3);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
