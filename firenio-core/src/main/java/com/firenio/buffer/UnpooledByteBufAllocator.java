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
package com.firenio.buffer;

import com.firenio.common.Unsafe;

public final class UnpooledByteBufAllocator extends ByteBufAllocator {

    static final UnpooledByteBufAllocator ALLOC = new UnpooledByteBufAllocator();

    public static UnpooledByteBufAllocator get() {
        return ALLOC;
    }

    @Override
    public ByteBuf allocate() {
        return allocate(512);
    }

    @Override
    public ByteBuf allocate(int capacity) {
        return ByteBuf.buffer(capacity);
    }

    @Override
    protected void doStart() {}

    @Override
    protected void doStop() {}

    @Override
    public void expansion(ByteBuf buf, int cap) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void freeMemory() {}

    @Override
    public long getCapacity() {
        return -1;
    }

    @Override
    public int getUnit() {
        return -1;
    }

    public boolean isDirect() {
        return Unsafe.DIRECT_BUFFER_AVAILABLE;
    }

    @Override
    public void release(ByteBuf buf) {
        throw new UnsupportedOperationException();
    }

}
