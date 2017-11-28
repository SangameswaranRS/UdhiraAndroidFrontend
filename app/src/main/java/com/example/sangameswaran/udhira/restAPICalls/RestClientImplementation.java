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
import com.example.sangameswaran.udhira.Entities.AuthPostParamEntity;
import com.example.sangameswaran.udhira.Entities.BloodGroupApiEntity;
import com.example.sangameswaran.udhira.Entities.BloodRequestEntity;
import com.example.sangameswaran.udhira.Entities.DonationCentreAPIEntity;
import com.example.sangameswaran.udhira.Entities.DonorRegistrationEntity;
import com.example.sangameswaran.udhira.Entities.ErrorEntity;
import com.example.sangameswaran.udhira.Entities.GetAllBloodRequestsApiEntity;
import com.example.sangameswaran.udhira.Entities.GetAllDonorInfoApiEntity;
import com.example.sangameswaran.udhira.Entities.LoginEntity;
import com.example.sangameswaran.udhira.Entities.PostSatisfiedRequestEntity;
import com.example.sangameswaran.udhira.Entities.SignupEntity;
import com.google.android.gms.vision.barcode.Barcode;
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
                    try {
                        if(error.networkResponse!=null){
                        ErrorEntity errorEntity;
                        errorEntity = gson.fromJson(new String(error.networkResponse.data), ErrorEntity.class);
                        loginEntity.setImeiIndex(errorEntity.getMessage());
                        restClientInterface.onLoginInfoSubmit(loginEntity, error);}
                        else {
                            Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();
                            restClientInterface.onLoginInfoSubmit(loginEntity,error);
                        }
                    }catch (Exception e){
                        Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
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
                try {
                    if (error.networkResponse!=null) {
                        ErrorEntity errorEntity = gson.fromJson(new String(error.networkResponse.data), ErrorEntity.class);
                        Toast.makeText(context, errorEntity.getMessage(), Toast.LENGTH_LONG).show();
                        udhiraRestClientInterface.onGetBloodGroup(bloodGroupApiEntity1, error);
                    }else {
                        Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                }
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
                    try {
                        if(error.networkResponse!=null) {
                            ErrorEntity errorEntity = gson.fromJson(new String(error.networkResponse.data), ErrorEntity.class);
                            Toast.makeText(context, errorEntity.getMessage(), Toast.LENGTH_LONG).show();
                            udhiraRestClientInterface.onSubmitDonorDetails(donorRegistrationEntity, error);
                        }else{
                            Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();
                            udhiraRestClientInterface.onSubmitDonorDetails(donorRegistrationEntity,error);
                        }
                    }catch (Exception e){
                        Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                        udhiraRestClientInterface.onSubmitDonorDetails(donorRegistrationEntity,error);
                    }
                }
            });
            queue.add(postRequest);
        } catch (JSONException e) {
            e.printStackTrace();
            udhiraRestClientInterface.onSubmitDonorDetails(donorRegistrationEntity,new VolleyError());
        }
    }

    public static void getDonationCentresApi(final DonationCentreAPIEntity donationCentreAPIEntity, final DonationCentreAPIEntity.UdhiraRestClientInterface restClientInterface, final Context context){
        String HIT_URL=getAbsoluteURL("/getCentreLocation");
        final Gson gs=new Gson();
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        JsonBaseRequest getRequest=new JsonBaseRequest(Request.Method.GET, HIT_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                DonationCentreAPIEntity entity=gs.fromJson(String.valueOf(response),DonationCentreAPIEntity.class);
                donationCentreAPIEntity.setMessage(entity.getMessage());
                donationCentreAPIEntity.setStatusCode(entity.getStatusCode());
                restClientInterface.onGetCentreDetails(donationCentreAPIEntity,null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    if(error.networkResponse!=null) {
                        ErrorEntity errorEntity = gs.fromJson(new String(error.networkResponse.data), ErrorEntity.class);
                        Toast.makeText(context, errorEntity.getMessage(), Toast.LENGTH_LONG).show();
                        restClientInterface.onGetCentreDetails(donationCentreAPIEntity, error);
                    }else {
                        Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();
                        restClientInterface.onGetCentreDetails(donationCentreAPIEntity,error);
                    }
                }catch (Exception e){
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        queue.add(getRequest);
    }

    public static void signUpApi(final SignupEntity entity, final SignupEntity.UdhiraRestClientInterface restClientInterface, final Context context){
        String HIT_URL=getAbsoluteURL("/register");
        final Gson gs=new Gson();
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        String postParamJsonString=gs.toJson(entity);
        try {
            JSONObject postParams=new JSONObject(postParamJsonString);
            JsonBaseRequest postRequest=new JsonBaseRequest(Request.Method.POST, HIT_URL, postParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        ErrorEntity errorEntity = gs.fromJson(String.valueOf(response), ErrorEntity.class);
                        Toast.makeText(context, errorEntity.getMessage(), Toast.LENGTH_LONG).show();
                        restClientInterface.onGettingResponse(entity, null);
                    }catch (Exception e){
                        restClientInterface.onGettingResponse(entity,new VolleyError());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        if(error.networkResponse!=null) {
                            ErrorEntity errorEntity = new ErrorEntity();
                            errorEntity = gs.fromJson((new String(error.networkResponse.data)), ErrorEntity.class);
                            Toast.makeText(context, errorEntity.getStatusCode() + ":" + errorEntity.getMessage(), Toast.LENGTH_LONG).show();
                            restClientInterface.onGettingResponse(entity, error);
                        }else {
                            Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();
                            restClientInterface.onGettingResponse(entity,error);
                        }
                    }catch (Exception e){
                        Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                        restClientInterface.onGettingResponse(entity,error);
                    }
                }
            });
            queue.add(postRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void postBloodRequestApi(final BloodRequestEntity bloodRequestEntity, final BloodRequestEntity.UdhiraRestClientInterface restClientInterface, final Context context){
        String HIT_URL=getAbsoluteURL("/raiseBloodRequest");
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        final Gson gson=new Gson();
        String JsonString=gson.toJson(bloodRequestEntity);
        try {
            JSONObject postParams=new JSONObject(JsonString);
            JsonBaseRequest postRequest=new JsonBaseRequest(Request.Method.POST, HIT_URL, postParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        ErrorEntity errorEntity = gson.fromJson(String.valueOf(response), ErrorEntity.class);
                        Toast.makeText(context, errorEntity.getMessage(), Toast.LENGTH_LONG).show();
                        restClientInterface.onRequestBlood(bloodRequestEntity,null);
                    }catch (Exception e){
                        Toast.makeText(context,"Exception : "+e.getMessage(),Toast.LENGTH_LONG).show();
                        restClientInterface.onRequestBlood(bloodRequestEntity,new VolleyError());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        if(error.networkResponse!=null) {
                            ErrorEntity errorEntity = gson.fromJson(new String(error.networkResponse.data), ErrorEntity.class);
                            Toast.makeText(context, errorEntity.getStatusCode() + " : " + errorEntity.getMessage(), Toast.LENGTH_LONG).show();
                            restClientInterface.onRequestBlood(bloodRequestEntity, error);
                        }else {
                            Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();
                            restClientInterface.onRequestBlood(bloodRequestEntity,error);
                        }
                    }catch (Exception e){
                        Toast.makeText(context,"Exception : "+e.getMessage(),Toast.LENGTH_LONG).show();
                        restClientInterface.onRequestBlood(bloodRequestEntity,new VolleyError());
                    }
                }
            });
            queue.add(postRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void postAuthEntityForVerification(final AuthPostParamEntity authPostParamEntity, final AuthPostParamEntity.UdhiraRestClientInterface restClientInterface, final Context context){
        String HIT_URL=getAbsoluteURL("/checkFirebase");
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        final Gson gs=new Gson();
        String postParamString=gs.toJson(authPostParamEntity);
        try {
            JSONObject postParam=new JSONObject(postParamString);
            JsonBaseRequest postRequest=new JsonBaseRequest(Request.Method.POST, HIT_URL, postParam, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    ErrorEntity errorEntity=gs.fromJson(response.toString(),ErrorEntity.class);
                    Toast.makeText(context,errorEntity.getMessage(),Toast.LENGTH_LONG).show();
                    restClientInterface.postAuthParams(authPostParamEntity,null);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        if(error.networkResponse!=null) {
                            ErrorEntity errorEntity = gs.fromJson(new String(error.networkResponse.data), ErrorEntity.class);
                            Toast.makeText(context, errorEntity.getMessage(), Toast.LENGTH_LONG).show();
                            restClientInterface.postAuthParams(authPostParamEntity, error);
                        }else {
                            Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();
                            restClientInterface.postAuthParams(authPostParamEntity,error);
                        }
                    }catch (Exception e){
                        Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                        restClientInterface.postAuthParams(authPostParamEntity,error);
                    }
                }
            });
            queue.add(postRequest);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            restClientInterface.postAuthParams(authPostParamEntity,new VolleyError());
        }

    }

    public static void getAllDonorInfoApi(AuthPostParamEntity authParam, final GetAllDonorInfoApiEntity.UdhiraRestClientInterface restClientInterface, final Context context){
        String HIT_URL=getAbsoluteURL("/getAllDonorInfo");
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        final Gson gson=new Gson();
        String jsonString=gson.toJson(authParam);
        try {
            JSONObject jsonObject=new JSONObject(jsonString);
            JsonBaseRequest postRequest=new JsonBaseRequest(Request.Method.POST, HIT_URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    GetAllDonorInfoApiEntity allDonorInfoApiEntity=gson.fromJson(response.toString(),GetAllDonorInfoApiEntity.class);
                    restClientInterface.onGetAllDonorInfo(allDonorInfoApiEntity,null);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        if(error.networkResponse!=null) {
                            ErrorEntity errorEntity = gson.fromJson(new String(error.networkResponse.data), ErrorEntity.class);
                            Toast.makeText(context, errorEntity.getMessage(), Toast.LENGTH_LONG).show();
                            restClientInterface.onGetAllDonorInfo(new GetAllDonorInfoApiEntity(), error);
                        }else {
                            Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();
                            restClientInterface.onGetAllDonorInfo(new GetAllDonorInfoApiEntity(),error);
                        }
                    }catch (Exception e){
                        Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                        restClientInterface.onGetAllDonorInfo(new GetAllDonorInfoApiEntity(),error);
                    }
                }
            });
            queue.add(postRequest);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            restClientInterface.onGetAllDonorInfo(new GetAllDonorInfoApiEntity(),new VolleyError());
        }
    }

    public static void getAllBloodRequestsApi(final AuthPostParamEntity entity, final GetAllBloodRequestsApiEntity.UdhiraRestClientInterface restClientInterface, final Context context)  {
        String API_URL=getAbsoluteURL("/getAllBloodRequests");
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        final Gson gs=new Gson();
        String postParamString=gs.toJson(entity);
        try {
            JSONObject jsonObject=new JSONObject(postParamString);
            JsonBaseRequest postRequest=new JsonBaseRequest(Request.Method.POST, API_URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        GetAllBloodRequestsApiEntity entity1 = gs.fromJson(response.toString(), GetAllBloodRequestsApiEntity.class);
                        restClientInterface.onGettingAllBloodRequests(entity1,null);
                    }catch (Exception e){
                        restClientInterface.onGettingAllBloodRequests(new GetAllBloodRequestsApiEntity(),new VolleyError());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try{
                        if(error.networkResponse!=null) {
                            ErrorEntity errorEntity = new ErrorEntity();
                            errorEntity = gs.fromJson(new String(error.networkResponse.data), ErrorEntity.class);
                            Toast.makeText(context, errorEntity.getStatusCode() + ":" + errorEntity.getMessage(), Toast.LENGTH_LONG).show();
                            restClientInterface.onGettingAllBloodRequests(new GetAllBloodRequestsApiEntity(), error);
                        }else {
                            Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();
                            restClientInterface.onGettingAllBloodRequests(new GetAllBloodRequestsApiEntity(),error);
                        }
                    }catch (Exception e){
                        Toast.makeText(context,"Exception : "+e.getMessage(),Toast.LENGTH_LONG).show();
                        restClientInterface.onGettingAllBloodRequests(new GetAllBloodRequestsApiEntity(),error);
                    }
                }
            });
            queue.add(postRequest);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context,"Exception : "+e.getMessage(),Toast.LENGTH_LONG).show();
            restClientInterface.onGettingAllBloodRequests(new GetAllBloodRequestsApiEntity(),new VolleyError());
        }
    }

    public static void postSatisifedRequestApi(PostSatisfiedRequestEntity requestEntity, final PostSatisfiedRequestEntity.UdhiraRestClientInterface restClientInterface, final Context context) {
        String API_URL=getAbsoluteURL("/postSatisfiedBloodRequest");
        queue=VolleySingleton.getInstance(context).getRequestQueue();
        final Gson gson=new Gson();
        String jsonString=gson.toJson(requestEntity);
        try {
            JSONObject postParams=new JSONObject(jsonString);
            JsonBaseRequest postRequest=new JsonBaseRequest(Request.Method.POST, API_URL, postParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    ErrorEntity errorEntity=gson.fromJson(response.toString(),ErrorEntity.class);
                    restClientInterface.onRequestSubmit(errorEntity,null);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.networkResponse!=null){
                        ErrorEntity errorEntity=gson.fromJson(new String(error.networkResponse.data),ErrorEntity.class);
                        Toast.makeText(context,errorEntity.getMessage(),Toast.LENGTH_SHORT).show();
                        restClientInterface.onRequestSubmit(errorEntity,error);
                    }else {
                        Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();
                        restClientInterface.onRequestSubmit(new ErrorEntity(),error);
                    }
                }
            });
            queue.add(postRequest);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            restClientInterface.onRequestSubmit(new ErrorEntity(),new VolleyError());
        }
    }
}
