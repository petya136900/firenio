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
package test.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Base64;

import sun.reflect.misc.FieldUtil;

import com.firenio.common.Cryptos;
import com.firenio.common.FileUtil;
import com.firenio.common.Unsafe;
import com.firenio.common.Util;

/**
 * @author: wangkai
 **/
public class Test7 {

    public static void main(String[] args) throws Exception {
        String str   = "{\"X03\":\"G418Response\",\"X11\":false,\"X13\":true,\"X15\":4168,\"X17\":\"74137377\",\"X18\":\"zj003\",\"X19\":\"C\",\"X39\":\"00000000\",\"listData\":[{\"CUSTTRADEID\":\"0025110000000025\",\"INNERCLIENTID\":\"0000000001\",\"USERCODE\":\"_ST_0000000001\",\"CLIENTABBR\":\"自营紫金矿业\",\"SEATID\":\"002511\",\"CLIENTNAME\":\"紫金矿业\"},{\"CUSTTRADEID\":\"0025210100000900\",\"INNERCLIENTID\":\"0000000002\",\"USERCODE\":\"_ST_0000000002\",\"CLIENTABBR\":\"代理泉州宝辉\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"泉州宝辉\"},{\"CUSTTRADEID\":\"0025210100003161\",\"INNERCLIENTID\":\"0000000003\",\"USERCODE\":\"_ST_0000000003\",\"CLIENTABBR\":\"代理贵州紫金\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"贵州紫金\"},{\"CUSTTRADEID\":\"0025210100003251\",\"INNERCLIENTID\":\"0000000004\",\"USERCODE\":\"_ST_0000000004\",\"CLIENTABBR\":\"代理上杭金山\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"上杭金山\"},{\"CUSTTRADEID\":\"0025210100003374\",\"INNERCLIENTID\":\"0000000005\",\"USERCODE\":\"_ST_0000000005\",\"CLIENTABBR\":\"代理上杭华辉\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"上杭华辉\"},{\"CUSTTRADEID\":\"0025210100005310\",\"INNERCLIENTID\":\"0000000006\",\"USERCODE\":\"_ST_0000000006\",\"CLIENTABBR\":\"代理厦门紫金\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"厦门紫金\"},{\"CUSTTRADEID\":\"0025210100007075\",\"INNERCLIENTID\":\"0000000007\",\"USERCODE\":\"_ST_0000000007\",\"CLIENTABBR\":\"代理福建金怡\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"福建金怡\"},{\"CUSTTRADEID\":\"0025210100007693\",\"INNERCLIENTID\":\"0000000008\",\"USERCODE\":\"_ST_0000000008\",\"CLIENTABBR\":\"代理四川紫金\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"四川紫金\"},{\"CUSTTRADEID\":\"0025210100009459\",\"INNERCLIENTID\":\"0000000009\",\"USERCODE\":\"_ST_0000000009\",\"CLIENTABBR\":\"代理珲春紫金\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"珲春紫金\"},{\"CUSTTRADEID\":\"0025210100012093\",\"INNERCLIENTID\":\"0000000010\",\"USERCODE\":\"_ST_0000000010\",\"CLIENTABBR\":\"代理隆兴矿业\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"隆兴矿业\"},{\"CUSTTRADEID\":\"0025210100012543\",\"INNERCLIENTID\":\"0000000011\",\"USERCODE\":\"_ST_0000000011\",\"CLIENTABBR\":\"代理和昱树脂\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"和昱树脂\"},{\"CUSTTRADEID\":\"0025210100013061\",\"INNERCLIENTID\":\"0000000012\",\"USERCODE\":\"_ST_0000000012\",\"CLIENTABBR\":\"代理上杭鸿阳\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"上杭鸿阳\"},{\"CUSTTRADEID\":\"0025210100015973\",\"INNERCLIENTID\":\"0000000013\",\"USERCODE\":\"_ST_0000000013\",\"CLIENTABBR\":\"代理上杭万祥\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"上杭万祥\"},{\"CUSTTRADEID\":\"0025210100017436\",\"INNERCLIENTID\":\"0000000014\",\"USERCODE\":\"_ST_0000000014\",\"CLIENTABBR\":\"代理正龙金矿\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"正龙金矿\"},{\"CUSTTRADEID\":\"0025210100018932\",\"INNERCLIENTID\":\"0000000015\",\"USERCODE\":\"_ST_0000000015\",\"CLIENTABBR\":\"代理金山黄金\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"金山黄金\"},{\"CUSTTRADEID\":\"0025210100019270\",\"INNERCLIENTID\":\"0000000016\",\"USERCODE\":\"_ST_0000000016\",\"CLIENTABBR\":\"代理曙光化工\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"曙光化工\"},{\"CUSTTRADEID\":\"0025210100023747\",\"INNERCLIENTID\":\"0000000017\",\"USERCODE\":\"_ST_0000000017\",\"CLIENTABBR\":\"代理凯韦投资\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"凯韦投资\"},{\"CUSTTRADEID\":\"0025210100024704\",\"INNERCLIENTID\":\"0000000018\",\"USERCODE\":\"_ST_0000000018\",\"CLIENTABBR\":\"代理紫金矿冶\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"紫金矿冶\"},{\"CUSTTRADEID\":\"0025210100024861\",\"INNERCLIENTID\":\"0000000019\",\"USERCODE\":\"_ST_0000000019\",\"CLIENTABBR\":\"代理石狮新成\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"石狮新成\"},{\"CUSTTRADEID\":\"0025210100025581\",\"INNERCLIENTID\":\"0000000020\",\"USERCODE\":\"_ST_0000000020\",\"CLIENTABBR\":\"代理紫金投资\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"紫金投资\"},{\"CUSTTRADEID\":\"0025210100027763\",\"INNERCLIENTID\":\"0000000021\",\"USERCODE\":\"_ST_0000000021\",\"CLIENTABBR\":\"代理梅州海鑫\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"梅州海鑫\"},{\"CUSTTRADEID\":\"0025210100032488\",\"INNERCLIENTID\":\"0000000022\",\"USERCODE\":\"_ST_0000000022\",\"CLIENTABBR\":\"代理龙岩金鼎象\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"龙岩金鼎象\"},{\"CUSTTRADEID\":\"0025210100032602\",\"INNERCLIENTID\":\"0000000023\",\"USERCODE\":\"_ST_0000000023\",\"CLIENTABBR\":\"代理信宜东坑\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"信宜东坑\"},{\"CUSTTRADEID\":\"0025210100033074\",\"INNERCLIENTID\":\"0000000024\",\"USERCODE\":\"_ST_0000000024\",\"CLIENTABBR\":\"代理深圳名象\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"深圳名象\"},{\"CUSTTRADEID\":\"0025210100034008\",\"INNERCLIENTID\":\"0000000025\",\"USERCODE\":\"_ST_0000000025\",\"CLIENTABBR\":\"代理龙岩兴隆鑫\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"龙岩兴隆鑫\"},{\"CUSTTRADEID\":\"0025210100035740\",\"INNERCLIENTID\":\"0000000026\",\"USERCODE\":\"_ST_0000000026\",\"CLIENTABBR\":\"代理龙岩鑫晖\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"龙岩鑫晖\"},{\"CUSTTRADEID\":\"0025210100037876\",\"INNERCLIENTID\":\"0000000027\",\"USERCODE\":\"_ST_0000000027\",\"CLIENTABBR\":\"代理厦门敦豪\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"厦门敦豪\"},{\"CUSTTRADEID\":\"0025210100037887\",\"INNERCLIENTID\":\"0000000028\",\"USERCODE\":\"_ST_0000000028\",\"CLIENTABBR\":\"代理永安格力\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"永安格力\"},{\"CUSTTRADEID\":\"0025210100038057\",\"INNERCLIENTID\":\"0000000029\",\"USERCODE\":\"_ST_0000000029\",\"CLIENTABBR\":\"代理厦门广颐\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"厦门广颐\"},{\"CUSTTRADEID\":\"0025210100038271\",\"INNERCLIENTID\":\"0000000030\",\"USERCODE\":\"_ST_0000000030\",\"CLIENTABBR\":\"代理顺裕公司\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"顺裕公司\"},{\"CUSTTRADEID\":\"0025210100038417\",\"INNERCLIENTID\":\"0000000031\",\"USERCODE\":\"_ST_0000000031\",\"CLIENTABBR\":\"代理厦门速帆\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"厦门速帆\"},{\"CUSTTRADEID\":\"0025210100038518\",\"INNERCLIENTID\":\"0000000032\",\"USERCODE\":\"_ST_0000000032\",\"CLIENTABBR\":\"代理龙岩天利\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"龙岩天利\"},{\"CUSTTRADEID\":\"0025210100041309\",\"INNERCLIENTID\":\"0000000033\",\"USERCODE\":\"_ST_0000000033\",\"CLIENTABBR\":\"代理龙岩顺裕\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"龙岩顺裕\"},{\"CUSTTRADEID\":\"0025210100041781\",\"INNERCLIENTID\":\"0000000034\",\"USERCODE\":\"_ST_0000000034\",\"CLIENTABBR\":\"代理元阳华西\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"元阳华西\"},{\"CUSTTRADEID\":\"0025210100051197\",\"INNERCLIENTID\":\"0000000035\",\"USERCODE\":\"_ST_0000000035\",\"CLIENTABBR\":\"代理丘北拓岩\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"丘北拓岩\"},{\"CUSTTRADEID\":\"0025210100055124\",\"INNERCLIENTID\":\"0000000036\",\"USERCODE\":\"_ST_0000000036\",\"CLIENTABBR\":\"代理上杭威特\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"上杭威特\"},{\"CUSTTRADEID\":\"0025210100055966\",\"INNERCLIENTID\":\"0000000037\",\"USERCODE\":\"_ST_0000000037\",\"CLIENTABBR\":\"代理龙岩永清\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"龙岩永清\"},{\"CUSTTRADEID\":\"0025210100057306\",\"INNERCLIENTID\":\"0000000038\",\"USERCODE\":\"_ST_0000000038\",\"CLIENTABBR\":\"代理上杭绿之源\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"上杭绿之源\"},{\"CUSTTRADEID\":\"0025210100057317\",\"INNERCLIENTID\":\"0000000039\",\"USERCODE\":\"_ST_0000000039\",\"CLIENTABBR\":\"代理龙岩石壁龙\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"龙岩石壁龙\"},{\"CUSTTRADEID\":\"0025210100060670\",\"INNERCLIENTID\":\"0000000040\",\"USERCODE\":\"_ST_0000000040\",\"CLIENTABBR\":\"代理龙岩鼎鑫\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"龙岩鼎鑫\"},{\"CUSTTRADEID\":\"0025210100074204\",\"INNERCLIENTID\":\"0000000041\",\"USERCODE\":\"_ST_0000000041\",\"CLIENTABBR\":\"代理紫金黄金珠宝\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"紫金矿业集团黄金珠宝有限公司\"},{\"CUSTTRADEID\":\"0025210100078590\",\"INNERCLIENTID\":\"0000000042\",\"USERCODE\":\"_ST_0000000042\",\"CLIENTABBR\":\"代理内蒙金中\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"内蒙金中\"},{\"CUSTTRADEID\":\"0025210100078961\",\"INNERCLIENTID\":\"0000000043\",\"USERCODE\":\"_ST_0000000043\",\"CLIENTABBR\":\"代理紫金铜业\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"紫金铜业\"},{\"CUSTTRADEID\":\"0025210100084070\",\"INNERCLIENTID\":\"0000000044\",\"USERCODE\":\"_ST_0000000044\",\"CLIENTABBR\":\"代理陇南紫金\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"陇南紫金\"},{\"CUSTTRADEID\":\"0025210100091539\",\"INNERCLIENTID\":\"0000000045\",\"USERCODE\":\"_ST_0000000045\",\"CLIENTABBR\":\"代理漳平银丰\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"漳平银丰\"},{\"CUSTTRADEID\":\"0025210100096309\",\"INNERCLIENTID\":\"0000000046\",\"USERCODE\":\"_ST_0000000046\",\"CLIENTABBR\":\"代理贵州西南紫金\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"贵州西南紫金\"},{\"CUSTTRADEID\":\"0025210100123816\",\"INNERCLIENTID\":\"0000000047\",\"USERCODE\":\"_ST_0000000047\",\"CLIENTABBR\":\"代理紫金贸易\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"紫金贸易\"},{\"CUSTTRADEID\":\"0025210100139981\",\"INNERCLIENTID\":\"0000000048\",\"USERCODE\":\"_ST_0000000048\",\"CLIENTABBR\":\"代理紫金环球\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"紫金环球\"},{\"CUSTTRADEID\":\"0025210100146372\",\"INNERCLIENTID\":\"0000000049\",\"USERCODE\":\"_ST_0000000049\",\"CLIENTABBR\":\"代理紫金矿业进口\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"紫金矿业进口\"},{\"CUSTTRADEID\":\"0025210100147025\",\"INNERCLIENTID\":\"0000000050\",\"USERCODE\":\"_ST_0000000050\",\"CLIENTABBR\":\"代理黄金冶炼\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"黄金冶炼\"},{\"CUSTTRADEID\":\"0025210100149421\",\"INNERCLIENTID\":\"0000000051\",\"USERCODE\":\"_ST_0000000051\",\"CLIENTABBR\":\"代理紫金金行\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"紫金金行\"},{\"CUSTTRADEID\":\"0025210100157374\",\"INNERCLIENTID\":\"0000000052\",\"USERCODE\":\"_ST_0000000052\",\"CLIENTABBR\":\"代理厦金宝\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"厦金宝\"},{\"CUSTTRADEID\":\"0025210100165632\",\"INNERCLIENTID\":\"0000000101\",\"USERCODE\":\"_ST_0000000101\",\"CLIENTABBR\":\"代理厦门华万腾\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"厦门华万腾\"},{\"CUSTTRADEID\":\"0025210100184868\",\"INNERCLIENTID\":\"0000000121\",\"USERCODE\":\"_ST_0000000121\",\"CLIENTABBR\":\"代理福建紫金贵金属材料有限公司\",\"SEATID\":\"002521\",\"CLIENTNAME\":\"福建紫金贵金属材料有限公司\"}]}";
        byte[] bytes = str.getBytes(Util.GBK);
        System.out.println(bytes.length);
    }

    static void printInputStream(String clazz) {
        try {
            String      name  = clazz.replace(".", "/") + ".class";
            InputStream is    = Test7.class.getClassLoader().getResourceAsStream(name);
            int         len   = is.available();
            byte[]      bb    = new byte[len];
            int         r_len = 0;
            for (; r_len != len; ) {
                r_len += is.read(bb, r_len, len - r_len);
            }
            System.out.println(clazz + ">>" + Base64.getEncoder().encodeToString(bb));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
