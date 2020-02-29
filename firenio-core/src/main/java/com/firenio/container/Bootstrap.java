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
package com.firenio.container;

import com.firenio.common.Assert;
import com.firenio.common.FileUtil;
import com.firenio.common.Util;
import com.firenio.log.DebugUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangkai
 */
public class Bootstrap {

    private boolean            prodMode;
    private String             rootPath;
    private DynamicClassLoader classLoader;
    private String             bootClassName;
    private boolean            checkDuplicate;
    private List<String>       notEntrustPackageList;
    private List<String>       libPaths = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        String bootClassName = Util.getStringProperty("boot.className");
        String libPath = Util.getStringProperty("boot.libPath");
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.setBootClassName(bootClassName);
        bootstrap.setCheckDuplicate(false);
        bootstrap.setProdMode(true);
        bootstrap.addRelativeLibPath("/conf");
        bootstrap.addRelativeLibPath(libPath);
        bootstrap.startup();
    }

    public Bootstrap() {
        this.rootPath = FileUtil.getCurrentPath();
    }

    public void startup() throws Exception {
        Assert.notNull(bootClassName, "className");
        DebugUtil.getLogger().info("PROD_MODE: {}", prodMode);
        DebugUtil.getLogger().info("ROOT_PATH: {}", rootPath);
        DynamicClassLoader classLoader = new DynamicClassLoader(notEntrustPackageList, checkDuplicate);
        for (String libPath : libPaths) {
            DebugUtil.getLogger().info("CLS_PATH: {}", libPath);
            classLoader.scan(libPath);
        }
        Class bootClass = Class.forName(bootClassName, true, classLoader);
        Thread.currentThread().setContextClassLoader(classLoader);
        BootstrapEngine engine = (BootstrapEngine) bootClass.newInstance();
        engine.bootstrap(rootPath, prodMode);
    }

    public boolean isProdMode() {
        return prodMode;
    }

    public void setProdMode(boolean prodMode) {
        this.prodMode = prodMode;
    }

    public void setBootClassName(String bootClassName) {
        this.bootClassName = bootClassName;
    }

    public String getBootClassName() {
        return bootClassName;
    }

    public DynamicClassLoader getClassLoader() {
        return classLoader;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void addLibPath(String path) {
        libPaths.add(path);
    }

    public void addRelativeLibPath(String path) {
        libPaths.add(getRootPath() + path);
    }

    public void setNotEntrustPackageList(List<String> notEntrustPackageList) {
        this.notEntrustPackageList = notEntrustPackageList;
    }

    public List<String> getNotEntrustPackageList() {
        return notEntrustPackageList;
    }

    public void setCheckDuplicate(boolean checkDuplicate) {
        this.checkDuplicate = checkDuplicate;
    }

    public boolean isCheckDuplicate() {
        return checkDuplicate;
    }

}
