package com.AntonSibgatulin.Video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.AntonSibgatulin.Database.DatabasConnect;
import com.AntonSibgatulin.Debug.D;
import com.AntonSibgatulin.Main.Server;
import com.AntonSibgatulin.Socket.Socket;
import com.AntonSibgatulin.User.User;
import com.AntonSibgatulin.httpproto.SocketProcessor;

public class VideoServerController {
	public Map<String, VideoThread> videoview = new HashMap<>();
	public Server server = null;
	public DatabasConnect base = null;

	public VideoServerController(DatabasConnect base, Server server) {
		this.server = server;
		this.base = base;
	}

	public void getVideo(String cookie, int from) {

	}

	public void sendResponse(SocketProcessor processor, VideoThread videoThread) {
		try {
			processor.writeResponseVideo(videoThread.length, videoThread.filetype);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			D.log("VideoServerController 29 line");
		}
	}

	public void executeVideo(HashMap<String, String> info, String name, SocketProcessor processor) {
		String[] string = new String[5];
		string[0] = info.get("db");
		string[1] = info.get("auth");
		string[2] = info.get("authif");
		string[3] = info.get("decode");
		D.log("allright");
		// if (string ==null)return;
		// if(string[0]!=null && string[1]!=null && string[2]!=null &&
		// string[3]!=null){
		User user = server.authification(string[0], string[1], string[2], string[3]);
		// if(user!=null){
		D.log("oggin "+name);
		if (name != null) {
			D.log("name is not null");

			VideoThread videoThread = videoview.get(name);
			if (videoThread == null) {
				D.log("videothread is null");
				videoThread = base.getVideoThread(name, this);
				
				videoview.put(name, videoThread);
				sendResponse(processor, videoThread);

				D.log(videoThread.file);

			} else {
				sendResponse(processor, videoThread);
				D.log(videoThread.file);

			}

			// }
			// }

		}
	}

	public void executeGetVideo(HashMap<String, String> info, String name, long from, SocketProcessor process) {
		String[] string = new String[5];
		string[0] = info.get("db");
		string[1] = info.get("auth");
		string[2] = info.get("authif");
		string[3] = info.get("decode");
		String add = "";
		D.bugss("null");
		if (from > (1024 * 1024 * 15 * 20)) {
			// add=";"+(int)(Math.ceil(from/(1024*1024*15*20)));
		}
		D.bugss("before");

		D.log("allright");
		// if (string ==null)return;
		// if(string[0]!=null && string[1]!=null && string[2]!=null &&
		// string[3]!=null){
		// User user =
		// server.authification(string[0],string[1],string[2],string[3]);
		// if(user!=null){

		if (name != null) {
			D.bugss("name");
			VideoThread videoThread = videoview.get(name);
			D.bugss(name);
			if (videoThread == null) {
				if (videoview.get(name) == null) {

					videoThread = base.getVideoThread(name, this);
					videoview.put(name, videoThread);
					D.bugss("l http");
					if (videoThread != null)
						sendResponse(process, videoThread, from);
				}
				D.bugss("m");
			} else {
				D.bugss("l http1");
				sendResponse(process, videoThread, from);

			}
		}
		// }

		// }

	}

	public void sendResponse(SocketProcessor process, VideoThread videoThread, long from) {
		try {
			D.bugss("all");
			if (process != null)
				process.writeResponseVideo(videoThread.getByteMassive(from), videoThread.filetype, from,
						videoThread.chunk, videoThread.length, videoThread.getInt(from));
			else
				return;
		} catch (Throwable e) {
			e.printStackTrace();
			// D.bugss(e);// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}
}
