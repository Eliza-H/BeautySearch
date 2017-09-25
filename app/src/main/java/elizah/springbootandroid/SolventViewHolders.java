package elizah.springbootandroid;

/**
 * Created by elh on 18.09.17.
 */

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;

public class SolventViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView countryName;
    public ImageView countryPhoto;
    public TextView listDescription;
    public ImageView likeImageView;
    public ImageView shareImageView;
    public ImageView deleteImageView;
    RequestQueue queue;
    String HOST = "http://192.168.0.102:8080";
    List<ItemObjects> itemList;

    public SolventViewHolders(final View itemView, final List<ItemObjects> itemList) {
        super(itemView);
        itemView.setOnClickListener(this);
        queue = Volley.newRequestQueue(itemView.getContext());
        this.itemList = itemList;
        countryName = (TextView) itemView.findViewById(R.id.country_name);
        countryPhoto = (ImageView) itemView.findViewById(R.id.country_photo);
        listDescription = (TextView) itemView.findViewById(R.id.list_description);
        likeImageView = (ImageView) itemView.findViewById(R.id.likeImageView);
        shareImageView = (ImageView) itemView.findViewById(R.id.shareImageView);
        deleteImageView = (ImageView) itemView.findViewById(R.id.deleteImageView);
        likeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Long serviceId = itemList.get(position).getId();
                Long userId = 1L;
                boolean id = (boolean) likeImageView.getTag();
                if (id == false) {
                    likeImageView.setTag(true);
                   likeImageView.setImageResource(R.drawable.ic_liked);
                    Toast.makeText(v.getContext(), countryName.getText() + " added to favourites", Toast.LENGTH_SHORT).show();
                    likeOrUnlike(true, userId, serviceId);

                } else {
                    likeImageView.setTag(false);
                  likeImageView.setImageResource(R.drawable.ic_like);
                    Toast.makeText(v.getContext(), " removed from favourites", Toast.LENGTH_SHORT).show();
                    likeOrUnlike(false, userId, serviceId);
                }
            }
        });

        shareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + v.getResources().getResourcePackageName(countryPhoto.getId())
                        + '/' + "drawable" + '/' + v.getResources().getResourceEntryName((int) countryPhoto.getTag()));

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                ;
                //shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                shareIntent.setType("image/jpeg");
                Intent chosenIntent = Intent.createChooser(shareIntent, v.getResources().getText(R.string.send_to));
                v.getContext().startActivity(chosenIntent);
            }
        });
        deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                int position = getAdapterPosition();
                Long id = itemList.get(position).getId();
                delete(id, v);
            }
        });
    }

    private void delete(final Long id, final View v) {
        String url = HOST + "/service/delete/" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        refresh(v);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
        };
         queue.add(stringRequest);
    }

    private void likeOrUnlike(final boolean like,final Long userId,final Long serviceId) {
        String url;
        if(like == true){
        url = HOST + "/service/like";}
        else{url = HOST + "/service/unlike";}
        final String requestBody;
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("userId", userId);
            jsonBody.put("serviceId", serviceId);
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
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
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

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
//    public void getClickedPosition(View view) {
//      getAdapterPosition();
//    }

    public void refresh(View v) {
        Intent refresh = new Intent(v.getContext(), MainActivity.class);
        v.getContext().startActivity(refresh);
    }
}