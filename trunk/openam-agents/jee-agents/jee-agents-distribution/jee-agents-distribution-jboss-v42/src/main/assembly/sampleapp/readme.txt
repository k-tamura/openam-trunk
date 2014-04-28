<!--
   DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
  
   Copyright (c) 2008 Sun Microsystems Inc. All Rights Reserved
  
   The contents of this file are subject to the terms
   of the Common Development and Distribution License
   (the License). You may not use this file except in
   compliance with the License.

   You can obtain a copy of the License at
   https://opensso.dev.java.net/public/CDDLv1.0.html or
   opensso/legal/CDDLv1.0.txt
   See the License for the specific language governing
   permission and limitations under the License.

   When distributing Covered Code, include this CDDL
   Header Notice in each file and include the License file
   at opensso/legal/CDDLv1.0.txt.
   If applicable, add the following below the CDDL Header,
   with the fields enclosed by brackets [] replaced by
   your own identifying information:
   "Portions Copyrighted [year] [name of copyright owner]"

   $Id: readme.txt,v 1.3 2009/01/25 05:59:31 naghaon Exp $

-->

<!--
     Portions Copyrighted 2013-2014 ForgeRock AS.
-->

------------------------------------
J2EE Policy Agent Sample Application
------------------------------------

This document describes how to use the agent sample application in conjunction 
with the Jboss Server and the J2EE Agent. Please note that the agent
needs to be installed first before deploying this sample application.

    * Overview
    * Configure the OpenAM server
    * Configure the agent properties
    * Compiling and Assembling the Application
    * Deploying the Sample Application
    * Running the Sample Application
    * Troubleshooting


Overview
--------
The sample application is a collection of servlets, JSPs and EJB's that 
demonstrate the salient features of the J2EE policy Agent. These features 
include SSO, web-tier declarative security, programmatic security, URL policy 
evaluation and session/policy/profile attribute fetch. The web.xml deployment
descriptor has already been edited to include the Agent Filter. The
deployment descriptors and source code are available in the sampleapp/src
directory.

The sample application is supported for Policy Agent 3.0.

The application is already built and ready to be deployed. It is available at
sampleapp/dist/agentsample.ear.

Note, the instructions here assume that you have installed the agent
successfully and have followed the steps outlined in the OpenAM
Policy Agent 3.0 Guide.



Configure the OpenAM server
---------------------------------------------
This agent sample application requires that the OpenAM server is configured
with the subjects and policies required by the sample application.

On OpenAM admin console, do the following configuration.
1.  Create the following users:
    Here is the following list of users with username/password :

    * andy/andy
    * bob/bob
    * chris/chris
    * dave/dave
    * ellen/ellen
    * frank/frank
    * gina/gina


2. Assign Users to Groups
   Create new groups for employee, manager, everyone, and customer. Then assign 
   the users to the groups as follows:

    * employee:
          o andy, bob, chris, dave, ellen, frank
    * manager:
          o andy, bob, chris
    * everyone:
          o andy, bob, chris, dave, ellen, frank, gina
    * customer:
          o chris, ellen
    
3. Create the following URL Policies:
   In the following URLs, replace the <hostname> and <port> with the 
   actual fully qualified host name and port on which the sample 
   application will be running.

    * Policy 1:
          o allow:
                + http://<hostname>:<port>/agentsample/jsp/*
                + http://<hostname>:<port>/agentsample/invokerservlet
                + http://<hostname>:<port>/agentsample/protectedservlet
                + http://<hostname>:<port>/agentsample/securityawareservlet
                + http://<hostname>:<port>/agentsample/protectedejbservlet
          o Subject: all authenticated users.                     
    * Policy 2:
          o allow:
                + http://<hostname>:<port>/agentsample/urlpolicyservlet
          o Subject: Group: customer

