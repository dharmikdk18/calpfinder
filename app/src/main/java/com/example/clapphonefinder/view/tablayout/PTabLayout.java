package com.example.clapphonefinder.view.tablayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.clapphonefinder.R;
import com.google.android.material.tabs.TabLayout;

public class PTabLayout extends TabLayout implements TabLayout.OnTabSelectedListener {
    int selectedBg;
    int unSelectedBg;
    int selectedTabTextColor;
    int unselectedTabTextColor;

    public PTabLayout(@NonNull Context context) {
        super(context);
    }

    public PTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        readCustomAttributes(context, attrs);
    }

    public PTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readCustomAttributes(context, attrs);
    }

    private void readCustomAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PTabLayout);

        selectedBg = a.getResourceId(R.styleable.PTabLayout_selected_tab_bg, R.drawable.selected_tab);
        unSelectedBg = a.getResourceId(R.styleable.PTabLayout_unselected_tab_bg, R.drawable.unselected_tab);
        selectedTabTextColor = a.getResourceId(R.styleable.PTabLayout_selected_tab_text_color, R.color.white);
        unselectedTabTextColor = a.getResourceId(R.styleable.PTabLayout_unselected_tab_text_color, R.color.black);
        a.recycle();

    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    @Override
    public void setContentDescription(CharSequence contentDescription) {
        super.setContentDescription(contentDescription);
    }

    @Override
    public void setSelectedTabIndicatorColor(int color) {
        super.setSelectedTabIndicatorColor(color);
    }

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);
    }

    @Override
    public void addTab(@NonNull Tab tab, int position, boolean setSelected) {
        super.addTab(tab, position, setSelected);
        customizeTab(tab);
        addOnTabSelectedListener(this);
    }

    private void customizeTab(Tab tab) {
        @SuppressLint("InflateParams") View customView = LayoutInflater.from(getContext()).inflate(R.layout.custom_tab_layout, null);
        TextView text = customView.findViewById(R.id.tv_tab_title);
        text.setText(tab.getText());
        text.setTextColor(getResources().getColor(unselectedTabTextColor));
        if (tab.getPosition() == 0) {
            text.setBackgroundResource(selectedBg);
            text.setTextColor(getResources().getColor(selectedTabTextColor));
        }
        tab.setCustomView(customView);
    }

    @Override
    public void onTabSelected(Tab tab) {
        View customView = tab.getCustomView();
        if (customView != null) {
            TextView tabTitle = customView.findViewById(R.id.tv_tab_title);
            tabTitle.setBackgroundResource(selectedBg);
            tabTitle.setTextColor(getResources().getColor(selectedTabTextColor));
        }
    }

    @Override
    public void onTabUnselected(Tab tab) {
        View customView = tab.getCustomView();
        if (customView != null) {
            TextView tabTitle = customView.findViewById(R.id.tv_tab_title);
            tabTitle.setBackgroundResource(unSelectedBg);
            tabTitle.setTextColor(getResources().getColor(unselectedTabTextColor));
        }
    }

    @Override
    public void onTabReselected(Tab tab) {

    }
}
