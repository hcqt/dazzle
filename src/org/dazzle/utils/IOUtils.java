package org.dazzle.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.dazzle.common.exception.BaseException;

/** @author hcqt@qq.com */
public class IOUtils {
	
	private static String msg3Code = "COMMON_IO_UTIL_12446";
	private static String msg3 = "文件{0}创建失败，详情——{1}";

	private static String msg4Code = "COMMON_IO_UTIL_12447";
	private static String msg4 = "文件{0}创建失败";

	private static String msg5Code = "COMMON_IO_UTIL_OISA8";
	private static String msg5 = "URI{0}语法错误，无法创建文件，详情——{1}";

	/** @author hcqt@qq.com */
	public static final String readText(String filePath) {
		return RDText.read(filePath, (String) null, (String) null);
	}

	/** @author hcqt@qq.com */
	public static String readText(byte[] inByte, String lineEndSeparator, String charsetName) {
		return RDText.read(inByte, lineEndSeparator, charsetName);
	}

	/** @author hcqt@qq.com */
	public static String readText(String uriStr, String lineEndSeparator, String charsetName) {
		return RDText.read(uriStr, lineEndSeparator, charsetName);
	}

	/** @author hcqt@qq.com */
	public static String readText(URI uri, String lineEndSeparator, String charsetName) {
		return RDText.read(uri, lineEndSeparator, charsetName);
	}

	/** @author hcqt@qq.com */
	public static String readText(URL url, String lineEndSeparator, String charsetName) {
		return RDText.read(url, lineEndSeparator, charsetName);
	}

	/** @author hcqt@qq.com */
	public static String readText(InputStream inputStream, String lineEndSeparator, String charsetName) {
		return RDText.read(inputStream, lineEndSeparator, charsetName);
	}

	/** @author hcqt@qq.com */
	public static String readText(File file, String lineEndSeparator, String charsetName) {
		return RDText.read(file, lineEndSeparator, charsetName);
	}

	/** @author hcqt@qq.com */
	public static String readText(byte[] inByte, String charsetName) {
		return RDText.read(inByte, "", charsetName);
	}

	/** @author hcqt@qq.com */
	public static String readText(String uriStr, String charsetName) {
		return RDText.read(uriStr, "", charsetName);
	}

	/** @author hcqt@qq.com */
	public static String readText(URI uri, String charsetName) {
		return RDText.read(uri, "", charsetName);
	}

	/** @author hcqt@qq.com */
	public static String readText(URL url, String charsetName) {
		return RDText.read(url, "", charsetName);
	}

	/** @author hcqt@qq.com */
	public static String readText(InputStream inputStream, String charsetName) {
		return RDText.read(inputStream, "", charsetName);
	}

	/** @author hcqt@qq.com */
	public static String readText(File file, String charsetName) {
		return RDText.read(file, "", charsetName);
	}

	/** @author hcqt@qq.com */
	public static String readText(byte[] inByte, String charsetName, int rowNum) {
		return RDText.read(inByte, charsetName, rowNum);
	}

	/** @author hcqt@qq.com */
	public static String readText(String uriStr, String charsetName, int rowNum) {
		return RDText.read(uriStr, charsetName, rowNum);
	}

	/** @author hcqt@qq.com */
	public static String readText(URI uri, String charsetName, int rowNum) {
		return RDText.read(uri, charsetName, rowNum);
	}

	/** @author hcqt@qq.com */
	public static String readText(URL url, String charsetName, int rowNum) {
		return RDText.read(url, charsetName, rowNum);
	}

	/** @author hcqt@qq.com */
	public static String readText(InputStream inputStream, String charsetName, int rowNum) {
		return RDText.read(inputStream, charsetName, rowNum);
	}

	/** @author hcqt@qq.com */
	public static String readText(File file, String charsetName, int rowNum) {
		return RDText.read(file, charsetName, rowNum);
	}