Configure the agent properties
------------------------------

   If the agent configuration is centralized, then do the following steps.
   1). login to OpenAM console as amadmin user
   2). navigate to Access Control/realm/Agents/J2EE, and click on the agent 
       instance link (assume the agent instance is already created, otherwise 
       refer to the agent doc to create the agent instance).
   3). in tab "Application", section "Access Denied URI Processing", property 
       "Resource Access Denied URI", enter agentsample in the Map Key field,
       /agentsample/authentication/accessdenied.html in the Map Value field, and
       SAVE the change.
   4). in tab "Application", section "Login Processing", property "Login Form URI",
       add /agentsample/authentication/login.html, and SAVE the change.
   5). in tab "Application", section "Not Enforced URI Processing", property
       "Not Enforced URIs", add the following entries:
          /agentsample/public/*
          /agentsample/images/*
          /agentsample/styles/*
          /agentsample/index.html
          /agentsample/
          /agentsample
       and SAVE the change. 
   
   6). If the JBoss 3.0 agent is installed on the JBoss-4.0.5.GA, in console, under 
         J2EE Agents -> Advanced Tab -> JBoss Aplication Server section set
         com.sun.identity.agents.config.jboss.webauth.available=false , and SAVE the change.
   7). If the JBoss 3.0 agent is installed on the JBoss-4.2.x.GA, in console, under 
         J2EE Agents -> Advanced Tab -> JBoss Aplication Server section set
       com.sun.identity.agents.config.jboss.webauth.available=true , and SAVE the change.
  
   If the agent configuration is local, then edit the local agent configuration
   file OpenSSOAgentConfiguration.properties located at the directory 
   <agent_install_root>/Agent_<instance_number>/config with following changes: 

    * Not enforced List:
      com.sun.identity.agents.config.notenforced.uri[0] = /agentsample/public/*
      com.sun.identity.agents.config.notenforced.uri[1] = /agentsample/images/*
      com.sun.identity.agents.config.notenforced.uri[2] = /agentsample/styles/*
      com.sun.identity.agents.config.notenforced.uri[3] = /agentsample/index.html
      com.sun.identity.agents.config.notenforced.uri[4] = /agentsample/
      com.sun.identity.agents.config.notenforced.uri[5] = /agentsample

    * Access Denied URI:
      com.sun.identity.agents.config.access.denied.uri[agentsample] = /agentsample/authentication/accessdenied.html
    * Form List:
      com.sun.identity.agents.config.login.form[0] = /agentsample/authentication/login.html

     * WebAuthentication :
      If the JBoss 3.0 agent is installed on the JBoss-4.0.5.GA, set the property
      com.sun.identity.agents.config.jboss.webauth.available=false 
      If the JBoss 3.0 agent is installed on the JBoss-4.2.x.GA, set the property
      com.sun.identity.agents.config.jboss.webauth.available=true in the OpenSSOAgentConfiguration.properties.


   Optionally, you can try out the fetch mode features that allow the agent to
   fetch some values and make them available to your application. For example, 
   you can fetch user profile values(like email or full name) from the user data
   store of your OpenAM setup and make them available to your application code
   (through cookies, headers, or request attributes) for application 
   customization. See the Policy Agent 3.0 for details about the fetching 
   attributes for details on using this feature. If you change the agent's 
   configuration for the attribute fetching, the showHttpHeaders.jsp page of the
   sample application will show all the attributes being fetched. You can choose
   to try this later after you have already installed and deployed the agent and
   sample application in order to learn about this feature.


Compiling and Assembling the Application
----------------------------------------
This section contains instructions to build and assemble the sample application using a Command 
Line Interface (CLI).

To rebuild the entire application from scratch, follow these steps:

   1. Set your JAVA_HOME and CLASSPATH to JDK1.4 or above.
   2. Replace 'JBOSS_SERVER_LIB_DIR' in build.xml with the directory where jboss-j2ee.jar or servlet-api.jar is located.
      For example: replace JBOSS_LIB_DIR with /opt/jboss-4.2.3/server/default/lib
      Note:a)If you are installing the agent on JBoss-5.0.0.GA Replace 'JBOSS_SERVER_LIB_DIR' in build.xml
      with the directory where jboss-javaee.jar is located.(eg.JBoss-5.0.0.GA/common/lib)
      and Replace all the occurrences of jboss-j2ee.jar with the jboss-javaee.jar in the build.xml
      b)Replace the javax-servlet.jar in the build.xml with the servlet-api.jar 
      before building sample app for JBoss 4.2.x and JBoss 5.0.x.
   3. Change the Universal User Id (UUID) 
      By default, the JBoss server specific deployment descriptors assume 
      that the OpenAM Server product was installed under default Org/Realm
      "dc=openam,dc=forgerock,dc=org". If the Org/Realm for the deployment scenario
      is different from the default root suffix, the UUID for the role/principal
      mappings should be changed accordingly. The UUID can be obtained from the
      OpenAM server console the group/role pages.
      e.g. the root suffix of the OpenAM server deployment is "dc=xyz,dc=com".
      then replace all the occurrences of "dc=openam,dc=forgerock,dc=org" with
      "dc=xyz,dc=com" in ejb-jar.xml, jboss.xml and web.xml
   4. Compile and assemble the application. Ant needs to be available for this task.
      Download latest Ant from http://ant.apache.org. Install Ant and build the sampleapp.ear. 
      For example: execute the command <ant_home>/bin/ant 
      under <agent_install_root>/sampleapp/ to execute the default target rebuild the EAR file. 
      The build target creates a built and dist directory with the EAR file. 
   5. Deploy the application. After you have re-created the sample application from scratch, you may 
      proceed directly to Deploying the Sample Application, or optionally perform step 3.
   6. Optionally you can run 'ant rebuild' to clean the application project area and run a 
      new build.

Now you are ready to use the dist/agentsample.ear file for deployment.

Deploying the Sample Application
--------------------------------
To deploy the application, do the following:

Copy the <agent_install_root>/sampleapp/dist/agentsample.ear to JBoss server instance's deploy dir. 
Example: cp <agent_install_root>/sampleapp/dist/agentsample.ear /opt/jboss-4.2.3/server/default/deploy

If the server instance is not default, please change it to the instance name for which you are configuring
the Policy Agent. 


Verifying Deployment
--------------------

As an optional step, you can verify that the application has been registered. 
Otherwise, proceed directly to Running the Sample Application.

To verify the registration of the application:

   1. If the JBoss server is running, the agentsample.ear gets loaded dynamically. or 
      When you start JBoss using run script, you will see that agentsample gets loaded.

   2. Alternately, use JMX Console and look for agentsample. You will 
      see agentsample listed.



Running the Sample Application
----------------------------
You can access the application through the following URL in a web browser:

http://<hostname>:<port>/agentsample

Traverse the various links to understand each agent feature.


Troubleshooting
----------------------------
If you encounter problems when running the application, review the log files to learn what exactly 
went wrong. JBoss server log files are located at 
<jboss_home>/server/<instance-name>/log/server.log and the J2EE Agent logs can be found 
at <agent_install_root>/agent_<instance_number>/logs/debug directory.


