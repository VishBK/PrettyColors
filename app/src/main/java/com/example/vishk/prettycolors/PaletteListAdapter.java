package com.example.vishk.prettycolors;

import android.content.Context;
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
import android.widget.Toast;

import java.util.List;


public class PaletteListAdapter extends RecyclerView.Adapter<PaletteListAdapter.ViewHolder> {

    private List<PaletteItem> paletteItems;
    private Context context;

    public PaletteListAdapter(List<PaletteItem> paletteItems, Context context) {
        this.paletteItems = paletteItems;
        this.context = context;
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
        holder.textViewDesc.setText(paletteItem.getDescription());
        String newText = paletteItem.getTitle();
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "You clicked " + paletteItem.getTitle(), Toast.LENGTH_LONG).show();
            }
        });

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
//
            }
        });
    }

    @Override
    public int getItemCount() {
        return paletteItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewTitle;
        public TextView textViewDesc;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
