# Solved by: Yaacov, Finian, and Jacobus
# Programmed by: Jacobus
# Problem: Rating Problems
# Solution:
#   Given the number of known judges and their scores, we can directly
#   calculate the max and min score by collecting the sum of the current
#   scores, and then calculating the max and min with the remaining
#   judges.

n, k = map(int, input().split())
a = n - k  # find remaining judges
total = sum([int(input()) for _ in range(k)])  # total of current votes
min = ((a * -3) + sum) / n  # min = (-3a + sum) / n
max = ((a * 3) + sum) / n  # max = (3a + sum) / n
print(min, max)
