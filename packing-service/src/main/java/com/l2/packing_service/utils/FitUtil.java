package com.l2.packing_service.utils;


public final class FitUtil {

    private FitUtil() { }

    public static int maxFit(double[] outer, double[] inner) {
        if (outer.length != 3 || inner.length != 3) {
            throw new IllegalArgumentException("Arrays must have 3 dimensions");
        }

        int[][] perm = {
                {0,1,2}, {0,2,1}, {1,0,2},
                {1,2,0}, {2,0,1}, {2,1,0}
        };

        int best = 0;
        for (int[] p : perm) {
            int nH = (int)Math.floor(outer[0] / inner[p[0]]);
            int nW = (int)Math.floor(outer[1] / inner[p[1]]);
            int nL = (int)Math.floor(outer[2] / inner[p[2]]);
            best = Math.max(best, nH * nW * nL);
        }
        return best;
    }
}
