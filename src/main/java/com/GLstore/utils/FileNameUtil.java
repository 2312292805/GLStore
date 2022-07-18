package com.GLstore.utils;

import java.util.UUID;
//文件上传到服务器端以后进行重新命名
public class FileNameUtil {
	//根据UUID生成文件名
	public static String getUUIDFileName() {
		UUID uuid = UUID.randomUUID();
		//先生成一个36位的字符串，然后将中间的“-”替换为“”，转化成32位的字符串
		return uuid.toString().replace("-", "");
	}
	//从请求头中提取文件名和类型
	public static String getRealFileName(String context) {
		// Content-Disposition: form-data; name="myfile"; filename="a_left.jpg"
		int index = context.lastIndexOf("=");
		String filename = context.substring(index + 2, context.length() - 1);
		return filename;
	}
	//根据给定的文件名和后缀截取文件名
	public static String getFileType(String fileName){
		//9527s.jpg
		int index = fileName.lastIndexOf(".");
		return fileName.substring(index);
	}
}