	/** @author hcqt@qq.com */
	public static void read(byte[] inByte, String charsetName, ReadRow readRow) {
		RDText.read(inByte, charsetName, readRow);
	}

	/** @author hcqt@qq.com */
	public static void read(String uriStr, String charsetName, ReadRow readRow) {
		RDText.read(uriStr, charsetName, readRow);
	}

	/** @author hcqt@qq.com */
	public static void read(URI uri,  String charsetName, ReadRow readRow) {
		RDText.read(uri, charsetName, readRow);
	}

	/** @author hcqt@qq.com */
	public static void read(URL url, String charsetName, ReadRow readRow) {
		RDText.read(url, charsetName, readRow);
	}

	/** @author hcqt@qq.com */
	public static void read(InputStream inputStream, String charsetName, ReadRow readRow) {
		RDText.read(inputStream, charsetName, readRow);
	}

	/** @author hcqt@qq.com */
	public static void read(File file, String charsetName, ReadRow readRow) {
		RDText.read(file, charsetName, readRow);
	}

	/** @author hcqt@qq.com */
	public static int readRowCount(byte[] inByte) {
		return RDText.readRowCount(inByte);
	}

	/** @author hcqt@qq.com */
	public static int readRowCount(String uriStr) {
		return RDText.readRowCount(uriStr);
	}

	/** @author hcqt@qq.com */
	public static int readRowCount(URI uri) {
		return RDText.readRowCount(uri);
	}

	/** @author hcqt@qq.com */
	public static int readRowCount(URL url) {
		return RDText.readRowCount(url);
	}

	/** @author hcqt@qq.com */
	public static int readRowCount(InputStream inputStream) {
		return RDText.readRowCount(inputStream);
	}

	/** @author hcqt@qq.com */
	public static int readRowCount(File file) {
		return RDText.readRowCount(file);
	}

	/** @author hcqt@qq.com */
	public static void write(byte[] inByte, OutputStream outputStream) {
		WR.write(inByte, outputStream);
	}

	/** @author hcqt@qq.com */
	public static void write(byte[] inByte, File outFile) {
		WR.write(inByte, outFile);
	}

	/** @author hcqt@qq.com */
	public static void write(File inFile, OutputStream outputStream) {
		WR.write(inFile, outputStream);
	}

	/** @author hcqt@qq.com */
	public static void write(URI inUri, OutputStream outputStream) {
		WR.write(inUri, outputStream);
	}

	/** @author hcqt@qq.com */
	public static void write(URL inUrl, OutputStream outputStream) {
		WR.write(inUrl, outputStream);
	}

	/** @author hcqt@qq.com */
	public static void write(InputStream inputStream, URI outUri) {
		WR.write(inputStream, outUri);
	}

	/** @author hcqt@qq.com */
	public static void write(InputStream inputStream, URL outUrl) {
		WR.write(inputStream, outUrl);
	}

	/** @author hcqt@qq.com */
	public static void write(InputStream inputStream, OutputStream outputStream) {
		WR.write(inputStream, outputStream);
	}

	/** @author hcqt@qq.com */
	public static void write(byte[] inByte, URI outUri) {
		WR.write(inByte, outUri);
	}

	/** @author hcqt@qq.com */
	public static void write(byte[] inByte, URL outUrl) {
		WR.write(inByte, outUrl);
	}

	/** @author hcqt@qq.com */
	public static void write(String inString, OutputStream outputStream) {
		WR.write(inString, outputStream);
	}

	/** @author hcqt@qq.com */
	public static void write(String inString, File outFile) {
		WR.write(inString, outFile);
	}

	/** @author hcqt@qq.com */
	public static void write(String inString, URI outUri) {
		WR.write(inString, outUri);
	}

	/** @author hcqt@qq.com */
	public static void write(String inString, URL outUrl) {
		WR.write(inString, outUrl);
	}

