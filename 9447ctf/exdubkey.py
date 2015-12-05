from pwn import *

s = remote('dub-key-t8xd5pn6.9447.plumbing',9447)
sha_beginning = s.recv(12)
def hash(start, depth):
    for i in range(0,256):
        if depth == 0:
            ha = hashlib.sha1()
            ha.update(start+chr(i))
            sha1 = ha.digest()
            if sha1.endswith('\x00\x00\x00'):
                return start+chr(i)
        else:
            ret = hash(start+chr(i), depth - 1)
            if ret != None:
                return ret
    return None

answer = hash(sha_beginning, 6)
print answer
ha = hashlib.sha1()
ha.update(answer)
print answer
s.send(answer)

s.interactive()
