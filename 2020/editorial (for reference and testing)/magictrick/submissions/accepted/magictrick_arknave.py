#!python3
from collections import Counter


def main():
    c = Counter(input())
    unique = all(v == 1 for _, v in c.items())

    print(1 if unique else 0)


if __name__ == "__main__":
    main()
