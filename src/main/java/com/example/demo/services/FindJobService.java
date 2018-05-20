package com.example.demo.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.entity.Vacancy;
@Component
public class FindJobService {
	
	public static final String[] req_words = {"Requirements","Main requirements","Требования","Вимоги до кандидата"
			,"ТРЕБОВАНИЯ","Ожидаем от кандидата","Must have","Необходимые навыки:" 
			,"техническими навыками","требования","Требования","Ожидаем","ожидаем", "техническими навыками:","Наши пожелания","we expect from"
			,"Job Requirements","Required skills","Required Skills","Qualifications"};
	
	
	public List<Vacancy> getVacancy(String url) throws IOException, ProtocolException, UnsupportedEncodingException {
		
		url = url.replaceAll(" ", "-");
		List<String> lines = new LinkedList<>();
	
		doGetVacancy(lines, "https://rabota.ua/zapros/"+url);
		
		//lines has strings with vacancy name, url and other trash
		
		List<Vacancy> vacancy = new ArrayList<>(); 
		initList(lines.size(), vacancy);
		
		setPostFromLines(lines, vacancy);
		setUrlFromLines(lines,vacancy);
		setRequerments(vacancy);
		
		return vacancy;
	}
	
	
	public void doGetVacancy(List<String> lines, String s_url) throws IOException, ProtocolException, UnsupportedEncodingException {
		URL url = new URL(s_url);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		
		try {
		con.connect();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8" ));
				String inputLine;
				StringBuffer content = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
				    content.append(inputLine);
				    content.append("\n");
				    if(inputLine.contains("fd-beefy-gunso f-vacancylist-vacancytitle"))lines.add(inputLine);
				    
				}
				in.close();
		}finally {
			con.disconnect();
		}
	}

	public void setRequerments(List<Vacancy> vacancy) {
		for (Vacancy vac : vacancy) {
			setRequirments(vac.getVacancy_url(), vac);

		}
		
	}

	public void setRequirments(String URL, Vacancy vac) {
		String code = loadVacPage(URL); //optimized or shorten variant

		try {
			setVacancyRequirments(code,vac);
		}catch(Exception e) {
			System.out.println("another error");
		}
	}
	
	private String loadVacPage(String URL) {
		String code = null;
		HttpURLConnection con = null;
		StringBuffer content = null;
		try {
		URL url = new URL(URL);
		con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.connect();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8" ));
		String inputLine;
		content = new StringBuffer();
			
			while ((inputLine = in.readLine()) != null) {
				if(inputLine.contains("class=\"d_content\"") ) { //optimized way
				for (String word : req_words) {
					if(inputLine.contains(word)) {
						code = inputLine;
					    break;
					}
				}
			    if(code != null) break;
			   
				}
				content.append(inputLine);
			}
			
			in.close(); //bad close need to be in finnaly
		} catch (Exception e) {
			e.printStackTrace();
		
		}finally {
			con.disconnect();
		}
		if(code == null) return content.toString(); // если не получилось выдернуть оптимизированный вариант, то передаю целый файл

		return code;
	}
	
	public void setVacancyRequirments(String code, Vacancy vac) {
		int pos = 0;
		String key_word = null;
		String target_line = null;
	
		for (String word : req_words) {
			if(code.contains(word)) {
				
			pos = code.lastIndexOf(word) + word.length();
			target_line = code;
			key_word = word;
			}
		}
//		if(key_word.equals("Job Requirements")) { TODO TEST
//		System.out.println("finded");
//		System.out.println(target_line);
//		}
		setVacReq(pos,target_line,vac,key_word);
	
	}

	public void setVacReq(int pos, String target_line, Vacancy vac, String key_word) {

		try {
			
	
			pos = target_line.lastIndexOf(key_word);

			target_line = target_line.substring( pos);
			int begin = target_line.indexOf("<ul>");
			int end = target_line.indexOf("</ul>");
	
			String req = target_line.substring(begin,end);
	
			
			
			req = req.replaceAll( ";", "").
					replaceAll("<p>|</p>|</li>|<br>|<li>|<ul>", "");

			
			vac.setRequirments(req.toLowerCase());
			
			
			}catch(Exception e) {
				System.out.println("some exception");
			//	e.printStackTrace();
			}
	
	}


	public void initList(int size, List<Vacancy> vacancy) {
		for(int i = 0; i < size; i++) {
			vacancy.add(new Vacancy());
		}
	}

	public void setUrlFromLines(List<String> lines, List<Vacancy> vacancy) {
		List<String> urls = new ArrayList<>();
		for (String single_line : lines) {
			int indexOf = single_line.lastIndexOf("href=\"/company");
			urls.add(single_line.substring(indexOf + "href=\"/company".length(),single_line.lastIndexOf("\">")));
		}

		for (int i = 0; i <urls.size();i++) {
			if(urls.get(i).length() > 50) continue;
			vacancy.get(i).setVacancy_url("https://rabota.ua/company" + urls.get(i) );
		}
	}

	
	public void setPostFromLines(List<String> lines, List<Vacancy> vacancy) {
		List<String> parsedLines = new LinkedList<>();
		for (String line : lines) {
			int lastIndexOf = line.lastIndexOf(">");
			parsedLines.add(line.substring(lastIndexOf +1, line.length() ));
			
		}
		
		for (int i = 0; i < parsedLines.size();i++) {
			vacancy.get(i).setPost(parsedLines.get(i));
		}
		
	}


	public void setCorresponding(String pref, List<Vacancy> vacancy) {
		pref=pref.toLowerCase().replaceAll("  ", " ");//два пробела уберёт в один
		
		String[] prefs = pref.split(";");
		int prefs_size = prefs.length;
		///
		for (Vacancy vac : vacancy) {
			
		int vac_corresp = 0;
		for (String str : prefs) {
			if(vac.getRequirments() != null && vac.getRequirments().contains(str))vac_corresp++;
			
		}
		int corresponding = vac_corresp*100/prefs_size;
		vac.setCorresponding(corresponding);
		}
		
	}
	
	
}