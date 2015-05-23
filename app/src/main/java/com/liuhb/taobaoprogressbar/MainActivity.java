package com.liuhb.taobaoprogressbar;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.liuhb.taobaoprogressbar.com.liuhb.taobaoprogressbar.view.CustomProgressBar;


public class MainActivity extends ActionBarActivity {

    private CustomProgressBar mProgressBar ;
    private CustomProgressBar mProgressBar2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (CustomProgressBar) findViewById(R.id.cpb_progresbar);
        mProgressBar.setProgressDesc("剩余");
        mProgressBar.setMaxProgress(50);
        mProgressBar.setProgressColor(Color.parseColor("#F6CB82"));
        mProgressBar.setCurProgress(40);


        mProgressBar2 = (CustomProgressBar) findViewById(R.id.cpb_progresbar2);
        mProgressBar2.setProgressDesc("剩余");
        mProgressBar2.setMaxProgress(100);
        mProgressBar2.setProgressColor(Color.parseColor("#79aa6b"));
        mProgressBar2.setCurProgress(80,2000);
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
}
