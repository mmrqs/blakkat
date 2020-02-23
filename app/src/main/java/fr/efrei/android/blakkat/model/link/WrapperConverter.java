package fr.efrei.android.blakkat.model.link;

import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;

import fr.efrei.android.blakkat.model.link.wrappers.IMediaWrapper;
import fr.efrei.android.blakkat.model.link.wrappers.ShowWrapper;
import fr.efrei.android.blakkat.model.link.medias.Media;
import fr.efrei.android.blakkat.model.link.medias.Show;
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
        return wrappers.get(klazz);
    }
}
