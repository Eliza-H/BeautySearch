package elizah.springbootandroid;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class FormAddNewService extends AppCompatActivity {
       // Instantiate the RequestQueue.
    RequestQueue queue;
    String HOST = "http://192.168.0.102:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_add_new_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        queue = Volley.newRequestQueue(this);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public void onAddService(View view) {
        hideKeyboard(this);
        EditText text1 = (EditText) findViewById(R.id.text1);
        String textOne = text1.getText().toString();
        EditText text2 = (EditText) findViewById(R.id.text2);
        String textTwo = text2.getText().toString();
        if (textOne != null && textTwo != null) {
            sendRequest(textOne, textTwo, view);
            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            findViewById(R.id.addService).setVisibility(View.GONE); ///need to check it
        }
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void sendRequest(final String textOne, final String textTwo, final View view) {
        final TextView mTextView = (TextView) findViewById(R.id.textView);
        String url = HOST + "/service/add";
        final String requestBody;
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("title", textOne);
            jsonBody.put("description", textTwo);
            requestBody = jsonBody.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        findViewById(R.id.addService).setVisibility(View.VISIBLE);//need to check it
                        mTextView.setText("Response is: "+ response);
                        refresh(view);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                findViewById(R.id.addService).setVisibility(View.VISIBLE);//need to check it
                error.printStackTrace();
                mTextView.setText("That didn't work!");
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };


        queue.add(stringRequest);
    }

    public void refresh(View v) {
        Intent refresh = new Intent(v.getContext(), MainActivity.class);
        v.getContext().startActivity(refresh);
    }
}
