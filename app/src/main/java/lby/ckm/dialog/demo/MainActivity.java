package lby.ckm.dialog.demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import lby.ckm.dialog.XDialog;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.et_show).setOnClickListener(v -> {
            XDialog.Builder.createBuilder(this)
                    .setContentView(R.layout.dialog_content)
                    .fullWidth()
                    .formBottom()
                    .setText(R.id.btn_test, "Dialog测试")
                    .show();
            XDialog dialog = XDialog.Builder.createBuilder(this).create();

        });
    }
}