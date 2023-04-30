package it.polimi.ingsw.view.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@Deprecated
public class WrappedImageView extends ImageView {

    public WrappedImageView() {
        setPreserveRatio(true);
    }

    @Override
    public double minWidth(double width) {
        return 40;
    }

    @Override
    public double prefWidth(double width){
        Image img = getImage();

        if (img == null)
            return minWidth(width);

        return img.getWidth();
    }

    @Override
    public double maxWidth(double width){
        return 16384;
    }

    @Override
    public double minHeight(double height){
        return 40;
    }

    @Override
    public double prefHeight(double height){

        Image img = getImage();

        if (img == null)
            return minHeight(height);

        return img.getHeight();
    }

    @Override
    public double maxHeight(double height) {
        return 16384;
    }

    @Override
    public boolean isResizable(){
        return true;
    }

    @Override
    public void resize(double width, double height){
        setFitWidth(width);
        setFitHeight(height);

        centerImage();
    }

    private void centerImage() {
        Image img = getImage();
        if(img == null)
            return;

        double w = 0;
        double h = 0;

        double ratioX = getFitWidth() / img.getWidth();
        double ratioY = getFitHeight() / img.getHeight();

        double reduceCoeff = Math.min(ratioX, ratioY);

        w = img.getWidth() * reduceCoeff;
        h = img.getHeight() * reduceCoeff;

        setTranslateX((getFitWidth() - w) / 2);
        setTranslateY((getFitHeight() - h) / 2);
    }
}