	/**复制文件  @see #copyFile(File, File) 
	 * @author hcqt@qq.com*/
	public static void copyFile(URI srcPath, File newFile) {
		copyFile(new File(URLUtils.resolve(srcPath)), newFile);
	}

	/**复制文件  @see #copyFile(File, File) 
	 * @author hcqt@qq.com*/
	public static File copyFile(File srcFile, URI newPath) {
		File ret = new File(URLUtils.resolve(newPath));
		copyFile(srcFile, ret);
		return ret;
	}
	
	/**复制文件  @see #copyFile(File, File) 
	 * @author hcqt@qq.com*/
	public static File copyFile(URI srcPath, URI newPath) {
		File ret = new File(URLUtils.resolve(newPath));
		copyFile(new File(URLUtils.resolve(srcPath)), ret);
		return ret;
	}

	/**复制文件
	 * @param srcFile 如果此参数为空，则返回null
	 * @param newFile 如果此参数为空，则返回null
	 * @throws BaseException
	 * {
	 * {@value #msg78Code}:{@value #msg78},
	 * {@value #msg79Code}:{@value #msg79},
	 * {@value #msg80Code}:{@value #msg80}
	 * } 
	 * @author hcqt@qq.com */
	public static void copyFile(File srcFile, File newFile) {
		InputStream in = null;
		OutputStream fs = null;
		try {
			try {
				in = new FileInputStream(srcFile); //读入原文件
			} catch (FileNotFoundException e) {
				throw new BaseException(msg78Code, ResMsgUtils.resolve(msg78, srcFile), e);
			}
			try {
				createFile(newFile);
				fs = new FileOutputStream(newFile);
			} catch (FileNotFoundException e) {
				throw new BaseException(msg79Code, ResMsgUtils.resolve(msg79, newFile), e);
			}
			org.apache.commons.io.IOUtils.copy(in, fs);
		} catch (IOException e) {
			String eDetail = ExceptionUtils.out(e);
			throw new BaseException(msg80Code, ResMsgUtils.resolve(msg80, eDetail), e, eDetail);
		} finally {
			if(in != null) {
				try { in.close(); } catch (IOException e) { e.printStackTrace(); }
			}
			if(fs != null) {
				try { fs.close(); } catch (IOException e) { e.printStackTrace(); }
			}
		}
	}

	private static String msg80Code = "SYS_COMMON_COPY_FILE_hj34Q";
	private static String msg80 = "文件复制失败，详情——{0}";

	private static String msg79Code = "SYS_COMMON_COPY_FILE_nb23g";
	private static String msg79 = "无法复制文件到目标位置，目标文件{0}找不到";

	private static String msg78Code = "SYS_COMMON_COPY_FILE_892hb";
	private static String msg78 = "无法从指定位置复制文件，源文件{0}找不到";

//	/** 将输入流中的数据转换成字符串
//	 * @throws IOException */
//	 public static String InputStreamTOString(InputStream in) throws IOException{  
//        int BUFFER_SIZE = 4096;  
//        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
//        byte[] data = new byte[BUFFER_SIZE];  
//        int count = -1;  
//        while((count = in.read(data,0,BUFFER_SIZE)) != -1)  
//            outStream.write(data, 0, count);  	          
//        data = null;  
//        return new String(outStream.toByteArray(),"utf-8");  
//	  }  

	/**检查文件是否存在，如果不存在则创建 
	 * @author hcqt@qq.com*/
	public static File createFile(File file) {
		try {
			if(!file.exists()) {
				Logger.getLogger(IOUtils.class).debug("#####写入文件的绝对路径>>>>>>>>>>>>>>>>>>>>>>" + file);
				file.createNewFile();
			}
		} catch (IOException e) {
			createDir(file.getParentFile());
			try {
				file.createNewFile();
			} catch (IOException e1) {
//				throw new BaseException(msg3Code, ResMsgUtils.processMsg(msg3, file, ExceptionUtils.outDetail(e1)), file, ExceptionUtils.outDetail(e1));
				throw new BaseException(msg3Code, ResMsgUtils.resolve(msg3, file, ExceptionUtils.out(e1)), e1, file, ExceptionUtils.out(e1));
//				System.out.println(b);
//				System.out.println(b.getMessage());
//				System.out.println(b.getCode());
			}
		}
		return file;
	}
	
