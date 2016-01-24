from pwn import *
from socket import gethostbyname
from base64 import b64decode
from subprocess import call
import gzip

def connect(s):
    port,host = 0,""
    for item in s.split():
        if(item.isdigit()):
            port = int(item)
        else:
            try:
                gethostbyname(item)
                host = item
            except:
                continue
    return remote(host,port)

def getInfo(p):
    print p.recvuntil('wait...\n')

def getBin(p):
    b64binary = p.recvuntil('hurry up!\n').split()[0]
    print b64binary
    zipbinary = b64decode(b64binary)
    f = open('aegb64.elf','w')
    f.write(b64binary)
    f.close()
    f = open('aegzip.elf','w')
    f.write(zipbinary)
    f.close()
    call("gunzip -c aegzip.elf > aegraw.elf",shell=True)
    f = open('aegraw.elf','rb')
    rawbinary = f.read()
    f.close()
    return rawbinary
    

p = connect("nc pwnable.kr 9005")

getInfo(p)

print getBin(p)

p.interactive()
