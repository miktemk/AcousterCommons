package utils.mpeg.fload;

import java.io.IOException;

public class _TestFloat {

	public static void main(String[] args) {
		TestMpegMake ttt = new TestMpegMake();
		try {
			ttt.WriteMPEGSequence("test.mpg", 30, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
