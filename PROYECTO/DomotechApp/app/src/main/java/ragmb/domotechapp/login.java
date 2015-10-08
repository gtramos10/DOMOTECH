package ragmb.domotechapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

public class login extends AppCompatActivity {

    Button btn1;
    EditText txt1;
    EditText txt2;
    EditText txt3;
    EditText txt4;
    EditText txtpi;
    String pin;
    String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn1 = (Button)findViewById(R.id.btnLog);
        txt1= (EditText)findViewById(R.id.txtip1);
        txt2= (EditText)findViewById(R.id.txtip2);
        txt3= (EditText)findViewById(R.id.txtip3);
        txt4= (EditText)findViewById(R.id.txtip4);
        txtpi= (EditText)findViewById(R.id.txtpin);
        btn1 .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ip = txt1.getText().toString()+txt2.getText().toString()+txt3.getText().toString()+txt4.getText().toString();
                pin = txtpi.getText().toString();
                if (ip.equals("19816211") && pin.equals("12345678")) {
                    finish();
                    Intent intent = new Intent(login.this, panel.class);
                    startActivity(intent);
                } else {
                    alerta();
                }
            }
        });
    }
   public void alerta() {
        //se prepara la alerta creando nueva instancia
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        //seleccionamos la cadena a mostrar
       dialogBuilder.setMessage("IP/PIN INCORRECTOS");
        //elegimo un titulo y configuramos para que se pueda quitar
        dialogBuilder.setCancelable(true).setTitle("ERROR");
        //mostramos el dialogBuilder
        dialogBuilder.create().show();
    }
}
