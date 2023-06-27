package it.polimi.ingsw.controller;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A custom exclusion strategy for Gson serialization and deserialization.
 * This strategy is used to exclude fields marked with the {@link ExcludedFromJSON} annotation from JSON processing.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 1.0
 * @since 03/04/2023
 */
public class JSONExclusionStrategy implements ExclusionStrategy {
    /**
     * Determines whether the specified field should be skipped during JSON processing based on the presence of the
     * {@link ExcludedFromJSON} annotation.
     * @param f The field being evaluated.
     * @return {@code true} if the field should be skipped, {@code false} otherwise.
     */
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(ExcludedFromJSON.class) != null;
    }

    /**
     * Determines whether the specified class should be skipped during JSON processing.
     * This implementation always returns {@code false}, indicating that no classes should be skipped.
     * @param clazz The class being evaluated.
     * @return {@code false}.
     */
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

