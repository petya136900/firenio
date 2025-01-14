/*
 * Copyright 2015 The FireNio Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test.io.load.http11;

import com.firenio.Options;
import com.firenio.buffer.ByteBuf;
import com.firenio.codec.http11.HttpCodec;
import com.firenio.codec.http11.HttpConnection;
import com.firenio.codec.http11.HttpContentType;
import com.firenio.codec.http11.HttpDateUtil;
import com.firenio.codec.http11.HttpFrame;
import com.firenio.codec.http11.HttpStatus;
import com.firenio.collection.AttributeKey;
import com.firenio.collection.AttributeMap;
import com.firenio.collection.ByteTree;
import com.firenio.common.Util;
import com.firenio.component.Channel;
import com.firenio.component.ChannelAcceptor;
import com.firenio.component.ChannelEventListenerAdapter;
import com.firenio.component.FastThreadLocal;
import com.firenio.component.Frame;
import com.firenio.component.IoEventHandle;
import com.firenio.component.NioEventLoopGroup;
import com.firenio.component.SocketOptions;
import com.firenio.log.DebugUtil;
import com.firenio.log.LoggerFactory;
import com.jsoniter.output.JsonStream;
import com.jsoniter.output.JsonStreamPool;
import com.jsoniter.spi.Slice;

/**
 * @author wangkai
 */
public class TestHttpLoadServerTFB {

    static final AttributeKey<ByteBuf> JSON_BUF         = newJsonBufKey();
    static final AttributeKey<ByteBuf> WRITE_BUF        = newWriteBufKey();
    static final byte[]                STATIC_PLAINTEXT = "Hello, World!".getBytes();

    static class Message {

        private final String message;

        public Message(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }

