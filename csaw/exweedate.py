from __future__ import absolute_import, division, unicode_literals

import os
import hashlib
import base58
import string

def gen_sha_value(data):
    """ Gets the Base58-encoded SHA value of the given data.

    :param data:
    :return:
    """
    shavalue = hashlib.sha256()
    shavalue.update(data)
    return (shavalue.digest()).encode('hex')

print gen_sha_value("Donald Trump6")
exit()
your_list = 'abcdefghijklmnopqrstuvwxyz1234567890' + string.ascii_uppercase
complete_list = []
for current in xrange(10):
    a = [i for i in your_list]
    for y in xrange(current):
        a = ''.join([x+i for i in your_list for x in a])
        if gen_sha_value("Donald Trump"+a) == "c01362ce5290f9c55cdbc6faab5f10fe89ea32349c5838547e6e780cdcf427f0":
             print a
             exit()
