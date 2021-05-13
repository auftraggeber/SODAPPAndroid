package me.langner.jonas.sodapp.model.https;

import android.os.AsyncTask;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.Collections;
import java.util.Map;

/**
 * Behandelt Abfragen zum Web.
 * @author Jonas Langner
 * @version Alpha
 * @since Alpha
 */
public abstract class SimpleHTTPSRequest extends AsyncTask<String, String, String> {

    public static final String MAIN_URL = "https://sod.clan.rip/api/xml_0_1_3/";

    private HttpsURLConnection connection;
    private Map<String, String> parameters;
    private Method method = Method.POST;

    public enum Method {
        POST, GET;
    }

    /**
     * Erstellt eine neue Abfrage.
     * @param urlName Die URL.
     * @param params Die Parameter, die übergeben werden sollen.
     * @throws IOException Falls die URL falsch ist, wird diese Exception geworfen.
     */
    public SimpleHTTPSRequest(String urlName, Method method, Map<String, String> params) {
        this.method = method;
        this.parameters = params;

        super.execute(urlName);
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            if (strings.length >= 1 && strings[0] != null) {
                URL url = new URL(strings[0]);

                URLConnection con = url.openConnection();
                if (con instanceof HttpsURLConnection) {
                    connection = (HttpsURLConnection) con;

                    initConnection();

                    connection.connect();
                    listenForInput();
                    connection.disconnect();
                }
            }

        }
        catch(IOException ex) {

        }

        return null;
    }

    /**
     * Setzt alle Einstellungen für die Verbindung.
     * @throws IOException Falls irgendeine Einstellung nicht möglich war.
     */
    private void initConnection() throws IOException {
        connection.setRequestMethod(this.method.name());
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        printParams();
    }

    /**
     * Setzt die Parameter für die Abfrage.
     * @throws NullPointerException Falls einige Objekte nicht erstellt werden konnten.
     * @throws IOException Falls beim verschlüsseln oder ausgeben irgendein Fehler aufgetreten ist.
     */
    private void printParams() throws NullPointerException, IOException {
        String paramsString = "";

        for (String key : parameters.keySet()) {
            String value = parameters.get(key);

            if (paramsString != "")
                paramsString += "&";

            paramsString += URLEncoder.encode(key, "utf-8");
            paramsString += "=";
            paramsString += URLEncoder.encode(value, "utf-8");
        }

        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(paramsString);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * Schaut, ob irgendein Input kommt.
     * @throws IOException Falls es Fehler beim Lesen gab.
     */
    private void listenForInput() throws IOException {
        int status = connection.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        onResponse(status, content.toString());
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    public abstract void onResponse(int status, String response);
}
