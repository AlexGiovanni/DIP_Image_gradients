/*
 * Alexis Santiago
 *Project 5: Image Gradients
 *
 */
package project5;

import java.awt.image.BufferedImage;
/**
 *
 * @author alexi
 */
public class Project5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("____________Project5___________");
       //read image 
       BufferedImage inImage   = ImageIo.readImage("utb.jpg");
       //convert to gray
       BufferedImage grayImage = ImageIo.toGray(inImage);
       ImageIo.writeImage(grayImage, "jpg", "grayed.jpg");  
       //get image values as array
       byte[][] grayInput  = ImageIo.getGrayByteImageArray2DFromBufferedImage(grayImage);
       //create array to store sobel image values with convolution
       byte[][] sobel  = new byte[grayInput.length][grayInput[0].length];
       
       // Gradient Calculations
       Gradients gradientObj = new Gradients();
       
       //Sobel with Mask and convolution___________________________________________________
       gradientObj.gradient1Sobel(grayInput, sobel, Masks.gethMask1(),1.0f/8);
       
       // with no Masks
       byte[][] sobel2 = gradientObj.sobelGradient(grayInput);
       
       // Output sobel images
       BufferedImage graient1image= ImageIo.setGrayByteImageArray2DToBufferedImage(sobel);
       BufferedImage graient2image= ImageIo.setGrayByteImageArray2DToBufferedImage(sobel2);
       ImageIo.writeImage(graient1image, "jpg", "sobelGradient1image.jpg"); 
       ImageIo.writeImage(graient2image, "jpg", "sobelGradient2image.jpg"); 
       
       //Basic Gradient-01_________________________________________________________________
       Gradients gradientOne = new Gradients();
       byte[][] gradient1 = gradientOne.gradientOne(grayInput);
       // Output gradient 1 image 
       BufferedImage basicGradient01= ImageIo.setGrayByteImageArray2DToBufferedImage(gradient1);
       ImageIo.writeImage(basicGradient01, "jpg", "Basicgradient01.jpg"); 
       
       //Basic Gradient-02___________________________________________________________________
       Gradients gradientTwo = new Gradients();
       byte[][] gradient2 = gradientTwo.gradientTwo(grayInput);
       // Output gradient 1 image 
       BufferedImage basicGradient02 = ImageIo.setGrayByteImageArray2DToBufferedImage(gradient2);
       ImageIo.writeImage(basicGradient02, "jpg", "Basicgradient02.jpg"); 
       
       //Robert Gradient___________________________________________________________________
       Gradients robertGradient = new Gradients();
       byte[][] rGradient = robertGradient.robertGradient(grayInput);
       // Output gradient 1 image 
       BufferedImage robGradient = ImageIo.setGrayByteImageArray2DToBufferedImage(rGradient);
       ImageIo.writeImage(robGradient, "jpg", "RobertGradient.jpg"); 
       
       //Prewitt Gradient___________________________________________________________________
       Gradients prewittGradient = new Gradients();
       byte[][] pGradient = prewittGradient.prewittGradient(grayInput);
       // Output gradient 1 image 
       BufferedImage preGradient = ImageIo.setGrayByteImageArray2DToBufferedImage(pGradient);
       ImageIo.writeImage(preGradient, "jpg", "PrewittGradient.jpg"); 
       
       
       
      
        
    }
    
}
