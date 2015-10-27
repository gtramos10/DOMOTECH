package ragmb.domotechapp.objects;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import ragmb.domotechapp.R;
import ragmb.domotechapp.panel;

public class Cliente_HTTP extends AsyncTask {
    private Context mContext;
    private String mId;
    private String mStatus;
    private final String TAG = "**APP: Cliente HTTP**";
    ProgressDialog mDialog;
    AlertDialog.Builder alerta;

    public Cliente_HTTP(Context m){
        this.mContext=m;
        alerta = new AlertDialog.Builder(mContext);
    }

    public Cliente_HTTP(Context m, String id, String status){
        this.mContext=m;
        this.mId=id;
        this.mStatus=status;
        alerta = new AlertDialog.Builder(mContext);
    }

    @Override
    protected void onPreExecute() {
        Log.d(TAG, "Inside onPreExecute");
    }

    @Override
    protected void onProgressUpdate(Object... params) {
        super.onProgressUpdate(params[0]);

        alerta.setTitle("Resultado");
        //alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
        //    public void onClick(DialogInterface dialog, int which) {
        //    }
        //});
        //alerta.setMessage(params[0].toString());
        //alerta.show();
        if(panel.STATE == 1) {
            Toast.makeText(mContext, "Estatus Updated", Toast.LENGTH_SHORT).show();

            Vibrator v = (Vibrator) mContext.getSystemService(mContext.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(500);
        }else {
            Intent notificationIntent = new Intent(mContext, panel.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);

            Notification notification = new NotificationCompat.Builder(mContext)
                    .setCategory(Notification.CATEGORY_PROMO)
                    .setContentTitle("Ingreso")
                    .setContentText( "Estatus Updated")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setVisibility(1)
                    .addAction(android.R.drawable.ic_menu_view, "View details", contentIntent)
                    .setContentIntent(contentIntent)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000}).build();
            NotificationManager notificationManager =
                    (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notification);
        }

        Log.d(TAG, "Inside onProgressUpdate, params: " + params[0]);
    }

    @Override
    protected Object doInBackground(Object... params) {
        Log.d(TAG, "Inside doInBackground");
        try{
            //String mensaje = sendPost((String)params[0]);
            String mensaje = sendGet("http://android.net78.net/domotic_api/ctrl_action.php","update_status", mId, mStatus);
            this.publishProgress(mensaje);
        }catch(Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Intent intent = new Intent(mContext, panel.class);
        Log.d(TAG, "Inside onPostExecute");
    }

    public String sendPost(String s) throws URISyntaxException, UnsupportedEncodingException {
        Log.d(TAG, "Inside insertar procedure");

        String mensaje      = "";

        try {
            URL url = new URL("http://android.net78.net/domotic_api/button.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            DataOutputStream printout;
            DataInputStream input;

            Log.d(TAG, "Inside insertar procedure p="+s);
            // Fill the parameter to send
            String urlParameters = s + "=" + s;
            // Activar método POST
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            DataOutputStream dStream = new DataOutputStream(conn.getOutputStream());

            dStream.writeBytes(urlParameters);
            dStream.flush();
            dStream.close();

            int responseCode = conn.getResponseCode();
            String output = "Request URL" + url;
            output += System.getProperty("line.separator") + "Request parameters " + urlParameters;
            output += System.getProperty("line.separator") + "Response code " + responseCode;

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder responseOut = new StringBuilder();

            while ((line = br.readLine()) != null) {
                responseOut.append(line);
            }

            mensaje = s;

        }catch(Exception e){
            mensaje = "error";
        }
        return mensaje;
    }

    public String sendGet(String _url, String action, String deviceId, String deviceStatus) throws URISyntaxException, UnsupportedEncodingException {

        String mensaje = "FALSE";

        try {
            URL url = new URL(_url + "?action=" + action.replace(" ", "") + "&id=" + deviceId.replace(" ", "") + "&status=" + deviceStatus.replace(" ", ""));
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
            String xmlString = org.apache.commons.io.IOUtils.toString(new BufferedInputStream(stream), "UTF-8");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            InputSource inStream = new InputSource();
            inStream.setCharacterStream(new StringReader(xmlString));
            Document doc = db.parse(inStream);

            NodeList nl = doc.getElementsByTagName("estado");
            org.w3c.dom.Element nameElement = (org.w3c.dom.Element) nl.item(0);
            mensaje = nameElement.getFirstChild().getNodeValue().trim();

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
}