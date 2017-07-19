//package com.spf.common.logback;
//
//import ch.qos.logback.classic.LoggerContext;
//import ch.qos.logback.classic.joran.JoranConfigurator;
//import org.slf4j.LoggerFactory;
//import org.slf4j.bridge.SLF4JBridgeHandler;
//import org.springframework.util.ClassUtils;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletContextEvent;
//import javax.servlet.http.HttpServlet;
//import java.io.FileNotFoundException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.logging.ConsoleHandler;
//import java.util.logging.Handler;
//import java.util.logging.LogManager;
//
//
///**
// * @Auther ShuPF
// * @Create 2017/7/7 0007
// */
//
//public class LogbackConfigListener extends HttpServlet {
//
//    public LogbackConfigListener() {
//    }
//
//    public void contextInitialized(ServletContextEvent event) {
//        String logbackConfigLocation = event.getServletContext().getInitParameter("logbackConfigLocation");
//        if(logbackConfigLocation == null || "".endsWith(logbackConfigLocation.trim())) {
//            logbackConfigLocation = "classpath*:/WEB-INF/logback-spring.xml";
//        }
//
//        try {
//            logbackConfigLocation = resolveSystemPropertyPlaceholder(logbackConfigLocation);
//            if(isClassPath(logbackConfigLocation)) {
//                logbackConfigLocation = resolveClasspath(logbackConfigLocation);
//            } else if(!isUrl(logbackConfigLocation)) {
//                logbackConfigLocation = getRealPath(event.getServletContext(), logbackConfigLocation);
//            }
//
//            LoggerContext e = (LoggerContext) LoggerFactory.getILoggerFactory();
//            e.reset();
//            JoranConfigurator joranConfigurator = new JoranConfigurator();
//            joranConfigurator.setContext(e);
//            joranConfigurator.doConfigure(logbackConfigLocation);
//            event.getServletContext().log("加载logback日志文件：" + logbackConfigLocation);
//            this.adjustJDKLog();
//            event.getServletContext().log("jul日志输出到logback");
//            event.getServletContext().log("*********************");
////			String env = null;
////
////			try {
////				env = Env.getEnv();
////			} catch (Exception var7) {
////				event.getServletContext().log(var7.getMessage(), var7);
////			}
////
////			if(env != null) {
////				event.getServletContext().log("当前环境标识:" + env);
////			}
//
//            event.getServletContext().log("*********************");
//        } catch (Exception var8) {
//            event.getServletContext().log("加载logback配置文件[" + logbackConfigLocation + "]失败", var8);
//            throw new RuntimeException("加载logback配置文件[" + logbackConfigLocation + "]失败", var8);
//        }
//
////		UncaughtExceptionHandlerWrapper.install();
//    }
//
//    private void adjustJDKLog() {
//        Handler[] handlers = LogManager.getLogManager().getLogger("").getHandlers();
//        boolean existSLF4JBridgeHandler = false;
//        if(handlers != null) {
//            Handler[] var3 = handlers;
//            int var4 = handlers.length;
//
//            for(int var5 = 0; var5 < var4; ++var5) {
//                Handler handler = var3[var5];
//                if(handler instanceof SLF4JBridgeHandler) {
//                    existSLF4JBridgeHandler = true;
//                }
//
//                if(handler instanceof ConsoleHandler) {
//                    LogManager.getLogManager().getLogger("").removeHandler(handler);
//                }
//            }
//        }
//
//        if(!existSLF4JBridgeHandler) {
//            SLF4JBridgeHandler.install();
//        }
//
//    }
//
//    public void contextDestroyed(ServletContextEvent event) {
////		if(ClassUtils.isPresent("com.alibaba.dubbo.config.ProtocolConfig", event.getServletContext().getClassLoader())) {
////			ShutdownHooks.addShutdownHook(new DubboShutdownHook(), "dubboShutdownHook");
////		}
////
////		ShutdownHooks.shutdownAll();
//        LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();
//        loggerContext.stop();
//    }
//
//    private static String resolveClasspath(String resourceLocation) {
//        String path = resourceLocation.substring("classpath:".length());
//        URL url = ClassUtils.getDefaultClassLoader().getResource(path);
//        if(url == null) {
//            throw new RuntimeException("没有找到logback配置文件:" + resourceLocation);
//        } else {
//            return url.getFile();
//        }
//    }
//
//    private static String resolveSystemPropertyPlaceholder(String str) {
//        StringBuilder buf = new StringBuilder(str);
//        int begin = buf.indexOf("${");
//        if(begin < 0) {
//            return str;
//        } else {
//            int end = buf.indexOf("}", begin);
//            if(end < 0) {
//                return str;
//            } else {
//                String placeholderName = buf.substring(begin + "${".length(), end);
//                String propVal = System.getProperty(placeholderName);
//                if(propVal == null) {
//                    propVal = System.getenv(placeholderName);
//                }
//
//                if(propVal == null) {
//                    propVal = "";
//                }
//
//                buf.replace(begin, end + 1, propVal);
//                return resolveSystemPropertyPlaceholder(buf.toString());
//            }
//        }
//    }
//
//    private static boolean isUrl(String resourceLocation) {
//        if(resourceLocation == null) {
//            return false;
//        } else {
//            try {
//                new URL(resourceLocation);
//                return true;
//            } catch (MalformedURLException var2) {
//                return false;
//            }
//        }
//    }
//
//    private static boolean isClassPath(String logbackConfigLocation) {
//        return logbackConfigLocation.startsWith("classpath:");
//    }
//
//    private static String getRealPath(ServletContext servletContext, String path) throws FileNotFoundException {
//        if(!path.startsWith("/")) {
//            path = "/" + path;
//        }
//
//        String realPath = servletContext.getRealPath(path);
//        if(realPath == null) {
//            throw new FileNotFoundException("ServletContext resource [" + path + "] cannot be resolved to absolute file path - " + "web application archive not expanded?");
//        } else {
//            return realPath;
//        }
//    }
//
//}
