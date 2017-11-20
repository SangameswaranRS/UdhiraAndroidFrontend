package com.example.sangameswaran.udhira.restAPICalls;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.sangameswaran.udhira.Constants.Constants;
import com.example.sangameswaran.udhira.Entities.BloodGroupApiEntity;
import com.example.sangameswaran.udhira.Entities.DonorRegistrationEntity;
import com.example.sangameswaran.udhira.Entities.ErrorEntity;
import com.example.sangameswaran.udhira.Entities.LoginEntity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sangameswaran on 20-11-2017.
 */

public class RestClientImplementation {
    static RequestQueue queue;
    public static String getAbsoluteURL(String relativeURL){
        return Constants.BASE_URL+relativeURL;
    }

    public static void loginApi(final LoginEntity loginEntity, final LoginEntity.UdhiraRestClientInterface restClientInterface, final Context context){
        String HIT_URL= getAbsoluteURL("/login");
        Log.d("RestClient",HIT_URL);
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        final Gson gson=new Gson();
        String jsonString=gson.toJson(loginEntity);
        try {
            JSONObject jsonPostObject=new JSONObject(jsonString);
            JsonBaseRequest postRequest=new JsonBaseRequest(Request.Method.POST, HIT_URL, jsonPostObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String message =response.getString("message");
                        loginEntity.setImeiIndex(message);
                        restClientInterface.onLoginInfoSubmit(loginEntity,null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        restClientInterface.onLoginInfoSubmit(loginEntity,new VolleyError());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    ErrorEntity errorEntity;
                    errorEntity=gson.fromJson(new String(error.networkResponse.data),ErrorEntity.class);
                    loginEntity.setImeiIndex(errorEntity.getMessage());
                    restClientInterface.onLoginInfoSubmit(loginEntity,error);
                }
            });
            queue.add(postRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void getAllBloodGroupsApi(final BloodGroupApiEntity bloodGroupApiEntity1, final BloodGroupApiEntity.UdhiraRestClientInterface udhiraRestClientInterface, final Context context){
        String HIT_URL=getAbsoluteURL("/getAllBloodGroups");
        final Gson gson=new Gson();
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        JsonObjectRequest postRequest=new JsonObjectRequest(Request.Method.GET, HIT_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                BloodGroupApiEntity bloodGroupApiEntity;
                bloodGroupApiEntity=gson.fromJson(String.valueOf(response),BloodGroupApiEntity.class);
                bloodGroupApiEntity1.setMessage(bloodGroupApiEntity.getMessage());
                bloodGroupApiEntity1.setStatusCode(bloodGroupApiEntity.getStatusCode());
                udhiraRestClientInterface.onGetBloodGroup(bloodGroupApiEntity1,null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ErrorEntity errorEntity=gson.fromJson(new String(error.networkResponse.data),ErrorEntity.class);
                Toast.makeText(context,error.getMessage(),Toast.LENGTH_LONG).show();
                udhiraRestClientInterface.onGetBloodGroup(bloodGroupApiEntity1,error);
            }
        });
        queue.add(postRequest);
    }

    public static void postDonorInfoApi(final DonorRegistrationEntity donorRegistrationEntity, final DonorRegistrationEntity.UdhiraRestClientInterface udhiraRestClientInterface, final Context context){
        String HIT_URL=getAbsoluteURL("/updateDonorInfo");
        final Gson gson=new Gson();
        String jsonPostParamString=gson.toJson(donorRegistrationEntity);
        try {
            JSONObject postParams=new JSONObject(jsonPostParamString);
            queue=VolleySingleton.getInstance(context).getRequestQueue();
            JsonBaseRequest postRequest=new JsonBaseRequest(Request.Method.POST, HIT_URL, postParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    ErrorEntity errorEntity=gson.fromJson(String.valueOf(response),ErrorEntity.class);
                    Toast.makeText(context,errorEntity.getMessage(),Toast.LENGTH_LONG).show();
                    udhiraRestClientInterface.onSubmitDonorDetails(donorRegistrationEntity,null);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    ErrorEntity errorEntity=gson.fromJson(new String(error.networkResponse.data),ErrorEntity.class);
                    Toast.makeText(context,errorEntity.getMessage(),Toast.LENGTH_LONG).show();
                    udhiraRestClientInterface.onSubmitDonorDetails(donorRegistrationEntity,error);
                }
            });
            queue.add(postRequest);
        } catch (JSONException e) {
            e.printStackTrace();
            udhiraRestClientInterface.onSubmitDonorDetails(donorRegistrationEntity,new VolleyError());
        }
    }

}
