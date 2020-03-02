package fr.efrei.android.blakkat.consuming.wrappers;

import java.util.List;

import fr.efrei.android.blakkat.model.Media;

public interface IMediaWrapper<T extends Media> {
    T getMedia();
    List<T> getMedias();
}
