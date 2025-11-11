package com.techflow.ws.sys.domain;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Iterator;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;


public class ImageUtils {

	public static Dimension getSizeKeepAspectRation(Dimension original, Dimension target) {
		Dimension dim = new Dimension(
				(int) (original.width * target.height / original.height),
				(int) (original.height * target.width / original.width)
				);

		if (dim.height > target.height) {
			dim.height = target.height;
		}
		else {
			dim.width = target.width;
		}
		return dim;
	}
	
	public static String getMimeType(Path savedFile) {
		return getMimeType(savedFile.toFile());
	}

	public static String getMimeType(String filename) {
		return getMimeType(new File(filename));
	}

	public static String getMimeType(File file) {
		MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
		return fileTypeMap.getContentType(file.getName());
	}

	public static Dimension getDimension(Path file) throws IOException {
		return getDimension(file.toFile());
	}

	public static Dimension getDimension(File file) throws IOException {
		BufferedImage bimg = ImageIO.read(file);
		Dimension dim = new Dimension();
		dim.setSize(bimg.getWidth(), bimg.getHeight());
		return dim;
	}

	public static Dimension getDimension(String filename) throws IOException {
		return getDimension(new File(filename));
	}
	
	public static void resizeImage(String inputImagePath, String outputImagePath, Dimension dim, Boolean keepAspectRatio) throws IOException {
		resizeImage(new File(inputImagePath), new File(outputImagePath), (int)dim.getWidth(), (int)dim.getHeight(), keepAspectRatio);
	}
	
	
	public static void resizeImage(String inputImagePath, String outputImagePath, int width, int height, Boolean keepAspectRatio) throws IOException {
		resizeImage(new File(inputImagePath), new File(outputImagePath), width, height, keepAspectRatio);
	}
	
	public static void resizeImage(File inputFile, File outputFile, int width, int height, Boolean keepAspectRatio) throws IOException {
        // reads input image
        BufferedImage inputImage = ImageIO.read(inputFile);

        if(keepAspectRatio == null) keepAspectRatio = false;
        if(keepAspectRatio) {
        	Dimension dim = getSizeKeepAspectRation(new Dimension(inputImage.getWidth(), inputImage.getHeight()), new Dimension(width, height));
        	width = (int) dim.getWidth();
        	height = (int) dim.getHeight();
        }
        // calculates the scale
        //double scale = (double) scaledWidth / inputImage.getWidth();
        //int scaledHeight = (int) (inputImage.getHeight() * scale);

        // creates output image
        BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        g2d.dispose();

        // writes to output file
        ImageIO.write(outputImage, "jpg", outputFile);
    }
	
	public static void compressJPEG(File input, File output, float quality) throws IOException {
		BufferedImage image = ImageIO.read(input);
		OutputStream out = new FileOutputStream(output);

		// Get a ImageWriter for jpeg format.
		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
		ImageWriter writer = writers.next();

		// Create an ImageOutputStream.
		ImageOutputStream ios = ImageIO.createImageOutputStream(out);
		writer.setOutput(ios);

		// Set the compression quality
		ImageWriteParam param = writer.getDefaultWriteParam();
		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(quality); // Change the quality value as needed.

		// Write the image
		writer.write(null, new IIOImage(image, null, null), param);

		// Cleanup
		out.close();
		ios.close();
		writer.dispose();
	}

	public static void compressJPEG(String imageFilePath, String outputFilePath, float quality) throws IOException {
		compressJPEG(new File(imageFilePath), new File(outputFilePath), quality);
	}

	/*
	  Bits 24-31: Alpha channel (transparency, often ignored)
	  Bits 16-23: Red component (8 bits)
	  Bits 8-15: Green component (8 bits)
	  Bits 0-7: Blue component (8 bits)
	 */
	public static int[] intToRGB(int rgb) {
		int[] result = new int[3];
		result[0] = (rgb >> 16) & 0xff; // Extract red component (bits 16-23)
		result[1] = (rgb >> 8) & 0xff;  // Extract green component (bits 8-15)
		result[2] = rgb & 0xff;          // Extract blue component (bits 0-7)
		return result;
	}

	public static byte[] compressImage(byte[] imageData, float quality, int maxWidth) throws IOException {
		BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageData));
		
		// Calculate new dimensions while maintaining aspect ratio
		int originalWidth = originalImage.getWidth();
		int originalHeight = originalImage.getHeight();
		int newWidth = Math.min(originalWidth, maxWidth);
		int newHeight = (int) ((double) originalHeight / originalWidth * newWidth);
		
		// Create compressed image
		BufferedImage compressedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = compressedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
		g.dispose();
		
		// Write to byte array
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(compressedImage, "jpg", baos);
		return baos.toByteArray();
	}


}
