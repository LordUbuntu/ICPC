#!python3
def main():
    n, k = map(int, input().split())
    ratings = [int(input()) for _ in range(k)]
    total = sum(ratings)
    lo = (total + (n - k) * -3) / n
    hi = (total + (n - k) * +3) / n
    print(lo, hi)


if __name__ == "__main__":
    main()
