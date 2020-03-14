package fr.efrei.android.blakkat.helpers;

import java.util.List;

import fr.efrei.android.blakkat.model.Record.SuggestionRecord;

public class SuggestionHelper {

    public List<SuggestionRecord> getAllSugestions() {
        List<SuggestionRecord> l = SuggestionRecord.listAll(SuggestionRecord.class);
        return l;
    }
}
