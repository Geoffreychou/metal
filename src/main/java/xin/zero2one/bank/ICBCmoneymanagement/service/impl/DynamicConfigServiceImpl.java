package xin.zero2one.bank.ICBCmoneymanagement.service.impl;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xin.zero2one.bank.ICBCmoneymanagement.service.DynamicConfigService;
import xin.zero2one.bank.ICBCmoneymanagement.utils.GitClient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author zhoujundong
 * @data 8/30/2019
 * @description
 */
@Service
public class DynamicConfigServiceImpl implements DynamicConfigService {

    @Value("${remote.git.branchName}")
    private String branchName;

    @Value("${local.file.dir}")
    private String operationPath;

    @Value("${local.file.name}")
    private String configFileName;

    public void pullConfig() throws IOException, GitAPIException {
        GitClient gitClient = new GitClient(branchName, operationPath);
        gitClient.pull();
        gitClient.close();
    }

    public void parseConfigFile() throws IOException {
        Path path = Paths.get(operationPath, configFileName);
        File file = path.toFile();
        String configStr = FileUtils.readFileToString(file, "UTF-8");


    }

}
