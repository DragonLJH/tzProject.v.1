package yb.dragon.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

public class Common {
	/**
	 * ip->long： 1.将ip地址按字符串读入，用分隔符分割开后成为一个字符串数组{xyzo}。
	 * 2.将数组里的字符串强转为long类型后执行：x^24+y^16+z^8+o 得到最后的返回值。 3.这里的加权采用移位(<<)完成。
	 * 
	 * @param strIp :ip地址 例：x.y.z.o
	 * @return 转换后的long类型值
	 */
	public long ipToLong(String strIp) {
		long ip = 0;
		if (strIp.indexOf(".") != -1) {
			String[] s = strIp.split("\\.");
			ip = (Long.parseLong(s[0]) << 24) + (Long.parseLong(s[1]) << 16) + (Long.parseLong(s[2]) << 8)
					+ (Long.parseLong(s[3]));
		}
		return ip;
	}

	// 生成唯一的UUID
	public String MyUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString().split("-")[0];
		return str;
	}

	// 在linux服务器下创建响应的目录
	public void createFile(String domain, String uuid) {
		File f = new File("/www/wwwroot/" + domain + "/" + uuid);
		if (f.exists()) {
		} else {
			// 先创建文件所在的目录
			f.mkdirs();
			try {
				// 创建新文件
//					f.createNewFile();
				copy("/www/wwwroot/" + domain + "/", "index.html", "/www/wwwroot/" + domain + "/" + uuid + "/");
			} catch (IOException e) {
				System.out.println("创建新文件时出现了错误。。。");
				e.printStackTrace();
			}

		}

	}

	/**
	 * @param path(目标路径)
	 * @param filename(目标文件名称)
	 * @param specifypath(指定路径)
	 * @throws Exception
	 */
	public void copy(String path, String filename, String specifypath) throws IOException {
		String filepath = path + filename;
		InputStream inputStream = new FileInputStream(filepath);
		OutputStream outputStream = new FileOutputStream(specifypath + filename);
		final byte[] bytes = new byte[1024];
		int read = inputStream.read(bytes); // 读到的字节数
		while (read != -1) {
			outputStream.write(bytes, 0, read); // 写，即输出
			System.out.println(outputStream);
			read = inputStream.read(bytes); // 接着读
		}
		outputStream.close();
		inputStream.close();
	}

	// 在linux服务器下删除目录
	public void deleteFile(String domain, String uuid) {
		// 获取域名，uuid文件夹
		File file = new File("/www/wwwroot/" + domain + "/" + uuid);
		deleteDir(file);
	}

	// 在linux服务器下删除目录
	public static void deleteDir(File dir) {
		if (!dir.isDirectory()) {
			System.out.println("选择的不是文件目录");
			return;
		}
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isDirectory()) {
				// 如果是文件目录，则继续循环
				deleteDir(file);
			} else {
				// 如果是文件则删除文件
				boolean isDeleted = file.delete();
				if (!isDeleted) {
					System.out.println("文件删除异常");
				}
			}
			// 最后删除目录
			dir.delete();
		}
	}

	// java访问url获取json数据
	public String loadJson(String ip) {
		String url = "https://restapi.amap.com/v3/ip?ip=" + ip + "&output=json&key=7e828137946ccc5600cd5401d69fc38d";
		StringBuilder json = new StringBuilder();
		try {
			URL urlObject = new URL(url);
			URLConnection uc = urlObject.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "utf-8"));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				json.append(inputLine);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}

}
