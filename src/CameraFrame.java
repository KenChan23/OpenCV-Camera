import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.opencv.highgui.VideoCapture;

public class CameraFrame extends JFrame implements ActionListener
{
	CameraPanel cameraPanel;
	
	CameraFrame()
	{
		System.loadLibrary("opencv_java2410");
		//	Capture a video. Parameter indicates the device ID of the camera on the PC.
		VideoCapture list = new VideoCapture(0);
		
		cameraPanel = new CameraPanel();
		Thread thread = new Thread(cameraPanel);
		
		JMenu cameraMenu = new JMenu("Camera");
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(cameraMenu);
		
		int i = 1;
		
		//	Find all the cameras available on the local PC.
		//	Create menu options to toggle amongst each camera.
		while(list.isOpened())
		{
			JMenuItem cameraOption = new JMenuItem("Camera " + i);
			cameraOption.addActionListener(this);
			cameraMenu.add(cameraOption);
			list.release();
			list = new VideoCapture(i);
			i++;
		}
		
		thread.start();
		add(cameraPanel);
		setJMenuBar(menuBar);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setVisible(true);
	}
	
	public static void main(String[] args)
	{
		CameraFrame cameraFrame = new CameraFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
