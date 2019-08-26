package com.aman.clothesshop.model.request;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;

public class AddProductRequest {

    private Integer productId;

    public AddProductRequest(Integer productId) {
        this.productId = productId;
    }

    public RequestBody getRequestBody() {

        RequestBody body = null;
        String productIdString = String.valueOf(productId);
        JSONObject json = new JSONObject();

        try {
            json.put("productId", productIdString);
            body = RequestBody.create(okhttp3.MediaType.parse("applicationType/json"), json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return body;
    }
}
