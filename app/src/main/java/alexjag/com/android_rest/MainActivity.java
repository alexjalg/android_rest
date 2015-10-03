package alexjag.com.android_rest;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class MainActivity extends ActionBarActivity{

    Button btnInvocar;
    TextView txvMensaje;
    GuardarIdService guardarId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInvocar = (Button)findViewById(R.id.btnInvocar);
        txvMensaje = (TextView)findViewById(R.id.txvMensaje);
        btnInvocar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                guardarId = new GuardarIdService(getParent());
                guardarId.execute("CON FE!!!", "Practicando Android tiene que salir el cliente REST", "1");
            }
        });
    }

    private class GuardarIdService extends AsyncTask<String, String, Boolean>{
        private JSONObject responseJSON;
        private Context context;
        public  GuardarIdService(Context context){
            this.context = context;
        }
        @Override
        protected Boolean doInBackground(String... params){
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://jsonplaceholder.typicode.com/posts");
            post.setHeader("content-type","application/json");
            JSONObject dato = new JSONObject();
            try {
                dato.put("title",params[0]);
                dato.put("body",params[1]);
                dato.put("userId", params[2]);
                StringEntity entity = new StringEntity(dato.toString());
                post.setEntity(entity);
                HttpResponse resp = httpClient.execute(post);
                String respStr = EntityUtils.toString(resp.getEntity());
                JSONObject respJSON = new JSONObject(respStr);

                Toast.makeText(
                        this.context.getApplicationContext(),
                        respJSON.toString(),
                        Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }





}
