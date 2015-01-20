import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;


public class CameraPanel extends JPanel implements Runnable, ActionListener
{
	BufferedImage image;
	VideoCapture capture;
	JButton screenshotButton;
	
	CameraPanel()
	{
		screenshotButton = new JButton("Screenshot");
		screenshotButton.addActionListener(this);
		add(screenshotButton);
	}
	
	//	Capture a screenshot from the camera feed.
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		File output = new File("screenshot0.png");
		int i = 0;
		while(output.exists())
		{
			i++;
			output = new File("screenshot" + i + ".png");
		}
		try 
		{
			ImageIO.write(image, "png", output);
		} 
		catch (IOException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	//	Read images captured by the camera, and draw them to the screen
	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		System.loadLibrary("opencv_java2410");
		capture = new VideoCapture(0);
		//	Capture the images within a matrix (frame-by-frame)
		Mat webcam_image = new Mat();
		
		if(capture.isOpened())
		{
			while(true)
			{
				capture.read(webcam_image);
				if(!webcam_image.empty())
				{
					//	Handle sizing of window.
					JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
					topFrame.setSize(webcam_image.width() + 20, webcam_image.height() + 40);
					MatToBufferedImage(webcam_image);
					repaint();
				}
			}
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if(this.image == null)
		{
			return;
		}
		
		g.drawImage(image, 10, 40, image.getWidth(), image.getHeight(), null);
	}
	
	public void MatToBufferedImage(Mat matBGR)
	{
		int width = matBGR.width();
		int height = matBGR.height();
		int channels = matBGR.channels();
		
		byte[] source = new byte[width * height * channels];
		//	Start from the first pixel and input into the source.
		matBGR.get(0, 0, source);
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		
		final byte[] target = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
		System.arraycopy(source, 0, target, 0, source.length);
	}
	
	public void switchCamera(int x)
	{
		capture = new VideoCapture(x);
	}
}
