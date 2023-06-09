package it.polimi.ingsw.utils.color;

class SimpleMyShelfieAttribute extends MyShelfieAttribute {

    private final String _code;

    /**
     * Constructor. Maps an attribute to an Ansi code.
     *
     * @param code Ansi code that represents the attribute.
     */
    SimpleMyShelfieAttribute(String code) {
        _code = code;
    }

    @Override
    public String toString() {
        return _code;
    }

}