	/**检查文件是否存在，如果不存在则创建 
	 * @author hcqt@qq.com*/
	public static File createFile(URI uri) {
		File file = null;
		try {
			file = new File(URLUtils.resolve(uri));
			if(!file.exists()) {
				Logger.getLogger(IOUtils.class).debug("#####写入文件的路径>>>>>>>>>>>>>>>>>>>>>>" + file);
				file.createNewFile();
			}
		} catch (IOException e) {
			createDir(file.getParentFile());
			try {
				file.createNewFile();
			} catch (IOException e1) {
				throw new BaseException(msg4Code, ResMsgUtils.resolve(msg4, file), e1, file);
			}
		}
		return file;
	}
	
	/**检查目录是否存在，如果不存在则创建目录 
	 * @author hcqt@qq.com*/
	public static void createDir(File fileDir) {
		if(!fileDir.exists() && !fileDir.isDirectory()) {
			fileDir.mkdirs();
		}
	}

//	/**检查目录是否存在，如果不存在则创建目录*/
//	public static void createDir(String fileFullPath) {
//		File fileDir = new File(fileFullPath);
//		if(!fileDir.exists() && !fileDir.isDirectory()) {
//			fileDir.mkdirs();
//		}
//	}
	
//	/**把指定的文本追加到文件当中
//	 * 检查文件是否存在，若不存在则创建文件
//	 * @throws IOException */
//	public static void appendContent2File(String filePath, String content) throws IOException {
//		appendContent2File(createFile(filePath), content);
//	}

	/**向文件当中换行后追加内容，
	 * 检查文件是否存在，若不存在则创建文件
	 * @throws IOException 
	 * @author hcqt@qq.com */
	public static void appendContent2File(File file, String content) throws IOException {
		WR.writeString(file, content, true);
	}

	private static SimpleDateFormat dateFormat_for_createDateFile = new SimpleDateFormat("yyyy/MM/dd");
	private static String createDateFile(){
		String dateFile=dateFormat_for_createDateFile.format(new Date());
		return dateFile;
	}

	/**创建随机命名的文件，用途：临时文件
	 * @param uriPath 格式形如:"file:/var/folder1/folder2/"
	 * @param fileSuffix 格式形如:".pdf" 
	 * @author hcqt@qq.com
	 *  */
	public static File createRandomFile(URI uriPath, String fileSuffix) {
		try {
			return createFile(new URI(new StringBuilder()
					.append(URLUtils.resolve(uriPath)).append(createDateFile()).append("/")
					.append(System.currentTimeMillis()).append(".")
					.append(System.nanoTime())
					.append(Math.random())
					.append(fileSuffix).toString()));
		} catch (URISyntaxException e) {
			throw new BaseException(msg5Code, ResMsgUtils.resolve(msg5, uriPath, e.getMessage()), e, uriPath, e.getMessage());
		}
	}
	 
	/** @author hcqt@qq.com */
	private static final class RDText {
		
		private static final String read(byte[] inByte, String lineEndSeparator, String charsetName) {
			return read(CStream.createInputStream(inByte), lineEndSeparator, charsetName);
		}
		
		private static final String read(String uriStr, String lineEndSeparator, String charsetName) {
//			try {
			return read(URI.create(uriStr), lineEndSeparator, charsetName);
//			} catch (URISyntaxException e) {
//				throw new BaseException("COMMON_READ_FILE_4yfK", "输入字符串违反URI语法，无法识别URI——{0}", e, uriStr);
//			}
		}

		private static final String read(URI uri, String lineEndSeparator, String charsetName) {
			return read(CStream.createInputStream(uri), lineEndSeparator, charsetName);
		}
		
