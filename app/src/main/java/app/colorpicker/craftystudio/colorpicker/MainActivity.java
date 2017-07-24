package app.colorpicker.craftystudio.colorpicker;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.LoginFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.madrapps.pikolo.HSLColorPicker;
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import utils.Detail;
import utils.DetailDataSourceBridge;

import static app.colorpicker.craftystudio.colorpicker.R.id.decor_content_parent;
import static app.colorpicker.craftystudio.colorpicker.R.id.fab;
import static app.colorpicker.craftystudio.colorpicker.R.id.toolbar;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbarTextview, statusbarTextview, fabTextview;
    FloatingActionButton fab;

    HSLColorPicker colorPicker;

    ArrayList<Detail> mSavedColorsList;

    Detail detail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        statusbarTextview = (TextView) findViewById(R.id.statusbar_hexcode);
        toolbarTextview = (TextView) findViewById(R.id.toolbar_hexcode);
        fabTextview = (TextView) findViewById(R.id.fab_hexcode);

        colorPicker = (HSLColorPicker) findViewById(R.id.coloPicker);
        colorPicker.setColorSelectionListener(new SimpleColorSelectionListener() {
            @Override
            public void onColorSelected(int color) {
                // Do whatever you want with the color
                //imageView.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);

                //getting darker color of toolbar
                int statuscolor = manipulateColor(color, 0.85f);

                //setting status,toolbar,fabcolor
                changeColor(color, statuscolor, color);
            }
        });


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //random color for toolbar
                Random rndToolbar = new Random();
                int randomcolor_toolbar = Color.argb(255, rndToolbar.nextInt(256), rndToolbar.nextInt(256), rndToolbar.nextInt(256));

                //calling method for getting darker toolbar color
                int randomcolor_status = manipulateColor(randomcolor_toolbar, 0.7f);

                //random color for fab
                Random rndFab = new Random();
                int randomcolor_fab = Color.argb(255, rndFab.nextInt(256), rndFab.nextInt(256), rndFab.nextInt(256));

                changeColor(randomcolor_toolbar, randomcolor_status, randomcolor_fab);
                colorPicker.setColor(randomcolor_toolbar);


            }
        });

        //getting saved color for display from intent
        try {
            detail = (Detail) getIntent().getSerializableExtra("Detail");
            if (detail != null) {
                changeColor(Color.parseColor(detail.getPrimaryHexCode()),
                        Color.parseColor(detail.getPrimaryDarkHexCode()),
                        Color.parseColor(detail.getAccentHexCode()));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void changeColor(int toolbarColor, int statusColor, int fabColor) {

        //setting colors
        toolbar.setBackgroundColor(toolbarColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = MainActivity.this.getWindow();
            window.setStatusBarColor(statusColor);
        }
        fab.setBackgroundTintList(ColorStateList.valueOf(fabColor));


        //getting hexcode from colors
        String statusHex = Integer.toHexString(statusColor);
        String toolbarHex = Integer.toHexString(toolbarColor);
        String fabHex = Integer.toHexString(fabColor);


        //making visible
        toolbarTextview.setVisibility(View.VISIBLE);
        statusbarTextview.setVisibility(View.VISIBLE);
        fabTextview.setVisibility(View.VISIBLE);

        //setting textview with hexcode
        toolbarTextview.setText("#" + toolbarHex);
        statusbarTextview.setText("#" + statusHex);
        fabTextview.setText("#" + fabHex);

        Log.d("Hex", statusHex + "-" + toolbarHex + "-" + fabHex);
        //  Toast.makeText(MainActivity.this, "hexcode for toolbar " + toolbarHex, Toast.LENGTH_SHORT).show();

    }

    public static int manipulateColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r, 255),
                Math.min(g, 255),
                Math.min(b, 255));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save_color) {

            DetailDataSourceBridge detailDataSourceBridge = new DetailDataSourceBridge(MainActivity.this);
            detailDataSourceBridge.open();
            Detail detail = detailDataSourceBridge.createColorObject(toolbarTextview.getText().toString(), statusbarTextview.getText().toString(), fabTextview.getText().toString());
            Toast.makeText(MainActivity.this, "PrimaryDark code is-" + detail.getPrimaryHexCode(), Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, "Primary code is-" + detail.getPrimaryDarkHexCode(), Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, "Accent code is-" + detail.getAccentHexCode(), Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, "Id is" + detail.getId(), Toast.LENGTH_SHORT).show();


            return true;
        } else if (id == R.id.action_get_saved_color) {
            Intent intent = new Intent(MainActivity.this, SavedColorListActivity.class);
            startActivity(intent);
            // Toast.makeText(this, mSavedColorsList.get(1).getPrimaryHexCode(), Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }

    public void copyToolbarHexCode(View view) {

        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(this.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", toolbarTextview.getText());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Toolbar Code Copied", Toast.LENGTH_SHORT).show();
    }

    public void copyStatusHexCode(View view) {

        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(this.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", statusbarTextview.getText());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "StatusBar Code Copied", Toast.LENGTH_SHORT).show();
    }


    public void copyFabHexCode(View view) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(this.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", fabTextview.getText());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Fab color Code Copied", Toast.LENGTH_SHORT).show();

    }

    public void enterToolBarHexcodeDialog(View view) {

        final EditText manualHexcodeEdittext;

        final Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_INDEFINITE);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

        // Inflate your custom view with an Edit Text
        LayoutInflater objLayoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View snackView = objLayoutInflater.inflate(R.layout.custom_snakbar_layout, null); // custom_snac_layout is your custom xml


        manualHexcodeEdittext = (EditText) snackView.findViewById(R.id.customsnack_addcode_edittext);
        requestEdittextFocus(manualHexcodeEdittext);
        snackbar.setAction("ADD", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (manualHexcodeEdittext.getText().toString().isEmpty()) {
                    Snackbar snackbar1 = Snackbar.make(view, "No color is inputted", Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                } else {
                    Snackbar snackbar1 = Snackbar.make(view, "Toolbar color is set", Snackbar.LENGTH_SHORT);
                    //making visible
                    toolbarTextview.setVisibility(View.VISIBLE);
                    toolbarTextview.setText(manualHexcodeEdittext.getText().toString());
                    toolbar.setBackgroundColor(Color.parseColor(manualHexcodeEdittext.getText().toString()));
                    snackbar1.show();
                }
            }
        });
        layout.addView(snackView, 0);
        snackbar.show();
    }

    public void enterStatusHexcodeDialog(View view) {

        final EditText manualHexcodeEdittext;

        final Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_INDEFINITE);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

        // Inflate your custom view with an Edit Text
        LayoutInflater objLayoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View snackView = objLayoutInflater.inflate(R.layout.custom_snakbar_layout, null); // custom_snac_layout is your custom xml


        manualHexcodeEdittext = (EditText) snackView.findViewById(R.id.customsnack_addcode_edittext);
        requestEdittextFocus(manualHexcodeEdittext);
        snackbar.setAction("ADD", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (manualHexcodeEdittext.getText().toString().isEmpty()) {
                    Snackbar snackbar1 = Snackbar.make(view, "No color is inputted", Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                } else {
                    Snackbar snackbar1 = Snackbar.make(view, "StatusBar color is set", Snackbar.LENGTH_SHORT);
                    statusbarTextview.setVisibility(View.VISIBLE);
                    statusbarTextview.setText(manualHexcodeEdittext.getText().toString());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = MainActivity.this.getWindow();
                        window.setStatusBarColor(Color.parseColor(manualHexcodeEdittext.getText().toString()));
                    } else {
                        Toast.makeText(MainActivity.this, "Cannot change color in this android version", Toast.LENGTH_SHORT).show();
                    }
                    snackbar1.show();
                }
            }
        });
        layout.addView(snackView, 0);
        snackbar.show();

    }

    public void enterFabHexcodeDialog(View view) {

        final EditText manualHexcodeEdittext;

        final Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_INDEFINITE);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

        // Inflate your custom view with an Edit Text
        LayoutInflater objLayoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View snackView = objLayoutInflater.inflate(R.layout.custom_snakbar_layout, null); // custom_snac_layout is your custom xml


        manualHexcodeEdittext = (EditText) snackView.findViewById(R.id.customsnack_addcode_edittext);
        requestEdittextFocus(manualHexcodeEdittext);
        snackbar.setAction("ADD", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (manualHexcodeEdittext.getText().toString().isEmpty()) {
                    Snackbar snackbar1 = Snackbar.make(view, "No color is inputted", Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                } else {
                    Snackbar snackbar1 = Snackbar.make(view, "Fab Button color is set", Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                    //making visible
                    fabTextview.setVisibility(View.VISIBLE);
                    fabTextview.setText(manualHexcodeEdittext.getText().toString());
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(manualHexcodeEdittext.getText().toString())));

                }
            }
        });
        layout.addView(snackView, 0);
        snackbar.show();
    }


    public void requestEdittextFocus(EditText editText) {

        editText.requestFocus();
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void selectToolBarColor(View view) {


    }

    public void selectStatusBarColor(View view) {

    }

    public void selectFabColor(View view) {

    }


    public void interstitialAdTimer(){

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                showAds();
            }
        }, 0, 30000);

    }

    private void showAds() {


    }
}
