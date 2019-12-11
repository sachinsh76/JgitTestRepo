package com.company;

import com.company.auth.Authenticator;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.URIish;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;

public class Main {


    public static void main(String[] args) throws IOException, GitAPIException, URISyntaxException {
	// write your code here
        Git.init().setDirectory(new File("").getAbsoluteFile()).call();
        Git git = Git.open(new File(""));
        git.remoteAdd().setName("origin").setUri(new URIish("git@github.com:sachinsh76/JgitTestRepo.git")).call();
        git.add().addFilepattern("src").call();

//        git.push().setRemote("origin").add("master").setTransportConfigCallback(transportConfigCallback).call();

        PullCommand pullCommand = Authenticator.authenticatedPullCommand(git);

        System.out.println("Pulling code from repo ");
        PullResult pullResult = pullCommand.setRebase(true)
                .setProgressMonitor(new TextProgressMonitor(new PrintWriter(System.out)))
                .call();

        System.out.println(pullResult.getFetchResult());
        System.out.println("Completed pulling code from repo ");

        PushCommand pushCommand = Authenticator.authenticatedPushCommand(git);

        System.out.println("\nPushing code to the repo ");
        Iterable<PushResult> pushResults = pushCommand.setRemote("origin")
                .add("master")
                .setProgressMonitor(new TextProgressMonitor(new PrintWriter(System.out)))
                .call();

        for (PushResult pushResult : pushResults) {
            for (RemoteRefUpdate update : pushResult.getRemoteUpdates()) {
                System.out.println(update.getStatus());
            }
            System.out.println(pushResult.toString());
        }
        System.out.println("Finished pushing code to the repo ");

        git.close();
    }
}
