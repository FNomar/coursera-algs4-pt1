package src;/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final WeightedQuickUnionUF backwash;
    private boolean[][] sites;
    private int openedSites;
    private final int num;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        num = n;
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);
        backwash = new WeightedQuickUnionUF(n * n + 1);

        sites = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sites[i][j] = false;
            }
        }

        openedSites = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || row > num || col <= 0 || col > num) {
            throw new IllegalArgumentException();
        }
        int r = row - 1;
        int c = col - 1;

        if (!sites[r][c]) {
            sites[r][c] = true;
            openedSites++;
        }

        // upper site
        if (row > 1) {
            if (isOpen(row - 1, col)) {
                int p = num * r + c;
                int q = num * (r - 1) + c;
                weightedQuickUnionUF.union(p, q);
                backwash.union(p, q);
            }
        }

        // bottom site
        if (row < num) {
            if (isOpen(row + 1, col)) {
                int p = num * r + c;
                int q = num * (r + 1) + c;
                weightedQuickUnionUF.union(p, q);
                backwash.union(p, q);
            }
        }


        // left side
        if (col > 1) {
            if (isOpen(row, col - 1)) {
                int p = num * r + c;
                int q = num * r + c - 1;
                weightedQuickUnionUF.union(p, q);
                backwash.union(p, q);
            }
        }

        // right site
        if (col < num) {
            if (isOpen(row, col + 1)) {
                int p = num * r + c;
                int q = num * r + c + 1;
                weightedQuickUnionUF.union(p, q);
                backwash.union(p, q);
            }
        }

        // top row
        if (row == 1) {
            int p = num * r + c;
            int q = num * num;
            weightedQuickUnionUF.union(p, q);
            backwash.union(p, q);
        }

        // bottom row
        if (row == num) {
            int p = num * r + c;
            int q = num * num + 1;
            weightedQuickUnionUF.union(p, q);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > num || col <= 0 || col > num) {
            throw new IllegalArgumentException();
        }

        int r = row - 1;
        int c = col - 1;

        if (sites[r][c]) {
            return true;
        }
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > num || col <= 0 || col > num) {
            throw new IllegalArgumentException();
        }

        int r = row - 1;
        int c = col - 1;

        int topRoot = backwash.find(num * num);
        int siteRoot = backwash.find(num * r + c);

        if (topRoot == siteRoot) {
            return true;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openedSites;
    }

    // does the system percolate?
    public boolean percolates() {
        int topRoot = weightedQuickUnionUF.find(num * num);
        int bottomRoot = weightedQuickUnionUF.find(num * num + 1);
        if (topRoot == bottomRoot) {
            return true;
        }
        return false;
    }

    // test client (see below)
    public static void main(String[] args) {
    }
}
