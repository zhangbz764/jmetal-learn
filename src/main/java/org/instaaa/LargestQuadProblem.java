package org.instaaa;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import wblut.geom.WB_AABB;
import wblut.geom.WB_Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * description
 *
 * @author zbz_lennovo
 * @project jmetal-learn
 * @date 2023/11/16
 * @time 11:50
 */
public class LargestQuadProblem extends AbstractDoubleProblem {
    public LargestQuadProblem(WB_Polygon polygon) {
        WB_AABB aabb = polygon.getAABB();
        double minx = aabb.getMinX();
        double maxx = aabb.getMaxX();
        double miny = aabb.getMinY();
        double maxy = aabb.getMaxY();

        this.numberOfObjectives(1);//定义目标的数量
        this.numberOfConstraints(4);//定义约束的数量
        this.name("LargestQuadProblem");

        //定义变量x1,x2,x3的最小值
        List<Double> lowerLimit = new ArrayList<>(Arrays.asList(minx, miny, minx, miny, minx, miny, minx, miny));
        //定义变量x1,x2,x3的最大值
        List<Double> upperLimit = new ArrayList<>(Arrays.asList(maxx, maxy, maxx, maxy, maxx, maxy, maxx, maxy));
        //设置变量的取值范围
        this.variableBounds(lowerLimit, upperLimit);
    }


    //设置目标，用于评价解，默认求目标的最小值
    @Override
    public DoubleSolution evaluate(DoubleSolution solution) {
        double x1 = (Double) solution.variables().get(0);
        double y1 = (Double) solution.variables().get(1);
        double x2 = (Double) solution.variables().get(2);
        double y2 = (Double) solution.variables().get(3);
        double x3 = (Double) solution.variables().get(4);
        double y3 = (Double) solution.variables().get(5);
        double x4 = (Double) solution.variables().get(6);
        double y4 = (Double) solution.variables().get(7);
        solution.objectives()[0] = -x2 * y1 + x1 * y2 - x3 * y2 + x2 * y3 - x4 * y3 + x3 * y4 - x1 * y4 + x4 * y1;

        this.evaluateConstraints(solution);
        return solution;
    }

    //设置约束式，用于评价解，默认约束式≥0时，不违背约束条件
    //需注意，在构建算法时设置OverallConstraintViolationDegreeComparator，约束式评价才起作用
    public void evaluateConstraints(DoubleSolution solution) {
        double[] constraint = new double[this.numberOfConstraints()];
        double x1 = (Double) solution.variables().get(0);
        double x2 = (Double) solution.variables().get(1);
        double x3 = (Double) solution.variables().get(2);
        constraint[0] = -x1 - 2 * x2 + x3 + 2;
        constraint[1] = -x1 - 4 * x2 - x3 + 4;
        constraint[2] = -x1 - x2 + 3;
        constraint[3] = -4 * x2 - x3 + 6;
    }
}
