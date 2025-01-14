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
package test.others;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author wangkai
 */
public class TestTemp {

    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        System.out.println(buffer.order());

        System.out.println(ByteOrder.nativeOrder());

    }

}
