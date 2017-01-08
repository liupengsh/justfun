package com.justfun.http;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * HTTP工具类
 * 
 * @author chenxiangde
 */
public class MultiThreadHttpClient {
	private static Log logger = LogFactory.getLog(MultiThreadHttpClient.class);
	// 多线程
	private static MultiThreadedHttpConnectionManager connectionManager;

	public static MultiThreadedHttpConnectionManager getMultiThreadedHttpConnectionManager() {
		if (connectionManager != null) {
			return connectionManager;
		} else {
			connectionManager = new MultiThreadedHttpConnectionManager();
			// 测试是否超时
			HttpConnectionManagerParams managerParams = connectionManager.getParams();
			// 设置连接超时时间(单位毫秒)
			managerParams.setConnectionTimeout(200000);
			// 设置读数据超时时间(单位毫秒)
			managerParams.setSoTimeout(180000);
			managerParams.setDefaultMaxConnectionsPerHost(10);// 系统默认：2 host数
			managerParams.setMaxTotalConnections(200);// 系统默认20，连接总数
			return connectionManager;
		}
	}
	
	public static Integer getMethod(String url) {
		logger.info("regUrl:" + url);
//		HttpClient httpClient = new HttpClient(MultiThreadHttpClient.getMultiThreadedHttpConnectionManager());
		HttpClient httpClient = new HttpClient();

		int status = -1;
		GetMethod getMethod = new GetMethod(url);
		try {
			long startTime = System.currentTimeMillis();
			status = httpClient.executeMethod(getMethod);
			long endTime = System.currentTimeMillis();
			logger.info(status + "status,连接用时" + (endTime - startTime) + "ms");
			if (status == HttpStatus.SC_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream(), "utf-8"));
				String line = "";
				StringBuffer buffer = new StringBuffer();
				while ((line = br.readLine()) != null) {
					buffer.append(line);
				}
			} else {
				logger.info("服务器HTTP响应异常!状态码:" + status);
			}
		} catch (HttpException e) {
			logger.info("发生Http异常:" + e);
		} catch (NoRouteToHostException e) {
			logger.info("本机未联网:" + e);
		} catch (ConnectException e) {
			logger.info("连接不上服务器:" + e);
		} catch (SocketTimeoutException e) {
			logger.info("读取数据超时:" + e);
		} catch (ConnectTimeoutException e) {
			logger.info("连接超时:" + e);
		} catch (IOException e) {
			logger.info("发生网络异常:" + e);
		} catch (Exception e) {
			logger.info("发生异常:" + e);
		} finally {
			getMethod.releaseConnection();
		}
		return status;
	}

	@SuppressWarnings("deprecation")
	public static String postMethod(String reqUrl, String strStream) {
		String returnStr = "";
		logger.info("regUrl:" + reqUrl);
		HttpClient httpClient = new HttpClient(MultiThreadHttpClient.getMultiThreadedHttpConnectionManager());

		PostMethod postMethod = new PostMethod(reqUrl);
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			postMethod.setRequestBody(strStream);
			long startTime = System.currentTimeMillis();
			int status = httpClient.executeMethod(postMethod);
			long endTime = System.currentTimeMillis();
			logger.info(status + "status,连接用时" + (endTime - startTime) + "ms");
			if (status == HttpStatus.SC_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream(), "utf-8"));
				String line = "";
				StringBuffer buffer = new StringBuffer();
				while ((line = br.readLine()) != null) {
					buffer.append(line);
				}
				logger.info("响应体:" + buffer.toString());
				returnStr = buffer.toString();
			} else {
				logger.info("服务器HTTP响应异常!状态码:" + status);
			}
		} catch (HttpException e) {
			logger.info("发生Http异常:" + e);
		} catch (NoRouteToHostException e) {
			logger.info("本机未联网:" + e);
		} catch (ConnectException e) {
			logger.info("连接不上服务器:" + e);
		} catch (SocketTimeoutException e) {
			logger.info("读取数据超时:" + e);
		} catch (ConnectTimeoutException e) {
			logger.info("连接超时:" + e);
		} catch (IOException e) {
			logger.info("发生网络异常:" + e);
		} catch (Exception e) {
			logger.info("发生异常:" + e);
		} finally {
			postMethod.releaseConnection();
		}
		return returnStr;
	}
	
	@SuppressWarnings("deprecation")
	public static String postStream(String reqUrl, String data) {
		String returnStr = "";
		logger.info("regUrl:" + reqUrl);
		HttpClient httpClient = new HttpClient(MultiThreadHttpClient.getMultiThreadedHttpConnectionManager());

		PostMethod postMethod = new PostMethod(reqUrl);
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			postMethod.setRequestBody(new ByteArrayInputStream(data.getBytes("UTF-8")));
			long startTime = System.currentTimeMillis();
			int status = httpClient.executeMethod(postMethod);
			long endTime = System.currentTimeMillis();
			logger.info(status + "status,连接用时" + (endTime - startTime) + "ms");
			if (status == HttpStatus.SC_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream(), "utf-8"));
				String line = "";
				StringBuffer buffer = new StringBuffer();
				while ((line = br.readLine()) != null) {
					buffer.append(line);
				}
				String bufferStr = buffer.toString();
				logger.info("响应体:" + bufferStr);
				returnStr = bufferStr;
			} else {
				logger.info("服务器HTTP响应异常!状态码:" + status);
			}
		} catch (HttpException e) {
			logger.info("发生Http异常:" + e);
		} catch (NoRouteToHostException e) {
			logger.info("本机未联网:" + e);
		} catch (ConnectException e) {
			logger.info("连接不上服务器:" + e);
		} catch (SocketTimeoutException e) {
			logger.info("读取数据超时:" + e);
		} catch (ConnectTimeoutException e) {
			logger.info("连接超时:" + e);
		} catch (IOException e) {
			logger.info("发生网络异常:" + e);
		} catch (Exception e) {
			logger.info("==========转发至vm发生异常:" + e);
		} finally {
			postMethod.releaseConnection();
		}
		return returnStr;
	}
}
