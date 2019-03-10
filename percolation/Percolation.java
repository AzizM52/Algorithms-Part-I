/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static int gridDimension;
    private WeightedQuickUnionUF wQUUF;
    private boolean[][] siteMatrix;
    private int openSiteCount;
    private boolean[] bottomRowOpenSites;
    // Indicates whether the site represented by row & col is open or not.

    public Percolation(int n) {
        try {
            if (n <= 0) {
                throw new java.lang.IllegalArgumentException("Invalid argument");
            }
            wQUUF = new WeightedQuickUnionUF((n
                    * n
                    + 1)); // The two extra array indices are treated as the virtual source (pos 0) and virtual sink (pos grid*grid+1)
            siteMatrix = new boolean[n][n];
            bottomRowOpenSites = new boolean[n];
            setDimension(n);
            openSiteCount = 0;
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    siteMatrix[i][j] = false;
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setDimension(int n) {
        gridDimension = n;
    }

    public static int encode(int row, int col) { // Works on zero-based indexing
        return (row * gridDimension + col + 1);
    }

    public void open(int row, int col) { // Works on one-based indexing
        try {
            if (row < 1 || col < 1) {
                throw new java.lang.IllegalArgumentException("Invalid argument at open()");
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        row--;
        col--;
        if (!siteMatrix[row][col]) {
            if (row == 0) {
                wQUUF.union(0, encode(row, col));
                /* System.out.println(
                row + "," + col + " is connected to virtual source");*/
            }
            else if (row == gridDimension - 1) {
                bottomRowOpenSites[col] = true;
            }
            siteMatrix[row][col] = true;
            // System.out.println("Processing for node: " + row + "," + col);
            /* This routine will perform union operation on all the adjoining open sites of the newly opened site */
            if (col + 1 < gridDimension) {
                if ((siteMatrix[row][col + 1]) && (
                        !wQUUF.connected(encode(row, col), encode(row, col + 1))
                                && encode(row, col + 1) > 0
                                && encode(row, col + 1) < (gridDimension * gridDimension
                                + 1))) {
                    wQUUF.union(encode(row, col), encode(row, col + 1));
                    /* System.out.println(
                            row + "," + col + " is connected to " + (row) + "," + (col + 1));*/
                }
            }

            if (col - 1 >= 0) {
                if ((siteMatrix[row][col - 1]) && (
                        !wQUUF.connected(encode(row, col), encode(row, col - 1))
                                && encode(row, col - 1) >= 0
                                && encode(row, col - 1) <= (gridDimension * gridDimension
                                + 1))) {
                    wQUUF.union(encode(row, col), encode(row, col - 1));
                    /* System.out.println(
                            //row + "," + col + " is connected to " + (row) + "," + (col - 1));*/
                }
            }
            if (row + 1 < gridDimension) {
                if ((siteMatrix[row + 1][col]) && (
                        !wQUUF.connected(encode(row, col), encode(row + 1, col))
                                && encode(row + 1, col) >= 0
                                && encode(row + 1, col) <= (gridDimension * gridDimension
                                + 1))) {
                    wQUUF.union(encode(row, col), encode(row + 1, col));
                    /* System.out
                            .println(row + "," + col + " is connected to " + (row + 1) + ","
                                             + col);*/
                }
            }
            if (row - 1 >= 0) {
                if ((siteMatrix[row - 1][col]) && (
                        !wQUUF.connected(encode(row, col), encode(row - 1, col))
                                && encode(row - 1, col) >= 0
                                && encode(row - 1, col) <= (gridDimension * gridDimension
                                + 1))) {
                    wQUUF.union(encode(row, col), encode(row - 1, col));
                    /* System.out
                            .println(row + "," + col + " is connected to " + (row - 1) + ","
                                             + col);*/
                }
            }
            openSiteCount++;
        }

    }

    public boolean isOpen(int row, int col) {
        try {
            if (row < 1 || col < 1) {
                throw new java.lang.IllegalArgumentException("Invalid argument at isOpen()");
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return siteMatrix[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        try {
            if (row < 1 || col < 1) {
                throw new java.lang.IllegalArgumentException("Invalid argument at isFull()");
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return wQUUF.connected(0, encode(row - 1, col - 1));
    }

    public int noOfOpenSites() {
        return openSiteCount;
    }

    public boolean percolates() {
        int i = 0;
        boolean percFlag = false;
        while (!percFlag && i < gridDimension) {
            if (bottomRowOpenSites[i] && wQUUF.connected(0, encode(gridDimension - 1, i)))
                percFlag = true;
            i++;
        }
        return percFlag;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);      // input file
        int n = in.readInt();         // n-by-n percolation system
        Percolation p = new Percolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            p.open(i, j);
        }
    }
}
