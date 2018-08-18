package cn.sibat.iceflow.image.server.controller.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 使用拦截器实现Gzip压缩
 * @author iceflow
 */
@GzipWriter
@Provider
public class GzipWriterInterceptor implements WriterInterceptor {
	 Logger log = LoggerFactory.getLogger(GzipWriterInterceptor.class);

	@Override
	public final void aroundWriteTo(WriterInterceptorContext context)
			throws IOException, WebApplicationException {
		OutputStream outputStream = context.getOutputStream();
		context.setOutputStream(new GZIPOutputStream(outputStream));
		context.getHeaders().add("Content-Encoding", "gzip");
		context.proceed();
	}
}
