# goat on a rope
# my solution
from math import sqrt
x, y, x1, y1, x2, y2 = map(int, input().split())
rx, ry = (x1 + x2)/2, (y1 + y2)/2
h, w = abs(x1 - x2), abs(y1 - y2)
dx = max(abs(rx - x) - w/2, 0)
dy = max(abs(ry - y) - h/2, 0)
print("%.3f\n" % (round(float(sqrt(dx**2 + dy**2)), 3)))

# Tia's solution (not in the team, but thought it was neat)
# x, y, x1, y1, x2, y2 = map(int, input().split())
# dx = min(abs(x - x1), abs(x - x2))
# dy = min(abs(y - y1), abs(y - y2))
# print(float(max(dx, dy)))
