package tech.japannews.trancatedb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import tech.japannews.trancatedb.utils.Const;


public class TrancateDB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {

			TruncateMessage("message");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void TruncateMessage(String tableName) throws IOException {
		// TODO Auto-generated method stub
		URL url = new URL(Const.TRUNCATE_DB);
		
		Map<String,Object> params = new LinkedHashMap<>();

        params.put("message", tableName);
  
        String results = PostToServer(url,params);
        System.out.println(results);
	}
	
	private static String PostToServer(URL url, Map<String,Object> params) throws IOException{
		StringBuilder builder = new StringBuilder();
		
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        String line;
        while ((line= in.readLine()) != null){
        	builder.append(line);
        }
        
        return builder.toString();
    
	}
}
