/***
The University of Texas at Dallas
*****/

/***
Name   : Ashwin Sai C
Course : ML - CS6375-003
Title  : Mini Project 4 Part - 1
Term   : Fall 2023
***/


import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
 

public class KMeans {
    public static void main(String [] args)
    {
    	int mul_initialization = 10;
    	double[] cmpRatio = new double[mul_initialization+1];
    	

		if (args.length < 3)
			{
			    System.out.println("Usage: Kmeans <input-image> <k> <output-image>");
			    return;
			}
		try
			{
			    BufferedImage originalImage = ImageIO.read(new File(args[0]));			    
			    int k=Integer.parseInt(args[1]);
			    for(int i=1; i<=mul_initialization; i++)
			    {
			    	System.out.println("x------------------------------------Initialization : "+i);
				    BufferedImage kmeansJpg = kmeans_helper(originalImage,k);
				    ImageIO.write(kmeansJpg, "jpg", new File("_k"+args[1]+"_Ini"+i+args[2]));
				    cmpRatio[i] = calculate_compressionRatio(args[0],"_k"+args[1]+"_Ini"+i+args[2]);				    
				}			   

				calculate_average_variance(cmpRatio);
			    
			}
		catch(IOException e)
			{
			    System.out.println(e.getMessage());
			}	
    }
    
    private static BufferedImage kmeans_helper(BufferedImage originalImage, int k)
    {
		int w=originalImage.getWidth();
		int h=originalImage.getHeight();
		BufferedImage kmeansImage = new BufferedImage(w,h,originalImage.getType());
		Graphics2D g = kmeansImage.createGraphics();
		g.drawImage(originalImage, 0, 0, w,h , null);
		// Read rgb values from the image
		int[] rgb=new int[w*h];
		int count=0;		
		for(int i=0;i<w;i++)
			{
			    for(int j=0;j<h;j++)
				    {
					rgb[count++]=kmeansImage.getRGB(i,j);
				    }
			}
		// Call kmeans algorithm: update the rgb values
		// System.out.println(rgb[0]);		
		kmeans(rgb,k);
		// System.out.println(rgb[0]);		
		// Write the new rgb values to the image
		count=0;
		for(int i=0;i<w;i++)
			{
			    for(int j=0;j<h;j++)
				    {
					kmeansImage.setRGB(i,j,rgb[count++]);
				    }
			}
		return kmeansImage;
    }

    // Your k-means code goes here
    // Update the array rgb by assigning each entry in the rgb array to its cluster center
   
    private static void kmeans(int[] rgb, int k)
    {
    	int max_iter_value = 20;       	 
        Color[] pixels = new Color[rgb.length];
        int[] final_assignments = new int[pixels.length];

        for (int i = 0; i < rgb.length; i++) 
	        {
	            pixels[i] = new Color(rgb[i]);
	            // System.out.println(rgb[i]);
	            // System.out.println(pixels[i]);
	            // System.exit(0);
	        }

        Color[] centers = new Color[k];
        for (int i = 0; i < k; i++) 
	        {
	            centers[i] = pixels[(int) (Math.random() * pixels.length)];
	            // System.out.println("Center : "+i+" "+centers[i]);
	        }

        // System.exit(0);

        for(int itercount = 1; itercount <= max_iter_value; itercount++)
        	{
        		// System.out.println("<-------Iteration : "+itercount+"-------->");
		        
		        int[] assignments = new int[pixels.length];
		        for (int i = 0; i < pixels.length; i++) 
			        {
			            double minDistance = Integer.MAX_VALUE;
			            for (int j = 0; j < k; j++) 
				            {
				                double distance = calculateColorDistance(pixels[i], centers[j]);
				                if (distance < minDistance) 
					                {
					                    minDistance = distance;
					                    assignments[i] = j;
					                }
				            }
			        }

		    	for(int i = 0; i < k; i++)
			    	{
			            int sumRed = 0, sumGreen = 0, sumBlue = 0, count = 0;
			            for (int j = 0; j < pixels.length; j++) 
				            {
				                if (assignments[j] == i) 
					                {
					                    sumRed += pixels[j].getRed();
					                    sumGreen += pixels[j].getGreen();
					                    sumBlue += pixels[j].getBlue();
					                    count++;
					                }
				            }
			            if (count != 0) 
				            {
				                centers[i] = new Color(sumRed / count, sumGreen / count, sumBlue / count);
				            }

				        // System.out.println("Updated Center : "+i+" "+centers[i]);
			        }
			    final_assignments = assignments.clone();
			    // System.out.println(final_assignments[0]+" "+assignments[0]);
		    }

        for (int i = 0; i < pixels.length; i++) 
	        {
	            rgb[i] = centers[final_assignments[i]].getRGB();
	        }   
    }

    private static double calculateColorDistance(Color c1, Color c2) 
    {
        int dr = c1.getRed() - c2.getRed();
        int dg = c1.getGreen() - c2.getGreen();
        int db = c1.getBlue() - c2.getBlue();
        
        double pixel_distance = Math.pow((dr * dr + dg * dg + db * db),0.5);
        
        return pixel_distance;
    }

    private static double calculate_compressionRatio(String orgfile_Path, String newfile_Path)
    {
    	File orgFile = new File(orgfile_Path);
    	File newFile = new File(newfile_Path);

    	double orgFile_size = orgFile.length();
    	double newfile_size = newFile.length();

    	// orgFile_size = orgFile_size / 1024.0;
    	// newfile_size = newfile_size / 1024.0;

    	// System.out.println("Original Size    : "+orgFile_size+" Bytes");
    	// System.out.println("Compression Size : "+newfile_size+" Bytes");

    	double compression_Ratio = (orgFile_size / newfile_size);

    	System.out.println("\nCompression Ratio: "+compression_Ratio);

    	return compression_Ratio;
    }

    private static void calculate_average_variance(double[] cmpRatio)
    {
    	double mean, sum=0,variance,sq_sum_diff=0;
    	for(int i=1;i<cmpRatio.length;i++)
    	{
    		sum += cmpRatio[i];
    	}

    	mean = sum / (cmpRatio.length-1);

    	for(int i=1;i<cmpRatio.length;i++)
    	{
    		sq_sum_diff += Math.pow((cmpRatio[i] - mean),2);
    	}

    	variance = sq_sum_diff / (cmpRatio.length-1);
    	
    	System.out.println("\nAverage Compression Ratio  : "+mean);
	   	System.out.println("Variance Compression Ratio : "+variance);

    }

}
