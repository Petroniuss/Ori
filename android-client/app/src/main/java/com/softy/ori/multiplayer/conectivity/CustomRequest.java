package com.softy.ori.multiplayer.conectivity;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;

/**
 * This class parses POJO request into json and response json into POJO.
 *
 *
 * @param <E> request
 * @param <R> response
 */
public class CustomRequest<E, R> extends JsonRequest<R> {

    private static final Gson gson = new Gson();

    private final Class<? extends R> rClass;
    private final Listener<R> listener;

    public CustomRequest(int method, String url,
                         Class<? extends E> eClass, Class<? extends R> rClass, E requestObject,
                         Listener<R> listener, ErrorListener errorListener) {
        super(method, url, gson.toJson(requestObject, eClass), listener, errorListener);

        this.rClass = rClass;
        this.listener = listener;
    }

    @Override
    protected void deliverResponse(R response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<R> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    gson.fromJson(json, rClass),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}
