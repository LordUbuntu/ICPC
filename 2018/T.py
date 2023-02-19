# goat on a rope
# Tia's solution (not in the team, but thought it was neat)
x, y, x1, y1, x2, y2 = map(int, input().split())
dx = min(abs(x - x1), abs(x - x2))
dy = min(abs(y - y1), abs(y - y2))
print(float(max(dx, dy)))
