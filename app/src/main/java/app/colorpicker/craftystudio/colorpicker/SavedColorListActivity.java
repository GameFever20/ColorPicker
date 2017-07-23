package app.colorpicker.craftystudio.colorpicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import utils.ClickListener;
import utils.ColorListAdapter;
import utils.Detail;
import utils.DetailDataSourceBridge;
import utils.RecyclerTouchListener;

public class SavedColorListActivity extends AppCompatActivity {

    RecyclerView savedColorRecyclerview;
    ArrayList<Detail> mSavedColorArraylist;
    ColorListAdapter colorListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_color_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        savedColorRecyclerview = (RecyclerView) findViewById(R.id.savedcolorRecyclerview);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        //getting list of savedcolors
        DetailDataSourceBridge detailDataSourceBridge = new DetailDataSourceBridge(SavedColorListActivity.this);
        detailDataSourceBridge.open();
        mSavedColorArraylist = detailDataSourceBridge.getAllSavedColors();

        Log.d("Size of all color List", mSavedColorArraylist.size() + "");
        Log.d("Saved data ", mSavedColorArraylist.get(1).getPrimaryHexCode());

        colorListAdapter = new ColorListAdapter(mSavedColorArraylist, SavedColorListActivity.this);
        savedColorRecyclerview.setLayoutManager(mLayoutManager);
        savedColorRecyclerview.setAdapter(colorListAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


       //Calling MAinActivity on Touch of recyclerview
        savedColorRecyclerview.addOnItemTouchListener(new RecyclerTouchListener(SavedColorListActivity.this, savedColorRecyclerview, new ClickListener() {
            @Override
            public void onClick(View view, int position) {


                Intent intent=new Intent(SavedColorListActivity.this,MainActivity.class);
                intent.putExtra("Detail",mSavedColorArraylist.get(position));

                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }
}
