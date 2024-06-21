package com.example.cliker;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    private int cliker = 0; // количество кликов
    private int rPoints = 0; // бонусные очки
    private double clickRate = 1; // скорость клика
    private int energy = 1000; // энергия
    private String clikerImg = "akva1"; // изображение для кликера по умолчанию
    private String selectedBackground = "bk"; // фон по умолчанию
    private SharedPreferences sharedPreferences; // для сохранения данных

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // устанавливаем макет activity_main

        // Инициализация SharedPreferences для хранения данных
        sharedPreferences = getSharedPreferences("CilkerPreferences", MODE_PRIVATE);

        // Восстановление сохраненных значений из SharedPreferences
        cliker = sharedPreferences.getInt("cliker", 0);
        rPoints = sharedPreferences.getInt("rPoints", 0);
        clickRate = Double.longBitsToDouble(sharedPreferences.getLong("clickRate", Double.doubleToLongBits(1)));
        energy = sharedPreferences.getInt("energy", 1000);
        selectedBackground = sharedPreferences.getString("backgroundImagePath", "bk"); // фон по умолчанию
        clikerImg = sharedPreferences.getString("clikerImgPath", "akva1"); // изображение по умолчанию

        // Обновление пользовательского интерфейса
        updateUI();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Сохранение текущих значений в SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("cliker", cliker);
        editor.putInt("rPoints", rPoints);
        editor.putLong("clickRate", Double.doubleToRawLongBits(clickRate));
        editor.putInt("energy", energy);
        editor.putString("backgroundImagePath", selectedBackground);
        editor.putString("clikerImgPath", clikerImg);
        editor.apply();
    }

    // Метод обработки клика по кликеру
    public void onClick(View view) {
        cliker += (int) clickRate; // увеличиваем количество кликов с учетом скорости клика
        updateUI(); // обновляем интерфейс
    }

    // Метод обновления пользовательского интерфейса
    private void updateUI() {
        // Обновляем текст количества кликов
        TextView clikerTextView = findViewById(R.id.cliker_text);
        clikerTextView.setText(String.valueOf(cliker));

        // Устанавливаем фон для ConstraintLayout
        ConstraintLayout constraintLayout = findViewById(R.id.main);
        int backgroundResId = getResources().getIdentifier(selectedBackground, "drawable", getPackageName());
        constraintLayout.setBackgroundResource(backgroundResId);

        // Устанавливаем изображение для ImageView
        ImageView clikerImage = findViewById(R.id.cliker_img);
        int imageResId = getResources().getIdentifier(clikerImg, "drawable", getPackageName());
        clikerImage.setImageResource(imageResId);
    }

    // Метод для перехода в инвентарь
    public void goToInventory(View view) {
        setContentView(R.layout.activity_inventory); // устанавливаем макет activity_inventory
    }

    // Метод для возвращения на главный экран
    public void goToMain(View view) {
        setContentView(R.layout.activity_main); // возвращаемся к макету activity_main
        updateUI(); // обновляем интерфейс
    }
}