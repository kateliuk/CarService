package com.cqhot.api.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
	public static String openUri(URI uri, UrlEncodedFormEntity entity)
			throws ClientProtocolException, IOException {
		HttpPost httppost = new HttpPost(uri);
		if (entity != null)
			httppost.setEntity(entity);
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response = null;
		response = httpclient.execute(httppost);
		int code = response.getStatusLine().getStatusCode();
		if (code == HttpStatus.SC_OK) {
			HttpEntity entity1 = response.getEntity();
			BufferedReader rd = null;
			rd = new BufferedReader(new InputStreamReader(entity1.getContent()), 8192);
			String Info = "", line = "";
			while ((line = rd.readLine()) != null)
				Info += line;
			return Info;
		}
		return null;
	}
	public static String openSSLUri(String uri, String method) throws ClientProtocolException, IOException {
		return openSSLUri(uri, null, method);
	}
	public static String openUri(String uri, UrlEncodedFormEntity entity) throws ClientProtocolException, IOException {
		HttpPost httppost = new HttpPost(uri);
		if (entity != null)
			httppost.setEntity(entity);
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response = null;
		response = httpclient.execute(httppost);
		int code = response.getStatusLine().getStatusCode();
		if (code == HttpStatus.SC_OK) {
			HttpEntity entity1 = response.getEntity();
			BufferedReader rd = null;
			rd = new BufferedReader(new InputStreamReader(entity1.getContent()), 8192);
			String Info = "", line = "";
			while ((line = rd.readLine()) != null)
				Info += line;
			return Info;
		}
		return null;
	}

	public static String openSSLUri(String uri, UrlEncodedFormEntity entity, String method)
			throws ClientProtocolException, IOException {
		HttpUriRequest request = null;
		if (method.equals("GET"))
			request = new HttpGet(uri);
		else if (method.equals("POST")) {
			HttpPost httppost = new HttpPost(uri);
			if (entity != null)
				httppost.setEntity(entity);
			request = httppost;
		} else
			return null;
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		HttpClient httpclient = getNewHttpClient();
		HttpResponse response = httpclient.execute(request);
		int code = response.getStatusLine().getStatusCode();
		if (code == HttpStatus.SC_OK) {
			HttpEntity entity1 = response.getEntity();
			BufferedReader rd = new BufferedReader(new InputStreamReader(entity1.getContent()),
					8192);
			String Info = "", line = "";
			while ((line = rd.readLine()) != null)
				Info += line;
			return Info;
		}
		return null;
	}

	public static HttpClient getNewHttpClient() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params, 10000);
			HttpConnectionParams.setSoTimeout(params, 10000);
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));
			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
			HttpConnectionParams.setConnectionTimeout(params, 50000);
			HttpConnectionParams.setSoTimeout(params, 200000);
			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

	private static class MySSLSocketFactory extends SSLSocketFactory {
		SSLContext sslContext = SSLContext.getInstance("TLS");

		public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException,
				KeyManagementException, KeyStoreException, UnrecoverableKeyException {
			super(truststore);
			TrustManager tm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain, String authType)
						throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType)
						throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port, boolean autoClose)
				throws IOException, UnknownHostException {
			injectHostname(socket, host);
			return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}

		private void injectHostname(Socket socket, String host) {
			try {
				Field field = InetAddress.class.getDeclaredField("hostName");
				field.setAccessible(true);
				field.set(socket.getInetAddress(), host);
			} catch (Exception ignored) {
			}
		}
	}

	public static boolean detectNetwork(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager == null)
			return false;
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isAvailable())
			return false;
		return true;
	}
}
