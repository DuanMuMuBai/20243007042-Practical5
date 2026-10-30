package com.example.practicalfive;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MUNICIPALITY = "com.example.practicalfive.extra.MUNICIPALITY";

    private EditText municipalityInput;
    private boolean navigating;

    @Override
    protected void onResume() {
        super.onResume();
        navigating = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        View root = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars()
                    | WindowInsetsCompat.Type.ime());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        municipalityInput = findViewById(R.id.municipalityInput);
        municipalityInput.setOnEditorActionListener((v, actionId, event) -> {
            boolean goPressed = event != null
                    ? event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                    : actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_DONE;
            if (goPressed) {
                openMunicipality();
                return true;
            }
            return false;
        });
    }

    private void openMunicipality() {
        if (navigating) {
            return;
        }
        municipalityInput.setError(null);
        String input = municipalityInput.getText().toString();
        if (input.trim().isEmpty()) {
            showError(getString(R.string.error_empty));
            return;
        }
        String name = TextProcessor.processMunicipality(input);
        if (name == null) {
            showError(getString(R.string.error_invalid));
            return;
        }
        navigating = true;
        Intent intent = new Intent(this, Municipality.class);
        intent.putExtra(EXTRA_MUNICIPALITY, name);
        hideKeyboard();
        startActivity(intent);
    }

    private void hideKeyboard() {
        InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.hideSoftInputFromWindow(municipalityInput.getWindowToken(), 0);
        }
    }

    private void showError(String message) {
        municipalityInput.setError(message);
    }
}
