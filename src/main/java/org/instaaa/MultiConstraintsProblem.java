package org.instaaa;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import wblut.geom.WB_Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * max: z = 3*x1 - 2*x2 + 5*x3
 * s.t.
 * x1 + 2*x2 - x3 <= 2
 * x1 + 4*x2 + x3 <=4
 * x1 + x2 <= 3
 * 4*x2 + x3 <= 6
 * xi in {0,1}
 *
 * 将上述表达式转换成标准形式
 * jmetal默认求最小值，所有约束式≥0
 * min: -3*x1 + 2*x2 - 5*x3
 * s.t.
 * -x1 - 2*x2 + x3 +2 >= 0
 * -x1 - 4*x2 - x3 +4 >= 0
 * -x1 - x2 + 3 >= 0
 * -4*x2 - x3 + 6 >= 0
 * */


public class MultiConstraintsProblem extends AbstractDoubleProblem {

    public MultiConstraintsProblem(){
        this.numberOfObjectives(1);//定义目标的数量
        this.numberOfConstraints(4);//定义约束的数量
        this.name("MultiConstraintsProblem");

        //定义变量x1,x2,x3的最小值
        List<Double> lowerLimit = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0));
        //定义变量x1,x2,x3的最大值        
        List<Double> upperLimit = new ArrayList<>(Arrays.asList(1.0, 1.0, 1.0));
        //设置变量的取值范围        
        this.variableBounds(lowerLimit, upperLimit);
    }


    //设置目标，用于评价解，默认求目标的最小值
    @Override
    public DoubleSolution evaluate(DoubleSolution solution) {
        double x1 = (Double)solution.variables().get(0);
        double x2 = (Double)solution.variables().get(1);
        double x3 = (Double)solution.variables().get(2);
        solution.objectives()[0] = -3*x1 + 2*x2 - 5*x3;

        this.evaluateConstraints(solution);
        return solution;
    }


    //设置约束式，用于评价解，默认约束式≥0时，不违背约束条件
    //需注意，在构建算法时设置OverallConstraintViolationDegreeComparator，约束式评价才起作用
    public void evaluateConstraints(DoubleSolution solution) {
        double[] constraint = new double[this.numberOfConstraints()];
        double x1 = (Double)solution.variables().get(0);
        double x2 = (Double)solution.variables().get(1);
        double x3 = (Double)solution.variables().get(2);
        constraint[0] = -x1 - 2*x2 + x3 +2;
        constraint[1] = -x1 - 4*x2 - x3 +4;
        constraint[2] = -x1 - x2 + 3;
        constraint[3] = -4*x2 - x3 + 6;
    }
}