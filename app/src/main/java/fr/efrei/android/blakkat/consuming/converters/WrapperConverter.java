package fr.efrei.android.blakkat.consuming.converters;

import androidx.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

import fr.efrei.android.blakkat.model.IMedia;
import fr.efrei.android.blakkat.consuming.wrappers.IMediaWrapper;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * This class allows us to automatically convert our model classes from malformed json
 * without needing us to actually write a conversion class
 * We rather trick the converter into parsing json into a matching wrapper
 */
public class WrapperConverter extends Converter.Factory {
    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        try {
            if(type.toString().contains("List")) {
                return listWrapperConverter(type, annotations, retrofit);
            } else {
                return instanceWrapperConverter(type, annotations, retrofit);
            }
        } catch (ClassCastException e) {
            return gsonConverter(type, annotations, retrofit);
        }
    }

    /**
     * Asks for a converter for the corresponding wrapper for the list of media
     * @return a lambda converter that extracts the list of media from its wrapper
     */
    private Converter<ResponseBody, ?> listWrapperConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        //extracting the parameter type (in List<Foo>, it is Foo)
        ParameterizedType parameterizedType = (ParameterizedType)type;
        Type underlyingType = parameterizedType.getActualTypeArguments()[0];

        //casts the type to a media type, to enforce type checking
        Class<? extends IMedia> mediaKlazz = (Class<? extends IMedia>)underlyingType;

        // checking if the media has a registered wrapper
        if(!WrapperRegistry.hasListWrapper(mediaKlazz))
            return gsonConverter(type, annotations, retrofit);

        // actual asking for the converter ; we get the wrapper matching the media list class
        // and then a converter that matches this
        Converter<ResponseBody, ? extends IMediaWrapper> delegate = retrofit
                .nextResponseBodyConverter(this, WrapperRegistry
                        .getListWrapper(mediaKlazz), annotations);

        //this lambda returns the list of medias from the converted wrapper
        return body -> Objects.requireNonNull(delegate.convert(body)).getMedias();
    }

    /**
     * Asks for a converter for the corresponding wrapper for the media class
     * @return a lambda converter that extracts the media from its wrapper
     */
    private Converter<ResponseBody, ?> instanceWrapperConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        //casts the type to a media type, to enforce type checking
        Class<? extends IMedia> mediaKlazz = (Class<? extends IMedia>)type;

        // checking if the media has a registered wrapper
        if(!WrapperRegistry.hasInstanceWrapper(mediaKlazz))
            return gsonConverter(type, annotations, retrofit);

        //actual asking for the converter ; we get the wrapper matching the media class
        // and then a converter that matches this
        Converter<ResponseBody, ? extends IMediaWrapper> delegate = retrofit
                .nextResponseBodyConverter(this, WrapperRegistry
                        .getInstanceWrapper(mediaKlazz), annotations);

        //this lambda returns the media from the converted wrapper
        return body -> Objects.requireNonNull(delegate.convert(body)).getMedia();
    }

    /**
     * This is the fallback
     * Asks for a converter for the type provided
     * @return a lambda converter that (should) directly convert json into an instance
     */
    private Converter<ResponseBody, ?> gsonConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        //asking for the converter matching the type
        Converter<ResponseBody, ?> delegate = retrofit
                .nextResponseBodyConverter(this, type, annotations);

        return delegate::convert;
    }
}
