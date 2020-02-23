package fr.efrei.android.blakkat.model.link.wrappers;

import java.util.List;

import fr.efrei.android.blakkat.model.link.medias.Media;

public interface IMediaWrapper<T extends Media> {
    T getMedia();
    List<T> getMedias();
}
