package ragmb.domotechapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class intro extends AppCompatActivity {
    Button btn1;
    Button btn2;
    Button btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        btn1 = (Button)findViewById(R.id.btnLogin);
        btn2 = (Button)findViewById(R.id.btnSalir);
        btn3 = (Button)findViewById(R.id.btnCon);

        btn1 .setOnClickListener(new View.OnClickListener() {
            @Override    public void onClick(View v) {
                Intent intent = new Intent(intro.this, login.class);
                startActivity(intent);    }});


        btn2 .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn3 .setOnClickListener(new View.OnClickListener() {
            @Override    public void onClick(View v) {
                Intent intent = new Intent(intro.this, contactenos.class);
                startActivity(intent);    }});
    }
    }





