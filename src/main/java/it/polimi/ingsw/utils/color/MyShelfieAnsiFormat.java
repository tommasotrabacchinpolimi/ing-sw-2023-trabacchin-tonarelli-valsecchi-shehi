package it.polimi.ingsw.utils.color;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Abstracts an Array of {@link MyShelfieAttribute}s.
 * Use it if you find this more readable than Attribute[].
 */
public class MyShelfieAnsiFormat {

    // Starts with capacity=2 because that's how many attributes are used on average
    private final ArrayList<MyShelfieAttribute> _MyShelfie_attributes = new ArrayList<>(2);

    /**
     * @param myShelfieAttributes All ANSI attributes to format a text.
     */
    public MyShelfieAnsiFormat(MyShelfieAttribute... myShelfieAttributes) {
        _MyShelfie_attributes.addAll(Arrays.asList(myShelfieAttributes));
    }

    /**
     * @param text String to format.
     * @return The formatted string, ready to be printed.
     */
    public String format(String text) {
        return MyShelfieAnsi.colorize(text, this.toArray());
    }

    protected MyShelfieAttribute[] toArray() {
        return _MyShelfie_attributes.toArray(new MyShelfieAttribute[0]);
    }
}
