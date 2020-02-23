package fr.efrei.android.blakkat.model.provider.wrappers;

import fr.efrei.android.blakkat.model.provider.medias.Media;

public interface IMediaWrapper<T extends Media> {
    T getMedia();
}
