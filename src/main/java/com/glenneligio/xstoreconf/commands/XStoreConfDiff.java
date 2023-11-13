package com.glenneligio.xstoreconf.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rvesse.airline.HelpOption;
import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Group;
import com.github.rvesse.airline.annotations.Option;
import com.glenneligio.xstoreconf.model.DbConfiguration;
import com.glenneligio.xstoreconf.model.XStoreConf;
import com.glenneligio.xstoreconf.model.XStoreConfExcelEntry;
import com.glenneligio.xstoreconf.service.XStoreConfExcelServiceImpl;
import com.glenneligio.xstoreconf.service.XStoreConfServiceImpl;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Command(name = "diff", description = "Check difference of XSTORECONF tables")
@Group(name = "xstoreconf")
public class XStoreConfDiff implements Runnable{

    private static Logger logger = LoggerFactory.getLogger(XStoreConfDiff.class);

    @Inject
    private HelpOption<XStoreConfDiff> help;

    @Option(name = { "-v", "--verbose" },
            description = "Set log verbosity on/off")
    protected boolean verbose = false;

    @Option(name = {"-d", "--dbConfigs"},
            description = "YAML file for configurations")
    protected String dbConfigsYaml;

    @Option(name = {"-b1", "--brand1"}, description = "Brand name of the first table, e.g. CLE")
    protected String brand1;

    @Option(name = {"-b2", "--brand2"}, description = "Brand name of the second table, e.g. AUTH")
    protected String brand2;

    @Option(name = {"-e1", "--env1"}, description = "Environment of the first table, e.g. INT")
    protected String env1;

    @Option(name = {"-e2", "--env2"}, description = "Environment of the second table, e.g. UAT")
    protected String env2;

    @Option(name={"-et1", "--subEnv1"}, description = "Environment type of the first table, e.g. AUTH or LIVE")
    protected String envType1;

    @Option(name={"-et2", "--subEnv2"}, description = "Environment type of the second table, e.g. AUTH or LIVE")
    protected String envType2;

    @Option(name = {"-t1", "--table1"}, description = "Table name of the first table. e.g. XSTORECONF")
    protected String tableName1;

    @Option(name = {"-t2", "--table2"}, description = "Table name of the second table. e.g. XSTORECONF")
    protected String tableName2;

    private String hostname1, hostname2;
    private int port1, port2;
    private String username1, username2, password1, password2;

    @SneakyThrows
    @Override
    public void run() {
//        List<XStoreConf> xStoreConfList1 = new XStoreConfServiceImpl().getAllXStoreConf();
//		List<XStoreConf> xStoreConfList2 = new XStoreConfServiceImpl().getAllXStoreConf2();
//
//		logger.info("List 1: {}", xStoreConfList1);
//		logger.info("List 2: {}", xStoreConfList2);
//
//		List<XStoreConfExcelEntry> xStoreConfExcelEntries = new XStoreConfExcelServiceImpl().getXStoreExcelEntries(xStoreConfList1, xStoreConfList2);
//
//		ObjectMapper mapper = new ObjectMapper();
//		logger.info("Excel list: {}", mapper.writeValueAsString(xStoreConfExcelEntries));
//        if (!help.showHelpIfRequested())
//            System.out.println("Verbosity: " + verbose);

        logger.info("Input for table 1 - brand: {}, env: {}, envType: {}, table: {}", brand1, env1, envType1, tableName1 );
        logger.info("Input for table 2 - brand: {}, env: {}, envType: {}, table: {}", brand2, env2, envType2, tableName2 );

        logger.info(dbConfigsYaml);
        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(dbConfigsYaml);
        Map<String, Object[]> obj = yaml.load(inputStream);
        logger.info(obj.toString());
//        List<DbConfiguration> = obj.values().forEach();
//        logger.info(dbConfigurations.toString());

//        if(!dbConfigurations1.isEmpty() && !dbConfigurations2.isEmpty()) {
//            logger.info("DB Configuration for table 1 - brand: {}, env: {}, envType: {}, table: {}", brand1, env1, envType1, tableName1 );
//            logger.info("DB Configuration for table 2 - brand: {}, env: {}, envType: {}, table: {}", brand2, env2, envType2, tableName2 );
//        } else if (dbConfigurations1.isEmpty()) {
//            logger.info("DB Configuration for table 1 does not exist in config db yaml file");
//        } else {
//            logger.info("DB Configuration for table 1 does not exist in config db yaml file");
//        }

    }
}

