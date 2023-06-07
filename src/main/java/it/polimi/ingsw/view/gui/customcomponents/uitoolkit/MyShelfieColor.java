package it.polimi.ingsw.view.gui.customcomponents.uitoolkit;

import javafx.scene.paint.Color;

/**
 * A set of color designed for MyShelfie application
 *
 * @see Color
 */
public enum MyShelfieColor {
    /**
     * Charleston-Green color set
     */
    CHARLESTON(Color.rgb(28, 37, 41),
            Color.rgb(16, 27, 31),
            Color.rgb(18, 83, 110)),

    /**
     * Dark-Lava color set
     */
    DARK_LAVA(Color.rgb(71, 60, 51),
            Color.rgb(53, 39, 28),
            Color.rgb(149, 80, 24)),

    /**
     * Kobicha color set
     */
    KOBICHA(Color.rgb(101, 61, 30),
            Color.rgb(71, 40, 17),
            Color.rgb(168, 81, 14)),

    /**
     * Caput-Mortuum color set
     */
    CAPUT_MORTUUM(Color.rgb(95, 35, 41),
            Color.rgb(65, 19, 24),
            Color.rgb(162, 15, 30)),

    /**
     * Kobe color set
     */
    KOBE(Color.rgb(140, 53, 25),
            Color.rgb(94, 34, 13),
            Color.rgb(198, 57, 10)),

    /**
     * Rust color set
     */
    RUST(Color.rgb(180, 81, 34),
            Color.rgb(123, 52, 18),
            Color.rgb(240, 85, 13)),

    /**
     * Gamboge color set
     */
    GAMBOGE(Color.rgb(218, 158, 55),
            Color.rgb(158, 109, 24),
            Color.rgb(245, 177, 61)),

    /**
     * Bone color set
     */
    BONE(Color.rgb(230, 221, 199),
            Color.rgb(197, 167, 86),
            Color.rgb(248, 231, 191)),

    /**
     * Rifle-Green color set
     */
    RIFLE_GREEN(Color.rgb(77, 84, 57),
            Color.rgb(54, 63, 32),
            Color.rgb(128, 165, 26)),

    /**
     * Middle-Green-Yellow color set
     */
    MIDDLE_GREEN(Color.rgb(171, 194, 112),
            Color.rgb(123, 151, 50),
            Color.rgb(197, 237, 95));

    /**
     * The color associated with each type
     */
    private Color color;

    /**
     * Darken version of the color associated with each type
     */
    private Color darkenColor;

    /**
     * Lighten version of the color associated with each type
     */
    private Color lightenColor;

    /**
     * Construct a standard {@code MyShelfieColor}
     * with specified color
     *
     * @param color the color associated with
     *              the standard type
     */
    MyShelfieColor(Color color, Color darkenColor, Color lightenColor){
        this.color = color;
        this.darkenColor = darkenColor;
        this.lightenColor = lightenColor;
    }

    /**
     * Retrieves the color associated to the
     * {@code MyShelfieColor} type
     *
     * @return the color associated with the standard type
     */
    public Color getColor() {
        return color;
    }

    /**
     * Retrieves the darkened version of color associated to the
     * {@code MyShelfieColor} type
     *
     * @return the darken color associated with the standard type
     */
    public Color getDarkenColor(){
        return darkenColor;
    }

    /**
     * Retrieves the lightened version of color associated to the
     * {@code MyShelfieColor} type
     *
     * @return the lighten color associated with the standard type
     */
    public Color getLightenColor() {
        return lightenColor;
    }

    /**
     * Modifies the enumeration instance with the desired
     * transparency value and retrieves the color modified
     *
     * @apiNote please note that all colors in the calling
     * set are modified with the desired transparency
     *
     * @param alpha the desired transparency value
     */
    public Color alpha(double alpha){
        this.color = new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), alpha);
        this.darkenColor = new Color(darkenColor.getRed(), darkenColor.getGreen(), darkenColor.getBlue(), alpha);
        this.lightenColor = new Color(lightenColor.getRed(), lightenColor.getGreen(), lightenColor.getBlue(), alpha);

        return color;
    }

    /**
     * Retrieves the color associated to the
     * {@code MyShelfieColor} type with desired transparency
     *
     * @param alpha the desired transparency
     *
     * @return the shelfie color with the specified
     * transparency
     *
     * @apiNote note that the instance used is not modified,
     * for that purpose please refer to
     * {@link #alpha(double aplha)}
     */
    public Color getColor(double alpha) {
        return new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), alpha);
    }

    /**
     * Retrieves the darkened version of color associated to the
     * {@code MyShelfieColor} type with desired transparency
     *
     * @param alpha the desired transparency
     *
     * @return the darkened version of shelfie color with
     * the specified transparency
     *
     * @apiNote note that the instance used is not modified,
     * for that purpose please refer to
     * {@link #alpha(double aplha)}
     */
    public Color getDarkenColor(double alpha){
        return new Color(darkenColor.getRed(), darkenColor.getGreen(), darkenColor.getBlue(), alpha);
    }

    /**
     * Retrieves the lightened version of color associated to the
     * {@code MyShelfieColor} type with desired transparency
     *
     * @param alpha the desired transparency
     *
     * @return the lightened version of shelfie color with
     * the specified transparency
     *
     * @apiNote note that the instance used is not modified,
     * for that purpose please refer to
     * {@link #alpha(double aplha)}
     */
    public Color getLightenColor(double alpha){
        return new Color(lightenColor.getRed(), lightenColor.getGreen(), lightenColor.getBlue(), alpha);
    }
}
