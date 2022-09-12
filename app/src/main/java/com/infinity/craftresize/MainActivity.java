package com.infinity.craftresize;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String locale_language = Locale.getDefault().getLanguage();

        if (locale_language.equals("zh")) {
            setContentView(R.layout.activity_chinese);
        } else {
            setContentView(R.layout.activity_english);
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    1000);
        }

        EditText fileName = findViewById(R.id.file_name);
        EditText resize = findViewById(R.id.resize);
        Button button = findViewById(R.id.result_btn);
        Button helpButton = findViewById(R.id.helpButton);
        TextView byJason = findViewById(R.id.textView);
        RadioButton toWidth = findViewById(R.id.toWidth);


        File filePath = getExternalFilesDir("");
        CraftResize cR = new CraftResize();

        button.setOnClickListener(click -> {

            if (cR.craftResizeTool(filePath + "/" + fileName.getText(), String.valueOf(resize.getText()), toWidth.isChecked())) {
                Log.d("T", String.valueOf(toWidth.isChecked()));
                if (locale_language.equals("zh")) {
                    Toast.makeText(MainActivity.this, "文件名或缩放比例有误!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "File name error or number error!", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (locale_language.equals("zh")) {
                    Toast.makeText(MainActivity.this, "缩放成功", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "已保存在APP包下files文件夹中", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Resize success.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "File save to package/files/", Toast.LENGTH_SHORT).show();
                }
            }

        });

        helpButton.setOnClickListener(click -> {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse("https://www.simplerockets.com/Mods/View/177010/Resize-Tool-of-Android");
            intent.setData(content_url);
            startActivity(intent);
        });

        byJason.setOnClickListener(click -> {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse("https://www.simplerockets.com/u/JasonCaesar007");
            intent.setData(content_url);
            startActivity(intent);
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //同意申请权限
                Toast.makeText(MainActivity.this, "Permission success.\r\n权限获取成功", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "Please move the Craft file to package/files/\r\n请将作品移动到本APP包下的files文件夹使用", Toast.LENGTH_SHORT).show();
            } else {
                // 用户拒绝申请权限
                Toast.makeText(MainActivity.this, "Please Permission internal storage.\r\n请授权应用使用内部存储", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}