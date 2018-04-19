package com.movies.icuba.mymovieworld.other;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by icuba on 2/8/2018.
 */

public class RequestInterceptor implements Interceptor {
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl url = request.url().newBuilder().addQueryParameter("api_key","adc8cf4fe60c9e74d4260be93bdda240").build();
            request = request.newBuilder().url(url).build();
            return chain.proceed(request);
        }
    }
