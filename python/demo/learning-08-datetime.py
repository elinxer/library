# date & time
import time  # reference time module

now_time = time.time()  # get server now time stamp
print(now_time)
# format time stamp to str [xxxx-xx-xx xx:xx:xx]
print(time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()))
