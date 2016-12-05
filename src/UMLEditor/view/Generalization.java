package UMLEditor.view;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

/**
 * Created by beltre on 12/4/16.
 */
public class Generalization extends UmlLine {

    private Rotate rotate = new Rotate();
    private Polygon polygon = new Polygon();

    public Generalization(Anchors a1, Anchors a2){
        super(a1, a2);

        //coordinates to make a triangle
        polygon.getPoints().addAll(new Double[]{0.0, 0.0,
                -12.0, -7.0,
                -12.0, 7.0}
        );
        polygon.setFill(Color.WHITE);
        polygon.setStrokeWidth(1);
        polygon.setStroke(Color.BLACK);

        double deltaX = this.getStartY() - this.getEndY();
        double deltaY = this.getEndX() - this.getStartX();
        double slopeInDegrees = Math.toDegrees(Math.atan2(deltaX, deltaY));

        polygon.getTransforms().add(rotate);
        rotate.setAngle(-slopeInDegrees);
        polygon.setTranslateX(this.getEndX());
        polygon.setTranslateY(this.getEndY());

    }

    @Override
    public void updateAnchorPoints()
    {
        double min = 999999999;
        point1Int = 0;
        point2Int = 0;
        for (int i = 0; i < anchorPoint1.getAnchorCount(); ++i)
        {
            startingAnchor = new Point2D(anchorPoint1.getAnchorPoint(i).getX(),
                    anchorPoint1.getAnchorPoint(i).getY());
            for (int j = 0; j < anchorPoint2.getAnchorCount(); ++j)
            {
                endingAnchor = new Point2D(anchorPoint2.getAnchorPoint(j).getX(),
                        anchorPoint2.getAnchorPoint(j).getY());
                if (startingAnchor.distance(endingAnchor) < min)
                {
                    min = startingAnchor.distance(endingAnchor);
                    point1Int = i;
                    point2Int = j;
                }
            }
        }
        this.setStartX(anchorPoint1.getAnchorPoint(point1Int).getX());
        this.setStartY(anchorPoint1.getAnchorPoint(point1Int).getY());
        this.setEndX(anchorPoint2.getAnchorPoint(point2Int).getX());
        this.setEndY(anchorPoint2.getAnchorPoint(point2Int).getY());

        double deltaX = this.getStartY() - this.getEndY();
        double deltaY = this.getEndX() - this.getStartX();
        //gets you the degress of the x axis(on the left) of the second clicked node
        double slopeInDegrees = Math.toDegrees(Math.atan2(deltaX,deltaY));

        if (polygon != null)
        {
            polygon.setTranslateX(this.getEndX());
            polygon.setTranslateY(this.getEndY());
            rotate.setAngle(-slopeInDegrees);

        }
    }

    public Polygon filledArrow(){ return polygon; }

    @Override
    public void deleteSelf()
    {
        Pane pane = (Pane)this.getParent();
        pane.getChildren().remove(polygon);
        anchorPoint1.deleteLine(id);
        anchorPoint2.deleteLine(id);
    }

}
