package com.bna.smile.model.SMS.traitement;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpURLConnectionExample {
	
	String httpLogin;
	String httpPwd;
	String wbLogin;
	String wbPwd;
	String wbAccount;
	String destNum; //list of destination mobile numbers separated by ;
	String msg=null; //message to send
	int type=0;// 0 : latin ; 2 : arabic
	String date;//sending day ; format dd/mm/yyyy',
	String hour;//sending hour ; format HH
	String minute;//sending minute ; format MM
	String label; //source label
	String reference; //sending reference
	String application=null;// sending application
	String body = "";
	
	public static void main(String[] args) throws Exception {

		HttpURLConnectionExample http = new HttpURLConnectionExample();
		http.httpLogin=""; //not used in bnasms
		http.httpPwd=""; //not used in bnasms
		http.wbLogin="medyosn";
		http.wbPwd="000000";
		http.wbAccount="BNA";
		http.destNum="40515824";
		http.msg="test";
		http.label="BNASMS";
		http.reference="";
		http.application="SMILE"; //your application / version
		Date actuelle = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		http.date = dateFormat.format(actuelle);
		http.sendPost();

	}

	private void ssl() throws Exception {
		
		/************************************************Avoid SSL***************************************/ 
        
	    TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	            public void checkClientTrusted(X509Certificate[] certs, String authType) {
	            }
	            public void checkServerTrusted(X509Certificate[] certs, String authType) {
	            }
	        }
	    };

	    // Install the all-trusting trust manager
	    SSLContext sc = SSLContext.getInstance("SSL");
	    sc.init(null, trustAllCerts, new java.security.SecureRandom());
	    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	    // Create all-trusting host name verifier
	    HostnameVerifier allHostsValid = new HostnameVerifier() {
	        public boolean verify(String hostname, SSLSession session) {
	            return true;
	        }
	    };

	    // Install the all-trusting host verifier
	    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid); 		
	  
	    /***************************************************************************************/ 
		
		
	}
	
		private void setHeader(HttpsURLConnection con, int i) throws Exception {
			//final String s =  this.httpLogin+":"+this.httpPwd;
		    //final byte[] authBytes = s.getBytes(StandardCharsets.UTF_8);
		    //final String encoded = Base64.getEncoder().encodeToString(authBytes);
			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", this.application);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setDoInput(true);
		    con.setDoOutput(true);
		    con.setUseCaches(false);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setRequestProperty("Content-Length", ""+i);
		    //con.setRequestProperty("Authorization", "Basic " + encoded);
		}
	
	// HTTP POST request
	private void sendPost() throws Exception {
		
		this.ssl();
		
		String urlParameters = "login="+this.wbLogin+
        		"&pass="+this.wbPwd+
        		"&compte="+this.wbAccount+
        		"&op=1"+
        		"&dest_num="+this.destNum+
        		"&msg="+this.msg+
        		"&type="+this.type+
        		"&dt="+this.date+
        		"&hr=00"+
        		"&mn=00"+
        		"&label="+this.label+
        		"&ref="+this.reference;
		
		String url = "http://10.1.8.1/wbmonitor/send/webapi/send_ack.php";
		
		URL obj = new URL(url);
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		//this.setHeader(con,urlParameters.length());
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\n Sending 'POST' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		System.out.println(response.toString());

	}

}
