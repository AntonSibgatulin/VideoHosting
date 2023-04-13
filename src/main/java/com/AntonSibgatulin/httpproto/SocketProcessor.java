package com.AntonSibgatulin.httpproto;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

import javax.swing.InternalFrameFocusTraversalPolicy;
import javax.swing.plaf.metal.MetalComboBoxUI.MetalPropertyChangeListener;
import javax.xml.stream.events.StartDocument;

import org.apache.commons.lang3.ArrayUtils;

import com.AntonSibgatulin.Debug.D;
import com.AntonSibgatulin.Main.Server;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer.FromDecimalArguments;

public class SocketProcessor implements Runnable {

	public Directory directory = null;
	private Socket s;
	private InputStream is;
	private OutputStream os;
	public Server server = null;

	public SocketProcessor(Socket s, Directory dir, Server server) throws Throwable {
		this.s = s;
		this.server = server;
		this.directory = dir;
		this.is = s.getInputStream();
		this.os = s.getOutputStream();
	}

	public void run() {
		try {
			readInputHeaders();
			// writeResponse("<html><body><script src
			// ='main.js'></script><script>alert();</script><h1>Hello from
			// Habrahabr</h1></body></html>");
		} catch (Throwable t) {
			/* do nothing */
		} finally {
			try {
				s.close();
			} catch (Throwable t) {
				/* do nothing */
			}
		}
		// System.err.println("Client processing finished");
	}

	public void writeResponse(String s) throws Throwable {

		String response = "HTTP/1.1 200 OK\r\n" + "Server: YarServer/2009-09-09\r\n" + "Content-Type: text/html\r\n"
				+ "Content-Length: " + s.getBytes().length + "\r\n" + "Connection: close\r\n\r\n";
		String result = response;
		if (s.length() > 0)
			result = result + s;
		os.write(result.getBytes());
		os.flush();
	}

	public void writeResponseJS(String s) throws Throwable {

		String response = "HTTP/1.1 200 OK\r\n" + "Server: YarServer/2009-09-09\r\n"
				+ "Content-Type: text/javascipt\r\n" + "Content-Length: " + s.getBytes().length + "\r\n"
				+ "Connection: close\r\n\r\n";
		String result = response;
		if (s.length() > 0)
			result = result + s;
		os.write(result.getBytes());
		os.flush();
	}

	public void writeResponseCSS(String s) throws Throwable {

		String response = "HTTP/1.1 200 OK\r\n" + "Server: YarServer/2009-09-09\r\n" + "Content-Type: text/css\r\n"
				+ "Content-Length: " + s.getBytes().length + "\r\n" + "Connection: close\r\n\r\n";
		String result = response;
		if (s.length() > 0)
			result = result + s;
		os.write(result.getBytes());
		os.flush();
	}

	public void writeResponseError(String s) throws Throwable {
		String response = "HTTP/1.1 404 ORROR\r\n" + "Server: YarServer/2009-09-09\r\n" + "Content-Type: text/html\r\n"
				+ "Content-Length: " + s.getBytes().length + "\r\n" + "Connection: close\r\n\r\n";
		String result = response + s;
		os.write(result.getBytes());
		os.flush();
	}

	public void writeResponseimg(byte[] s, String type) throws Throwable {
		String response = "HTTP/1.1 200 OK\r\n" + "Server: YarServer/2009-09-09\r\n" + "Content-Type: image/" + type
				+ "\r\n" + "Content-Length: " + s.length + "\r\n" + "Connection: close\r\n\r\n";
		String result = response;
		os.write(ArrayUtils.addAll(result.getBytes(), s));

		// os.write(result.getBytes());
		// os.write(s);
		os.flush();
	}

	public void writeResponseVideo1(byte[] st, String type, long i, int si) throws Throwable {

		byte[] s = Arrays.copyOfRange(st.clone(), (int) i, st.length - i > si ? (st.length) : ((int) i + si));

		String response = "HTTP/1.1 206 OK\r\n" + "Content-Range: bytes " + i + "-" + (i + si - 1) + "/" + (st.length)
				+ "\r\n" + "Server: YarServer/2020-12-16\r\n" + "Cache-Control: private,no-cache, max-age=0\r\n" +

				"Accept-Ranges: bytes\r\n" + "Content-Type: video/" + type + "\r\n" + "Content-Length: " + st.length
				+ "\r\n" + "Connection: keep-alive\r\n\r\n";
		String result = response;
		os.write(ArrayUtils.addAll(result.getBytes(), s));

		// os.write(result.getBytes());
		// os.flush();
		// os.write(s);
		os.flush();

	}

