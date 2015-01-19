import org.opencv.highgui.VideoCapture;


public class CameraFrame 
{
	CameraFrame()
	{
		System.loadLibrary("overcv_java2410");
		//	Capture a video. Parameter indicates the device ID of the camera on the PC.
		VideoCapture list = new VideoCapture(0);
	}
}
