from matplotlib import style
import matplotlib.pyplot as plot

file = open(".\\src\\plot.txt", "r")

max_ = file.readline().split(" ")
min_ = file.readline().split(" ")
avg_ = file.readline().split(" ")

# style.use('dark_background')
plot.plot(max_)
plot.plot(min_)
plot.plot(avg_)
plot.show()
