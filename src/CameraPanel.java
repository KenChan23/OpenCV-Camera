import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;

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
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		
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
}
