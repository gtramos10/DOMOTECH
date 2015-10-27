package ragmb.domotechapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import ragmb.domotechapp.objects.Cliente_HTTP;
import ragmb.domotechapp.objects.Device;

public class panel extends AppCompatActivity {
    Button btn1;
    //Button toggleButton1;
    ToggleButton toggleButton1;
    ToggleButton toggleButton2;
    ToggleButton toggleButton3;
    ToggleButton toggleButton4;
    ToggleButton toggleButton5;
    Button btnActualizar;

    public static int STATE = 1;
    private UpdateStatus mUpdateStatus = null;
    private final String TAG = "**APP: panel**";
    private static ArrayList<Device> aDevices = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);

        btn1 = (Button)findViewById(R.id.btnexit);
        toggleButton1 = (ToggleButton)findViewById(R.id.toggleButton);
        toggleButton2 = (ToggleButton)findViewById(R.id.toggleButton2);
        toggleButton3 = (ToggleButton)findViewById(R.id.toggleButton3);
        toggleButton4 = (ToggleButton)findViewById(R.id.toggleButton4);
        toggleButton5 = (ToggleButton)findViewById(R.id.toggleButton5);
        btnActualizar = (Button)findViewById(R.id.btn_actualizar);

        mUpdateStatus = new UpdateStatus();
        mUpdateStatus.execute((Void) null);

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
                Cliente_HTTP cliente = new Cliente_HTTP(panel.this, "1", p);
                cliente.execute("1", p);
            }
        });

        toggleButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p = String.valueOf(toggleButton2.getText());
                Cliente_HTTP cliente = new Cliente_HTTP(panel.this, "2", p);
                cliente.execute("2", p);
            }
        });

        toggleButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p = String.valueOf(toggleButton3.getText());
                Cliente_HTTP cliente = new Cliente_HTTP(panel.this, "3", p);
                cliente.execute("3", p);
            }
        });

        toggleButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p = String.valueOf(toggleButton4.getText());
                Cliente_HTTP cliente = new Cliente_HTTP(panel.this, "4", p);
                cliente.execute("4", p);
            }
        });

        toggleButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p = String.valueOf(toggleButton5.getText());
                Cliente_HTTP cliente = new Cliente_HTTP(panel.this, "5", p);
                cliente.execute("5", p);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUpdateStatus = new UpdateStatus();
                mUpdateStatus.execute((Void) null);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        mUpdateStatus = new UpdateStatus();
        mUpdateStatus.execute((Void) null);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UpdateStatus extends AsyncTask<Void, Void, String> {

        UpdateStatus() {
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            String message = "";

            //Register register = new Register(mUser, mEmail, mPassword);

            try {
                // Simulate network access.
                // Thread.sleep(2000);
                message = sendGet("http://android.net78.net/domotic_api/ctrl_action.php", "get_status");
            } catch (Exception e) {
                return message;
            }

            // TODO: register the new account here.
            return message;
        }

        @Override
        protected void onPostExecute(final String success) {

            if (success.equalsIgnoreCase("TRUE")) {
                setToggleButtonStatus();
                Toast.makeText(panel.this, "Updated Succesfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(panel.this, "Error: Coudn't Update devices state", Toast.LENGTH_SHORT).show();
            }
        }

        public String sendGet(String _url, String action) throws URISyntaxException, UnsupportedEncodingException {

            String mensaje = "FALSE";
            String idValue = "";
            String stateValue = "";

            try {
                URL url = new URL(_url + "?action=" + action.replace(" ", ""));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                InputStream stream = conn.getInputStream();

                System.out.println("Response Code: " + conn.getResponseCode());
                //InputStream in = new BufferedInputStream(conn.getInputStream());
                //String xmlString = org.apache.commons.io.IOUtils.toString(new BufferedInputStream(stream), "UTF-8");

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = factory.newDocumentBuilder();
                //InputSource inStream = new InputSource();
                //inStream.setCharacterStream(new StringReader(xmlString));
                //Document doc = db.parse(inStream);



                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_DOCDECL, true);
                parser.setInput(stream, null);
                parser.nextTag();
                aDevices = parseXML(parser);

                //NodeList nl = doc.getElementsByTagName("estado");
                //org.w3c.dom.Element nameElement = (org.w3c.dom.Element) nl.item(0);
                mensaje = "TRUE";//nameElement.getFirstChild().getNodeValue().trim();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                return mensaje;
            }
        }

        private ArrayList parseXML(XmlPullParser parser) throws XmlPullParserException,IOException {
            ArrayList<Device> devices = null;
            devices = new ArrayList();
            int eventType = parser.getEventType();
            Device currentDevice = null;

            while (eventType != XmlPullParser.END_DOCUMENT){
                String name = null;
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        devices = new ArrayList();
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("device")) {
                            currentDevice = new Device();
                        } else if (currentDevice != null){
                            if (name.equalsIgnoreCase("id")){
                                currentDevice.setId(parser.nextText());
                            } else if (name.equalsIgnoreCase("status")) {
                                currentDevice.setStatus(parser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("device") && currentDevice != null){
                            devices.add(currentDevice);
                        }
                }
                eventType = parser.next();
            }
            return devices;
        }

        private void setToggleButtonStatus(){
            if(aDevices!=null && !aDevices.isEmpty()){

                Boolean status = false;
                int i =0;
                Iterator<Device> it = aDevices.iterator();
                while(it.hasNext())
                {
                    Device currentDevice  = it.next();
                    status = false;
                    if(currentDevice.getStatus().equalsIgnoreCase("ON")){
                        status = true;
                    }
                    switch (i) {
                        case 0: toggleButton1.setChecked(status);
                            break;
                        case 1: toggleButton2.setChecked(status);
                            break;
                        case 2: toggleButton3.setChecked(status);
                            break;
                        case 3: toggleButton4.setChecked(status);
                            break;
                        case 4: toggleButton5.setChecked(status);
                            break;
                    }
                    i++;
                }
            }
        }
    }
}
