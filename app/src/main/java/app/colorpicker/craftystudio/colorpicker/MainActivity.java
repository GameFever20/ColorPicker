package app.colorpicker.craftystudio.colorpicker;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static app.colorpicker.craftystudio.colorpicker.R.id.toolbar;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbarTextview, statusbarTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        statusbarTextview = (TextView) findViewById(R.id.statusbar_hexcode);
        toolbarTextview = (TextView) findViewById(R.id.toolbar_hexcode);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                //random color for toolbar
                Random rnd = new Random();
                int randomcolor_toolbar = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

                //randomcolor for statusbar
                rnd = new Random();
                int randomcolor_status = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

                //call for changing color
                randomcolor_status = manipulateColor(randomcolor_toolbar, 0.8f);
                changeColor(randomcolor_toolbar, randomcolor_status);


            }
        });
    }

    public void changeColor(int toolbarColor, int statusColor) {
        toolbar.setBackgroundColor(toolbarColor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = MainActivity.this.getWindow();
            window.setStatusBarColor(statusColor);
        }

        String statusHex = Integer.toHexString(statusColor);
        String toolbarHex = Integer.toHexString(toolbarColor);

        toolbarTextview.setVisibility(View.VISIBLE);
        statusbarTextview.setVisibility(View.VISIBLE);

        toolbarTextview.setText("#" + toolbarHex);
        statusbarTextview.setText("#" + statusHex);
        Log.d("Hex", statusHex + "-" + toolbarHex);
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
        if (id == R.id.action_settings) {
            return true;
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
}
