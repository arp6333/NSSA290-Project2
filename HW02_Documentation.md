# Homework 2 - Server Programming Documentation
** Ellie Parobek, Winston Chang, Isabella Sturm **

# About the Program
## How it works
First a server connection is established using either TCP or UDP. The port number is entered by the user and the IP Address and hostname is echoed back.

When clients connect to the server, they enter TCP or UDP for the connection type, the hostname or the IP Address of the server and the port. 

Once this information is given, a connection is established and messages can be sent via the specified connection type.

### TCP
In Java, in order to start a server running TCP, you must create a ServerSocket. ServerSockerts pass messages using a Buffered Reader to read in message and Print Writer to write out message, which uses characters or character arrays to pass data.


### UDP
Unlike TCP, UDP in Java can be used by connecting to a DatagramSocket instead of a ServerSocket. A Datagram Socket sends data across the server to and from clients as bytes/ byte arrays which are then put into a Datagram Packet to send as opposed to TCP which can send characters/ character arrays.

