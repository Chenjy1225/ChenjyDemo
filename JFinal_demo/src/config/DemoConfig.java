package config;



import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import controller.HelloController;

public class DemoConfig extends JFinalConfig {

	public void configConstant(Constants me) {
		loadPropertyFile("datasource.properties");
		me.setEncoding("UTF-8");
		me.setDevMode(true);
	}

	public void configRoute(Routes me) {
		me.add("/hello", HelloController.class);
	}

	public void configPlugin(Plugins me) {
		
		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
        me.add(c3p0Plugin);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        me.add(arp);
	}

	public void configInterceptor(Interceptors me) {
	}

	public void configHandler(Handlers me) {
	}

	public static void main(String[] args) throws Exception {
		JFinal.start("WebRoot", 8088, "/Demo", 5);
	}
}
