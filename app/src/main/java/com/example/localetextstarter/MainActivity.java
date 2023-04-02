package com.example.localetextstarter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private EditText inputPrice;
    private TextView price100;

    int result = 0;
    private NumberFormat format = NumberFormat.getCurrencyInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputPrice = findViewById(R.id.input_price);
        price100 = findViewById(R.id.price_100);
        Button btnCalculate = findViewById(R.id.btnCount);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> showHelp());

        Date expDate = new Date();
        long expirationDate = expDate.getTime() + TimeUnit.DAYS.toMillis(3);
        expDate.setTime(expirationDate);

        String formDate = DateFormat.getDateInstance().format(expDate);
        TextView expDateText = findViewById(R.id.date);
        expDateText.setText(formDate);

        btnCalculate.setOnClickListener(v -> {
            try {
                String value = inputPrice.getText().toString();
                result = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                inputPrice.setError(getText(R.string.price_placeholder));
            }

            formatNumber();
            String count = format.format(result* 100L);
            price100.setText(count);
        });
    }

    private void formatNumber() {
        format.setMaximumFractionDigits(0);
        format.setCurrency(Currency.getInstance(Locale.getDefault()));
    }

    private void showHelp() {
        // Create the intent.
        Intent helpIntent = new Intent(this, HelpActivity.class);
        // Start the HelpActivity.
        startActivity(helpIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle options menu item clicks here.
        int id = item.getItemId();
        if (id == R.id.action_help) {
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_language) {
            Intent langIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(langIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}