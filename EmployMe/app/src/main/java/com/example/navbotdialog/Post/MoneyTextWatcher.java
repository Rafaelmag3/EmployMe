package com.example.navbotdialog.Post;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MoneyTextWatcher implements TextWatcher {
    private final EditText editText;

    public MoneyTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        editText.removeTextChangedListener(this);

        String originalText = editable.toString();

        // Remove any existing commas and dots
        String cleanText = originalText.replace(",", "").replace(".", "");

        // Add commas and dots in the correct places
        if (!cleanText.isEmpty()) {
            double value = Double.parseDouble(cleanText);
            String formattedText = String.format("%,.2f", value);
            editText.setText(formattedText);
            editText.setSelection(formattedText.length());
        }

        editText.addTextChangedListener(this);
    }
}
