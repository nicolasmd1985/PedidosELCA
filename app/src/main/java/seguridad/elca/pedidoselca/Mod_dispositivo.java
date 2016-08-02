package seguridad.elca.pedidoselca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import seguridad.elca.pedidoselca.android.IntentIntegrator;
import seguridad.elca.pedidoselca.android.IntentResult;

public class Mod_dispositivo extends AppCompatActivity implements View.OnClickListener {

    DBController controller = new DBController(this);
    EditText codigo,nombre,descripcion,latitud,longitud,tiemp;
    private Button scanBtn;

    String idped,code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_dispositivo);


        scanBtn = (Button)findViewById(R.id.scan_button);
        codigo = (EditText) findViewById(R.id.codigo);
        nombre = (EditText) findViewById(R.id.nombre);
        descripcion = (EditText) findViewById(R.id.descripcion);


        scanBtn.setOnClickListener(this);
        idped= getIntent().getStringExtra("idpedido");
        code=getIntent().getStringExtra("codigoscan");
        carga_datos(code);

    }


   //////////////**********************CARGAR DATOS************/////////////////


    private void carga_datos(String code) {



    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.scan_button){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }

    }



    /////////////************************OBTIENE INFO DEL SCANER*****************////////////////


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            //formatTxt.setText("FORMAT: " + scanFormat);
            codigo.setText(scanContent);
            //tiemp.setText(tiempo());
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    /////////////////****************ESTO ES PARA DEVOLVERSE*****************/////////////////////

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            Intent i = new Intent(Mod_dispositivo.this, Agregar_dispositivos.class);
            i.putExtra("idpedido", idped );
            startActivity(i);
            //return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    /**
     * Navigate to Home Screen
     * @param view
     */
    public void callHomeActivity(View view) {
        Intent objIntent = new Intent(getApplicationContext(),
                Agregar_dispositivos.class);
        objIntent.putExtra("idpedido", idped );
        startActivity(objIntent);
    }

    /**
     * Called when Cancel button is clicked
     * @param view
     */
    public void canceldisp(View view) {
        this.callHomeActivity(view);
    }



}
