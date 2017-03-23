package mJamsek;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import spark.Response;

public class Utils {
	
	public static HttpServletResponse downloadFile(String filename, Response res){
		Path path = Paths.get(Main.DATA_DIR + filename);
		byte[] data = null;
		
		try{
			data = Files.readAllBytes(path);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		HttpServletResponse raw = res.raw();
		
		try{
			raw.getOutputStream().write(data);
			raw.getOutputStream().flush();
			raw.getOutputStream().close();
		} catch (Exception e){
			e.printStackTrace();
		}
		return raw;
	}
}
