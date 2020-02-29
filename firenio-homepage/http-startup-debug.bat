java -XX:+PrintGCDetails -Xloggc:gc.log -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=6666,suspend=n ^
     -cp ./lib/*; ^
     -Dboot.prodMode=prod ^
	 -Dhttp.debug=true ^
     -Dboot.libPath=/app/lib ^
     -Dcom.firenio.develop.debug=true ^
     -Dboot.className=sample.http11.startup.TestHttpBootstrapEngine ^
     com.firenio.container.Bootstrap
