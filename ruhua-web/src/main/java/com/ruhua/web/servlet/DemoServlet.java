package com.ruhua.web.servlet;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 15-1-17
 * Time: 下午2:36
 * To change this template use File | Settings | File Templates.
 */


public class DemoServlet extends WebSocketServlet {
    private static final Logger logger = Logger.getLogger(DemoServlet.class);
    private static final long serialVersionUID = -4853540828121130946L;
    public static ArrayList<MyMessageInbound> mmiList = new ArrayList<MyMessageInbound>();

    @Override
    protected StreamInbound createWebSocketInbound(String str, HttpServletRequest request) {
        return new MyMessageInbound();
    }

    public class MyMessageInbound extends MessageInbound {
        WsOutbound myoutbound;

        @Override
        public void onOpen(WsOutbound outbound) {
            try {
                logger.info("Open Client.");
                this.myoutbound = outbound;
                mmiList.add(this);
                outbound.writeTextMessage(CharBuffer.wrap("欢迎来吐槽 缓解工作情绪 随便聊哦!"));
                outbound.writeTextMessage(CharBuffer.wrap("实时吐槽，完全匿名。"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onClose(int status) {
            logger.info("Close Client.");
            mmiList.remove(this);
        }

        @Override
        public void onTextMessage(CharBuffer cb) throws IOException {
            logger.info("Accept Message : " + cb);
            for (MyMessageInbound mmib : mmiList) {
                CharBuffer buffer = CharBuffer.wrap(cb);
                mmib.myoutbound.writeTextMessage(buffer);
                mmib.myoutbound.flush();
            }
        }

        @Override
        public void onBinaryMessage(ByteBuffer bb) throws IOException {
        }
    }

}
