package fr.efrei.android.blakkat.model.provider;

import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class WrapperConverter extends Converter.Factory {
    private HashMap<Class<? extends Media>, Class<? extends IMediaWrapper>> wrappers;

    @Nullable
    @Override
    public Converter<ResponseBody, ? extends Media> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        Class<? extends Media> klazz = (Class<? extends Media>)type;
        Converter<ResponseBody, ? extends IMediaWrapper> delegate = retrofit.nextResponseBodyConverter(this, getWrapper(klazz), annotations);

        return body -> {
            IMediaWrapper<?> wrapper = delegate.convert(body);
            return wrapper.getMedia();
        };
    }

    private Class<? extends IMediaWrapper> getWrapper(Class<? extends Media> klazz) {
        if(wrappers == null) {
            wrappers = new HashMap<>();
            wrappers.put(Show.class, ShowWrapper.class);
        }
        Log.i("LOREMISPUMTAMER", wrappers.get(klazz).getCanonicalName());
        return wrappers.get(klazz);
    }
}
