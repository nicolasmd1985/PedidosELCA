package seguridad.elca.pedidoselca;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import seguridad.elca.pedidoselca.android.IntentIntegrator;
import seguridad.elca.pedidoselca.android.IntentResult;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Scaner_dispositivo extends AppCompatActivity implements OnClickListener {

    DBController controller = new DBController(this);

    TextView mensaje1;

    EditText codigo,nombre,descripcion,latitud,longitud,tiemp;
    private Button scanBtn;
    private TextView formatTxt, contentTxt;
    String idped;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scaner_dispositivo);


        mensaje1 = (TextView) findViewById(R.id.mensaje_id);

        scanBtn = (Button)findViewById(R.id.scan_button);
       // formatTxt = (TextView)findViewById(R.id.scan_format);
       // contentTxt = (TextView)findViewById(R.id.scan_content);


        codigo = (EditText) findViewById(R.id.codigo);
        nombre = (EditText) findViewById(R.id.nombre);
        descripcion = (EditText) findViewById(R.id.descripcion);
        latitud = (EditText) findViewById(R.id.latitud);
        longitud = (EditText) findViewById(R.id.longitud);
        tiemp = (EditText) findViewById(R.id.tiempo);

        scanBtn.setOnClickListener(this);
        idped= getIntent().getStringExtra("idpedido");
        //System.out.println




        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.Scaner_dispositivo(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
        //      (LocationListener) Local);

        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                (LocationListener) Local);

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
            tiemp.setText(tiempo());
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
            Intent i = new Intent(Scaner_dispositivo.this, Agregar_dispositivos.class);
            i.putExtra("idpedido", idped );
            startActivity(i);
            //return true;
        }
        return super.onKeyDown(keyCode, event);
    }



//////////////////////TOMA UBICACION GPS/////////////////////

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
              //      mensaje2.setText("Mi direccion es: \n"
                //            + DirCalle.getAddressLine(0));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


//////////////*********Aqui empieza la Clase Localizacion**************/

    public class Localizacion implements LocationListener {
        Scaner_dispositivo Scaner_dispositivo;

        public Scaner_dispositivo getMainActivity() {
            return Scaner_dispositivo;
        }

        public void Scaner_dispositivo(Scaner_dispositivo Scaner_dispositivo) {
            this.Scaner_dispositivo = Scaner_dispositivo;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion
            String latituds,logintuds;
            latituds = ""+loc.getLatitude();
            logintuds = ""+loc.getLongitude();
            //loc.getLongitude();
           // String Text = "Mi ubicacion actual es: " + "\n Lat = "
             //       + loc.getLatitude() + "\n Long = " + loc.getLongitude();

            latitud.setText(latituds);
            longitud.setText(logintuds);

           // mensaje1.setText(Text);
            //this.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
          //  mensaje1.setText("GPS Desactivado");
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
           // mensaje1.setText("GPS Activado");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // Este metodo se ejecuta cada vez que se detecta un cambio en el
            // status del proveedor de localizacion (GPS)
            // Los diferentes Status son:
            // OUT_OF_SERVICE -> Si el proveedor esta fuera de servicio
            // TEMPORARILY_UNAVAILABLE -> Temporalmente no disponible pero se
            // espera que este disponible en breve
            // AVAILABLE -> Disponible
        }


    }




    ////////////////*********************CLICK EN EL BOTON*************////////////



    public void adddip(View view) {


        HashMap<String, String> queryValues = new HashMap<String, String>();

        queryValues.put("codigo", codigo.getText().toString());
        queryValues.put("nombre", nombre.getText().toString());
        queryValues.put("descripcion", descripcion.getText().toString());
        queryValues.put("latitud", latitud.getText().toString());
        queryValues.put("longitud", longitud.getText().toString());
        queryValues.put("tiempo", tiempo());
        queryValues.put("idpedido", idped);
        //System.out.println(tiempo());

        controller.inserdips(queryValues);

        this.callHomeActivity(view);

    }


    public String tiempo()
    {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        Date date = cal.getTime();
               //long dia = date.getTime();
        int year = date.getYear()-100;
        System.out.println (year);
        //System.out.println(""+date.getHours()+":"+date.getMinutes()+" "+date.getDay()+"/"+date.getMonth()+"/"+date.getYear());
        String time = "" + date.getHours() + ":" + date.getMinutes() + " " + date.getDay() + "/" + date.getMonth() + "/" + year;
        return time;
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
