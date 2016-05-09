package com.softblue.app.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.softblue.app.R;
import com.softblue.app.adapters.ChartPageRecyclerAdapter;
import com.softblue.app.listeners.RecyclerItemClickListener;
import com.softblue.app.model.ChartPage;
import com.softblue.app.utils.StringUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    // Fragment replace delay for better user experience
    private static final int FRAGMENT_ADD_DELAY = 300;
    private ArrayList<ChartPage> mChartPageList;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);

        // Setup layout manager for mChartPageList and column count
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        // Control orientation of the mChartPageList
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        // Attach layout manager
        mRecyclerView.setLayoutManager(layoutManager);

        mFragmentManager = getSupportFragmentManager();
        mChartPageList = new ArrayList<>();
        ChartPage mDymPage = new ChartPage(getString(R.string.dym),
                getString(R.string.dym_description));
        mDymPage.initFragment();
        ChartPage mToDrugie = new ChartPage(getString(R.string.option2),
                getString(R.string.option2_desc));
        mToDrugie.initFragment();
        mChartPageList.add(mDymPage);
        mChartPageList.add(mToDrugie);

        // Setting adapter
        final ChartPageRecyclerAdapter adapter = new ChartPageRecyclerAdapter(mChartPageList);
        mRecyclerView.setAdapter(adapter);

        // Listen to the item touching
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, final int position) {
                        Handler handler = new Handler(MainActivity.this.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                openChartPage(mChartPageList.get(position));
                            }
                        }, FRAGMENT_ADD_DELAY);
                    }
                }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    private void openChartPage(ChartPage page) {
        mRecyclerView.setVisibility(View.GONE);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getToolbarTitle(page.getLabel()));
        }
        mFragmentManager.beginTransaction().add(R.id.fragment_container,
                page.getFragment()).addToBackStack(page.getLabel()).commit();
    }

    private void closeChartPage() {
        mFragmentManager.popBackStackImmediate();
        mRecyclerView.setVisibility(View.VISIBLE);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.app_name);
        }
    }

    private String getToolbarTitle(String label) {
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.app_name))
                .append(StringUtils.DASH)
                .append(getString(R.string.measurement))
                .append(StringUtils.SPACE)
                .append(label);
        return sb.toString();
    }

    @Override
    public void onBackPressed() {
        if (mFragmentManager.getBackStackEntryCount() > 0) {
            closeChartPage();
        } else {
            super.onBackPressed();
        }
    }
}
