package org.instaaa;

import org.uma.jmetal.component.algorithm.EvolutionaryAlgorithm;
import org.uma.jmetal.component.algorithm.multiobjective.NSGAIIBuilder;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.crossover.impl.SBXCrossover;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.mutation.impl.PolynomialMutation;
import org.uma.jmetal.operator.selection.SelectionOperator;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.comparator.constraintcomparator.impl.OverallConstraintViolationDegreeComparator;
import org.uma.jmetal.util.comparator.dominanceComparator.impl.DominanceWithConstraintsComparator;
import org.uma.jmetal.util.ranking.Ranking;
import org.uma.jmetal.util.ranking.impl.FastNonDominatedSortRanking;

import java.util.List;

/**
 * @author wangbei
 * @version 1.0
 * @description //todo
 * @date 2023/04/21 11:49
 * Inst. AAA, S-ARCH, Southeast University
 */
public class MultiConstraints {
    public static void main(String[] args) {
        //定义优化问题
        MultiConstraintsProblem problem = new MultiConstraintsProblem();

        //配置交叉算子
        CrossoverOperator<DoubleSolution> crossover;
        double crossoverProbability = 0.9 ;
        double crossoverDistributionIndex = 20.0 ;
        crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex) ;

        //配置变异算子
        MutationOperator<DoubleSolution> mutation;
        double mutationProbability = 1.0 / problem.numberOfVariables() ;
        double mutationDistributionIndex = 20.0 ;
        mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex) ;

        //配置选择算子
        SelectionOperator<List<DoubleSolution>, DoubleSolution> selection = new BinaryTournamentSelection<DoubleSolution>(
                new RankingAndCrowdingDistanceComparator<DoubleSolution>());

        //种群数量
        int populationSize  = 100 ;
        int offspringPopulationSize = 100 ;

        //设置排序方式为总体约束违反度
        Ranking<DoubleSolution> ranking = new FastNonDominatedSortRanking<>(
                new DominanceWithConstraintsComparator<>(
                        new OverallConstraintViolationDegreeComparator<>()));


        //将组件注册到algorithm
        EvolutionaryAlgorithm<DoubleSolution> algorithm = new NSGAIIBuilder<>(
                problem,
                populationSize,
                offspringPopulationSize,
                crossover,
                mutation)
                .setRanking(ranking)
                .build();

        //运行算法
        algorithm.run();

        //获取结果集
        List<DoubleSolution> population = algorithm.getResult() ;

        //打印结果
        System.out.println("var "+population.get(0).variables());
        System.out.println("obj "+population.get(0).objectives()[0]);
        //JMetalLogger.logger.info("Total execution time : " + algorithm.getTotalComputingTime() + "ms");



    }


}