package it.polimi.ingsw.utils.color;

/**
 * Represents a simple MyShelfie attribute mapped to an ANSI code.
 *
 * This class extends the abstract class MyShelfieAttribute and provides a simple implementation
 * of a MyShelfie attribute by mapping it to an ANSI code.
 *
 * The attribute is represented by a string code, which is the ANSI code that represents the attribute.
 *
 * This class is used to create and manage simple MyShelfie attributes without additional properties or behaviors.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 1.0
 * @since 2023-06-28
 */
class SimpleMyShelfieAttribute extends MyShelfieAttribute {

    private final String _code;

    /**
     * Constructs a SimpleMyShelfieAttribute object with the given ANSI code.
     *
     * @param code The ANSI code that represents the attribute.
     */
    SimpleMyShelfieAttribute(String code) {
        _code = code;
    }

    /**
     * Returns the string representation of the attribute.
     *
     * @return The ANSI code that represents the attribute.
     */
    @Override
    public String toString() {
        return _code;
    }
}
