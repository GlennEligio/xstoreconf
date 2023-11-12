package com.glenneligio.xstoreconf.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rvesse.airline.HelpOption;
import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Group;
import com.github.rvesse.airline.annotations.Option;
import com.glenneligio.xstoreconf.model.XStoreConf;
import com.glenneligio.xstoreconf.model.XStoreConfExcelEntry;
import com.glenneligio.xstoreconf.service.XStoreConfExcelServiceImpl;
import com.glenneligio.xstoreconf.service.XStoreConfServiceImpl;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

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

    @SneakyThrows
    @Override
    public void run() {
        List<XStoreConf> xStoreConfList1 = new XStoreConfServiceImpl().getAllXStoreConf();
		List<XStoreConf> xStoreConfList2 = new XStoreConfServiceImpl().getAllXStoreConf2();

		logger.info("List 1: {}", xStoreConfList1);
		logger.info("List 2: {}", xStoreConfList2);

		List<XStoreConfExcelEntry> xStoreConfExcelEntries = new XStoreConfExcelServiceImpl().getXStoreExcelEntries(xStoreConfList1, xStoreConfList2);

		ObjectMapper mapper = new ObjectMapper();
		logger.info("Excel list: {}", mapper.writeValueAsString(xStoreConfExcelEntries));
        if (!help.showHelpIfRequested())
            System.out.println("Verbosity: " + verbose);
    }
}

