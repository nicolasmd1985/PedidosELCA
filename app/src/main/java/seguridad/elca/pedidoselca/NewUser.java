package seguridad.elca.pedidoselca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class NewUser extends Activity {
    EditText descripcion,cliente,calle,numero,ciudad,provincia;
    DBController controller = new DBController(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_user);
        descripcion = (EditText) findViewById(R.id.descripcion);
        cliente = (EditText) findViewById(R.id.cliente);
        calle = (EditText) findViewById(R.id.calle);
        numero = (EditText) findViewById(R.id.numero);
        ciudad = (EditText) findViewById(R.id.ciudad);
        provincia = (EditText) findViewById(R.id.provincia);
    }

    /**
     * Called when Save button is clicked
     * @param view
     */
    public void addNewUser(View view) {
        HashMap<String, String> queryValues = new HashMap<String, String>();
        queryValues.put("descripcion", descripcion.getText().toString());
        queryValues.put("cliente", cliente.getText().toString());
        queryValues.put("calle", calle.getText().toString());
        queryValues.put("numero", calle.getText().toString());
        queryValues.put("ciudad", ciudad.getText().toString());
        queryValues.put("provincia", provincia.getText().toString());



        if (cliente.getText().toString() != null
                && cliente.getText().toString().trim().length() != 0) {
            if (descripcion.getText().toString() != null
                    && descripcion.getText().toString().trim().length() != 0) {
                if (calle.getText().toString() != null
                        && calle.getText().toString().trim().length() != 0) {


                    controller.insertUser(queryValues);
                    this.callHomeActivity(view);






            }




            }else {
                Toast.makeText(getApplicationContext(), "Instroduzaca descripcion",
                        Toast.LENGTH_LONG).show();}
        } else {
            Toast.makeText(getApplicationContext(), "Instroduzca cliente",
                    Toast.LENGTH_LONG).show();
        }









    }




    /**
     * Navigate to Home Screen
     * @param view
     */
    public void callHomeActivity(View view) {
        Intent objIntent = new Intent(getApplicationContext(),
                Pedidos.class);
        startActivity(objIntent);
    }

    /**
     * Called when Cancel button is clicked
     * @param view
     */
    public void cancelAddUser(View view) {
        this.callHomeActivity(view);
    }
}