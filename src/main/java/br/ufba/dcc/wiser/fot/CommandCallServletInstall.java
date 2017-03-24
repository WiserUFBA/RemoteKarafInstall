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

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Install servlet for use with FoT Balance Management.
 * 
 * @author Jurandir Barbosa <jurandirbarbosa@ifba.edu.br>
 */
public class CommandCallServletInstall extends HttpServlet {

    private static final long serialVersionUID = 34992072289535683L;

    private BundleInstall bundleInstall;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String mvn = req.getParameter("mvn");
        resp.getWriter().println("Servlet is here");
        resp.getWriter().println("Installing:" + mvn);

        if (bundleInstall == null) {
            resp.getWriter().println("bundleInstall null:");
        } else {
            if (mvn != null) {
                try {
                    List<URI> list = new ArrayList<>();
                    URI uri = new URI("mvn:"+mvn);
                    list.add(uri);
                    bundleInstall.execute(list);
                } catch (Exception ex) {
                    java.util.logging.Logger.getLogger(CommandCallServletInstall.class.getName()).log(Level.SEVERE, null, ex);
                }

            }else{
                resp.getWriter().println("URI nul:");
            }
        }
        Logger log = LoggerFactory.getLogger(CommandCallServletInstall.class);
    }

    public BundleInstall getBundleInstall() {
        return bundleInstall;
    }

    public void setBundleInstall(BundleInstall bundleInstall) {
        this.bundleInstall = bundleInstall;
    }

}