		private static final String read(URL url, String lineEndSeparator, String charsetName) {
			return read(CStream.createInputStream(url), lineEndSeparator, charsetName);
		}
		
		private static final String read(File file, String lineEndSeparator, String charsetName) {
			return read(CStream.createInputStream(file), lineEndSeparator, charsetName);
		}
		
		private static final String read(InputStream inputStream, String lineEndSeparator, String charsetName) {
			return read0(inputStream, lineEndSeparator, charsetName).toString();
		}
		
		private static final void read(byte[] inStrByte, String charsetName, ReadRow readRow) {
			read(CStream.createInputStream(inStrByte), charsetName, readRow);
		}
		
		private static final void read(String uriStr, String charsetName, ReadRow readRow) {
			read(URI.create(uriStr), charsetName, readRow);
		}
		
		private static final void read(URI uri, String charsetName, ReadRow readRow) {
			read(CStream.createInputStream(uri), charsetName, readRow);
		}
		
		private static final void read(URL url, String charsetName, ReadRow readRow) {
			read(CStream.createInputStream(url), charsetName, readRow);
		}
		
		private static final void read(File file, String charsetName, ReadRow readRow) {
			read(CStream.createInputStream(file), charsetName, readRow);
		}
		
		private static final void read(InputStream inputStream, String charsetName, ReadRow readRow) {
			read1(inputStream, charsetName, readRow);
		}
		
		private static final String read(byte[] inStrByte, String charsetName, int rowNum) {
			return read(CStream.createInputStream(inStrByte), charsetName, rowNum);
		}
		
		private static final String read(String uriStr, String charsetName, int rowNum) {
			return read(URI.create(uriStr), charsetName, rowNum);
		}
		
		private static final String read(URI uri, String charsetName, int rowNum) {
			return read(CStream.createInputStream(uri), charsetName, rowNum);
		}
		
		private static final String read(URL url, String charsetName, int rowNum) {
			return read(CStream.createInputStream(url), charsetName, rowNum);
		}
		
		private static final String read(File file, String charsetName, int rowNum) {
			return read(CStream.createInputStream(file), charsetName, rowNum);
		}
		
		private static final String read(InputStream inputStream, String charsetName, int rowNum) {
			return read2(inputStream, charsetName, rowNum);
		}

		private static final int readRowCount(byte[] inStrByte) {
			return readRowCount(CStream.createInputStream(inStrByte));
		}
		
		private static final int readRowCount(String uriStr) {
			return readRowCount(URI.create(uriStr));
		}
		
		private static final int readRowCount(URI uri) {
			return readRowCount(CStream.createInputStream(uri));
		}
		
		private static final int readRowCount(URL url) {
			return readRowCount(CStream.createInputStream(url));
		}
		
		private static final int readRowCount(File file) {
			return readRowCount(CStream.createInputStream(file));
		}

		private static final int readRowCount(InputStream inputStream) {
			return read3(inputStream);
		}
		
		private static final StringBuilder read0(InputStream inputStream, String lineEndSeparator, String charsetName) {
			InputStreamReader inputStreamReader = null;
			BufferedReader bufferedReader = null;
			try {
				lineEndSeparator = (null == lineEndSeparator ? "\n" : lineEndSeparator);
				charsetName = (null == charsetName ? "UTF-8" : charsetName);
				try {
					inputStreamReader = new InputStreamReader(inputStream, charsetName);
				} catch (UnsupportedEncodingException e) {
					throw new BaseException("COMMON_READ_FILE_0knvR", "输入流不支持以字符集编码{1}进行读取", e, charsetName);
				}
				bufferedReader = new BufferedReader(inputStreamReader);
				StringBuilder stringBuilder = new StringBuilder();
				String line = null;
				try {
					while (null != (line = bufferedReader.readLine())) {
						stringBuilder.append(line).append(lineEndSeparator);
					}
				} catch (IOException e) {
					throw new BaseException("COMMON_READ_FILE_5eDfy", "文件[{0}]读取失败，详情——{1}", e, ExceptionUtils.out(e));
				}
				return stringBuilder;
			} finally {
				if(bufferedReader != null) {
					try { bufferedReader.close(); } catch (IOException e) { }
				}
				if(inputStreamReader!= null) {
					try { inputStreamReader.close(); } catch (IOException e) { }
				}
				if(inputStream != null) {
					try { inputStream.close(); } catch (IOException e) { }
				}
			}
		}
		
