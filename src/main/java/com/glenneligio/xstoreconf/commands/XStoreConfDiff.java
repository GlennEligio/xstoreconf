package com.glenneligio.xstoreconf.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rvesse.airline.HelpOption;
import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Group;
import com.github.rvesse.airline.annotations.Option;
import com.glenneligio.xstoreconf.dao.XStoreConfDaoImpl;
import com.glenneligio.xstoreconf.model.DbConfiguration;
import com.glenneligio.xstoreconf.model.XStoreConf;
import com.glenneligio.xstoreconf.service.XStoreConfServiceImpl;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

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

    private String hostname1, hostname2,username1, username2, password1, password2, name1, name2;
    private int port1, port2;

    @SneakyThrows
    @Override
    public void run() {
        logger.info("Input for table 1 - brand: {}, env: {}, envType: {}, table: {}", brand1, env1, envType1, tableName1 );
        logger.info("Input for table 2 - brand: {}, env: {}, envType: {}, table: {}", brand2, env2, envType2, tableName2 );

        // Read db config yaml file
        logger.info(dbConfigsYaml);
        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(dbConfigsYaml);
        Map<String, List<Object>> dbConfigs = yaml.load(inputStream);

        ObjectMapper mapper = new ObjectMapper();
        DbConfiguration dbConfiguration = mapper.convertValue(dbConfigs, new TypeReference<>() {});

        DbConfiguration.DbConfig dbConfig1 = dbConfiguration.getConfiguration().stream().filter(dbConfig ->
            dbConfig.getBrand_name().equals(brand1) && dbConfig.getEnvironment().equals(env1) && dbConfig.getEnvironment_type().equals(envType1)
        ).findFirst().orElse(null);

        DbConfiguration.DbConfig dbConfig2 = dbConfiguration.getConfiguration().stream().filter(dbConfig ->
                dbConfig.getBrand_name().equals(brand2) && dbConfig.getEnvironment().equals(env2) && dbConfig.getEnvironment_type().equals(envType2)
        ).findFirst().orElse(null);

        if(Objects.nonNull(dbConfig1) && Objects.nonNull(dbConfig2)) {
            logger.info("DB Configuration for table 1 - brand: {}, env: {}, envType: {}, table: {}", brand1, env1, envType1, tableName1 );
            logger.info("DB Configuration for table 2 - brand: {}, env: {}, envType: {}, table: {}", brand2, env2, envType2, tableName2 );

            hostname1 = dbConfig1.getDatabase().getHost();
            port1 = dbConfig1.getDatabase().getPort();
            username1 = dbConfig1.getDatabase().getUsername();
            password1 = dbConfig1.getDatabase().getPassword();
            name1 = dbConfig1.getDatabase().getName();

            hostname2 = dbConfig2.getDatabase().getHost();
            port2 = dbConfig2.getDatabase().getPort();
            username2 = dbConfig2.getDatabase().getUsername();
            password2 = dbConfig2.getDatabase().getPassword();
            name2 = dbConfig2.getDatabase().getName();
        } else {
            if (Objects.isNull(dbConfig1)) {
                logger.info("DB Configuration for table 1 does not exist in config db yaml file");
            }
            if (Objects.isNull(dbConfig2)){
                logger.info("DB Configuration for table 2 does not exist in config db yaml file");
            }
            return;
        }

        // Logic on connecting to database and getting the list

        XStoreConfDaoImpl dao1 = new XStoreConfDaoImpl(hostname1, username1, password1, name1, port1, tableName1);
        XStoreConfDaoImpl dao2 = new XStoreConfDaoImpl(hostname2, username2, password2, name2, port2, tableName2);

        List<XStoreConf> xStoreConfList1 = dao1.getAll();
        List<XStoreConf> xStoreConfList2 = dao2.getAll();

		logger.info("List 1: {}", xStoreConfList1);
		logger.info("List 2: {}", xStoreConfList2);

//		List<XStoreConfExcelEntry> xStoreConfExcelEntries = new XStoreConfExcelServiceImpl().getXStoreExcelEntries(xStoreConfList1, xStoreConfList2);
//
//		ObjectMapper mapper = new ObjectMapper();
//		logger.info("Excel list: {}", mapper.writeValueAsString(xStoreConfExcelEntries));
//        if (!help.showHelpIfRequested())
//            System.out.println("Verbosity: " + verbose);

    }
}

