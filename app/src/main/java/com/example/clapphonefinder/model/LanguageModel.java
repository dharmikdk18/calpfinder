package com.example.clapphonefinder.model;

public class LanguageModel {

    String language;
    String languageCode;
    int languageIcon;

    public LanguageModel(String language, String languageCode, int languageIcon) {
        this.language = language;
        this.languageCode = languageCode;
        this.languageIcon = languageIcon;
    }

    public LanguageModel() {
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public int getLanguageIcon() {
        return languageIcon;
    }

    public void setLanguageIcon(int languageIcon) {
        this.languageIcon = languageIcon;
    }
}