		private static final void read1(InputStream inputStream, String charsetName, ReadRow readRow) {
			InputStreamReader inputStreamReader = null;
			BufferedReader bufferedReader = null;
			try {
				charsetName = (null == charsetName ? "UTF-8" : charsetName);
				try {
					inputStreamReader = new InputStreamReader(inputStream, charsetName);
				} catch (UnsupportedEncodingException e) {
					throw new BaseException("COMMON_READ_FILE_0k65L", "输入流不支持以字符集编码{1}进行读取", e, charsetName);
				}
				bufferedReader = new BufferedReader(inputStreamReader);
				String line = null;
				try {
					for (int i = 1; null != (line = bufferedReader.readLine()); i++) {
						readRow.read(line, i);
					}
				} catch (IOException e) {
					throw new BaseException("COMMON_READ_FILE_ib4V3", "文件[{0}]读取失败，详情——{1}", e, ExceptionUtils.out(e));
				}
			} finally {
				if(bufferedReader != null) {
					try { bufferedReader.close(); } catch (IOException e) { }
				}
				if(inputStreamReader!= null) {
					try { inputStreamReader.close(); } catch (IOException e) { }
				}
				if(inputStream != null) {
					try { inputStream.close(); } catch (IOException e) { }
				}
			}
		}
		
		private static final String read2(InputStream inputStream, String charsetName, int rowNum) {
			InputStreamReader inputStreamReader = null;
			BufferedReader bufferedReader = null;
			try {
				charsetName = (null == charsetName ? "UTF-8" : charsetName);
				try {
					inputStreamReader = new InputStreamReader(inputStream, charsetName);
				} catch (UnsupportedEncodingException e) {
					throw new BaseException("COMMON_READ_FILE_G1gvR", "输入流不支持以字符集编码{1}进行读取", e, charsetName);
				}
				bufferedReader = new BufferedReader(inputStreamReader);
				String line = null;
				try {
					for (int i = 1; null != (line = bufferedReader.readLine()); i++) {
						if(i == rowNum) {
							return line;
						}
					}
					return null;
				} catch (IOException e) {
					throw new BaseException("COMMON_READ_FILE_kwQ20", "文件[{0}]读取失败，详情——{1}", e, ExceptionUtils.out(e));
				}
			} finally {
				if(bufferedReader != null) {
					try { bufferedReader.close(); } catch (IOException e) { }
				}
				if(inputStreamReader!= null) {
					try { inputStreamReader.close(); } catch (IOException e) { }
				}
				if(inputStream != null) {
					try { inputStream.close(); } catch (IOException e) { }
				}
			}
		}
		
		private static final int read3(InputStream inputStream) {
			InputStreamReader inputStreamReader = null;
			BufferedReader bufferedReader = null;
			try {
				inputStreamReader = new InputStreamReader(inputStream);
				bufferedReader = new BufferedReader(inputStreamReader);
				try {
					int count = 1;
					while(null != bufferedReader.readLine()) {
						count++;
					}
					return count;
				} catch (IOException e) {
					throw new BaseException("COMMON_READ_FILE_6jd2d", "文件[{0}]读取失败，详情——{1}", e, ExceptionUtils.out(e));
				}
			} finally {
				if(bufferedReader != null) {
					try { bufferedReader.close(); } catch (IOException e) { }
				}
				if(inputStreamReader!= null) {
					try { inputStreamReader.close(); } catch (IOException e) { }
				}
				if(inputStream != null) {
					try { inputStream.close(); } catch (IOException e) { }
				}
			}
		}
		
	}

