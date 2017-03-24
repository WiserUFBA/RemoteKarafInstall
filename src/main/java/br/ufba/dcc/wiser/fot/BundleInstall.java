/*
 * The MIT License
 *
 * Copyright 2017 Jurandir Barbosa <jurandirbarbosa@ifba.edu.br>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package br.ufba.dcc.wiser.fot;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.karaf.bundle.core.BundleService;
import org.apache.karaf.shell.api.console.Session;
import org.apache.karaf.shell.support.MultiException;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

/**
 * 
 * Bundle Install service for use with FoT Balance Management.
 * 
 * @author Jurandir Barbosa <jurandirbarbosa@ifba.edu.br>
 */
public class BundleInstall {
    private final boolean start = true;
    private Session session;
    private BundleService bundleService;
    private BundleContext bundleContext;

    public Object execute(List<URI> urls) throws Exception {
        // install the bundles
        List<Exception> exceptions = new ArrayList<>();
        List<Bundle> bundles = new ArrayList<>();
        for (URI url : urls) {
            try {
                bundles.add(bundleContext.installBundle(url.toString(), null));
            } catch (BundleException e) {
                exceptions.add(new Exception("Unable to install bundle " + url, e));
            }
        }
        
        // optionally start the bundles
        if (start) {
            for (Bundle bundle : bundles) {
                try {
                    bundle.start();
                } catch (BundleException e) {
                    exceptions.add(new Exception("Unable to start bundle " + bundle.getLocation(), e));
                }
            }
        }
        
        // print the installed bundles
        System.err.println("Installing >");
        if (bundles.size() == 1) {
            System.err.println("Bundle ID: " + bundles.get(0).getBundleId());
        } else {
            StringBuffer sb = new StringBuffer("Bundle IDs: ");
            for (Bundle bundle : bundles) {
                if (sb.length() > 0) {
                    sb.append("\n");
                }
                sb.append(bundle.getBundleId()).append(" : ").append(bundle.getLocation());
            }
            System.err.println(sb);
        }
        MultiException.throwIf("Error installing bundles", exceptions);
        return null;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setBundleService(BundleService bundleService) {
        this.bundleService = bundleService;
    }

    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    
}
