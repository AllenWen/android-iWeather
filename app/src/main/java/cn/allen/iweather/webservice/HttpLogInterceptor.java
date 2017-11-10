package cn.allen.iweather.webservice;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.Okio;

/**
 * Author: AllenWen
 * CreateTime: 2017/11/10
 * Email: wenxueguo@medlinker.com
 * Description:
 */

class HttpLogInterceptor implements Interceptor {
    private static final String TAG = "Network";
    private static final double MILLI_AS_NANO = 1e6d;
    private boolean isShowFullLog;

    public HttpLogInterceptor(boolean isShowFullLog) {
        this.isShowFullLog = isShowFullLog;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (isShowFullLog) {
            Log.e(TAG, "---> REQUEST " + request.method() + " " + request.url());
            logHeaders(request.headers());
            // copy original request for logging request body
            Request copy = request.newBuilder().build();
            RequestBody requestBody = copy.body();
            if (requestBody == null) {
                Log.e(TAG, "Body - no body");
            } else {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                Log.e(TAG, "Body - " + buffer.readString(requestBody.contentType().charset()));
            }
            Log.e(TAG, "---> END");
        }
        long t1 = System.nanoTime();
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();

        if (isShowFullLog) {
            Log.e(TAG, "<--- RESPONSE " + response.code() + " " + response.request().url());
            logHeaders(response.headers());
        }
        ResponseBody body = response.body();
        InputStream responseStream = null;
        if (body != null) {
            responseStream = body.byteStream();
        }
        byte[] b = input2byte(responseStream);
        String responseBody = new String(b);
        try {
            JSONObject json = new JSONObject(responseBody);
            if (TextUtils.isEmpty(responseBody)) {
                Log.e(TAG, request.method() + " " + request.url() + "Body - no body");
            } else {
                Log.e(TAG, request.method() + " " + request.url() + "Body - " + " \n" + json.toString(2));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (body != null) {
            Log.e(TAG, "<--- END " + "(Size: " + body.contentLength() + " bytes - " + "Network time: " + (t2 - t1)
                    / MILLI_AS_NANO + " ms)");
        }
        if (responseStream != null) {
            response = response.newBuilder()
                    .body(new ForwardingResponseBody(body, new ByteArrayInputStream(b)))
                    .build();
        }
        return response;
    }

    private static class ForwardingResponseBody extends ResponseBody {
        private final ResponseBody mBody;
        private final BufferedSource mInterceptedSource;

        public ForwardingResponseBody(ResponseBody body, InputStream interceptedStream) {
            mBody = body;
            mInterceptedSource = Okio.buffer(Okio.source(interceptedStream));
        }

        @Override
        public MediaType contentType() {
            return mBody.contentType();
        }

        @Override
        public long contentLength() {
            return mBody.contentLength();
        }

        @Override
        public BufferedSource source() {
            return mInterceptedSource;
        }
    }

    private static void logHeaders(Headers headers) {
        for (String headerName : headers.names()) {
            for (String headerValue : headers.values(headerName)) {
                Log.e(TAG, "Header - [" + headerName + ": " + headerValue + "]");
            }
        }
    }

    private byte[] input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 1024)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        return swapStream.toByteArray();
    }
}
