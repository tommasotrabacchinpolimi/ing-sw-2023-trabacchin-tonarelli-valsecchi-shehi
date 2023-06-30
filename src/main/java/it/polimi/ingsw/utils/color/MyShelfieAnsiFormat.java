package it.polimi.ingsw.utils.color;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Abstracts an Array of {@link MyShelfieAttribute}s.
 * Use it if you find this more readable than Attribute[].
 *
 * This class allows formatting text using ANSI attributes. The text can be formatted by applying one or more
 * ANSI attributes to it.
 *
 * The class stores an array of {@link MyShelfieAttribute}s and provides methods to format a string using these attributes.
 *
 * @author Tomamso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class MyShelfieAnsiFormat {

    // Starts with capacity=2 because that's how many attributes are used on average
    private final ArrayList<MyShelfieAttribute> _MyShelfie_attributes = new ArrayList<>(2);

    /**
     * Constructs a new MyShelfieAnsiFormat object with the given ANSI attributes.
     *
     * @param myShelfieAttributes All ANSI attributes to format a text.
     */
    public MyShelfieAnsiFormat(MyShelfieAttribute... myShelfieAttributes) {
        _MyShelfie_attributes.addAll(Arrays.asList(myShelfieAttributes));
    }

    /**
     * Formats the given text using the stored ANSI attributes.
     *
     * @param text The string to format.
     * @return The formatted string, ready to be printed.
     */
    public String format(String text) {
        return MyShelfieAnsi.colorize(text, this.toArray());
    }

    /**
     * Converts the internal list of MyShelfieAttributes to an array.
     *
     * @return The array of MyShelfieAttributes.
     */
    protected MyShelfieAttribute[] toArray() {
        return _MyShelfie_attributes.toArray(new MyShelfieAttribute[0]);
    }
}
