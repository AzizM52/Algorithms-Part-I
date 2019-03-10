/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private double[] experimentResult;
    private double mean;
    private double stdDev;
    private double confidenceLo;
    private double confidenceHi;
    private boolean execFlag;

    public PercolationStats(int n, int trials) {
        try {
            if (n <= 0 || trials <= 0) {
                execFlag = false;
                throw new java.lang.IllegalArgumentException("Invaild arguments");
            }
            else {
                execFlag = true;
                int counter = 0;
                experimentResult = new double[trials];
                while (counter < trials) {
                    Percolation p = new Percolation(n);
                    boolean percFlag = false;
                    while (!percFlag) {
                        int row = StdRandom.uniform(1, n + 1);
                        int col = StdRandom.uniform(1, n + 1);
                        p.open(row, col);
                        if (p.percolates())
                            percFlag = true;
                    }
                    experimentResult[counter] = (double) p.noOfOpenSites() / (n * n);
                    counter++;
                }
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public double mean() {
        mean = StdStats.mean(experimentResult);
        return mean;
    }

    public double stdDev() {
        stdDev = StdStats.stddev(experimentResult);
        return stdDev;
    }

    public double confidenceLo() {
        confidenceLo = mean - (1.96 * stdDev) / Math.sqrt(experimentResult.length);
        return confidenceLo;
    }

    public double confidenceHi() {
        confidenceHi = mean + (1.96 * stdDev) / Math.sqrt(experimentResult.length);
        return confidenceHi;
    }

    public static void main(String[] args) {
        Stopwatch sW = new Stopwatch();
        int argsOne = Integer.parseInt(args[0]);
        int argsTwo = Integer.parseInt(args[1]);
        PercolationStats pS = new PercolationStats(argsOne, argsTwo);
        if (pS.execFlag) {
            double elapsedTime = sW.elapsedTime();
        /* for (int i = 0; i < pS.experimentResult.length; i++)
        System.out.print(pS.experimentResult[i] + "\t");*/
            System.out.println("Time required: " + elapsedTime);
            System.out.println("Mean = " + pS.mean());
            System.out.println("stddev = " + pS.stdDev());
            System.out.println("95% confidence interval = [" + pS.confidenceLo() + ", "
                                       + pS.confidenceHi() + "]");
        }
    }
}
