/*
 * "@(#)StartTlsSimple.java	1.1	01/05/24 SMI"
 * 
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use, modify and
 * redistribute this software in source and binary code form, provided that i) this copyright notice
 * and license appear on all copies of the software; and ii) Licensee does not utilize the software
 * in a manner which is disparaging to Sun.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL EXPRESS OR IMPLIED
 * CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS
 * SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR
 * ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
 * PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE
 * USE OF OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGES.
 * 
 * This software is not designed or intended for use in on-line control of aircraft, air traffic,
 * aircraft navigation or aircraft communications; or in the design, construction, operation or
 * maintenance of any nuclear facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */
package com.zyos.core.common.util.auth;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.list.BeanList;
import com.zyos.session.common.User;

/**
 * 
 * usage: java StartTlsSimple
 */

public class StartTlsSimple {

	public StartTlsSimple() {

	}

	public boolean validateUser(String user, String password) {
		DirContext ctx = null;
		try {
			String path = StartTlsSimple.class.getResource("permanent.jks").getPath();
			System.out.println("INFO: loading = " + path);

			System.setProperty("javax.net.ssl.trustStore", path);

			System.setProperty("javax.net.ssl.keyStore", path);

			// Set up environment for creating initial context
			Hashtable<String, String> env = new Hashtable<String, String>(11);
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

			// Must use the name of the server that is found in its certificate
			env.put(Context.PROVIDER_URL, BeanList.properties.getProperty("ldap.zyos.co"));

			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL, "cn=" + user + ",ou=Users,dc=usta,dc=edu,dc=co");
			env.put(Context.SECURITY_CREDENTIALS, password);

			// Create the initial context

			ctx = new InitialDirContext(env);
			boolean result = ctx != null;

			return result;

		} catch (Exception e) {
			System.out.println("WARN: fail LDAP validation for " + user);
		} finally {
			if (ctx != null)
				try {
					ctx.close();
				} catch (NamingException e) {
					e.printStackTrace();
				}
		}
		return false;
	}

	private User getUserBasicAttributes(String username, LdapContext ctx) {
		User user = null;
		try {

			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String[] attrIDs = {"distinguishedName", "sn", "givenname", "mail", "telephonenumber"};
			constraints.setReturningAttributes(attrIDs);
			// First input parameter is search bas, it can be "CN=Users,DC=YourDomain,DC=com"
			// Second Attribute can be uid=username
			NamingEnumeration answer = ctx.search("DC=YourDomain,DC=com", "sAMAccountName=" + username, constraints);
			if (answer.hasMore()) {
				Attributes attrs = ((SearchResult) answer.next()).getAttributes();
				System.out.println("distinguishedName " + attrs.get("distinguishedName"));
				System.out.println("givenname " + attrs.get("givenname"));
				System.out.println("sn " + attrs.get("sn"));
				System.out.println("mail " + attrs.get("mail"));
				System.out.println("telephonenumber " + attrs.get("telephonenumber"));
			} else {
				throw new Exception("Invalid User");
			}

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
		return user;
	}

	public static void main(String[] args) {
		new StartTlsSimple();
	}
}
