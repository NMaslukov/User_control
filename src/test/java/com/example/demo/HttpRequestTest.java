package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)

public class HttpRequestTest {

	@Test
	public void test() throws IOException {

		URL url = new URL("http://www.heroeswm.ru/pl_info.php?id=6036985");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		try {
		con.connect();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(),"windows-1251" ));
				String inputLine;
				StringBuffer content = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
				    content.append(inputLine);
				    content.append("\n");
				}
				System.out.println(content);

				in.close();
		}finally {
			con.disconnect();
		}
		
	}
	
}
