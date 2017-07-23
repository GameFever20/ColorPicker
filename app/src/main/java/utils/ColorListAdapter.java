package utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.colorpicker.craftystudio.colorpicker.R;

/**
 * Created by Aisha on 7/23/2017.
 */

public class ColorListAdapter extends RecyclerView.Adapter<ColorListAdapter.ColorListAdapterHolder> {

    private ArrayList<Detail> colorListArrayList;
    Context context;

    public ColorListAdapter(ArrayList<Detail> colorListArrayList, Context context) {
        this.colorListArrayList = colorListArrayList;
        this.context = context;
    }

    @Override
    public ColorListAdapter.ColorListAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.colorlist_adapter_row, parent, false);
        return new ColorListAdapterHolder(view);

    }

    @Override
    public void onBindViewHolder(ColorListAdapter.ColorListAdapterHolder holder, int position) {

        try {
            final Detail detail = colorListArrayList.get(position);
            holder.mPrimaryColorTextView.setText(detail.getPrimaryHexCode());
            // holder.mPrimaryDarkColorTextView.setText(detail.getPrimaryDarkHexCode());
            holder.mAccentColorTextView.setText(detail.getAccentHexCode());

            holder.mPrimaryBackground.setBackgroundColor(Color.parseColor(detail.getPrimaryHexCode()));
            holder.mAccentBackground.setCardBackgroundColor(Color.parseColor(detail.getAccentHexCode()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return colorListArrayList.size();
    }

    public class ColorListAdapterHolder extends RecyclerView.ViewHolder {
        public TextView mPrimaryColorTextView, mPrimaryDarkColorTextView, mAccentColorTextView;
        public CardView mPrimaryBackground;
        public CardView mAccentBackground;


        public ColorListAdapterHolder(View view) {
            super(view);
            mPrimaryColorTextView = (TextView) view.findViewById(R.id.colorlist_adapter_header_primarycolor_textview);
            mAccentColorTextView = (TextView) view.findViewById(R.id.colorlist_adapter_header_accentcolor_textview);

            // mPrimaryDarkColorTextView = (TextView) view.findViewById(R.id.colorlist_adapter_header_primarydarkcolor_textview);
            mPrimaryBackground = (CardView) view.findViewById(R.id.colorlist_adapter_primarycolor_cardview);
            mAccentBackground = (CardView) view.findViewById(R.id.colorlist_adapter_accentcolor_fab_cardview);


        }
    }
}
