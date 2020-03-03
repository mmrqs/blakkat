package fr.efrei.android.blakkat.consuming.converters;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import fr.efrei.android.blakkat.consuming.wrappers.AnimeWrapper;
import fr.efrei.android.blakkat.consuming.wrappers.ShowWrapper;
import fr.efrei.android.blakkat.model.IMedia;
import fr.efrei.android.blakkat.consuming.wrappers.IMediaWrapper;
import fr.efrei.android.blakkat.model.Show;
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
                return listMediaWrapperConverter(type, annotations, retrofit);
            } else {
                return mediaWrapperConverter(type, annotations, retrofit);
            }
        } catch (ClassCastException e) {
            return gsonConverter(type, annotations, retrofit);
        }
    }

    /**
     * Asks for a converter for the corresponding wrapper for the list of media
     * @return a lambda converter that extracts the list of media from its wrapper
     */
    private Converter<ResponseBody, ?> listMediaWrapperConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        //extracting the parameter type (in List<Foo>, it is Foo)
        ParameterizedType parameterizedType = (ParameterizedType)type;
        Type underlyingType = parameterizedType.getActualTypeArguments()[0];

        //casts the type to a media type, to enforce type checking
        Class<? extends IMedia> mediaKlazz = (Class<? extends IMedia>)underlyingType;

        //actual asking for the converter ; we get the wrapper matching list of media
        // and then a converter that matches this
        Converter<ResponseBody, ? extends IMediaWrapper> delegate = retrofit
                .nextResponseBodyConverter(this, WrapperRegistery.getWrapper(mediaKlazz), annotations);

        //this lambda returns the list of medias from the converted wrapper
        return body -> delegate.convert(body).getMedias();
    }

    /**
     * Asks for a converter for the corresponding wrapper for the media class
     * @return a lambda converter that extracts the media from its wrapper
     */
    private Converter<ResponseBody, ?> mediaWrapperConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        //casts the type to a media type, to enforce type checking
        Class<? extends IMedia> mediaKlazz = (Class<? extends IMedia>)type;

        //actual asking for the converter ; we get the wrapper matching the media class
        // and then a converter that matches this
        Converter<ResponseBody, ? extends IMediaWrapper> delegate = retrofit
                .nextResponseBodyConverter(this, WrapperRegistery.getWrapper(mediaKlazz), annotations);

        //this lambda returns the media from the converted wrapper
        return body -> delegate.convert(body).getMedia();
    }

    /**
     * This is the fallback
     * Asks for a converter for the type provided
     * @return a lambda converter that (should) directly convert json into an instance
     */
    private Converter<ResponseBody, ?> gsonConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        //asking for the converter matching the type
        Converter<ResponseBody, ? extends IMediaWrapper> delegate = retrofit
                .nextResponseBodyConverter(this, type, annotations);

        return body -> delegate.convert(body);
    }
}
