package com.example.clapphonefinder.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.clapphonefinder.R;
import com.example.clapphonefinder.adapter.LanguageModel;
import com.example.clapphonefinder.databinding.ActivityLanguageBinding;

import java.util.ArrayList;

public class LanguageActivity extends AppCompatActivity {

    ActivityLanguageBinding binding;
    ArrayList<LanguageModel> languageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLanguageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        languageList = new ArrayList<>();

        languageList.add(new LanguageModel("English", "en", R.drawable.ic_english));
        languageList.add(new LanguageModel("Japanese", "ja", R.drawable.ic_japanese));
        languageList.add(new LanguageModel("Hindi", "hi", R.drawable.ic_hindi));
        languageList.add(new LanguageModel("Spanish", "es", R.drawable.ic_spanish));
        languageList.add(new LanguageModel("French", "fr", R.drawable.ic_french));
        languageList.add(new LanguageModel("Arabic", "ar", R.drawable.ic_arabic));
        languageList.add(new LanguageModel("Bengali", "bn", R.drawable.ic_bengali));
        languageList.add(new LanguageModel("Russian", "ru", R.drawable.ic_russian));
        languageList.add(new LanguageModel("Italian", "it", R.drawable.ic_italian));
        languageList.add(new LanguageModel("Indonesia", "in", R.drawable.ic_indonesia));
        languageList.add(new LanguageModel("German", "de", R.drawable.ic_german));
        languageList.add(new LanguageModel("Korean", "ko", R.drawable.ic_korean));



    }
}