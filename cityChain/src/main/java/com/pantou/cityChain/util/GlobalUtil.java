package com.pantou.cityChain.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 全局工具类
 */
public class GlobalUtil {

	/**
	 * 随机器
	 */
	public static Random random = new Random();

	/**
	 * 获取uuid
	 */
	public static String getUuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 根据索引获取
	 */
	public static <T extends Enum<T>> T valueOf(Class<T> clazz, int ordinal) {
		return (T) clazz.getEnumConstants()[ordinal];
	}

	/**
	 * 生成base64二维码
	 */
	public static byte[] createQrCode(String content) {
		try {
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			hints.put(EncodeHintType.MARGIN, 1);
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);
			int width = bitMatrix.getWidth();
			int height = bitMatrix.getHeight();
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
				}
			}
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(image, "JPG", out);
			return out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new byte[0];
	}
}