	public void writeResponseVideo(byte[] st, String type, long i, int si, long lon, long chunk) throws Throwable {
		
		long i1 = 0 + i;
		if (st.length <= i1) {
			// D.bugss("before "+i1);
			i1 = i1 - (chunk * si);
			// D.bugss("them "+i1);
		}
		// i = i-minus;
		int to = 0;
		int to1 = 0;
		if (st.length + (chunk * si) - i < si) {
			to = (int) (i + ((st.length) + (chunk * si) - i));
			to1 = (int) st.length;
			D.bugss("start");
		} else {
			D.bugss("stop");
			to = (int) i + si;
			to1 = (int) (i1 + si);
		}
		byte[] s = Arrays.copyOfRange(st.clone(), (int) i1, to1);

		D.bugss("len " + s.length + "    from " + i + "  to " + to);
		D.bugss("len " + s.length + "    from " + i1 + "  to " + to1);

		String response = "HTTP/1.1 206 OK\r\n" + "Content-Range: bytes " + i + "-" + (to - 1) + "/" + (lon) + "\r\n"
				+ "Server: YarServer/2020-12-16\r\n" + "Cache-Control: private,no-cache, max-age=0\r\n" +

				"Accept-Ranges: bytes\r\n" + "Content-Type: video/" + type + "\r\n" + "Content-Length: " + lon + "\r\n"
				+ "Connection: keep-alive\r\n\r\n";
		String result = response;

		// os.write(result.getBytes());
		D.bugss("write");
		os.write(result.getBytes());
		os.write(s);
		os.flush();

	}

	public void writeResponseVideo1(byte[] st, String type, long i, int si, long lon, long chunk) throws Throwable {

		if (st.length < i) {
			// i = i-((st.length/si)*si);
		}
		// i = i-minus;
		int to = (int) (st.length - i < si ? (st.length - i) : (i + si));
		byte[] s = Arrays.copyOfRange(st.clone(), (int) i, to);

		D.bugss("len " + s.length + "    from " + i + "  to " + to);
		String response = "HTTP/1.1 206 OK\r\n" + "Content-Range: bytes " + i + "-" + (to - 1) + "/" + (lon) + "\r\n"
				+ "Server: YarServer/2020-12-16\r\n" + "Cache-Control: private,no-cache, max-age=0\r\n" +

				"Accept-Ranges: bytes\r\n" + "Content-Type: video/" + type + "\r\n" + "Content-Length: " + lon + "\r\n"
				+ "Connection: keep-alive\r\n\r\n";
		String result = response;

		// os.write(result.getBytes());

		os.write(ArrayUtils.addAll(result.getBytes(), s));
		os.flush();

	}

	public void writeResponseVideo(long st, String type) throws Throwable {

		// byte[] s= Arrays.copyOfRange(st.clone(),(int)i,
		// st.length-i>si?(st.length):((int)i+si));
		// if(type.equals("mkv"))type = "mp4";
		String response = "HTTP/1.1 200 OK\r\n" +
		// "Content-Range: bytes "+i+"-"+(i+si-1)+"/"+(st.length)+"\r\n"+
				"Server: YarServer/2020-12-16\r\n" + "Cache-Control: private,no-cache, max-age=0\r\n" +

				// "//Accept-Ranges: bytes\r\n" + "Content-Type: video/" + type
				// + "\r\n" + "Content-Length: " + st + "\r\n"
				"//Accept-Ranges: bytes\r\n" + "Content-Type: video/" + type + "\r\n" + "Content-Length: " + st + "\r\n"
				+ "Connection: close\r\n\r\n";
		String result = response;
D.log("Responded");
		os.write(result.getBytes());
		// os.write(st);
		os.flush();
		os.close();
	}

	public void writeResponseFONT(byte[] s, String type) throws Throwable {
		String response = "HTTP/1.1 200 OK\r\n" + "Server: YarServer/2009-09-09\r\n" + "Content-Type: font/" + type
				+ "\r\n" + "Content-Length: " + s.length + "\r\n" + "Connection: close\r\n\r\n";
		String result = response;
		os.write(ArrayUtils.addAll(result.getBytes(), s));
		// os.write(result.getBytes());
		// os.write(s);
		os.flush();
	}

	public void readInputHeaders() throws Throwable {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String string = "";
		String[] range = null;
		String[] cookie = null;
		long i = -3;
		while (true) {
			String s = br.readLine();
			if (s == null || s.trim().length() == 0) {
				break;
			} else {
				if (s.startsWith("Range: bytes=")) {
					String[] g = s.replace("Range: bytes=", "").split("-");
					i = Long.valueOf(g[0]);

				}
				if (s.startsWith("Cookie: ")) {
					cookie = s.replace("Cookie: ", "").split(" ");
					if (cookie != null && cookie.length > 0)
						;
					else
						cookie = null;

				}

				string = string + s + "\n";
			}
		}
		// D.log(string);

		// D.bugss1(string);
		String[] getzapros = string.split(" ")[1].split("\\?");
		D.bugss2(string.split(" ")[1]);
		if (getzapros != null && getzapros.length > 1) {
			//
			execute(getzapros[0], cookie, i, getzapros[1].split("&"));
		} else {/// D.bugss2("truf");
			execute(string.split(" ")[1], cookie, i, null);
		}

	}

