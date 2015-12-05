from pwn import *
import string

p = remote('randBox-iw8w3ae3.9447.plumbing',9447)

def round1():
    print p.readline()
    ans = p.readline()
    ans = ans.split("'")[1]
    print "ans",ans
    print p.readline()

    p.sendline("0123456789abcdef"*2)

    mapp = p.readline()
    print mapp
    print int(mapp[0],16)

    guess = ""
    for i in ans :
        guess += hex((int(i,16)-int(mapp[0],16))%16)[2:]
    print guess
    p.sendline(guess)
    print p.readline()
    print p.readline()

def round2():
    print p.readline()
    ans = p.readline()
    ans = ans.split("'")[1]
    print "ans",ans
    print p.readline()
    p.sendline("0123456789abcdef"+"fedcba9876543210")
    ret = p.readline()
    print ret
    if int(ret[0],16) < int(ret[1],16):
        mapp = -int(ret[0],16)
    else:
        mapp = -16-16+int(ret[0],16)+1
    print mapp
    print ans[mapp:]+ans[:mapp]
    p.sendline(ans[mapp:]+ans[:mapp])
    print p.readline()
    print p.readline()

def round3():
    print p.readline()
    ans = p.readline()
    ans = ans.split("'")[1]
    print "ans",ans
    print p.readline()

    p.sendline("0123456789abcdef"*2)

    mapp = p.readline()
    print "map",mapp
    n = 0
    maps = []
    for x in mapp[:-1]:
        maps += [int(x,16)]
    print maps
    guess = ""
    for i in ans :
        guess += hex(maps[int(i,16)])[2:]
    print guess
    p.sendline(guess)
    print p.readline()
    print p.readline()

def round4():
    print p.readline()
    ans = p.readline()
    ans = ans.split("'")[1]
    print "ans",ans
    print p.readline()

    p.sendline("0123456789abcdef"*2)

    mapp = p.readline()
    print "map",mapp
    n = 0
    maps = {}
    for x in mapp[:16]:
        maps[int(x,16)] = n
        n +=1
    print maps
    guess = ""
    for i in ans :
        guess += hex(maps[int(i,16)])[2:]
    print guess
    p.sendline(guess)
    print p.readline()
    print p.readline()

round1()
round2()
round3()
round4()
round4()
p.interactive()
