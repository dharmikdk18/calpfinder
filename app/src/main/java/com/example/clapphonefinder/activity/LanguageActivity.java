package com.example.clapphonefinder.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.app.sdkads.adsType.Interstitial_Google;
import com.example.clapphonefinder.R;
import com.example.clapphonefinder.adapter.LanguageAdapter;
import com.example.clapphonefinder.model.LanguageModel;
import com.example.clapphonefinder.databinding.ActivityLanguageBinding;
import com.example.clapphonefinder.utils.LocaleHelper;

import java.util.ArrayList;

public class LanguageActivity extends AppCompatActivity {

    ActivityLanguageBinding binding;
    ArrayList<LanguageModel> languageList;
    LanguageAdapter adapter;
    String languageCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        binding = ActivityLanguageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        languageList = new ArrayList<>();

        languageList.add(new LanguageModel(getString(R.string.english), "en", R.drawable.ic_english));
        languageList.add(new LanguageModel(getString(R.string.japanese), "ja", R.drawable.ic_japanese));
        languageList.add(new LanguageModel(getString(R.string.hindi), "hi", R.drawable.ic_hindi));
        languageList.add(new LanguageModel(getString(R.string.spanish), "es", R.drawable.ic_spanish));
        languageList.add(new LanguageModel(getString(R.string.french), "fr", R.drawable.ic_french));
        languageList.add(new LanguageModel(getString(R.string.arabic), "ar", R.drawable.ic_arabic));
        languageList.add(new LanguageModel(getString(R.string.bengali), "bn", R.drawable.ic_bengali));
        languageList.add(new LanguageModel(getString(R.string.russian), "ru", R.drawable.ic_russian));
        languageList.add(new LanguageModel(getString(R.string.italian), "it", R.drawable.ic_italian));
        languageList.add(new LanguageModel(getString(R.string.indonesia), "in", R.drawable.ic_indonesia));
        languageList.add(new LanguageModel(getString(R.string.german), "de", R.drawable.ic_german));
        languageList.add(new LanguageModel(getString(R.string.korean), "ko", R.drawable.ic_korean));

        adapter = new LanguageAdapter(this, languageList);
        binding.rcvLanguage.setAdapter(adapter);

        adapter.setOnClickListener(new LanguageAdapter.ClickListener() {
            @Override
            public void onClick(int position) {
                languageCode = languageList.get(position).getLanguageCode();
            }
        });

        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_done){
                    LocaleHelper.setLocale(LanguageActivity.this, languageCode);
                    Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finishAffinity();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        Interstitial_Google.showBackInterstitial(this, new Interstitial_Google.OnclickInter() {
            @Override
            public void clicked() {
                finish();
            }
        });
    }
}