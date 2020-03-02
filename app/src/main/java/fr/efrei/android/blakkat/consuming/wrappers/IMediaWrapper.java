package fr.efrei.android.blakkat.consuming.wrappers;

import java.util.List;

import fr.efrei.android.blakkat.model.IMedia;

public interface IMediaWrapper<T extends IMedia> {
    T getMedia();
    List<T> getMedias();
}
