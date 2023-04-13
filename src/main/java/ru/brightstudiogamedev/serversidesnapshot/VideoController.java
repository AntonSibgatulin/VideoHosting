package ru.brightstudiogamedev.serversidesnapshot;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;

@RestController
@RequestMapping("/video")
public class VideoController {


    @RequestMapping(value = "mp4/{fileName}", method = RequestMethod.GET)
    public StreamingResponseBody getFile(@PathVariable("fileName") String fileName) throws
            IOException, FileNotFoundException {


        File music = new File("videofiles/the.mp4");

        final InputStream audioFileStream = new FileInputStream(music);

        return ( os) -> {
            readAndWrite(audioFileStream, os);
        };

    }
    private void readAndWrite(final InputStream is, OutputStream os)
            throws IOException {
        byte[] data = new byte[2048];
        int read = 0;
        while ((read = is.read(data)) > 0) {
            os.write(data, 0, read);
        }
        os.flush();
    }
}
