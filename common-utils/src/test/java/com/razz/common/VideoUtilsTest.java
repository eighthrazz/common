package com.razz.common;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.razz.common.util.media.VideoUtils;

public class VideoUtilsTest {

	@Test
	public void trimTest() throws Exception {
		final File mp4SrcFile = Paths.get(System.getProperty("user.home"), "TEST", "src.mp4").toFile();
		assertTrue(mp4SrcFile.exists());
		
		final File mp4DstFile = Paths.get(System.getProperty("user.home"), "TEST", "dst.mp4").toFile();
		if(mp4DstFile.exists())
			mp4DstFile.delete();
		assertFalse(mp4DstFile.exists());
		
		final long start = 5000;
		final long end = 15000;
		final TimeUnit timeUnit = TimeUnit.MILLISECONDS;
		final boolean includeAudio = false;
		final boolean includeVideo = true;
		VideoUtils.trim(mp4SrcFile, mp4DstFile, start, end, timeUnit);//, includeAudio, includeVideo);
		assertTrue(mp4DstFile.exists());
		assertTrue(mp4DstFile.length() > 0);
	}
}
