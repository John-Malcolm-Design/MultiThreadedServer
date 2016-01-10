# Multi Threaded TCP Server Application
John Malcolm Anderson | GOO290919

Written in Java, server.jar runs on the Azure server from the Desktop. ClientRunner.java allows for a user to login and perform some basic tasks on the server.

# Packages
  - client - Client classes
  - server - Server classes
  - user_mgmt - User classes
  - uml - UML Diagram

To run the server login to export the project as a Runnable JAR and set the Launch Configuration as ServerRunner.java. Before exporting the JAR file two lines of code that point are absolute file paths will need to be changed. The first is the root variable on *line 38 of ClientServiceThread.java* and also *line 25 of FileParser.java*.

I have included a ZIP that contains sample user folders for the user logins, these should be placed in the root folder on the Desktop in the server.

### Key features of the project
* Object Orientated Design
* Robust Error Handling
* Low level IO handling on client and server


> The server allows for uploading of text files and the download functionality is currently not working correctly. All other features outlined in the brief are working fine. ClientRunner.java should be run from the client

### Version Control
* [Github] - https://github.com/johnmalcolm/MultiThreadedServer



