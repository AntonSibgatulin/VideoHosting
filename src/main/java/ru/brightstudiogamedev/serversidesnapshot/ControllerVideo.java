package ru.brightstudiogamedev.serversidesnapshot;

import com.AntonSibgatulin.Main.Server;
import com.AntonSibgatulin.Video.VideoModel;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.PARTIAL_CONTENT;

@RestController
public class ControllerVideo {



/*
    @GetMapping(value = "/video")
    @ResponseBody
    public ResponseEntity<ResourceRegion> getVideo(@RequestHeader HttpHeaders headers) throws IOException {




        UrlResource video = new UrlResource("file:videofiles/Admin/MagicBattle/Jujutsu_Kaisen_(TV)_[04]_[AniMaunt]_[WEBRip_720x406].mp4");
        ResourceRegion region =getResourceRegion(video,headers);

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory
                        .getMediaType(video)
                        .orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(region);

    }

 */


    @PostMapping("/youtubei/v1/log_event")
    public String getAPI(){
        return "{ \"responseContext\": {} }";
    }

    @GetMapping(value = "/videothread_{id}")
    @ResponseBody
    public ResponseEntity<ResourceRegion> getVideoId(@PathVariable String id ,@RequestHeader HttpHeaders headers) throws IOException {

        System.out.println(id);
        Server server = ServerSideSnapshotApplication.server;
        VideoModel videoModel = server.base.getVideModel(id);
        if(videoModel == null){
            videoModel = new VideoModel("-1",":)","videofiles/index.mp4","");
        }
        UrlResource video = new UrlResource("file:"+videoModel.files);
        ResourceRegion region =getResourceRegion(video,headers);

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory
                        .getMediaType(video)
                        .orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(region);

    }



    public ResourceRegion getResourceRegion(UrlResource video,HttpHeaders httpHeaders) throws IOException {
        long contentLength = video.contentLength();
        HttpRange range = null;
        if(httpHeaders.getRange().size()>0) {
            range = httpHeaders.getRange().get(0);
        }


        if(range!=null){
            long start = range.getRangeStart(contentLength);
            long end = range.getRangeEnd(contentLength);
            long rangeLength = Math.max(1 * 1024 * 16, end - start + 1);
            return new ResourceRegion(video,start,rangeLength);
        }else{
            long rangeLength = Math.max(1 * 1024 * 16, contentLength);
            return new ResourceRegion(video, 0, rangeLength);
        }

    }


   // @GetMapping(value = "/video")
  //  @ResponseBody
    public ResponseEntity<InputStreamResource> getVideo(@CookieValue(value = "db") String dbCookie, @RequestHeader(value = "Range", required = false)
                                                             String range) throws IOException {
        //System.out.println(dbCookie);
        File file = new File("videofiles/index.mp4");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("video/mp4"));
        headers.set("Accept-Ranges", "bytes");
        headers.set("Expires", "0");
        headers.set("Cache-Control", "no-cache, no-store");
        headers.set("Connection", "keep-alive");
        headers.set("Content-Transfer-Encoding", "binary");
        if(range == null || range.isEmpty()){
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));//Resources.getResource(fileName).openStream();//or read from wherever your data is into stream

            headers.set("Content-Length", String.valueOf(file.length()));
          //  long start = 0;//Long.valueOf(range.split("=")[1].split("-")[0]);
         //   long end = start+8*1024;

          //  headers.set("Content-Range", format("bytes %s-%s/%s", start, end, file.length()));

            return new ResponseEntity<>(new InputStreamResource(inputStream),headers,OK);

        }else{
            long size = 64*1024;
            System.out.println(range+" "+(range.split("=")[1].split("-").length));

            long start = Long.valueOf(range.split("=")[1].split("-")[0]);
            long end = 0;
          //  if(range.split("=")[1].split("-"))
            if(range.split("=")[1].split("-").length==1){
                end=start+size;
            }else{
                end = Long.valueOf(range.split("=")[1].split("-")[1]);

            }
            if(end>=file.length()){
                end=file.length();
            }
            headers.set("Content-Length", String.valueOf(size));

            headers.set("Content-Range", format("bytes %s-%s/%s", start, end, file.length()));
            FileChannel binaryFileChannel = new RandomAccessFile(file, "r").getChannel();
            ByteBuffer buf = getByteBuffer(binaryFileChannel, start, size);
            byte[] bytes = getByteArray(buf);
            InputStream inputStream = new ByteArrayInputStream(bytes);
            //System.out.println(bytes.length);
            return new ResponseEntity<>(new InputStreamResource(inputStream), headers, PARTIAL_CONTENT);

            //  return null;


        }


    }

    public ByteBuffer getByteBuffer(FileChannel binaryFileChannel, int start, int size) throws IOException {


        return binaryFileChannel.map(FileChannel.MapMode.READ_ONLY, start, size);
    }
    byte[] getByteArray(ByteBuffer bb) {
        byte[] ba = new byte[bb.limit()];
        bb.get(ba);
        return ba;
    }

    public ByteBuffer getByteBuffer(FileChannel binaryFileChannel, long start, long size) throws IOException {


        return binaryFileChannel.map(FileChannel.MapMode.READ_ONLY, start, size);
    }
    /*
    @RequestMapping(value = "videos/file-name", method = GET)
    @ResponseBody
    public final ResponseEntity<InputStreamResource>
    retrieveResource(@PathVariable(value = "file-name")
                     final String fileName,
                     @RequestHeader(value = "Range", required = false)
                     String range) throws Exception {
        long rangeStart=0;
        long rangeEnd=5000;
        File file = new File("videofiles/the.mp4");
        if(range!=null && range.replace("bytes=","").split("-")[0]!=null){
     rangeStart = Long.valueOf(range.replace("bytes=","").split("-")[0]);//parse range header, which is bytes=0-10000 or something like that

}
     //    rangeEnd = Long.valueOf(range.replace("bytes=","").split("-")[0]);//parse range header, which is bytes=0-10000 or something like that
         long contentLenght = file.length();//you must have it somewhere stored or read the full file size

        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));//Resources.getResource(fileName).openStream();//or read from wherever your data is into stream
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("video/mp4"));
        headers.set("Accept-Ranges", "bytes");
        headers.set("Expires", "0");
        headers.set("Cache-Control", "no-cache, no-store");
        headers.set("Connection", "keep-alive");
        headers.set("Content-Transfer-Encoding", "binary");
        headers.set("Content-Length", String.valueOf(rangeEnd - rangeStart));

//if start range assume that all content
        if (rangeStart == 0) {
            return new ResponseEntity<>(new InputStreamResource(inputStream), headers, OK);
        } else {
            headers.set("Content-Range", format("bytes %s-%s/%s", rangeStart, rangeEnd, contentLenght));
            return new ResponseEntity<>(new InputStreamResource(inputStream), headers, PARTIAL_CONTENT);
        }
    }

 */
}
