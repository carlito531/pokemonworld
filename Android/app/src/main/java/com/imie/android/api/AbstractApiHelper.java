package com.imie.android.api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;

/**
 * Created by charly on 31/07/2016.
 */
public abstract class AbstractApiHelper<T>  {

    protected JSONArray resultList = null;

    public JSONArray getResultList() {
        return resultList;
    }

    public void setResultList(JSONArray resultList) {
        this.resultList = resultList;
    }

    /**
     * force subclasses to implement the method to get his route
     * @return
     */
    abstract public String getRoute();

/**
     * find all
     * @return
     *
    List<T> findAll(String requiredType) {
        List<T> listType = new ArrayList<T>();

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();

        client.setTimeout(100000);
        client.post("http://10.0.2.2:8888/api/" + this.getRoute(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (responseBody != null) {

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });

        return listType;
    }
    */

    /**
     * return all entities in JSON
     * @return
     */
    public void findAll() {

        AsyncHttpClient client = new AsyncHttpClient();

        client.setTimeout(100000);
        client.get("http://10.0.2.2:8888/api/" + this.getRoute(), new JsonHttpResponseHandler() {
            JSONArray test = null;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                setResultList(response);
            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                super.onPostProcessResponse(instance, response);
            }
        });
    }

}
