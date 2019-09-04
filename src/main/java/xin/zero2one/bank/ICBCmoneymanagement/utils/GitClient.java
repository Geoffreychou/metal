package xin.zero2one.bank.ICBCmoneymanagement.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author zhoujundong
 * @data 8/30/2019
 * @description git 客户端操作
 */
@Slf4j
public class GitClient {

    private static final String SUFFIX = ".git";

    private static final String DEFAULT_BRANCH_NAME = "master";

    private String username;

    private String password;

    private String uri;

    private String branchName;


    private Git git;

    private UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider;

    public GitClient(String branchName, String operationPath) throws IOException {
        this.branchName = branchName;
        git = Git.open(Paths.get(operationPath).toFile());
    }


    public GitClient(String branchName, String uri, String username, String password, String operationPath) throws IOException, GitAPIException {
        if (StringUtils.isBlank(branchName)) {
            this.branchName = DEFAULT_BRANCH_NAME;
        } else {
            this.branchName = branchName;
        }
        this.uri = uri;
        this.username = username;
        this.password = password;
        Path path = Paths.get(operationPath, SUFFIX);
        File gitFile = path.toFile();
        usernamePasswordCredentialsProvider = new UsernamePasswordCredentialsProvider(this.username, this.password);
        if (!gitFile.exists()) {
            git = Git.cloneRepository()
                    .setURI(this.uri)
                    .setCredentialsProvider(usernamePasswordCredentialsProvider)
                    .setDirectory(Paths.get(operationPath).toFile())
                    .call();
            git.checkout().setName(branchName).setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK).call();
            git.pull().call();
        } else {
            git = Git.open(Paths.get(operationPath).toFile());
        }
    }



    public void pull() throws GitAPIException {
        pull(this.branchName);
    }

    public void pull(String branchName) throws GitAPIException {
        try {
            git.checkout().setCreateBranch(false).setName(branchName)
                    .setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK)
                    .call();
            git.pull().call();
        } catch (GitAPIException e) {
            log.error("git pull error", e);
            throw e;
        }
    }

    public void close() {
        git.close();
    }
}
