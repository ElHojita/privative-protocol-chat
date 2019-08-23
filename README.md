# privative-protocol-chat

#INSTANT MESSAGING
The next project was suggested by the Network 1 course, from the Systems Engineering degree of the Mariano Gálvez University of Guatemala. On the course of the course, we requested the use of an existing protocol, to generate instant messaging to establish a person-to-person communication. It is required to perform the following project:
•	Open Protocol (Xmpp)
•	Determined domain (alumchat.xyz)

Instant messaging will be established through a user where they can interact in different actions such as:
•	Account Manager:
o	Register a new account on the server
o	Sign in with an account
o	Sign out with an account
o	Delete the server account
•	Communication:
o	Show all users/contacts and their status
o	Add a user to contacts
o	Show a user's contact details
o	1 to 1 communication with any user/contact
o	Participate in group conversations
o	Define presence message

#INSTANT MESSAGING ENCODING
This project was decided to perform in Java environment, so our main tool was used Netbeans IDE 8.2.
As main libraries:
•	Smack.jar
•	Smackx.jar
•	JDK 1.8

#Smack3.1.0 Extensions
Smack is an Open Source XMPP(Jabber) client libraryfor instant messaging and presence. A pure Java library can be integrated into your applications to create anything from a full XMPP client to simple XMPP integrations, such as sending notification messages.

#Smackx jivesoftware group  (version 3.1.0)
Smack is an Open Source XMPP(Jabber) client libraryfor instant messaging and presence. A pure Java library can be integrated into your applications to create anything from a full XMPP client to simple XMPP integrations, such as sending notification messages.

#What are communication protocols?
Communication protocols in telecommunications and computing are defined as a system of rules through which two or more entities that are part of a communication system are allowed to communicate with each other, to transmit information through any kind of variation by a physical magnitude.
 
#What are communication protocols for?
To allow data to be shared on a network, prior communication is required, and that communication is governed by the communication protocols, which, according to their compliance, will allow communication.

#XMPP protocol
Extensible Messaging and Presence Protocol,better known as XMPP (previously called Jabber),is an open protocoland XML-based extensible, originally designed for instant messaging. It is also used in a wide range of voice and video messaging applications.

What does it allow us to do?
We can create a network of servers that are completely independent and decentralized. When we connect by XMPP we can establish communications with user accounts registered on other servers, being our account on the server we want, as our own.
XMPP is an open protocol that was created for use in instant messaging systems originally, is XML-based. It was originally known as Jabber, and the project was started in 1998 by Jeremie Miller. Currently XMPP and its multiple extensions support instant messaging, video conferencing, view of users' online status, and file transfer through clients.

#Features:
•	Open: The protocol is free, open, public and easy to understand. That's why it has multiple deployments across clients, servers, server components, and code libraries.
•	Standard: The Internet Engineering Task Force (IETF)has formalized the core of the protocol as an instant messaging technology and presence information.
•	Tested: The first technologies were developed in 1998 and are now very stable. There are thousands of servers using this protocol on the Internet, and millions of people using it for instant messaging for public services such as Google Talk and deployments in organizations.
•	Decentralized: Its architecture is similar to email,so you can use your own server, allowing organizations to have control of their communications experience.
•	Secure: Any XMPP server can be isolated from the public network (as in an Intranet),use additional security in SASL and TLS formats.
•	Extensible: Custom functionalities can be built on top of the protocol core. Although there are common extensions, organizations can maintain their own extensions.
•	Flexible: The original XMPP (messaging and presence) applications have been extended and can now be found in network management, content unionization, collaboration tools, file sharing, games, monitoring of remote systems, web services, cloud computing, etc.
•	Diverse: A wide range of Open Source companies and projects use XMPP to build and deploy real-time applications and services.

#Benefits
•	Cooperation: Messaging systems offer additional functions to message exchange, such as file transfer, contact lists, simultaneous conversations. All these functions may be required by small businesses and corporations.
•	Mobile messaging: with which it is possible to transfer the desktop messaging experience to mobile devices with Internet access.
•	Networking: The ability to communicate with friends through chat rooms for instant messaging between all members of a network.

#Disadvantages
•	Presence data overload: Typically about 70% of traffic between servers is presence data, and about 60% of these are redundant transmissions. New protocols are currently being explored to alleviate this problem.
•	Scalability: XMPP also suffers from the same redundancy issue in chatroom and subscription services. Work is currently underway on your solution.
•	No binary data: XMPP is encoded as a single, long XML document, making it impossible to deliver unmodified binary data. However, file transfers have been fixed using other protocols such as HTTP. If unavoidable, XMPP can also perform transfers by encoding all data using base64.

#Decentralization and addressing
The XMPP network is server-based, but decentralized; by design, there is no central server, as is the case with services such as AOL Instant Messenger or MSN Messenger.
Each user on the XMPP network has a unique identifier (Jabber ID, usually abbreviated as JID). To avoid the need for a central server with an exhaustive list of identifiers, the Jabber ID is structured as an email address, with a username and DNS address for the server in the user resides, separated by a .
Because a user may want to identify theself from different places, the server allows the client to specify a reference string known as a resource, which identifies the client that the user is using (for example: home, work, laptop, etc.). This will be included in the JID by adding a / character followed by the resource name. Each resource must have a numeric priority value specified.
JIDs without the username part are also valid and are used to send system and control messages.

#Connecting to other protocols
A very useful feature of the XMPP protocol are gateways, which allow users to access networks with other instant messaging protocols such as MSN Messenger, ICQ or other types of messaging such as SMS or E-mail. This service is not provided from the client, but from the server using gateway services that provide connectivity to some other network. Any user can register with any of these gateways by providing their access data to the new network as username and password, and communicate with users of the new network. This means that any XMPP client can be used to access any network for which there is a gateway, without the need to adapt the client or have direct access to the Internet.

#CODE STRUCTURE
It created a package called pxmmp.
It consists of two classes called pxmmp and Logic,

AQUI ME QUUEDE CON LAS ESTRUCTURA
