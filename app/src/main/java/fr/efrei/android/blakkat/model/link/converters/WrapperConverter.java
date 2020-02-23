package fr.efrei.android.blakkat.model.link.converters;

import androidx.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import fr.efrei.android.blakkat.model.link.medias.Media;
import fr.efrei.android.blakkat.model.link.wrappers.IMediaWrapper;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class WrapperConverter extends Converter.Factory {
    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        try {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            Type underlyingType = parameterizedType.getActualTypeArguments()[0];
            Class<? extends Media> mediaKlazz = (Class<? extends Media>)underlyingType;
            Converter<ResponseBody, ? extends IMediaWrapper> delegate = retrofit
                    .nextResponseBodyConverter(this, WrapperRegistery.getWrapper(mediaKlazz), annotations);

            return body -> delegate.convert(body).getMedias();
        } catch (ClassCastException e) {
            Class<? extends Media> mediaKlazz = (Class<? extends Media>)type;
            Converter<ResponseBody, ? extends IMediaWrapper> delegate = retrofit
                    .nextResponseBodyConverter(this, WrapperRegistery.getWrapper(mediaKlazz), annotations);

            return body -> delegate.convert(body).getMedia();
        }
    }

}