	public void execute(String argss, String[] cookie, long range, String[] args2) {
		String[] args = argss.split("/");
		boolean withget = false;
		if (args2 != null)
			D.bugss2(args2[0]);
		if (args2 != null && args2.length > 0) {
			withget = true;

		}

		HashMap<String, String> stringGet = new HashMap<>();
		if (withget) {

			for (int i = 0; i < args2.length; i++) {
				String[] ppp = args2[i].split("=");
				if (ppp.length >= 2)
					stringGet.put(ppp[0], ppp[1]);
			}
		}
		String[] components = null;
		if (argss.equals("/")) {
			System.out.println("is inited \\index.html " + ("/index.html").replaceAll("/", "\\\\"));
			System.out.println(("/index.html").replaceAll("/", "\\\\"));
			System.out.println("\\index.html");
			FileSibgatulina fi = directory.geforce
					.get((directory.mainDerictory + "/index.html").replaceAll("/", "\\\\"));
			System.err.println("get :" + directory.mainDerictory + "" + argss);
			if (fi != null) {
				if (fi.type == typefile.PAGE) {
					try {
						writeResponse(fi.string);
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		D.log("length " + args.length);
		if (args.length >= 2) {

			components = args[args.length - 1].split("\\_");

			if (args.length == 2 && components.length >= 2) {
				D.log("line 245 length >0");
				if (components[0].equals("videoview")) {
					D.log("video");
					///////////////////////
					// if(cookie ==null)return;
					//////////////////////
					HashMap<String, String> cookiemap = new HashMap<>();
					for (int j = 0; j < cookie.length; j++) {
						String[] h = cookie[j].replaceAll(";", "").split("=");
						if (h[0].equals("db") || h[0].equals("auth") || h[0].equals("authif")
								|| h[0].equals("decode")) {
							cookiemap.put(h[0], h[1]);
						}

					}
					final String[] com = components;

					if (range < 0) {
						D.log("get");
						// if(SocketProcessor.this!=null)
						SocketProcessor.this.server.controller.executeVideo(cookiemap, com[1], SocketProcessor.this);
					} else {
						D.bugss("fe");
						// if(SocketProcessor.this!=null)
						SocketProcessor.this.server.controller.executeGetVideo(cookiemap, com[1], range,
								SocketProcessor.this);
					}

				}

			} else {

				if (args.length >= 2) {
					FileSibgatulina fi = null;
					// directory.geforce.get(directory.mainDerictory+""+argss);
					if (args[args.length - 1].split("\\.").length > 0) {
						fi = directory.geforce.get((directory.mainDerictory + "" + argss).replaceAll("/", "\\\\"));
						if (withget) {
							D.bugss2("WIDGET true");
							if (stringGet.get("HashAdmin").equals("Admin")) {
								D.bugss2("WIDGET2 true");
								if (fi.type == typefile.PAGE) {
									D.bugss2("truth");
									try {
										writeResponse(fi.string.replaceAll("main\\.js", "main.js?HashAdmin=Admin"));
									} catch (Throwable e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									return;

								} else if (fi.type == typefile.JS) {
									// $admin//
									// D.bugss2(fi.string);
									try {

										writeResponseJS(fi.string.replaceAll("\\/\\/\\$admin\\/\\/", directory.geforce
												.get(("html/js/admin.js").replaceAll("/", "\\\\")).string));
										return;
									} catch (Throwable e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}
							}
						}
						D.bugss1("d");
					} else {
						if (argss.endsWith("/"))
							fi = directory.geforce
									.get((directory.mainDerictory + "" + argss + "index.html").replaceAll("/", "\\\\"));
						else
							fi = directory.geforce.get(
									(directory.mainDerictory + "" + argss + "/index.html").replaceAll("/", "\\\\"));

					}
					D.bugss1(argss);
					// System.err.println("get
					// :"+directory.mainDerictory+""+argss);
					if (fi != null) {
						if (fi.type == typefile.IMAGE) {
							try {
								writeResponseimg(fi.bytes, fi.typefile);
							} catch (Throwable e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else if (fi.type == typefile.PAGE) {
							try {
								writeResponse(fi.string);
							} catch (Throwable e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else if (fi.type == typefile.JS) {
							try {
								writeResponseJS(fi.string);
							} catch (Throwable e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else if (fi.type == typefile.CSS) {
							try {
								writeResponseCSS(fi.string);
							} catch (Throwable e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else if (fi.type == typefile.FONT) {
							try {
								writeResponseFONT(fi.bytes, fi.typefile);
							} catch (Throwable e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} else {
						try {
							writeResponseError("<html>not found</html>");
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		} else {

			FileSibgatulina fi = directory.geforce.get(directory.mainDerictory + "index.html");
			System.err.println("get :" + directory.mainDerictory + "" + argss);
			if (fi != null) {
				if (fi.type == typefile.PAGE) {
					try {
						writeResponse(fi.string);
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}
}
