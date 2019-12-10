package com.company;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.transport.URIish;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws IOException, GitAPIException, URISyntaxException {
	// write your code here
        TransportConfigCallback transportConfigCallback = new SshTransportConfigCallback();
        Git.init().setDirectory(new File("").getAbsoluteFile()).call();
        Git git = Git.open(new File(""));
        git.remoteAdd().setName("origin").setUri(new URIish("git@github.com:sachinsh76/JgitTestRepo.git")).call();
        git.add().addFilepattern("src").call();
//        git.push().setRemote("origin");
        git.pull().setTransportConfigCallback(transportConfigCallback).setRebase(true).setProgressMonitor(new TextProgressMonitor(new PrintWriter(System.out))).call();
    }
}
