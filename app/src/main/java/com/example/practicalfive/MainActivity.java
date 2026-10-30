package com.example.practicalfive;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MUNICIPALITY = "com.example.practicalfive.extra.MUNICIPALITY";

    private EditText municipalityInput;

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
            boolean goPressed = actionId == EditorInfo.IME_ACTION_GO
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || (event != null
                    && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                    && event.getAction() == KeyEvent.ACTION_DOWN);
            if (goPressed) {
                openMunicipality();
                return true;
            }
            return false;
        });
    }

    private void openMunicipality() {
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
        Intent intent = new Intent(this, Municipality.class);
        intent.putExtra(EXTRA_MUNICIPALITY, name);
        startActivity(intent);
    }

    private void showError(String message) {
        municipalityInput.setError(message);
    }
}
