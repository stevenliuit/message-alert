package com.message.alert.utils;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OkHttpUtils {

  private static final Logger log = LoggerFactory.getLogger(OkHttpUtils.class);

  private static final HttpLoggingInterceptor LOG_INTERCEPTOR = new HttpLoggingInterceptor(System.out::println)
    .setLevel(HttpLoggingInterceptor.Level.HEADERS);

  private static final HttpLoggingInterceptor NONE_INTERCEPTOR = new HttpLoggingInterceptor(System.out::println)
    .setLevel(HttpLoggingInterceptor.Level.NONE);

  private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

  private static OkHttpClient CLIENT;

  static {
    String env = System.getenv("spring.profiles.active");
    boolean isProd = "prod".equalsIgnoreCase(env);
    X509TrustManager x509TrustManager = (X509TrustManager) SSLSocketClient.getTrustManager()[0];
    CLIENT = new OkHttpClient.Builder()
      .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), x509TrustManager)
      .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
      .callTimeout(Duration.ofSeconds(10))
      .addInterceptor(isProd ? NONE_INTERCEPTOR : LOG_INTERCEPTOR)
      .build();
  }

  public static String post(String url, Map<String, String> params) {
    FormBody.Builder builder = new FormBody.Builder();
    for (Map.Entry<String, String> entry : params.entrySet()) {
      builder.add(entry.getKey(), entry.getValue());
    }
    RequestBody formBody = builder.build();

    Request request = new Request.Builder().url(url).post(formBody).build();
    Call call = CLIENT.newCall(request);
    try {
      try (Response response = call.execute()) {
        if (response.code() == 200 && response.body() != null) {
          return response.body().string();
        } else {
          String responseBody = response.body() == null ? "" : response.body().string();
          log.warn("response code:" + response.code() + ", body:" + responseBody);
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public static String postJson(String url, String json) {
    return postJson(url, Collections.emptyMap(), json);
  }

  public static String postJson(String url, Map<String, String> headerMap, String json) {
    Headers.Builder headerBuilder = new Headers.Builder();
    if (MapUtils.isNotEmpty(headerMap)) {
      for (Map.Entry<String, String> entry : headerMap.entrySet()) {
        headerBuilder.add(entry.getKey(), entry.getValue());
      }
    }

    RequestBody body = RequestBody.create(JSON, json);
    Request request = new Request.Builder().url(url).post(body).headers(headerBuilder.build()).build();
    Call call = CLIENT.newCall(request);
    try {
      try (Response response = call.execute()) {
        if (response.code() == 200 && response.body() != null) {
          return response.body().string();
        } else {
          String responseBody = response.body() == null ? "" : response.body().string();
          log.warn("url:" + url + " response code:" + response.code() + ", body:" + responseBody);
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public static String delete(String url, Map<String, String> headerMap) {
    Headers.Builder headerBuilder = new Headers.Builder();
    if (MapUtils.isNotEmpty(headerMap)) {
      for (Map.Entry<String, String> entry : headerMap.entrySet()) {
        headerBuilder.add(entry.getKey(), entry.getValue());
      }
    }
    Request request = new Request.Builder().url(url).delete().headers(headerBuilder.build()).build();
    Call call = CLIENT.newCall(request);
    try {
      try (Response response = call.execute()) {
        if (response.code() == 200 && response.body() != null) {
          return response.body().string();
        } else {
          String responseBody = response.body() == null ? "" : response.body().string();
          log.warn("url:" + url + " response code:" + response.code() + ", body:" + responseBody);
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public static String postFormData(String url, Map<String, String> headerMap, Map<String, Object> params) {
    return postFormData(url, headerMap, params, null);
  }

  public static String postFormData(String url, Map<String, String> headerMap, Map<String, Object> params,
                                    Integer timeoutSeconds) {
    Headers.Builder headerBuilder = new Headers.Builder();
    if (MapUtils.isNotEmpty(headerMap)) {
      for (Map.Entry<String, String> entry : headerMap.entrySet()) {
        headerBuilder.add(entry.getKey(), entry.getValue());
      }
    }

    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
    if (MapUtils.isNotEmpty(headerMap)) {
      for (Map.Entry<String, Object> entry : params.entrySet()) {
        Object value = entry.getValue();
        if (value instanceof File) {
          File file = (File) value;
          builder.addFormDataPart(entry.getKey(), file.getName(), RequestBody.create(null, file));
        } else {
          builder.addFormDataPart(entry.getKey(), String.valueOf(entry.getValue()));
        }
      }
    }

    Request request = new Request.Builder().url(url).post(builder.build()).headers(headerBuilder.build()).build();

    OkHttpClient httpClient = CLIENT;
    if (timeoutSeconds != null) {
      httpClient = CLIENT.newBuilder().callTimeout(timeoutSeconds, TimeUnit.SECONDS).build();
    }

    Call call = httpClient.newCall(request);
    try {
      try (Response response = call.execute()) {
        if (response.code() == 200 && response.body() != null) {
          return response.body().string();
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public static String get(String url) {
    return get(url, Collections.emptyMap(), Collections.emptyMap());
  }

  public static String get(String url, Map<String, String> params) {
    return get(url, params, Collections.emptyMap());
  }

  public static String get(String url, Map<String, String> params, Map<String, String> headerMap) {
    HttpUrl.Builder builder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
    for (Map.Entry<String, String> entry : params.entrySet()) {
      builder.addQueryParameter(entry.getKey(), entry.getValue());
    }
    Headers.Builder headerBuilder = new Headers.Builder();
    if (MapUtils.isNotEmpty(headerMap)) {
      for (Map.Entry<String, String> entry : headerMap.entrySet()) {
        headerBuilder.add(entry.getKey(), entry.getValue());
      }
    }
    Request request = new Request.Builder().url(builder.build()).get().headers(headerBuilder.build()).build();
    Call call = CLIENT.newCall(request);
    try {
      try (Response response = call.execute()) {
        if (response.code() == 200 && response.body() != null) {
          return response.body().string();
        } else {
          String responseBody = response.body() == null ? "" : response.body().string();
          log.warn("response code:" + response.code() + ", body:" + responseBody);
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public static String getRedirect(String url, Map<String, String> params) {
    HttpUrl.Builder builder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
    for (Map.Entry<String, String> entry : params.entrySet()) {
      builder.addQueryParameter(entry.getKey(), entry.getValue());
    }

    Request request = new Request.Builder().url(builder.build()).get().build();
    Call call = CLIENT.newCall(request);
    try {
      try (Response response = call.execute()) {
        if (response.code() == 200 && response.body() != null) {
          return response.request().url().toString();
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public static byte[] getByteArray(String url) {
    HttpUrl.Builder builder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
    Request request = new Request.Builder().url(builder.build()).get().build();

    Duration duration = Duration.ofSeconds(10);
    OkHttpClient httpClient = CLIENT.newBuilder()
      .callTimeout(duration)
      .connectTimeout(duration)
      .writeTimeout(duration)
      .readTimeout(duration)
      .build();

    Call call = httpClient.newCall(request);
    try {
      try (Response response = call.execute()) {
        if (response.code() == 200 && response.body() != null) {
          return Objects.requireNonNull(response.body()).bytes();
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  public static Response getResponse(String url) {
    HttpUrl.Builder builder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
    Request request = new Request.Builder().url(builder.build()).get().build();

    Duration duration = Duration.ofSeconds(10);
    OkHttpClient httpClient = CLIENT.newBuilder()
      .callTimeout(duration)
      .connectTimeout(duration)
      .writeTimeout(duration)
      .readTimeout(duration)
      .build();

    Call call = httpClient.newCall(request);
    try {
      return call.execute();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

}
