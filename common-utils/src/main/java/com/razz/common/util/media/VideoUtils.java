package com.razz.common.util.media;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.Java2DFrameConverter;

public class VideoUtils {

	public static final String MP4_EXTENSION = "mp4";
	
	public static BufferedImage mp4ToImage(File mp4File, long timestamp, TimeUnit timeUnit) throws Exception {
		try( final FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(mp4File) ) {
			grabber.start();
			grabber.setTimestamp( timeUnit.toMicros(timestamp) );
		
			final Frame frame = grabber.grabFrame();
			final BufferedImage buffImg = new Java2DFrameConverter().getBufferedImage(frame);
			grabber.stop();
			return buffImg;
		}
	}
	
	public static BufferedImage subImage(BufferedImage buffImg, float leftRatio, float topRatio, float widthRatio, float heightRatio) {
		final int x = (int)(buffImg.getWidth() * leftRatio);
		final int y = (int)(buffImg.getHeight() * topRatio);
		final int w = (int)(buffImg.getWidth() * widthRatio);
		final int h = (int)(buffImg.getHeight() * heightRatio);
		return buffImg.getSubimage(x, y, w, h);
	}
	
	public static BufferedImage subImage(BufferedImage buffImg, int x, int y, int w, int h) {
		return buffImg.getSubimage(x, y, w, h);
	}
	
	public static void writeJpg(BufferedImage buffImg, File destJpg) throws Exception {
		ImageIO.write(buffImg, "jpg", destJpg);
	}
	
	public static void trim(File mp4SrcFile, File mp4DstFile, long startTime, long stopTime, TimeUnit timeUnit) throws Exception {
		// remove destination file 
		if(mp4DstFile.exists()) {
			mp4DstFile.delete();
		}
		
		try( final FrameGrabber grabber = new FFmpegFrameGrabber(mp4SrcFile) ) {
			// setup start/stop microseconds
			final long startMicros = timeUnit.toMicros(startTime);
			final long stopMicros = timeUnit.toMicros(stopTime);
			
			// setup grabber
			grabber.start();
			grabber.setTimestamp(startMicros);
			
			// setup recorder
			try( final FrameRecorder recorder = new FFmpegFrameRecorder(mp4DstFile, grabber.getAudioChannels()) ) {
				recorder.setFrameRate( grabber.getFrameRate() );
				recorder.setSampleRate( grabber.getSampleRate() );
				recorder.setImageWidth( grabber.getImageWidth() );
				recorder.setImageHeight( grabber.getImageHeight() );
				recorder.setVideoQuality(0); // important
				recorder.start();
						
				// record
				Frame frame;
				while( (frame = grabber.grab()) != null && recorder.getTimestamp() < stopMicros) {
				    recorder.record(frame);
				}
				
				// stop
				recorder.stop();
				grabber.stop();
			}
		}
	}
	
}
