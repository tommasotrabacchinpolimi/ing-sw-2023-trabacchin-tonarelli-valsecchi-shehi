package it.polimi.ingsw.view.gui.customcomponents.guitoolkit;

/**
 * This class represent the default edge radius value that
 * can be applied on different components to make their
 * corner smooth
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public enum MyShelfieRoundEdgeType {

    /**
     * Very slight-edge round
     */
    MINIMUM(0.10),

    /**
     * Small-edge round
     */
    SMALL(0.27),

    /**
     * Default-edge round
     */
    NORMAL(0.57),

    /**
     * Full-edge round mean that the component
     * will appear as a rectangle with two
     * circles applied to the edges
     */
    FULL(1.0);

    private final double edgeRadius;

    MyShelfieRoundEdgeType(double edgeRadius) {
        this.edgeRadius = edgeRadius;
    }

    public double getEdgeRadius(){
        return edgeRadius;
    }
}
