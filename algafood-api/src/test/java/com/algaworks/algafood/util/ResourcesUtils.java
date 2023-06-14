package com.algaworks.algafood.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.util.StreamUtils;

public class ResourcesUtils {
	
	public static String getContentFromResource(String resource) {
		try {
			InputStream stream = ResourcesUtils.class.getResourceAsStream(resource);
			return StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
		}catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

}
