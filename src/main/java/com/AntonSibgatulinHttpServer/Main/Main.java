package com.AntonSibgatulinHttpServer.Main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import org.apache.commons.codec.binary.Base64;

import com.AntonSibgatulin.Debug.D;
import com.AntonSibgatulin.Video.VideoThread;
import ws.schild.jave.*;

public class Main {

	public static void main(String[]args){
		System.exit(0);
		for(int i = 1;i<7;i++) {
			String id = ""+i;
			if(i<10){
				//id="0"+id;
			}
			File source = new File("videofiles/Admin/AfterLive3/After.Life.S03E0"+id+".WEBRip.400p.rus.2.0.HDREZKA.STUDIO.avi");
			File target = new File("videofiles/Admin/AfterLive3/mp4/After.Life.S03E0"+id+".WEBRip.400p.rus.2.0.HDREZKA.STUDIO.mp4");

			/* Step 2. Set Audio Attrributes for conversion*/
			AudioAttributes audio = new AudioAttributes();
			audio.setCodec("aac");
// here 64kbit/s is 64000
			audio.setBitRate(128000);
			audio.setChannels(4);
			audio.setSamplingRate(44100);

			/* Step 3. Set Video Attributes for conversion*/
			VideoAttributes video = new VideoAttributes();
			video.setCodec("h264");
			video.setX264Profile(VideoAttributes.X264_PROFILE.BASELINE);
// Here 160 kbps video is 160000
			video.setBitRate(1024000);
// More the frames more quality and size, but keep it low based on devices like mobile
			video.setFrameRate(25);
			video.setSize(new VideoSize(720, 406));

			/* Step 4. Set Encoding Attributes*/
			EncodingAttributes attrs = new EncodingAttributes();
			attrs.setFormat("mp4");
			attrs.setAudioAttributes(audio);
			attrs.setVideoAttributes(video);

			/* Step 5. Do the Encoding*/
			try {
				Encoder encoder = new Encoder();
				encoder.encode(new MultimediaObject(source), target, attrs);
			} catch (Exception e) {
				/*Handle here the video failure*/
				e.printStackTrace();
			}
		}
	}


	public static void main1(String[] args) throws IOException {
		File file2  = new File("videofiles/trees.mp4");
//    	D.log(file2.length());
    	long lengthfile = file2.length();
    	int chunk = lengthfile>(1024*1024*120)?(1024*1024*40):(1024*1024*8)/2;
    	if(lengthfile<=(1024*1024*8)){
    		//chunk = (int)lengthfile/2;
    	}
    	@SuppressWarnings("resource")
		FileChannel binaryFileChannel = new RandomAccessFile(file2, "r").getChannel();
double l = lengthfile/(chunk);
int l1 = (int)(Math.ceil(l));
int allright = l1>15?15:l1;
    	ArrayList<byte[]> gArrayList = new ArrayList<>();
    	for(int i = 0;i<allright ;i++){
    		int start = i*(chunk);
    		int size = chunk;
    		if(start+size >lengthfile)
    			size = (int)lengthfile-start;
    		D.log("Now all okey's");
    		long timenow = System.currentTimeMillis();
    	ByteBuffer buf = getByteBuffer(binaryFileChannel, start, size);
    	D.log("Now all okey's");
    	byte[] bbb =Base64.encodeBase64(getByteArray(buf));
    //	String encoded = java.util.Base64.getEncoder().encodeToString(bbb)+"\n";
//System.out.println(encoded);
    gArrayList.add(bbb);
	D.log("time one frame "+(System.currentTimeMillis()-timenow));
    	
	
    	}
  
        
    
    	if (allright >15){
    		
    	      // loaderFileFilmAll(chunk, allright, lengthfile, binaryFileChannel, l1);
    	}
    	FileOutputStream fos = new FileOutputStream("videofiles/out/"+System.currentTimeMillis()+".txt");
    	  
    	 
    	for(byte[] bytes:gArrayList){
    	System.out.println("load ..");
    		fos.write(bytes);
    		fos.write(("\n").getBytes());
    	}
    	fos.close();
    	
	}
	
	public static byte[] getByteArray(ByteBuffer bb) {
		byte[] ba = new byte[bb.limit()];
		bb.get(ba);
		return ba;
		}

	
	public static ByteBuffer getByteBuffer(FileChannel binaryFileChannel , int start, int size) throws IOException
	{


	return binaryFileChannel.map(FileChannel.MapMode.READ_ONLY, start, size);
	}

}
