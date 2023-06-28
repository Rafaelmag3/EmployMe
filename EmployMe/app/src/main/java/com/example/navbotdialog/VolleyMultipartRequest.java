package com.example.navbotdialog;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public abstract class VolleyMultipartRequest extends Request<NetworkResponse> {
    private final String boundary = "volleyMultipartBoundary";
    private final String lineEnd = "\r\n";
    private final String twoHyphens = "--";

    private Response.Listener<NetworkResponse> mListener;
    private Response.ErrorListener mErrorListener;
    private Map<String, DataPart> mByteDataParams;

    public VolleyMultipartRequest(int method, String url, Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mListener = listener;
        this.mErrorListener = errorListener;
        this.mByteDataParams = new HashMap<>();
    }

    public void addByteDataParam(String key, DataPart dataPart) {
        mByteDataParams.put(key, dataPart);
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data;boundary=" + boundary;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        try {
            // Adding Params
            Map<String, String> params = getParams();
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    buildTextPart(dos, entry.getKey(), entry.getValue());
                }
            }

            // Adding ByteDataParams
            if (mByteDataParams != null && !mByteDataParams.isEmpty()) {
                for (Map.Entry<String, DataPart> entry : mByteDataParams.entrySet()) {
                    buildDataPart(dos, entry.getValue(), entry.getKey());
                }
            }

            // Mark the end of the request
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(NetworkResponse response) {
        mListener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        mErrorListener.onErrorResponse(error);
    }

    private void buildTextPart(DataOutputStream dataOutputStream, String parameterName, String parameterValue) throws IOException {
        dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + parameterName + "\"" + lineEnd);
        dataOutputStream.writeBytes(lineEnd);
        dataOutputStream.writeBytes(parameterValue + lineEnd);
    }

    private void buildDataPart(DataOutputStream dataOutputStream, DataPart dataPart, String fileName) throws IOException {
        dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + dataPart.getName() + "\"; filename=\"" + fileName + "\"" + lineEnd);
        if (dataPart.getType() != null && !dataPart.getType().trim().isEmpty()) {
            dataOutputStream.writeBytes("Content-Type: " + dataPart.getType() + lineEnd);
        }
        dataOutputStream.writeBytes(lineEnd);
        dataOutputStream.write(dataPart.getContent());
        dataOutputStream.writeBytes(lineEnd);
    }


    public abstract Map<String, DataPart> getByteData();

    public static class DataPart {
        private String name;
        private byte[] content;
        private String type;

        public DataPart(String name, byte[] content) {
            this.name = name;
            this.content = content;
        }

        public DataPart(String name, byte[] content, String type) {
            this.name = name;
            this.content = content;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public byte[] getContent() {
            return content;
        }

        public String getType() {
            return type;
        }
    }
}
