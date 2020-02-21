package fr.efrei.android.blakkat.model.provider;

public class Envelope<T> {
    T response;

    public Envelope() { }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
