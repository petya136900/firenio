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
package test.io.http11;

import org.junit.Test;

import com.firenio.Options;
import com.firenio.codec.http11.ClientHttpCodec;
import com.firenio.codec.http11.ClientHttpFrame;
import com.firenio.codec.http11.WebSocketCodec;
import com.firenio.common.Util;
import com.firenio.component.Channel;
import com.firenio.component.ChannelConnector;
import com.firenio.component.Frame;
import com.firenio.component.IoEventHandle;
import com.firenio.component.LoggerChannelOpenListener;
import com.firenio.component.SslContext;
import com.firenio.component.SslContextBuilder;
import com.firenio.concurrent.Waiter;

import junit.framework.Assert;

public class TestSimpleHttpClient {

    @Test
    public void main() throws Exception {
        Options.setEnableEpoll(true);
        Waiter<String>   w          = new Waiter<>();
        ChannelConnector context    = new ChannelConnector("firenio.com", 443);
        SslContext       sslContext = SslContextBuilder.forClient(true).build();
        context.addProtocolCodec(new ClientHttpCodec());
        context.addProtocolCodec(new WebSocketCodec());
        context.setIoEventHandle(new IoEventHandle() {

            @Override
            public void accept(Channel ch, Frame frame) {
                ClientHttpFrame res = (ClientHttpFrame) frame;
                System.out.println();
                System.out.println(new String(res.getArrayContent()));
                System.out.println();
                Util.close(context);
                w.call(new String(res.getArrayContent()), null);
            }
        });
        context.addChannelEventListener(new LoggerChannelOpenListener());
        context.setSslContext(sslContext);
        long    start = Util.now();
        Channel ch    = context.connect(3000);
        ch.writeAndFlush(new ClientHttpFrame("/test?p=2222"));
        w.await(3000);
        System.out.println(Util.past(start));
        Assert.assertEquals("yes server already accept your message :) {p=2222}", w.getResponse());
    }

}
