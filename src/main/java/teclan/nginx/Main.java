package teclan.nginx;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import teclan.exec.Executor;

public class Main {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy_MM_dd");
	private static Timer TIMER = new Timer();
	private static String YESTODAY = null;
	private static String SUFFIX = SDF.format(new Date());

	public static void main(String[] args) {

		// 加载配置文件
		File file = new File("config/application.conf");

		Config root = ConfigFactory.parseFile(file);

		Config config = root.getConfig("config");



		final String nginxPath = config.getString("nginx.path");
		int flushRate = config.getInt("task.flushRate");

		TIMER.schedule(new TimerTask() {

			@Override
			public void run() {

				try {

					SUFFIX = SDF.format(new Date());

					LOGGER.info("当前:{},昨日:{}", SUFFIX, YESTODAY);

					if (!SUFFIX.equals(YESTODAY)) {

						Executor.exec("cmd", "/c", "move", nginxPath + "logs\\access.log",
								nginxPath + "logs\\access_" + SUFFIX + ".log");

						LOGGER.info("\n\n切割日志{}->{}", "access.log", "access_" + SUFFIX + ".log");

						Executor.exec("cmd", "/c", "move", nginxPath + "logs\\error.log",
								nginxPath + "logs\\error" + SUFFIX + ".log");

						LOGGER.info("\n\n切割日志{}->{}", "access.log", "access_" + SUFFIX + ".log");

						Executor.exec("cmd", "/c", "cd", nginxPath, "&&", "nginx.exe", "-s", "reopen");

						YESTODAY = SUFFIX;
					}

				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}, 1000, flushRate * 1000);

	}

}
