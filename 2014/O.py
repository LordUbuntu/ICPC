# Jacobus Burger (2022)
# diamonds problem, 2014 ICPC D2

def solution(diamonds):
    seq_count = [1 for _ in range(len(diamonds))]  # init all to 1
    for i in range(len(diamonds) - 1, -1, -1):  # iterating backwards
        for j in range(i + 1, len(diamonds)):  # foreach elem after i
            if diamonds[i] > diamonds[j]:  # if the sequence condition is met
                # set seq len of ith to max of ith or 1 + jth
                seq_count[i] = max(seq_count[i], 1 + seq_count[j])
    return max(seq_count)  # return longest increasing subsequence
