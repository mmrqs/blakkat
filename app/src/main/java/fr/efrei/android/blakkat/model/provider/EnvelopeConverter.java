package fr.efrei.android.blakkat.model.provider;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class EnvelopeConverter extends Converter.Factory {
    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        Type envelopedType = TypeToken.getParameterized(Envelope.class, type).getType();

        Converter<ResponseBody, Envelope<?>> delegate = retrofit.nextResponseBodyConverter(this, envelopedType, annotations);

        return body -> {
            try {
                Envelope<?> envelope = new Gson().fromJson(body.string(), envelopedType);
                Log.i("tamer", envelope.getClass().toString());
                return envelope.response;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        };
    }
}
