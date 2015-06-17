/*******************************************************************************
 * Copyright (c) 2012-2015 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.git.impl.nativegit.commands;

import org.eclipse.che.api.git.GitException;

import java.io.File;
import java.util.List;

/**
 * Download objects and refs from other repository
 *
 * @author Eugene Voevodin
 */
public class FetchCommand extends RemoteUperationCommand<Void> {

    private List<String> refSpec;
    private String   remote;
    private boolean  prune;

    public FetchCommand(File repository) {
        super(repository);
    }

    /** @see GitCommand#execute() */
    @Override
    public Void execute() throws GitException {
        reset();
        commandLine.add("fetch", getRemoteUrl());
        commandLine.add(refSpec);
        if (prune) {
            commandLine.add("--prune");
        }
        // Progress not shown if not a terminal. Activating progress output. See git fetch man page.
        commandLine.add("--progress");
        start();
        return null;
    }

    /**
     * @param refSpec
     *         ref spec to fetch
     * @return FetchCommand with established ref spec
     */
    public FetchCommand setRefSpec(List<String> refSpec) {
        this.refSpec = refSpec;
        return this;
    }

    /**
     * @param prune
     *         if <code>true</code> not existing remote branches will be removed
     * @return FetchCommand with established prune parameter
     */
    public FetchCommand setPrune(boolean prune) {
        this.prune = prune;
        return this;
    }
}
