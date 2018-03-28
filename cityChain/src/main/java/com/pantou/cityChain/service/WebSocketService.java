package com.pantou.cityChain.service;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

@ServerEndpoint(value = "/websocket")
@Component
public class WebSocketService {

	// 所有连接会话
	public static CopyOnWriteArraySet<WebSocketService> webSocketServices = new CopyOnWriteArraySet<WebSocketService>();
	// 与客户端的连接会话
	private Session session;

	/**
	 * 建立连接
	 */
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		webSocketServices.add(this);
		sendMessage("hi: " + this.toString());
		System.out.println("建立连接！当前在线：" + getOnlineCount());
	}

	/**
	 * 关闭连接
	 */
	@OnClose
	public void onClose() {
		webSocketServices.remove(this);
		System.out.println("关闭连接！当前在线：" + getOnlineCount());
	}

	/**
	 * 接收客户端消息
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("接收客户端消息：" + message);
		sendMessageAll(message);
	}

	/**
	 * 发生错误
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		error.printStackTrace();
		System.out.println("发生错误！当前在线：" + getOnlineCount());
	}

	/**
	 * 发消息
	 */
	public void sendMessage(String message) {
		try {
			this.session.getBasicRemote().sendText(message);
			// this.session.getAsyncRemote().sendText(message);
			System.out.println("发消息：" + message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 群发消息
	 */
	public static void sendMessageAll(String message) {
		for (WebSocketService webSocketService : webSocketServices) {
			webSocketService.sendMessage(message);
		}
		System.out.println("群发消息：" + message);
	}

	/**
	 * 获取连接数
	 */
	public static int getOnlineCount() {
		return webSocketServices.size();
	}
}