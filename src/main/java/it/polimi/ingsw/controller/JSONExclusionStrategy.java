package it.polimi.ingsw.controller;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class JSONExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(ExcludedFromJSON.class) != null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }

    /**
     * Create a specific annotation to exclude fields marked with this tag from JSON serialization and deserialization
     *
     * @apiNote <p>To serialize and deserialize a Player entity in JSON format should NOT be used the default
     * {@link com.google.gson.Gson Gson} constructor. Instead, a
     * {@link com.google.gson.GsonBuilder GsonBuilder} constructor should be used.</p>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface ExcludedFromJSON {}
}

