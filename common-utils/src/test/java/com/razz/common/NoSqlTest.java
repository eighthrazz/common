package com.razz.common;

import java.util.Properties;

import com.razz.common.nosql.NoSql;
import com.razz.common.nosql.NoSqlConfig;
import com.razz.common.util.config.ConfigManager;

public class NoSqlTest {

	public static void main(String args[]) {
		final Properties props = ConfigManager.get();
		final NoSqlConfig config = new NoSqlConfig(props);
		final NoSql noSql = new NoSql();
		try {
			noSql.connect(config);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			noSql.close();
		}
	}
}
