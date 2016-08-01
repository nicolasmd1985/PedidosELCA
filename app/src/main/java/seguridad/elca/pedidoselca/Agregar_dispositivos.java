package seguridad.elca.pedidoselca;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Agregar_dispositivos extends ActionBarActivity {


    //DB Class to perform DB related operations
    DBController controller = new DBController(this);
    //Progress Dialog Object
    ProgressDialog prgDialog;
    String idped;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_dispositivos);
        idped= getIntent().getStringExtra("idpedido");



        cargadisp();
    }



    ////////////////*****************CARGA BASE DE DATOS SQLITE************************
    public void cargadisp()
    {


        //inicia lista
        ArrayList<HashMap<String, String>> dipslist =  controller.getdisp();

        if(dipslist.size()!=0){
            //Set the User Array list in ListView
            ListAdapter adapter = new SimpleAdapter( Agregar_dispositivos.this,dipslist, R.layout.view_disp, new String[] { "codigo","nombre","descripcion"}, new int[] {R.id.cod_disp, R.id.nomdisp, R.id.descdisp});
            ListView myList=(ListView)findViewById(android.R.id.list);
            myList.setAdapter(adapter);
            //Display Sync status of SQLite DB
            // Toast.makeText(getApplicationContext(), controller.getSyncStatus(), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "No tiene dispositivos instaldos", Toast.LENGTH_LONG).show();}
        /*
        //Initialize Progress Dialog properties
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Synching SQLite Data with Remote MySQL DB. Please wait...");
        prgDialog.setCancelable(false);
*/

    }



    ////////////////////******************AGREGA PEDIDO******************//////////////////

    //Add User method getting called on clicking (+) button
    public void adddisp(View view) {
        Intent objIntent = new Intent(getApplicationContext(), Scaner_dispositivo.class);
        objIntent.putExtra("idpedido", idped );
        startActivity(objIntent);
    }


    //****************ESTO ES PARA DEVOLVERSE*****************

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            Intent i = new Intent(Agregar_dispositivos.this, detalles_pedido.class);
             i.putExtra("idpedido", idped );
            startActivity(i);
            //return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}