	/** @author hcqt@qq.com */
	public interface ReadRow {
		/** @author hcqt@qq.com */
		void read(String rowText, int rowNum);
	}

	/** @author hcqt@qq.com */
	private static final class WR {

		private static void write(byte[] inByte, OutputStream outputStream) {
			write(CStream.createInputStream(inByte), outputStream);
		}

		private static void write(byte[] inByte, File outFile) {
			write(CStream.createInputStream(inByte), CStream.createOutputStream(outFile));
		}
		
		private static void write(byte[] inByte, URI outUri) {
			write(CStream.createInputStream(inByte), CStream.createOutputStream(outUri));
		}
		
		private static void write(byte[] inByte, URL outUrl) {
			write(CStream.createInputStream(inByte), CStream.createOutputStream(outUrl));
		}
		
		private static void write(String inString, OutputStream outputStream) {
			write(CStream.createInputStream(inString.getBytes()), outputStream);
		}
		
		private static void write(String inString, File outFile) {
			write(CStream.createInputStream(inString.getBytes()), CStream.createOutputStream(outFile));
		}
		
		private static void write(String inString, URI outUri) {
			write(CStream.createInputStream(inString.getBytes()), CStream.createOutputStream(outUri));
		}
		
		private static void write(String inString, URL outUrl) {
			write(CStream.createInputStream(inString.getBytes()), CStream.createOutputStream(outUrl));
		}

		private static void write(File inFile, OutputStream outputStream) {
			write(CStream.createInputStream(inFile), outputStream);
		}

		private static void write(URI inUri, OutputStream outputStream) {
			write(CStream.createInputStream(inUri), outputStream);
		}
		
		private static void write(URL inUrl, OutputStream outputStream) {
			write(CStream.createInputStream(inUrl), outputStream);
		}

		private static void write(InputStream inputStream, URI outUri) {
			write(inputStream, CStream.createOutputStream(outUri));
		}

		private static void write(InputStream inputStream, URL outUrl) {
			write(inputStream, CStream.createOutputStream(outUrl));
		}

		private static void write(InputStream inputStream, OutputStream outputStream) {
			try {
				byte[] b = new byte[1024];
				int i = 0;
				while(true) {
					try {
						i = inputStream.read(b);
					} catch (IOException e) {
						throw new BaseException("COMMON_IO_UTIL_4gr6S", "读取输入流出错，详情——{0}", e, ExceptionUtils.out(e));
					}
					if(-1 == i) {
						break;
					}
					try {
						outputStream.write(b, 0, i);
					} catch (IOException e) {
						throw new BaseException("COMMON_IO_UTIL_u4vR4", "写入输出流出错，详情——{0}", e, ExceptionUtils.out(e));
					}
				}
			} finally {
				if(inputStream != null) {
					try { inputStream.close(); } catch (IOException e) { }
				}
				if(outputStream != null) {
					try { outputStream.flush(); } catch (IOException e) { }
					try { outputStream.close(); } catch (IOException e) { }
				}
			}
		}

		/**
		 * 把字符串写入文件当中
		 * @param file 要写入的文件
		 * @param content 要写入的字符串
		 * @param append 追加还是覆盖，true为追加，false为覆盖
		 * @throws IOException
		 */
		private static void writeString(File file, String content, boolean isAppend) throws IOException {
			FileWriter fileWriter = null;
			try {
				fileWriter = new FileWriter(file, isAppend);
				fileWriter.write(content);
			} finally {
				if(null != fileWriter) { 
					try { fileWriter.close(); } catch (IOException e) { e.printStackTrace(); } 
				}
			}
		}

	}

