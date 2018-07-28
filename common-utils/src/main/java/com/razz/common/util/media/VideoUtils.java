package com.razz.common.util.media;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.MediaToolAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IAudioSamplesEvent;
import com.xuggle.mediatool.event.IRawMediaEvent;
import com.xuggle.mediatool.event.IVideoPictureEvent;

public class VideoUtils {

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
	
	public static void trim(File mp4SrcFile, File mp4DstFile, long begin, long end, TimeUnit timeUnit) throws Exception {
		trim(mp4SrcFile, mp4DstFile, begin, end, timeUnit, true, true);
	}
	
	public static void trim(File mp4SrcFile, File mp4DstFile, long begin, long end, TimeUnit timeUnit, 
			boolean includeAudio, boolean includeVideo) throws Exception 
	{
		// remove destination file 
		if(mp4DstFile.exists()) {
			mp4DstFile.delete();
		}
		
		// create new Trimmer
		final Trimmer trimmer = new Trimmer(begin, end, timeUnit, includeAudio, includeVideo);
		
		// reader
		final IMediaReader reader = ToolFactory.makeReader(mp4SrcFile.getPath());
		reader.addListener(trimmer);
		
		// writer
		final IMediaWriter writer = ToolFactory.makeWriter(mp4DstFile.getPath(), reader);
		trimmer.addListener(writer);
		 
		// trim video
		while (reader.readPacket() == null);
	}
	
	static class Trimmer extends MediaToolAdapter {

		final private long begin, end;
		final private TimeUnit timeUnit;
		final private boolean includeAudio, includeVideo;
		
		Trimmer(long begin, long end, TimeUnit timeUnit, boolean includeAudio, boolean includeVideo) {
			this.begin = begin;
			this.end = end;
			this.timeUnit = timeUnit;
			this.includeAudio = includeAudio;
			this.includeVideo = includeVideo;
		}
		
		boolean capture(IRawMediaEvent event) {
			final long mark = timeUnit.convert(event.getTimeStamp(), event.getTimeUnit());
			if(mark >= begin && mark < end) {
				return true;
			}
			return false;
		}
		
		@Override
		public void onVideoPicture(IVideoPictureEvent event) {
			if(includeVideo && capture(event)) {
				super.onVideoPicture(event);
			}
		}
		
		@Override
		public void onAudioSamples(IAudioSamplesEvent event) {
			if(includeAudio && capture(event)) {
				super.onAudioSamples(event);
			}
		}
	}
	
}
