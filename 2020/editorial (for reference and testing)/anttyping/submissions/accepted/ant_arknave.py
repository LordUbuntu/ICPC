#!python3
import itertools

def main():
    s = list(map(int, input()))

    count = [[0 for _ in range(9)] for _ in range(9)]
    for x, y in zip(s, s[1:]):
        count[x-1][y-1] += 1

    def score(p):
        ans = p[s[0]-1]
        for x in range(9):
            for y in range(9):
                ans += abs(p[x] - p[y]) * count[x][y]

        return ans

    ans = min(score(p) for p in itertools.permutations(range(9)))

    # add in typing each character
    ans += len(s)

    print(ans)


if __name__ == "__main__":
    main()
