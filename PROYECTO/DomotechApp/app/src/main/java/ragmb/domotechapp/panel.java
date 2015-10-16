package ragmb.domotechapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class panel extends AppCompatActivity {
    Button btn1;
    Button toggleButton1;
    Button toggleButton2;
    Button toggleButton3;
    Button toggleButton4;
    Button toggleButton5;

    public static int STATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);

        btn1 = (Button)findViewById(R.id.btnexit);
        toggleButton1 = (Button)findViewById(R.id.toggleButton);
        toggleButton2 = (Button)findViewById(R.id.toggleButton2);
        toggleButton3 = (Button)findViewById(R.id.toggleButton3);
        toggleButton4 = (Button)findViewById(R.id.toggleButton4);
        toggleButton5 = (Button)findViewById(R.id.toggleButton5);

        btn1 .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();

            }
        });

        toggleButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p = String.valueOf(toggleButton1.getText());
                Cliente_HTTP cliente = new Cliente_HTTP(panel.this);
                cliente.execute(p);
            }
        });

    }

}
