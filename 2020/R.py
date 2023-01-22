# Solved by Jacobus, Yaacov, and Finian
# Programmed by Jacobus
# No Thanks!

n = int(input())
nums = sorted([int(n) for n in input().split()])
sum = nums[0]
for i in range(1, n):
    if nums[i - 1] != nums[i] - 1:
        sum += nums[i]
print(sum)
