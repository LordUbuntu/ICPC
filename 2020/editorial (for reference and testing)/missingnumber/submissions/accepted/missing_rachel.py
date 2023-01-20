#!python3

# read input
n = int(input())
number_string = input()

# step through the digit-string to locate missing number
target = 1
curr = 0
missing = n
for dig in number_string:
    # add this digit to the current number
    curr = curr * 10 + int(dig)

    # if we created the target number, start a new one
    if curr == target:
        target += 1
        curr = 0

    # if the number we created is larger than the next expected number,
    # we skipped target - and found the missing number
    elif curr > target:
        missing = target
        break

print(missing)