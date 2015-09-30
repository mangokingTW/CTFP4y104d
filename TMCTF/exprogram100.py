from urllib2 import *
import multiprocessing as mp



a = urlopen("http://ctfquest.trendmicro.co.jp:43210/click_on_the_different_color").read()

url="http://ctfquest.trendmicro.co.jp:43210"+a.split("'")[1][:-3]
next=""
def getnext(web):
	return web.split("'")[1][:-3]
def req(x,y,output):
	a = urlopen(url+"?x="+str(x*20+10)+"&y="+str(y*20+10)).read()
	print x,y,a
	output.put([a,x*20+10,y*20+10])
	return None
AAAA = 0
while True:
	output = mp.Queue()
	processes = [mp.Process(target=req, args=(x,y, output)) for x in range(20) for y in range(20)]

	for p in processes:
	    p.start()

	# Exit the completed processes
	for p in processes:
	    p.join()

	flag = 0
	# Get process results from the output queue
	for p in processes:
		o =  output.get()
		t = o[0]
		if "Wrong" not in t:
			flag = 1
			print t
			url = "http://ctfquest.trendmicro.co.jp:43210"+getnext(t)
			AAAA = AAAA + 1
			print AAAA,url,o[1],o[2]
			break;
	if flag == 0:
		exit()
