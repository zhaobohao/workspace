package org.springbootdev.modules.mockserver.config;


import com.google.common.collect.ImmutableSet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.rtsp.RtspResponseStatuses;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.mockserver.configuration.ConfigurationProperties;
import org.mockserver.log.MockServerEventLog;
import org.mockserver.log.model.LogEntry;
import org.mockserver.log.model.LogEntry.LogMessageType;
import org.mockserver.logging.MockServerLogger;
import org.mockserver.mappers.HttpServletRequestToMockServerRequestDecoder;
import org.mockserver.mock.HttpStateHandler;
import org.mockserver.mock.action.ActionHandler;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.MediaType;
import org.mockserver.model.PortBinding;
import org.mockserver.proxyconfiguration.ProxyConfiguration;
import org.mockserver.responsewriter.ResponseWriter;
import org.mockserver.scheduler.Scheduler;
import org.mockserver.scheduler.Scheduler.SchedulerThreadFactory;
import org.mockserver.serialization.PortBindingSerializer;
import org.mockserver.servlet.responsewriter.ServletResponseWriter;
import org.mockserver.socket.tls.NettySslContextFactory;
import org.slf4j.event.Level;
@WebServlet(name="mockserverServlet",urlPatterns = "/mockserver/*")
public class MockServerServlet extends HttpServlet implements ServletContextListener {
	private MockServerLogger mockServerLogger = new MockServerLogger(MockServerEventLog.class);
	private HttpStateHandler httpStateHandler;
	private Scheduler scheduler;
	private PortBindingSerializer portBindingSerializer;
	private HttpServletRequestToMockServerRequestDecoder httpServletRequestToMockServerRequestDecoder;
	private ActionHandler actionHandler;
	private EventLoopGroup workerGroup = new NioEventLoopGroup(ConfigurationProperties.nioEventLoopThreadCount(), new SchedulerThreadFactory(this.getClass().getSimpleName() + "-eventLoop"));

	public MockServerServlet() {
		this.httpServletRequestToMockServerRequestDecoder = new HttpServletRequestToMockServerRequestDecoder(this.mockServerLogger);
		this.scheduler = new Scheduler(this.mockServerLogger);
		this.httpStateHandler = new HttpStateHandler(this.mockServerLogger, this.scheduler);
		this.mockServerLogger = this.httpStateHandler.getMockServerLogger();
		this.portBindingSerializer = new PortBindingSerializer(this.mockServerLogger);
		this.actionHandler = new ActionHandler(this.workerGroup, this.httpStateHandler, (ProxyConfiguration)null, new NettySslContextFactory(this.mockServerLogger));
	}

	public void destroy() {
		this.shutdown();
	}

	public void contextInitialized(ServletContextEvent sce) {
	}

	public void contextDestroyed(ServletContextEvent sce) {
		this.shutdown();
	}

	private void shutdown() {
		this.scheduler.shutdown();
		if (!this.workerGroup.isShuttingDown()) {
			this.workerGroup.shutdownGracefully(100L, 750L, TimeUnit.MILLISECONDS).syncUninterruptibly();
		}

		this.httpStateHandler.stop();
	}

	public void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		ResponseWriter responseWriter = new ServletResponseWriter(new MockServerLogger(), httpServletResponse);
		HttpRequest request = null;

		try {
			request = this.httpServletRequestToMockServerRequestDecoder.mapHttpServletRequestToMockServerRequest(httpServletRequest);
			String hostHeader = request.getFirstHeader(HttpHeaderNames.HOST.toString());
			if (StringUtils.isNotBlank(hostHeader)) {
				this.scheduler.submit(() -> {
					ConfigurationProperties.addSubjectAlternativeName(hostHeader);
				});
			}

			if (!this.httpStateHandler.handle(request, responseWriter, true)) {
				if (request.getPath().getValue().equals("/_mockserver_callback_websocket")) {
					responseWriter.writeResponse(request, RtspResponseStatuses.NOT_IMPLEMENTED, "ExpectationResponseCallback, ExpectationForwardCallback or ExpectationForwardAndResponseCallback is not supported by MockServer deployed as a WAR", "text/plain");
				} else if (request.matches("PUT", new String[]{"/mockserver/status", "/status"}) || StringUtils.isNotBlank(ConfigurationProperties.livenessHttpGetPath()) && request.matches("GET", new String[]{ConfigurationProperties.livenessHttpGetPath()})) {
					responseWriter.writeResponse(request, HttpResponseStatus.OK, this.portBindingSerializer.serialize(PortBinding.portBinding(new Integer[]{httpServletRequest.getLocalPort()})), "application/json");
				} else if (request.matches("PUT", new String[]{"/mockserver/bind", "/bind"})) {
					responseWriter.writeResponse(request, RtspResponseStatuses.NOT_IMPLEMENTED);
				} else if (request.matches("PUT", new String[]{"/mockserver/stop", "/stop"})) {
					responseWriter.writeResponse(request, RtspResponseStatuses.NOT_IMPLEMENTED);
				} else {
					String portExtension = "";
					if ((httpServletRequest.getLocalPort() != 443 || !httpServletRequest.isSecure()) && httpServletRequest.getLocalPort() != 80) {
						portExtension = ":" + httpServletRequest.getLocalPort();
					}

					this.actionHandler.processAction(request, responseWriter, (ChannelHandlerContext)null, ImmutableSet.of(httpServletRequest.getLocalAddr() + portExtension, "localhost" + portExtension, "127.0.0.1" + portExtension), false, true);
				}
			}
		} catch (IllegalArgumentException var7) {
			this.mockServerLogger.logEvent((new LogEntry()).setType(LogMessageType.EXCEPTION).setLogLevel(Level.ERROR).setHttpRequest(request).setMessageFormat("exception processing:{}error:{}").setArguments(new Object[]{request, var7.getMessage()}));
			responseWriter.writeResponse(request, HttpResponseStatus.BAD_REQUEST, var7.getMessage(), MediaType.create("text", "plain").toString());
		} catch (Exception var8) {
			this.mockServerLogger.logEvent((new LogEntry()).setType(LogMessageType.EXCEPTION).setLogLevel(Level.ERROR).setHttpRequest(request).setMessageFormat("exception processing " + request).setThrowable(var8));
			responseWriter.writeResponse(request, HttpResponse.response().withStatusCode(HttpResponseStatus.BAD_REQUEST.code()).withBody(var8.getMessage()), true);
		}

	}
}