	/** @author hcqt@qq.com */
	private static final class CStream {
		private static final InputStream createInputStream(URI inUri) {
			if(inUri == null) {
				throw new BaseException("COMMON_IO_UTIL_9ncds", "创建输入流要求传入方法的java.net.URI实参不能为null");
			}
			try {
				return createInputStream(inUri.toURL());
			} catch (MalformedURLException e) {
				throw new BaseException("COMMON_IO_UTIL_34gfc", "URL格式不正确，出错URL——{0}", e, inUri);
			}
		}
		private static final InputStream createInputStream(URL inUrl) {
			if(inUrl == null) {
				throw new BaseException("COMMON_IO_UTIL_pMFe0", "创建输入流要求传入方法的java.net.URL实参不能为null");
			}
			try {
				return inUrl.openStream();
			} catch (IOException e) {
				throw new BaseException("COMMON_IO_UTIL_mghs1", "无法从指定的URL创建输入流，URL路径——{0}", e, inUrl);
			}
		}
		private static final InputStream createInputStream(byte[] inByte) {
			if(inByte == null) {
				throw new BaseException("COMMON_IO_UTIL_6fhzk", "创建输入流要求传入方法的byte[]实参不能为null");
			}
			return new ByteArrayInputStream(inByte);
		}
		private static final InputStream createInputStream(File inFile) {
			if(inFile == null) {
				throw new BaseException("COMMON_IO_UTIL_6jfIe", "创建输入流要求传入方法的java.io.File实参不能为null");
			}
			try {
				return new FileInputStream(inFile);
			} catch (FileNotFoundException e) {
				throw new BaseException("COMMON_IO_UTIL_8g3gP", "无法从不存在的文件创建输入流，详情——{0}", e, ExceptionUtils.out(e));
			}
		}

		private static final OutputStream createOutputStream(File outFile) {
			if(outFile == null) {
				throw new BaseException("COMMON_IO_UTIL_6jf4g", "创建输出流要求传入方法的java.io.File实参不能为null");
			}
			try {
				return new FileOutputStream(outFile);
			} catch (FileNotFoundException e) {
				throw new BaseException("COMMON_IO_UTIL_5hd7D", "无法从不存在的文件创建输出流，详情——{0}", e, ExceptionUtils.out(e));
			}
		}

		private static final OutputStream createOutputStream(URI outUri) {
			if(outUri == null) {
				throw new BaseException("COMMON_IO_UTIL_9jskO", "创建输出流要求传入方法的java.net.URI实参不能为null");
			}
			try {
				return new FileOutputStream(createFile(outUri));
			} catch (FileNotFoundException e) {
				throw new BaseException("COMMON_IO_UTIL_qgL2M", "无法从不存在的文件创建输出流，详情——{0}", e, ExceptionUtils.out(e));
			}
		}

		private static final OutputStream createOutputStream(URL outUrl) {
			if(outUrl == null) {
				throw new BaseException("COMMON_IO_UTIL_i3kdS", "创建输出流要求传入方法的java.net.URL实参不能为null");
			}
			try {
				return new FileOutputStream(createFile(outUrl));
			} catch (FileNotFoundException e) {
				throw new BaseException("COMMON_IO_UTIL_5hd7D", "无法从不存在的文件创建输出流，详情——{0}", e, ExceptionUtils.out(e));
			}
		}

		private static final File createFile(URI uri) {
			if(null == uri) {
				throw new BaseException("COMMON_IO_UTIL_jk3b3", "创建文件要求传入方法的java.net.URI实参不能为null");
			}
			return new File(uri);
		}
		
		private static final File createFile(URL url) {
			if(null == url) {
				throw new BaseException("COMMON_IO_UTIL_wju27", "创建文件要求传入方法的java.net.URL实参不能为null");
			}
			try {
				return new File(url.toURI());
			} catch (URISyntaxException e) {
				throw new BaseException("COMMON_IO_UTIL_3fcjh", "因语法错误，URL无法转换为URI，URL——{0}", e, ExceptionUtils.out(e));
			}
		}

	}

}
