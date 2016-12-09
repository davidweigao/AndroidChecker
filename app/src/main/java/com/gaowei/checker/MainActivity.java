package com.gaowei.checker;

import android.app.Activity;
import android.os.Bundle;

import com.gaowei.checker.fragments.GameFragment;

public class MainActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(findViewById(R.id.fragment_container) != null) {
            if(savedInstanceState != null) {
                return;
            }
            GameFragment issueListFragment = new GameFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, issueListFragment).commit();
        }
    }
}
