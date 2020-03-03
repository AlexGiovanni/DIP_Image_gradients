/*
 * Alexis Santiago
 *Project 5: Image Gradients
 *
 */
package project5;

/**
 *
 * @author faculty
 */
public class Gradients {
	
    public void handleBorder(byte[][] input, byte[][]output, int hmask, int vmask) {
        int h = input.length;
        int w = input[0].length;
        //top rows
        for (int i = 0; i < hmask; i++) {
            for (int j = 0; j < w; j++) {
                output[i][j] = input[i][j];
            }
        }
        //bottom rows
        for (int i = h - hmask; i < h; i++) {
            for (int j = 0; j < w; j++) {
                output[i][j] = input[i][j];
            }
        }
        //left columns
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < vmask; j++) {
                output[i][j] = input[i][j];
            }
        }
        //right columns
        for (int i = 0; i < h; i++) {
            for (int j = w - vmask; j < w; j++) {
                output[i][j] = input[i][j];
            }
        }
    }
    public void handleBorder2(byte[][] input, byte[][]output, int hmask, int vmask) {
        int h = input.length;
        int w = input[0].length;
        //top rows
        for (int i = 0; i < hmask; i++) {
            for (int j = 0; j < w; j++) {
                output[i][j] = (byte) 0;
            }
        }
        //bottom rows
        for (int i = h - hmask; i < h; i++) {
            for (int j = 0; j < w; j++) {
                output[i][j] = (byte) 0;
            }
        }
        //left columns
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < vmask; j++) {
                output[i][j] = (byte) 0;
            }
        }
        //right columns
        for (int i = 0; i < h; i++) {
            for (int j = w - vmask; j < w; j++) {
                output[i][j] = (byte) 0;
            }
        }
    }
    
    public void gradient1Sobel(byte[][]input, byte[][] output,float[][] hMask, float multiplyFactor) {
        float sumh;
        float sumv;
        // Decide border handling regions
        int h = (int) Math.floor((hMask.length/ 2));
        int v = (int) Math.floor((hMask[0].length/ 2));
                
        // Handle borders:
        handleBorder(input, output, h, v);

        // two masks: one for H-Edges and one for V-edges
        float[][] vMask = transpose(hMask); //trnaspose simply makes the columns into row and vice versa

        // Convolution
        int m2 = (int) Math.floor((hMask.length / 2));
        int n2 = (int) Math.floor((hMask[0].length / 2));
        
        for (int i = m2; i < output.length - m2; i++) {
            for (int j = n2; j < output[0].length - n2; j++) {
                sumh = 0.0f;
                sumv = 0.0f;
                for (int x = -m2; x <= m2; x++) {
                    for (int y = -n2; y <= n2; y++) {
                        sumh += hMask[x + m2][y + n2] * (input[i + x][ j + y] & 0xff);
                        sumv += vMask[x + m2][y + n2] * (input[i + x][ j + y] & 0xff);
                    }
                }
                sumh= (Math.abs(sumh));
                sumv= (Math.abs(sumv));

                output[i][j]= (byte) ImageIo.clip(multiplyFactor* (sumh +sumv));
            }
        }
    }
    
    public byte[][] sobelGradient(byte[][]input) {

        // Gradient
       byte[][] output= new byte[input.length][input[0].length];
        int gx, gy;
        for (int i = 1; i < output.length - 1; i++) 
        {
            for (int j = 1; j < output[0].length - 1; j++) 
            {
               gx= Math.abs(
               -(input[i - 1][ j-1] & 0xff)- 2*(input[i - 1][ j]   & 0xff) -(input[i - 1][ j+1]  & 0xff) 
               +(input[i + 1][ j-1] & 0xff)+ 2*(input[i + 1][ j]   & 0xff) +(input[i + 1][ j+1]  & 0xff) );
               
               gy= Math.abs(
               -(input[i - 1][ j-1] & 0xff)- 2*(input[i ] [j-1]   & 0xff) -(input[i + 1][ j-1]  & 0xff) 
               +(input[i - 1][ j+1] & 0xff)+ 2*(input[i ] [j+1]   & 0xff) +(input[i + 1][ j+1]  & 0xff) );
               
             output[i][j] = (byte) ImageIo.clip (1.0f/8.0f * (float) (gx+gy));
           
            }
        }
        return output;
    } 
    
    public byte[][] gradientOne(byte[][]input) {

        // Gradient
       byte[][] output= new byte[input.length][input[0].length];
        int g;
        for (int x = 1; x < output.length - 1; x++) //  x (rows)
        {
            for (int y = 1; y < output[0].length - 1; y++) //y (columns)
            {
             
               //   sqrt([f(x,y)-f(x+1,y)]^2  +  [f(x,y)-f(x,y+1]^2)
               g = (int) ((Math.sqrt(Math.pow((input[x][y]& 0xff) - (input[x+1][y]& 0xff),2 )))
                       +
                    ( Math.sqrt(Math.pow((input[x][y]& 0xff) - (input[x][y+1]& 0xff),2 ))));
                                                //1.0f/8.0f * 
             output[x][y] = (byte) ImageIo.clip ((float) (g));
           
            }
        }
        return output;
    } 
    
    public byte[][] gradientTwo(byte[][]input) {

        // Gradient
       byte[][] output= new byte[input.length][input[0].length];
        int g;
        for (int x = 1; x < output.length - 1; x++) //  x (rows)
        {
            for (int y = 1; y < output[0].length - 1; y++) //y (columns)
            {
             
               //  G(x,y) = abs|[f(x,y)-f(x+1,y)]|  +  abs|[f(x,y)-f(x,y+1]|
               g = (int) ((Math.abs((input[x][y]& 0xff) - (input[x+1][y]& 0xff) ))
                       +
                    ( Math.abs((input[x][y]& 0xff) - (input[x][y+1]& 0xff))));
                                                //1.0f/8.0f * 
             output[x][y] = (byte) ImageIo.clip ((float) (g));
           
            }
        }
        return output;
    } 


    public byte[][] robertGradient(byte[][]input) {

        // Gradient
       byte[][] output= new byte[input.length][input[0].length];
        int g;
        for (int x = 1; x < output.length - 1; x++) //  x (rows)
        {
            for (int y = 1; y < output[0].length - 1; y++) //y (columns)
            {
               //  G(x,y) = abs|[f(x,y)-f(x+1,y+1)]|  +  abs|[f(x+1,y)-f(x,y+1]|
               g = (int) ((Math.abs((input[x][y]& 0xff) - (input[x+1][y+1]& 0xff) ))
                       +
                    ( Math.abs((input[x+1][y]& 0xff) - (input[x][y+1]& 0xff))));
                                                //1.0f/8.0f * 
             output[x][y] = (byte) ImageIo.clip ((float) (g));
           
            }
        }
        return output;
    } 
    
    
    public byte[][] prewittGradient(byte[][]input) {

        // Gradient
       byte[][] output= new byte[input.length][input[0].length];
        int g;
        for (int x = 1; x < output.length - 1; x++) //  x (rows)
        {
            for (int y = 1; y < output[0].length - 1; y++) //y (columns)
            {
               
               g = (int) ((Math.abs(-(input[x-1][y-1]& 0xff) - (input[x-1][y]& 0xff) - (input[x-1][y+1]& 0xff)
               + (input[x+1][y-1]& 0xff) + (input[x+1][y]& 0xff) + (input[x+1][y+1]& 0xff)
               ))
                       +
               ( Math.abs(-(input[x-1][y-1]& 0xff) - (input[x][y-1]& 0xff) - (input[x+1][y-1]& 0xff)
                + (input[x-1][y+1]& 0xff) + (input[x][y+1]& 0xff) + (input[x+1][y+1]& 0xff)
               )));
                                                //1.0f/8.0f * 
             output[x][y] = (byte) ImageIo.clip ((float) (g));
           
            }
        }
        return output;
    } 


    
private float[][] transpose(float [][] matrix)
{
    float [][] transpose = new float[matrix.length][matrix[0].length];  //3 rows and 3 columns  
    
    for(int i=0;i<matrix.length;i++)
    {    
        for(int j=0;j<matrix[0].length;j++)
        {   
            transpose[i][j]=matrix[j][i];  
        }   
    }
    return transpose;
}

}
