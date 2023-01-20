#!python3]

# read number of students and number of questions
n, k = map(int, input().split())

# read student answers to each questions
# convert each student's answers to a binary number - T=1, F=0
student_tests = [0] * n;
for i in range(n):
    # read answer string
    answers = input()

    # convert to binary
    for j in range(k):
        if answers[j] == 'T':
            student_tests[i] |= (1 << j)

# try all possible answer keys, and compute lowest score for each
# track highest possible low score - ie, find the best manipulated answer key
highest_low = -1
for i in range(2**k):
    lowest_score = k
    # compute each student's score based on this key using binary operations
    for j in range(n):
        student_correct = k - bin(i ^ student_tests[j]).count("1")
        if student_correct < lowest_score:
            lowest_score = student_correct

    # if this key's low score is higher than previous, save this as best
    if lowest_score > highest_low:
        highest_low = lowest_score

# print highest possible low score
print(highest_low)