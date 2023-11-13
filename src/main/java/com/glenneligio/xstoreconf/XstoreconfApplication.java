package com.glenneligio.xstoreconf;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.rvesse.airline.annotations.Cli;
import com.github.rvesse.airline.help.Help;
import com.glenneligio.xstoreconf.commands.XStoreConfDiff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Cli(name = "xstoreconf",
		description = "XSTORECONF CLI",
		defaultCommand = Help.class,
		commands = {XStoreConfDiff.class, Help.class})
public class XstoreconfApplication {
	private static Logger logger = LoggerFactory.getLogger(XstoreconfApplication.class);

	public static void main(String[] args) {

		logger.info("Args: {}", Arrays.stream(args).toList().toString());
		com.github.rvesse.airline.Cli<Runnable> cli = new com.github.rvesse.airline.Cli<>(XstoreconfApplication.class);
		Runnable cmd = cli.parse(args);
		cmd.run();
	}

}
