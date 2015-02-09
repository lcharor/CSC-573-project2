ForRunning this project:
A makefile is given which will compile and create the object file for given java files.

Command to run server:
server class is FTP_Server.
server will start with giving command in standard format:
Simple_ftp_server port# file-name p
port has to be 7735 which is well known port on which server is running.Project will not run if server runs on any other port than 7735.Now on second parameter file name to which server will save the received file is needed with the file name extension.Last parameter is needed with value of probability(should be between 0 and 1) so that server can drop random packets depending upon the value of probability.

commands to run client:
client class:FTP_Client
<ftp> <serveraddress> <serverport> <filename> <N> <MSS>
in this format command line input is required.
serveraddress is localhost as we tried our program on single machine.Server port is 7735. Filename is the file name client wants to transfer to server.N is window size and MSS is the maximum data size to be transferred in one time.