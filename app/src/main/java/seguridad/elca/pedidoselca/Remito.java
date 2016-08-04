package seguridad.elca.pedidoselca;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import android.widget.AdapterView;

public class Remito extends ActionBarActivity {

    String idped;

    private DrawingView drawView;





    DBController controller = new DBController(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remito);
        idped = getIntent().getStringExtra("idpedido");

        cargadisp(idped);

        drawView = (DrawingView)findViewById(R.id.drawing);



    }


    //****************ESTO ES PARA DEVOLVERSE*****************

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            Intent i = new Intent(Remito.this, Agregar_dispositivos.class);
            i.putExtra("idpedido", idped );
            startActivity(i);
            //return true;
        }
        return super.onKeyDown(keyCode, event);
    }




    ////////////////*****************CARGA BASE DE DATOS SQLITE************************
    public void cargadisp(String idped)
    {


        //inicia lista
        ArrayList<HashMap<String, String>> dipslist =  controller.getdisp(idped);

        if(dipslist.size()!=0){
            //Set the User Array list in ListView
            //ListAdapter adapter = new SimpleAdapter( Remito.this,dipslist, R.layout.view_remito, new String[] {"nombre"}, new int[] {R.id.nomdisp});

            ListAdapter adapter = new SimpleAdapter( Remito.this,dipslist, R.layout.view_remito, new String[] { "nombre"}, new int[] {R.id.nomdipo});
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


   public void savere(View view)
   {
       System.out.println("hola");
       AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
       saveDialog.setTitle("Guardar Remito");
       saveDialog.setMessage("Desea guardar el remito?");
       saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
           public void onClick(DialogInterface dialog, int which){
               //save drawing
               drawView.setDrawingCacheEnabled(true);
               String imgSaved = MediaStore.Images.Media.insertImage(
                       getContentResolver(), drawView.getDrawingCache(),
                       UUID.randomUUID().toString()+".png", "drawing");
               if(imgSaved!=null){
                   Toast savedToast = Toast.makeText(getApplicationContext(),
                           "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
                   savedToast.show();
               }
               else{
                   Toast unsavedToast = Toast.makeText(getApplicationContext(),
                           "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
                   unsavedToast.show();
               }
               drawView.destroyDrawingCache();

           }
       });
       saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
           public void onClick(DialogInterface dialog, int which){
               dialog.cancel();
           }
       });
       saveDialog.show();
   }
}
