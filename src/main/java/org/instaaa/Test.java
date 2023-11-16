package org.instaaa;

import guo_cam.CameraController;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import processing.core.PApplet;
import wblut.geom.WB_Coord;
import wblut.geom.WB_Point;
import wblut.geom.WB_Polygon;
import wblut.processing.WB_Render;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * test ZLargestRectangle
 *
 * @author zhangbz ZHANG Baizhou
 * @project shopping_mall
 * @date 2021/9/7
 * @time 17:48
 */
public class Test extends PApplet {

    public static void main(String[] args) {
        PApplet.main("testUtils.Test5LargestRect");
    }

    /* ------------- settings ------------- */

    public void settings() {
        size(1000, 1000, P3D);
    }

    /* ------------- setup ------------- */

    private WB_Polygon poly;
    private WB_Polygon largestQuad;

    private WB_Render render;
    private CameraController gcam;

    public void setup() {
        this.gcam = new CameraController(this);
        this.render = new WB_Render(this);

        this.poly = new WB_Polygon(
                new WB_Coord[]{
                        new WB_Point(0, 0, 0),
                        new WB_Point(50, -12, 0),
                        new WB_Point(100, 25, 0),
                        new WB_Point(80, 50, 0),
                        new WB_Point(100, 100, 0),
                        new WB_Point(-20, 80, 0),
                        new WB_Point(0, 0, 0)
                }
        );

        LargestQuadProblem problem = new LargestQuadProblem(poly);
    }

    /* ------------- draw ------------- */

    public void draw() {
        background(255);

    }

    public void keyPressed() {
        if (key == 's') {
            String className = getClass().getSimpleName();
            save("./src/test/resources/exampleImgs/" + className + ".jpg");
        }
    }
}