    public static void main(String[] args) throws Exception {
        System.setProperty("core", "1");
        System.setProperty("frame", "16");
        System.setProperty("readBuf", "512");
        System.setProperty("pool", "true");
        System.setProperty("direct", "true");
        System.setProperty("inline", "true");
        System.setProperty("level", "1");
        System.setProperty("read", "false");
        System.setProperty("epoll", "true");
        System.setProperty("nodelay", "true");
        System.setProperty("cachedurl", "true");
        System.setProperty("unsafeBuf", "true");


        boolean lite      = Util.getBooleanProperty("lite");
        boolean read      = Util.getBooleanProperty("read");
        boolean pool      = Util.getBooleanProperty("pool");
        boolean epoll     = Util.getBooleanProperty("epoll");
        boolean direct    = Util.getBooleanProperty("direct");
        boolean nodelay   = Util.getBooleanProperty("nodelay");
        boolean cachedurl = Util.getBooleanProperty("cachedurl");
        boolean unsafeBuf = Util.getBooleanProperty("unsafeBuf");
        int     core      = Util.getIntProperty("core", 1);
        int     frame     = Util.getIntProperty("frame", 16);
        int     level     = Util.getIntProperty("level", 1);
        int     readBuf   = Util.getIntProperty("readBuf", 16);
        LoggerFactory.setEnableSLF4JLogger(false);
        LoggerFactory.setLogLevel(LoggerFactory.LEVEL_INFO);
        Options.setBufAutoExpansion(false);
        Options.setChannelReadFirst(read);
        Options.setEnableEpoll(epoll);
        Options.setEnableUnsafeBuf(unsafeBuf);
        DebugUtil.info("lite: {}", lite);
        DebugUtil.info("read: {}", read);
        DebugUtil.info("pool: {}", pool);
        DebugUtil.info("core: {}", core);
        DebugUtil.info("epoll: {}", epoll);
        DebugUtil.info("frame: {}", frame);
        DebugUtil.info("level: {}", level);
        DebugUtil.info("direct: {}", direct);
        DebugUtil.info("readBuf: {}", readBuf);
        DebugUtil.info("nodelay: {}", nodelay);
        DebugUtil.info("cachedurl: {}", cachedurl);
        DebugUtil.info("unsafeBuf: {}", unsafeBuf);

        int      processors = Util.availableProcessors() * core;
        int      fcache     = 1024 * 16;
        int      pool_unit  = 256 * 16;
        int      pool_cap   = 1024 * 8 * pool_unit * processors;
        String   server     = "firenio";
        ByteTree cachedUrls = null;
        if (cachedurl) {
            cachedUrls = new ByteTree();
            cachedUrls.add("/plaintext");
            cachedUrls.add("/json");
        }
        HttpCodec codec = new HttpCodec(server, fcache, lite, cachedUrls);

        IoEventHandle eventHandle = new IoEventHandle() {

            @Override
            public void accept(Channel ch, Frame frame) throws Exception {
                HttpFrame f      = (HttpFrame) frame;
                String    action = f.getRequestURL();
                if ("/plaintext".equals(action)) {
                    ByteBuf buf = ch.getAttributeUnsafe(WRITE_BUF);
                    if (buf == null) {
                        buf = ch.allocate();
                        ByteBuf temp = buf;
                        ch.setAttributeUnsafe(WRITE_BUF, buf);
                        ch.getEventLoop().submit(() -> {
                            ch.writeAndFlush(temp);
                            ch.setAttributeUnsafe(WRITE_BUF, null);
                        });
                    }
                    f.setContent(STATIC_PLAINTEXT);
                    f.setContentType(HttpContentType.text_plain);
                    f.setConnection(HttpConnection.NONE);
                    f.setDate(HttpDateUtil.getDateLine());
                    codec.encode(ch, buf, f);
                    ch.release(f);
                } else if ("/json".equals(action)) {
                    ByteBuf    temp   = FastThreadLocal.get().getAttributeUnsafe(JSON_BUF);
                    JsonStream stream = JsonStreamPool.borrowJsonStream();
                    try {
                        stream.reset(null);
                        stream.writeVal(Message.class, new Message("Hello, World!"));
                        Slice slice = stream.buffer();
                        temp.reset(slice.data(), slice.head(), slice.tail());
                        f.setContent(temp);
                        f.setContentType(HttpContentType.application_json);
                        f.setConnection(HttpConnection.NONE);
                        f.setDate(HttpDateUtil.getDateLine());
                        ch.writeAndFlush(f);
                        ch.release(f);
                    } finally {
                        JsonStreamPool.returnJsonStream(stream);
                    }
                } else {
                    System.err.println("404");
                    f.setString("404,page not found!", ch);
                    f.setContentType(HttpContentType.text_plain);
                    f.setStatus(HttpStatus.C404);
                    f.setDate(HttpDateUtil.getDateLine());
                    ch.writeAndFlush(f);
                    ch.release(f);
                }
            }

        };

        HttpDateUtil.start();
        NioEventLoopGroup group   = new NioEventLoopGroup();
        ChannelAcceptor   context = new ChannelAcceptor(group, 8080);
        group.setMemoryCapacity(pool_cap);
        group.setEnableMemoryPool(pool);
        group.setMemoryUnit(pool_unit);
        group.setWriteBuffers(8);
        group.setChannelReadBuffer(1024 * readBuf);
        group.setEventLoopSize(Util.availableProcessors() * core);
        group.setConcurrentFrameStack(false);
        if (nodelay) {
            context.addChannelEventListener(new ChannelEventListenerAdapter() {

                @Override
                public void channelOpened(Channel ch) throws Exception {
                    ch.setOption(SocketOptions.TCP_NODELAY, 1);
                    ch.setOption(SocketOptions.SO_KEEPALIVE, 0);
                }
            });
        }
        context.addProtocolCodec(codec);
        context.setIoEventHandle(eventHandle);
        context.bind(1024 * 8);
    }

    static AttributeKey<ByteBuf> newJsonBufKey() {
        return FastThreadLocal.valueOfKey("JSON_BUF", (AttributeMap map, int key) -> map.setAttribute(key, ByteBuf.heap(0)));
    }

    static AttributeKey<ByteBuf> newWriteBufKey() {
        return Channel.valueOfKey("WRITE_BUF");
    }

}
