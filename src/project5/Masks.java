/*
 * Alexis Santiago
 *Project 5: Image Gradients
 *
 */
package project5;

/**
 *
 * @author mahmoud
 */
public class Masks {

    private static float hMask1[][] = {
        {-1.0f, -2.0f, -1.0f},
        { 0.0f,  0.0f,  0.0f},
        { 1.0f,  2.0f,  1.0f}
    };

    /**
     * @return the hMask1
     */
    public static float[][] gethMask1() {
        return hMask1;
    }

}
